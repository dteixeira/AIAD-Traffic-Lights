package roadmap.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

import roadmap.Car;
import roadmap.Connection;
import roadmap.Road;
import roadmap.builder.RoadMapBuilder;
import roadmap.gui.MainFrame;
import roadmap.gui.StatsDialog;
import roadmap.parser.RoadMapInfo;
import roadmap.parser.RoadMapParser;

public class SimulationEngine {
	
	private RoadMapInfo world;
	private MainFrame gui;
	private StatsDialog stats;
	private Random randomGenerator;
	private static SimulationEngine instance = null;
	private static boolean pauseRequest = false;
	private static boolean paused = false;
	private static boolean manualSimulation = false;
	
	public static boolean isPauseRequest() {
		return pauseRequest;
	}

	public static void setPauseRequest(boolean pauseRequest) {
		SimulationEngine.pauseRequest = pauseRequest;
	}

	public static boolean isPaused() {
		return paused;
	}

	public static void setPaused(boolean paused) {
		SimulationEngine.paused = paused;
	}

	public static boolean isManualSimulation() {
		return manualSimulation;
	}

	public static void setManualSimulation(boolean manualSimulation) {
		SimulationEngine.manualSimulation = manualSimulation;
	}

	private SimulationEngine() {
	}
	
	public static SimulationEngine initInstance(String filename) {
		if(instance != null)
			return instance;
		
		// Builds map information
		instance = new SimulationEngine();
		instance.world = RoadMapParser.parseRoadMapXML(filename);
		RoadMapBuilder.buildAdvancedInfo(instance.world);
		instance.randomGenerator = new Random();
	
		// Build gui
		instance.gui = new MainFrame(instance.world);
		
		// Build statistics dialog
		instance.stats = new StatsDialog(instance.gui, "Statistics", instance.world);
		
		return instance;
	}
	
	public static SimulationEngine getInstance() {
		return instance;
	}

	public RoadMapInfo getWorld() {
		return world;
	}
	
	public MainFrame getGui() {
		return gui;
	}
	
	public StatsDialog getStats() {
		return stats;
	}
	
	public void setVisible(boolean vs) {
		gui.setVisible(vs);
		stats.setVisible(vs);
	}

	public void evolveWorld() {
		moveCars();
		retractCars();
		stats.updateStats();
		cleanup();
	}

	private void cleanup() {
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			for(Car car : road.getCarQueue()) {
				if(car.getCurrentRoadId() != road.getRoadId()) {
					car.setCurrentRoadId(road.getRoadId());
					car.setNextRoadId(-1);
				}
			}
		}
		gui.redrawMap();
	}

	private void retractCars() {
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			
			// Moves waiting cars into road
			for(int i = road.getCurrentCarCount(); i < road.getMaxCarCapacity() && road.getCarWaitingQueue().size() > 0; ++i) {
				road.getCarQueue().addLast(road.getCarWaitingQueue().pop());
			}
			
			// Return remaining cars home
			LinkedList<Car> remaining = road.getCarWaitingQueue();
			while(remaining.size() != 0) {
				returnHome(remaining.pop());
			}
		}
	}
	
	private void returnHome(Car car) {
		Road road = world.getRoads().get(car.getCurrentRoadId());
		road.getCarQueue().addFirst(car);
		while(road.getCurrentCarCount() > road.getMaxCarCapacity()){
			returnHome(road.getCarQueue().removeLast());
		}
	}

	private void moveCars() {
		for(Entry<Integer, Road> entry : world.getRoads().entrySet()) {
			Road road = entry.getValue();
			
			// Builds traffic movement permission map
			HashMap<Integer, Boolean> trafficLights = new HashMap<Integer, Boolean>();
			HashMap<Integer, Integer> movedCount = new HashMap<Integer, Integer>();
			ArrayList<Integer> possibleNext = new ArrayList<Integer>();
			for(Connection connect : road.getConnections()) {
				trafficLights.put(connect.getTrafficLight().getDestinationRoad().getRoadId(), connect.getTrafficLight().isTrafficAllowed());
				movedCount.put(connect.getTrafficLight().getDestinationRoad().getRoadId(), 0);
				possibleNext.add(connect.getTrafficLight().getDestinationRoad().getRoadId());
			}
			
			for(int i = 0; i < road.getCarQueue().size(); ++i) {
				// Gives car a direction if it doesn't have one
				Car car = road.getCarQueue().get(i);
				if(car.getNextRoadId() < 0)
					car.setNextRoadId(possibleNext.get(randomGenerator.nextInt(possibleNext.size())));				
				
				if(trafficLights.get(car.getNextRoadId()) && movedCount.get(car.getNextRoadId()) < road.getCarSpeed()) {
					world.getRoads().get(car.getNextRoadId()).getCarWaitingQueue().add(car);
					movedCount.put(car.getNextRoadId(), movedCount.get(car.getNextRoadId()) + 1);
					road.getCarQueue().remove(i);
					--i;
				}
			}
		}
	}
}
