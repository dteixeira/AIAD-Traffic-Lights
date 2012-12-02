package roadmap.agents.behaviours;

import java.io.IOException;

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

public class TrafficLightAgentReduceLoadBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 1L;
	
	public TrafficLightAgentReduceLoadBehaviour(Agent a) {
		super(a);
	}

	@Override
	public void action() {
		// Receive information update from world
		ACLMessage msg = myAgent.blockingReceive();
		while(msg.getPerformative() != ACLMessage.INFORM_REF)
			msg = myAgent.blockingReceive();
		TrafficLightInfoPacket packet = null;
		try {
			packet = (TrafficLightInfoPacket) msg.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		
		// Build response to world
		WorldUpdatePacket reply = new WorldUpdatePacket();
		ConnectionInfo connect = new ConnectionInfo();
		connect.setCarCount(-1);
		// Find greater car volume
		for(ConnectionInfo info : packet.getInfo()) {
			if(info.getCarCount() > connect.getCarCount())
				connect = info;
		}
		// Change traffic lights accordingly
		for(ConnectionInfo info : packet.getInfo()) {
			UpdateInfo uInfo = new UpdateInfo();
			uInfo.setRoadId(info.getRoadId());
			uInfo.setNextRoadId(info.getNextRoadId());
			if(connect.getRoadOrientation() == Orientation.UP || connect.getRoadOrientation() == Orientation.DOWN) {
				if(info.getRoadOrientation() == Orientation.UP || info.getRoadOrientation() == Orientation.DOWN)
					uInfo.setCurrentState(true);
				else
					uInfo.setCurrentState(false);
			} else if(connect.getRoadOrientation() == Orientation.LEFT || connect.getRoadOrientation() == Orientation.RIGHT) {
				if(info.getRoadOrientation() == Orientation.LEFT || info.getRoadOrientation() == Orientation.RIGHT)
					uInfo.setCurrentState(true);
				else
					uInfo.setCurrentState(false);
			}
			reply.addUpdateInfo(uInfo);
		}
		
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
