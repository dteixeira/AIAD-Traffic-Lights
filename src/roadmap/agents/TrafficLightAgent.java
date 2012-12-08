package roadmap.agents;

import roadmap.agents.behaviours.TrafficLightAgentCommunicationBehaviour;
import roadmap.agents.behaviours.TrafficLightAgentReduceLoadBehaviour;
import roadmap.agents.behaviours.TrafficLightAgentTimedBehaviour;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class TrafficLightAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private String agentType = "";
	private static final String TIMED_BEHAVIOUR = "timed";
	private static final String REDUCE_LOAD_BEHAVIOUR = "reduce-load";
	private static final String COMMUNICATION_BEHAVIOUR = "comm";

	protected void setup() {
		boolean correctArguments = parseArguments();
		if(!correctArguments) {
			doDelete();
			return;
		}
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType(Vocabulary.TRAFFIC_LIGHT_AGENT_TYPE);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		// Register this agent with the world agent
		registerWithWorld();

		// Set agent behavior
		SimpleBehaviour b = null;
		if(agentType.equals(TIMED_BEHAVIOUR))
			b = new TrafficLightAgentTimedBehaviour(this);
		else if(agentType.equals(REDUCE_LOAD_BEHAVIOUR))
			b = new TrafficLightAgentReduceLoadBehaviour(this);
		else if(agentType.equals(COMMUNICATION_BEHAVIOUR))
			b = new TrafficLightAgentCommunicationBehaviour(this);
		addBehaviour(b);
	}
	
	private boolean parseArguments() {
		Object[] args = getArguments();
		
		// Check for agent type
		try {
			agentType = (String) args[0];
			agentType = agentType.toLowerCase();
			if(!agentType.equals(REDUCE_LOAD_BEHAVIOUR) && !agentType.equals(TIMED_BEHAVIOUR) && !agentType.equals(COMMUNICATION_BEHAVIOUR)) {
				System.out.println("No valid agent type defined.");
				return false;
			}
		} catch(Exception e) {
			System.out.println("No valid agent type defined.");
			return false;
		}
		return true;
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	private void registerWithWorld() {
		// Search for the agent
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setType(Vocabulary.WORLD_AGENT_TYPE);
		template.addServices(sd1);
		DFAgentDescription[] result = {};
		try {
			result = DFService.search(this, template);
			while(result.length <= 0) {
				result = DFService.search(this, template);
			}
			
			// Send register inform to world agent
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setContent(Vocabulary.INFORM_REGISTER);
			msg.addReceiver(result[0].getName());
			send(msg);
			
			// Receive confirmation
			msg = blockingReceive();
			while(msg.getPerformative() != ACLMessage.INFORM || !msg.getContent().equals(Vocabulary.INFORM_REGISTER_REPLY))
				msg = blockingReceive();
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

}
