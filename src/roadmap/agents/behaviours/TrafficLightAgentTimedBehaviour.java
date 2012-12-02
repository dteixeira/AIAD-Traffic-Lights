package roadmap.agents.behaviours;

import java.io.IOException;

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

public class TrafficLightAgentTimedBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 1L;
	private static final int TICKS_PER_CYCLE = 3;
	private int ticksAfterLast;
	
	public TrafficLightAgentTimedBehaviour(Agent a) {
		super(a);
		ticksAfterLast = 0;
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
			++ticksAfterLast;
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		
		// Build response to world
		WorldUpdatePacket reply = new WorldUpdatePacket();
		for(ConnectionInfo info : packet.getInfo()) {
			UpdateInfo uInfo = new UpdateInfo();
			uInfo.setRoadId(info.getRoadId());
			uInfo.setNextRoadId(info.getNextRoadId());
			if(ticksAfterLast >= TICKS_PER_CYCLE) {
				uInfo.setCurrentState(!info.isCurrentState());
			} else
				uInfo.setCurrentState(info.isCurrentState());
			reply.addUpdateInfo(uInfo);
		}
		if(ticksAfterLast >= TICKS_PER_CYCLE)
			ticksAfterLast = 0;
		
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
