package roadmap.agents.behaviours;

import java.util.HashMap;
import java.util.Map.Entry;
import roadmap.Intersection;
import roadmap.agents.Vocabulary;
import roadmap.engine.SimulationEngine;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class WorldAgentBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Intersection> agentMapping = null;
	
	public WorldAgentBehaviour(Agent agent) {
		super(agent);
	}

	@Override
	public void action() {
		// Wait for all agents to be set
		if(agentMapping == null) {
			registerIntersections();
		}		
		
		// TODO REMOVE
		// TODO WORLD EVOLUTION
		SimulationEngine.getInstance().evolveWorld();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		return !SimulationEngine.getInstance().getGui().isDisplayable();
	}
	
	private void registerIntersections() {
		agentMapping = new HashMap<String, Intersection>();
		SimulationEngine simulationEngine = SimulationEngine.getInstance();
		
		// Set waiting message
		simulationEngine.getGui().setStatusMessage("Waiting for all intersection agents to register...");
		for(Entry<Integer, Intersection> entry : simulationEngine.getWorld().getIntersections().entrySet()) {
			
			// Register new intersection
			ACLMessage msg = myAgent.blockingReceive();
			while(msg.getPerformative() != ACLMessage.INFORM 
					|| msg.getContent() == null
					|| !msg.getContent().equals(Vocabulary.INFORM_REGISTER)
					|| agentMapping.get(msg.getSender().getName()) != null)
				msg = myAgent.blockingReceive();
			agentMapping.put(msg.getSender().getName(), entry.getValue());
			
			// Sends confirmation
			ACLMessage reply = msg.createReply();
			reply.setContent(Vocabulary.INFORM_REGISTER_REPLY);
			myAgent.send(reply);
		}
		simulationEngine.getGui().setStatusMessage("Ready");
	}
	
}