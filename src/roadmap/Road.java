package roadmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import roadmap.engine.SimulationEngine;
import roadmap.gui.RoadConfigurationDialog;

public class Road extends PickableSurface {

	private int carSpeed;
	private LinkedList<Car> carQueue;
	private LinkedList<Car> carWaitingQueue;
	private Intersection finishIntersection;
	private int maxCarCapacity;
	private boolean singleDirection;
	private Intersection startIntersection;
	private Orientation roadOrientation;
	private ArrayList<Connection> connections;
	private int roadId;
	private int maxRegisteredCarCount;
	private double percentageMaxRegisteredCarCount;
	private double avgCarCount;
	private static int roadLastId = 0;
	private static final float FONT_ROTATION = (float) (- Math.PI / 2.0f);
	private static final DecimalFormat formatter = new DecimalFormat("#.##");

	public Road(){
		maxRegisteredCarCount = 0;
		percentageMaxRegisteredCarCount = 0.0;
		avgCarCount = 0.0;
		roadId = roadLastId++;
		carQueue = new LinkedList<Car>();
		carWaitingQueue = new LinkedList<Car>();
		connections = new ArrayList<Connection>();
	}
	
	public void updateStats(int cycles) {
		if(maxRegisteredCarCount < carQueue.size()) {
			maxRegisteredCarCount = carQueue.size();
			percentageMaxRegisteredCarCount = (double) maxRegisteredCarCount / (double) maxCarCapacity;
		}
		avgCarCount = (avgCarCount * (double) (cycles - 1) + (double) carQueue.size()) / (double) cycles;
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	public LinkedList<Car> getCarQueue() {
		return carQueue;
	}

	public void setCarQueue(LinkedList<Car> carQueue) {
		this.carQueue = carQueue;
	}

	public LinkedList<Car> getCarWaitingQueue() {
		return carWaitingQueue;
	}

	public void setCarWaitingQueue(LinkedList<Car> carWaitingQueue) {
		this.carWaitingQueue = carWaitingQueue;
	}

	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}

	public static void resetLastId() {
		roadLastId = 0;
	}
	
	public int getRoadId() {
		return roadId;
	}
	
	public Orientation getRoadOrientation() {
		return roadOrientation;
	}

	public void setRoadOrientation(Orientation roadOrientation) {
		this.roadOrientation = roadOrientation;
	}
	
	public static Road buildOppositeRoad(Road road) {
		Road newRoad = new Road();
		newRoad.setCarSpeed(road.getCarSpeed());
		newRoad.setCurrentCarCount(road.getCurrentCarCount());
		newRoad.setMaxCarCapacity(road.getMaxCarCapacity());
		newRoad.setFinishIntersection(road.getStartIntersection());
		newRoad.setStartIntersection(road.getFinishIntersection());
		newRoad.setSingleDirection(road.isSingleDirection());
		if(road.getRoadOrientation() == Orientation.UP) newRoad.setRoadOrientation(Orientation.DOWN);
		else if(road.getRoadOrientation() == Orientation.DOWN) newRoad.setRoadOrientation(Orientation.UP);
		else if(road.getRoadOrientation() == Orientation.RIGHT) newRoad.setRoadOrientation(Orientation.LEFT);
		else if(road.getRoadOrientation() == Orientation.LEFT) newRoad.setRoadOrientation(Orientation.RIGHT);
		return newRoad;
	}
	
	public void drawCarCount(Graphics graphics) {
		
		// Find center of polygon
		String carCount = "" + carQueue.size();
		FontMetrics metrics = graphics.getFontMetrics();
		Rectangle bounds = hitBox.getBounds();
		
		// Derive new font
		graphics.setColor(Color.white);
		Font font = graphics.getFont().deriveFont(18f);
		
		// Draw text in correct orientation
		if(roadOrientation == Orientation.LEFT || roadOrientation == Orientation.RIGHT) {
			int centerX = (int) (bounds.getCenterX() - metrics.stringWidth(carCount) / 2);
			int centerY = (int) (bounds.getCenterY() + metrics.getHeight() / 4);
			graphics.setFont(font);
			graphics.drawString(carCount, centerX, centerY);
		} else {
			int centerX = (int) (bounds.getCenterX() + metrics.getHeight() / 4);
			int centerY = (int) (bounds.getCenterY() + metrics.stringWidth(carCount) / 2);
			AffineTransform transform = font.getTransform();
			transform.rotate(FONT_ROTATION);
			Font rotatedFont = font.deriveFont(transform);
			graphics.setFont(rotatedFont);
			graphics.drawString(carCount, centerX, centerY);
			graphics.setFont(font);
		}
	}

	@Override
	public void drawSelected(Graphics graphics) {
		graphics.setColor(new Color(0.0f, 0.6f, 0.8f, 0.7f));
		graphics.fillPolygon(hitBox);
	}

	@Override
	public void handleSelected() {
		if(SimulationEngine.isPaused() || SimulationEngine.isManualSimulation()) {
			SimulationEngine.getInstance().getGui().setStatusMessage("Redefining world...");
			new RoadConfigurationDialog(null, "Road Configuration", this).setVisible(true);
		} else {
			SimulationEngine.getInstance().getGui().setStatusMessage("Pause requested. Try again when simulation is paused");
			SimulationEngine.setPauseRequest(true);
		}
	}

	@Override
	public boolean isSelected(Point point) {
		return hitBox.contains(point);
	}

	@Override
	public void setHitBox(Polygon polygon) {
		this.hitBox = polygon;
	}

	public int getCarSpeed() {
		return carSpeed;
	}

	public void setCarSpeed(int carSpeed) {
		this.carSpeed = carSpeed;
	}

	public int getCurrentCarCount() {
		return carQueue.size();
	}

	public void setCurrentCarCount(int currentCarCount) {
		if(currentCarCount < carQueue.size()) {
			while(carQueue.size() > currentCarCount)
				carQueue.pop();
		} else if(currentCarCount > carQueue.size()) {
			while(carQueue.size() < currentCarCount) {
				Car car = new Car();
				car.setCurrentRoadId(roadId);
				car.setNextRoadId(-1);
				carQueue.add(car);
			}
		}
	}

	public Intersection getFinishIntersection() {
		return finishIntersection;
	}

	public void setFinishIntersection(Intersection finishIntersection) {
		this.finishIntersection = finishIntersection;
	}

	public int getMaxCarCapacity() {
		return maxCarCapacity;
	}

	public void setMaxCarCapacity(int maxCarCapacity) {
		this.maxCarCapacity = maxCarCapacity;
	}

	public boolean isSingleDirection() {
		return singleDirection;
	}

	public void setSingleDirection(boolean singleDirection) {
		this.singleDirection = singleDirection;
	}

	public Intersection getStartIntersection() {
		return startIntersection;
	}

	public void setStartIntersection(Intersection startIntersection) {
		this.startIntersection = startIntersection;
	}

	@Override
	public String getInfoText() {
		String info = "<html>";
		info += "<strong>Road ID:</strong><p>" + roadId + "</p>";
		info += "<strong>Car count:</strong><p>" + carQueue.size() + " cars</p>";
		info += "<strong>Car speed:</strong><p>" + carSpeed + " car/tick</p>";
		info += "<strong>Car capacity:</strong><p>" + maxCarCapacity + " cars</p>";
		info += "<strong>Max car count:</strong><p>" + maxRegisteredCarCount + " cars</p>";
		info += "<strong>Max capacity reached:</strong><p>" + formatter.format(percentageMaxRegisteredCarCount * 100.0) + "%</p>";
		info += "<strong>Avg car count:</strong><p>" + formatter.format(avgCarCount) + " cars</p>";
		info += "</html>";
		return info;
	}

}