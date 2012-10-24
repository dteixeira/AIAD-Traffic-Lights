package roadmap;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class Road extends PickableSurface {

	private int carSpeed;
	private int currentCarCount;
	private Intersection finishIntersection;
	private int maxCarCapacity;
	private int roadId;
	private boolean singleDirection;
	private Intersection startIntersection;

	public Road(){

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

	public int getRoadId() {
		return roadId;
	}

	public void setRoadId(int roadId) {
		this.roadId = roadId;
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