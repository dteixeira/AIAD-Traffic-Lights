package roadmap.agents.behaviours;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import roadmap.Orientation;
import roadmap.agents.Vocabulary;
import roadmap.agents.packets.ConnectionInfo;
import roadmap.agents.packets.TrafficLightInfoPacket;
import roadmap.agents.packets.UpdateInfo;
import roadmap.agents.packets.WorldUpdatePacket;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class TrafficLightAgentCommunicationBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 1L;
	private static final int MAX_CYCLES_WITHOUT_CHANGE = 3;
	private static final int MIN_CYCLES_WITHOUT_CHANGE = 0;
	private int cycleCount = 0;
	
	public TrafficLightAgentCommunicationBehaviour(Agent a) {
		super(a);
	}

	@Override
	public void action() {
		// Receive information update from world
		HashMap<Integer, ConnectionInfo> queryResponses = new HashMap<Integer, ConnectionInfo>();
		boolean worldMsg = false;
		TrafficLightInfoPacket packet = null;
		while(!worldMsg) {
			ACLMessage msg = myAgent.blockingReceive();
			while(msg.getPerformative() != ACLMessage.INFORM_REF)
				msg = myAgent.blockingReceive();
			try {
				packet = (TrafficLightInfoPacket) msg.getContentObject();
				worldMsg = true;
			} catch(Exception e) {
				try {
					ConnectionInfo conn = (ConnectionInfo) msg.getContentObject();
					queryResponses.put(conn.getRoadId(), conn);
				} catch(Exception e1) {
					e.printStackTrace();
				}
			}
		}
		
		// Inform all dependent agents
		ArrayList<ConnectionInfo> info = packet.getInfo();
		for(ConnectionInfo conn : info) {
			// Send register inform to world agent
			ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM_REF);
			try {
				msg2.setContentObject(conn);
				msg2.addReceiver(conn.getAgentAID());
				myAgent.send(msg2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Receive responses
		boolean receiving = false;
		for(ConnectionInfo conn2 : packet.getInfo()) {
			if(queryResponses.get(conn2.getNextRoadId()) == null) {
				receiving = true;
				break;
			}
		}
		while(receiving) {
			ACLMessage msg3 = myAgent.blockingReceive();
			if(msg3.getPerformative() == ACLMessage.INFORM_REF) {
				try {
					ConnectionInfo conn = (ConnectionInfo) msg3.getContentObject();
					queryResponses.put(conn.getRoadId(), conn);
					receiving = false;
					for(ConnectionInfo conn2 : packet.getInfo()) {
						if(queryResponses.get(conn2.getNextRoadId()) == null) {
							receiving = true;
							break;
						}
					}
				} catch (UnreadableException e) {
					e.printStackTrace();
				}
			}
		}
		
		int verticalSend = 0;
		int horizontalSend = 0;
		HashMap<Integer, ConnectionInfo> connections = new HashMap<Integer, ConnectionInfo>();
		for(ConnectionInfo conn : packet.getInfo()) {
			int localMin = Math.min(conn.getMaxCarSpeed(), conn.getCarCount());
			connections.put(conn.getNextRoadId(), conn);
			if(conn.getRoadOrientation() == Orientation.UP || conn.getRoadOrientation() == Orientation.DOWN)
				verticalSend += localMin;
			else
				horizontalSend += localMin;
		}
		for(Entry<Integer, ConnectionInfo> entry : queryResponses.entrySet()) {
			Orientation ori = connections.get(entry.getValue().getRoadId()).getRoadOrientation();
			if(ori == Orientation.UP || ori == Orientation.DOWN)
				verticalSend -= (entry.getValue().getRoadCapacity() - entry.getValue().getCarCount());
			else
				horizontalSend -= (entry.getValue().getRoadCapacity() - entry.getValue().getCarCount());
		}
		boolean openVertical;
		if(verticalSend > horizontalSend)
			openVertical = true;
		else
			openVertical = false;
		
		// Build response to world
		++cycleCount;
		boolean stateChanged = true;
		WorldUpdatePacket reply = new WorldUpdatePacket();
		for(ConnectionInfo conn : packet.getInfo()) {
			UpdateInfo uInfo = new UpdateInfo();
			uInfo.setRoadId(conn.getRoadId());
			uInfo.setNextRoadId(conn.getNextRoadId());
			if(cycleCount >= MAX_CYCLES_WITHOUT_CHANGE) {
				uInfo.setCurrentState(!conn.isCurrentState());
			} else if(cycleCount <= MIN_CYCLES_WITHOUT_CHANGE) {
				uInfo.setCurrentState(conn.isCurrentState());
			} else {
				if(openVertical && (conn.getRoadOrientation() == Orientation.UP || conn.getRoadOrientation() == Orientation.DOWN))
					uInfo.setCurrentState(true);
				else if(!openVertical && (conn.getRoadOrientation() == Orientation.LEFT || conn.getRoadOrientation() == Orientation.RIGHT))
					uInfo.setCurrentState(true);
				else
					uInfo.setCurrentState(false);
			}
			if(uInfo.isCurrentState() == conn.isCurrentState())
				stateChanged = false;
			reply.addUpdateInfo(uInfo);
		}
		if(stateChanged)
			cycleCount = 0;
		
		// Reply to world
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setType(Vocabulary.WORLD_AGENT_TYPE);
		template.addServices(sd1);
		DFAgentDescription[] result = {};
		try {
			result = DFService.search(myAgent, template);
			while(result.length <= 0) {
				result = DFService.search(myAgent, template);
			}
			
			// Clear message queue before responding
			while (myAgent.receive() != null) {
			}
			
			// Send register inform to world agent
			ACLMessage replyMsg = new ACLMessage(ACLMessage.INFORM_REF);
			replyMsg.setContentObject(reply);
			replyMsg.addReceiver(result[0].getName());
			myAgent.send(replyMsg);
		} catch (FIPAException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		return false;
	}

}
