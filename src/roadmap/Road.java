package roadmap;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Road extends PickableSurface {

	private int carSpeed;
	private int currentCarCount;
	private Intersection finishIntersection;
	private int maxCarCapacity;
	private boolean singleDirection;
	private Intersection startIntersection;
	private Orientation roadOrientation;
	private int roadId;
	private static int roadLastId = 0;
	private static final float FONT_ROTATION = -3.14159f / 2.0f;

	public Road(){
		roadId = roadLastId++;
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
		String carCount = "" + currentCarCount;
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
		// TODO Auto-generated method stub
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
		return currentCarCount;
	}

	public void setCurrentCarCount(int currentCarCount) {
		this.currentCarCount = currentCarCount;
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
		info += "<strong>Car count:</strong><p>" + currentCarCount + " cars</p>";
		info += "<strong>Car speed:</strong><p>" + carSpeed + " car/tick</p>";
		info += "<strong>Car capacity:</strong><p>" + currentCarCount + " cars</p>";
		return info;
	}

}