package roadmap;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

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

	@Override
	public void drawSelected(Graphics graphics) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected(Point point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHitBox(Polygon polygon) {
		// TODO Auto-generated method stub
		
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

}