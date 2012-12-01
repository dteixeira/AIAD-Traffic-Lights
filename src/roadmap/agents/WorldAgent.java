package roadmap.agents;

import roadmap.agents.behaviours.WorldAgentBehaviour;
import roadmap.engine.SimulationEngine;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class WorldAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private SimulationEngine simulationEngine = null;
	private boolean manualSimulation = false;
	
	@Override
	protected void setup() {
		
		// Collect arguments
		boolean correctArguments = parseArguments();
		if(!correctArguments) {
			doDelete();
			return;
		}
		
		// Registers itself
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("World Agent");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
			doDelete();
			return;
		}
		
		// Makes the GUI visible
		simulationEngine.setVisible(true);
		
		// Setup behavior
		Behaviour behaviour = new WorldAgentBehaviour(this);
		addBehaviour(behaviour);
	}
	
	@Override
	protected void takeDown() {
		try {
			if(simulationEngine != null)
				simulationEngine.getGui().dispose();
			DFService.deregister(this);
		} catch (FIPAException e) {
			System.out.println(e);
		}
	}
	
	private boolean parseArguments() {
		Object[] args = getArguments();
		String filename = "";
		
		// Check for filename
		try {
			filename = (String) args[0];
		} catch(Exception e) {
			System.out.println("The name of the file containing the map must be provided.");
			return false;
		}
		
		// Check for simulation type
		try {
			manualSimulation = Boolean.parseBoolean((String) args[1]);
		} catch(Exception e) {
			System.out.println("Could not determine type of simulation. Defaulting to \"agent based simulation\"");
		}
		
		// Initialize simulation engine
		try {
			simulationEngine = SimulationEngine.initInstance(filename);
		} catch(Exception e) {
			System.out.println("The file did not represent a valid world.");
			return false;
		}
		
		return true;
	}
}
