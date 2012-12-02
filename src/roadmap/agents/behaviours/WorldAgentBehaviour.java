package roadmap.agents.behaviours;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import roadmap.Connection;
import roadmap.Intersection;
import roadmap.agents.Vocabulary;
import roadmap.agents.packets.TrafficLightInfoPacket;
import roadmap.agents.packets.UpdateInfo;
import roadmap.agents.packets.WorldUpdatePacket;
import roadmap.engine.SimulationEngine;
import roadmap.parser.RoadMapInfo;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class WorldAgentBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, String> agentMapping = null;
	private static final int TICK_DURATION = 2000;
	
	public WorldAgentBehaviour(Agent agent) {
		super(agent);
	}

	@Override
	public void action() {
		
		// Ignores agent processing if the simulation is set to manual
		if(SimulationEngine.isManualSimulation())
			return;
		
		// Check for pause requests
		if(SimulationEngine.isPaused())
			return;
		else if(SimulationEngine.isPauseRequest()) {
			SimulationEngine.getInstance().getGui().setStatusMessage("Paused");
			SimulationEngine.setPaused(true);
			return;
		}
		
		// Wait for all agents to be set
		if(agentMapping == null) {
			registerIntersections();
		}
		
		RoadMapInfo world = SimulationEngine.getInstance().getWorld();
		SimulationEngine.getInstance().getGui().setStatusMessage("Sending information to agents...");
		for(Entry<Integer, Intersection> entry : world.getIntersections().entrySet()) {
			Intersection intersection = entry.getValue();
			TrafficLightInfoPacket packet = new TrafficLightInfoPacket(intersection, agentMapping);
			
			// Search for the agent
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd1 = new ServiceDescription();
			sd1.setType(agentMapping.get(intersection.getIntersectionId()));
			template.addServices(sd1);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM_REF);
				msg.setContentObject(packet);
				msg.addReceiver(result[0].getName());
				myAgent.send(msg);
			} catch (FIPAException | IOException e) {
				e.printStackTrace();
			}
		}
		SimulationEngine.getInstance().getGui().setStatusMessage("Waiting for response...");
		
		for(int i = 0; i < agentMapping.size(); ++i) {
			ACLMessage response = myAgent.blockingReceive();
			while(response.getPerformative() != ACLMessage.INFORM_REF)
				response = myAgent.blockingReceive();
			WorldUpdatePacket respPacket = null;
			try {
				respPacket = (WorldUpdatePacket) response.getContentObject();
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
			
			// Update world info
			for(UpdateInfo info : respPacket.getInfo()) {
				for(Connection connect : world.getRoads().get(info.getRoadId()).getConnections()) {
					if(connect.getTrafficLight().getDestinationRoad().getRoadId() == info.getNextRoadId()) {
						connect.getTrafficLight().setTrafficAllowed(info.isCurrentState());
					}
				}
			}
		}
		
		// World evolution
		SimulationEngine.getInstance().getGui().setStatusMessage("Evolving world...");
		SimulationEngine.getInstance().evolveWorld();
		
		// Wait for next tick
		SimulationEngine.getInstance().getGui().setStatusMessage("Ready");
		try {
			Thread.sleep(TICK_DURATION);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		return !SimulationEngine.getInstance().getGui().isDisplayable();
	}
	
	private void registerIntersections() {
		agentMapping = new HashMap<Integer, String>();
		SimulationEngine simulationEngine = SimulationEngine.getInstance();
		
		// Set waiting message
		simulationEngine.getGui().setStatusMessage("Waiting for all intersection agents to register...");
		for(Entry<Integer, Intersection> entry : simulationEngine.getWorld().getIntersections().entrySet()) {
			
			// Register new intersection
			ACLMessage msg = myAgent.blockingReceive();
			while(msg.getPerformative() != ACLMessage.INFORM 
					|| msg.getContent() == null
					|| !msg.getContent().equals(Vocabulary.INFORM_REGISTER)
					|| agentMapping.values().contains(msg.getSender().getName()))
				msg = myAgent.blockingReceive();
			agentMapping.put(entry.getValue().getIntersectionId(), msg.getSender().getName());
			
			// Sends confirmation
			ACLMessage reply = msg.createReply();
			reply.setContent(Vocabulary.INFORM_REGISTER_REPLY);
			myAgent.send(reply);
		}
		simulationEngine.getGui().setStatusMessage("Ready");
	}
	
}