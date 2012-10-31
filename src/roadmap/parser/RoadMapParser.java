package roadmap.parser;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import roadmap.Coordinates;
import roadmap.Intersection;
import roadmap.Road;
import roadmap.builder.RoadMapBuilder;

public class RoadMapParser {
	
	// XML tags
	static String ROOT_TAG 			= "roadmap";
	static String CONFIG_TAG		= "config";
	static String INTERSECTIONS_TAG = "intersections";
	static String ROADS_TAG			= "roads";
	static String MAP_TAG			= "map";
	static String DEFAULT_TAG		= "default";
	static String INTERSECTION_TAG	= "intersection";
	static String ROAD_TAG			= "road";
	
	// XML attributes
	static String ID_ATTR			= "id";
	static String HEIGHT_ATTR		= "height";
	static String WIDTH_ATTR		= "width";
	static String SPEED_ATTR		= "speed";
	static String CAPACITY_ATTR		= "capacity";
	static String CARS_ATTR			= "cars";
	static String X_ATTR			= "x";
	static String Y_ATTR			= "y";
	static String START_ATTR		= "start";
	static String FINISH_ATTR		= "finish";
	static String SINGLE_ATTR		= "single";

	public static RoadMapInfo parseRoadMapXML(String filename) {
		try {
			File xml = new File(filename);
			return parseRoadMapXML(xml);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static RoadMapInfo parseRoadMapXML(File file) {
		try {
			// Build DOM from XML file
			Document xml;
			xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			xml.getDocumentElement().normalize();
			
			// Extract map from DOM
			RoadMapInfo roadMapInfo = new RoadMapInfo();
			parseXMLConfig(xml, roadMapInfo);
			parseXMLIntersections(xml, roadMapInfo);
			parseXMLRoads(xml, roadMapInfo);
			return roadMapInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void parseXMLConfig(Document xml, RoadMapInfo roadMapInfo) {
		// Configuration node parsing
		NodeList config = xml.getElementsByTagName(CONFIG_TAG).item(0).getChildNodes();
		for(int index = 0; index < config.getLength(); ++index) {
			Node node = config.item(index);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = node.getNodeName();
				Element element = (Element) node;
				if(nodeName.equalsIgnoreCase(MAP_TAG)) {
					roadMapInfo.setMapHeight(Integer.parseInt(element.getAttribute(HEIGHT_ATTR)));
					roadMapInfo.setMapWidth(Integer.parseInt(element.getAttribute(WIDTH_ATTR)));
				} else if(nodeName.equalsIgnoreCase(DEFAULT_TAG)) {
					roadMapInfo.setDefaultRoadCapacity(Integer.parseInt(element.getAttribute(CAPACITY_ATTR)));
					roadMapInfo.setDefaultNumberCars(Integer.parseInt(element.getAttribute(CARS_ATTR)));
					roadMapInfo.setDefaultSpeed(Integer.parseInt(element.getAttribute(SPEED_ATTR)));
				}
			}
		}
	}
	
	private static void parseXMLRoads(Document xml, RoadMapInfo roadMapInfo) throws Exception {
		NodeList roads = xml.getElementsByTagName(ROAD_TAG);
		Intersection startIntersection, finishIntersection;
		String speed, cars, capacity;
		for(int index = 0; index < roads.getLength(); ++index) {
			Node node = roads.item(index);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element road = (Element) node;
				Road newRoad = new Road();
				
				// Try to get intersection references
				startIntersection = roadMapInfo.getIntersections().get(Integer.parseInt(road.getAttribute(START_ATTR)));
				finishIntersection = roadMapInfo.getIntersections().get(Integer.parseInt(road.getAttribute(FINISH_ATTR)));
				if(finishIntersection == null || startIntersection == null)
					throw new Exception();
				
				// Mandatory attributes
				//newRoad.setRoadId(Integer.parseInt(road.getAttribute(ID_ATTR)));
				newRoad.setStartIntersection(startIntersection);
				newRoad.setFinishIntersection(finishIntersection);
				newRoad.setSingleDirection(Boolean.parseBoolean(road.getAttribute(SINGLE_ATTR)));
				
				// Optional attributes
				speed = road.getAttribute(SPEED_ATTR);
				cars = road.getAttribute(CARS_ATTR);
				capacity = road.getAttribute(CAPACITY_ATTR);
				newRoad.setCarSpeed(speed.equals("") ? roadMapInfo.getDefaultSpeed() : Integer.parseInt(speed));
				newRoad.setCurrentCarCount(cars.equals("") ? roadMapInfo.getDefaultNumberCars() : Integer.parseInt(cars));
				newRoad.setMaxCarCapacity(capacity.equals("") ? roadMapInfo.getDefaultRoadCapacity() : Integer.parseInt(capacity));
				
				// Add new Road
				roadMapInfo.getRoads().add(newRoad);
				if(! newRoad.isSingleDirection())
					roadMapInfo.getRoads().add(Road.buildOppositeRoad(newRoad));
			}
		}
	}
	
	private static void parseXMLIntersections(Document xml, RoadMapInfo roadMapInfo) {
		NodeList intersections = xml.getElementsByTagName(INTERSECTION_TAG);
		for(int index = 0; index < intersections.getLength(); ++index) {
			Node node = intersections.item(index);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element intersection = (Element) node;
				Intersection newIntersection = new Intersection();
				Coordinates coordinates = new Coordinates();
				
				// Mandatory attributes
				newIntersection.setIntersectionId(Integer.parseInt(intersection.getAttribute(ID_ATTR)));
				coordinates.setxCoord(Integer.parseInt(intersection.getAttribute(X_ATTR)));
				coordinates.setyCoord(Integer.parseInt(intersection.getAttribute(Y_ATTR)));
				newIntersection.setCoordinates(coordinates);
				roadMapInfo.getIntersections().put(newIntersection.getIntersectionId(), newIntersection);
			}
		}
	}
	
	public static void main(String[] args) {
		RoadMapInfo map = RoadMapParser.parseRoadMapXML("mapWidth3.xml");
		/*System.out.println("CONFIG");
		System.out.println("  WIDTH: " + map.getMapWidth());
		System.out.println("  HEIGHT: " + map.getMapHeight());
		System.out.println("  CARS: " + map.getDefaultNumberCars());
		System.out.println("  SPEED: " + map.getDefaultSpeed());
		System.out.println("  CAPACITY: " + map.getDefaultRoadCapacity());
		System.out.println("INTERSECTIONS");
		for(Entry<Integer, Intersection> entry : map.getIntersections().entrySet()) {
			System.out.println("  INTERSECTION");
			System.out.println("    ID: " + entry.getValue().getIntersectionId());
			System.out.println("    X: " + entry.getValue().getCoordinates().getxCoord());
			System.out.println("    Y: " + entry.getValue().getCoordinates().getyCoord());
		}
		System.out.println("ROADS");
		for(Entry<Integer, Road> entry : map.getRoads().entrySet()) {
			System.out.println("  ROAD");
			System.out.println("    ID: " + entry.getValue().getRoadId());
			System.out.println("    SPEED: " + entry.getValue().getCarSpeed());
			System.out.println("    CARS: " + entry.getValue().getCurrentCarCount());
			System.out.println("    CAPACITY: " + entry.getValue().getMaxCarCapacity());
			System.out.println("    SINGLE: " + entry.getValue().isSingleDirection());
			System.out.println("    START_ID: " + entry.getValue().getStartIntersection().getIntersectionId());
			System.out.println("    FINISH_ID: " + entry.getValue().getFinishIntersection().getIntersectionId());
		}*/
		RoadMapBuilder.buildAdvancedInfo(map);
		/*for(Entry<Integer, Intersection> entry : map.getIntersections().entrySet()) {
			for(Connection connection : entry.getValue().getInboundConnections()) {
				System.out.println(connection.getConnectedRoad().getRoadId() + " | " + connection.getTrafficLight().getDestinationRoad().getRoadId());
			}
		}*/
		if(map.getRoads().get(0).equals(map.getIntersections().get(1).getInboundConnections().get(0).getConnectedRoad()))
			System.out.println("YAY");
	}
}
