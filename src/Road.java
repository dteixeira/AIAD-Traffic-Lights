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

	/**
	 * @return the carSpeed
	 */
	public int getCarSpeed() {
		return carSpeed;
	}

	/**
	 * @param carSpeed the carSpeed to set
	 */
	public void setCarSpeed(int carSpeed) {
		this.carSpeed = carSpeed;
	}

	/**
	 * @return the currentCarCount
	 */
	public int getCurrentCarCount() {
		return currentCarCount;
	}

	/**
	 * @param currentCarCount the currentCarCount to set
	 */
	public void setCurrentCarCount(int currentCarCount) {
		this.currentCarCount = currentCarCount;
	}

	/**
	 * @return the finishIntersection
	 */
	public Intersection getFinishIntersection() {
		return finishIntersection;
	}

	/**
	 * @param finishIntersection the finishIntersection to set
	 */
	public void setFinishIntersection(Intersection finishIntersection) {
		this.finishIntersection = finishIntersection;
	}

	/**
	 * @return the maxCarCapacity
	 */
	public int getMaxCarCapacity() {
		return maxCarCapacity;
	}

	/**
	 * @param maxCarCapacity the maxCarCapacity to set
	 */
	public void setMaxCarCapacity(int maxCarCapacity) {
		this.maxCarCapacity = maxCarCapacity;
	}

	/**
	 * @return the roadId
	 */
	public int getRoadId() {
		return roadId;
	}

	/**
	 * @param roadId the roadId to set
	 */
	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}

	/**
	 * @return the singleDirection
	 */
	public boolean isSingleDirection() {
		return singleDirection;
	}

	/**
	 * @param singleDirection the singleDirection to set
	 */
	public void setSingleDirection(boolean singleDirection) {
		this.singleDirection = singleDirection;
	}

	/**
	 * @return the startIntersection
	 */
	public Intersection getStartIntersection() {
		return startIntersection;
	}

	/**
	 * @param startIntersection the startIntersection to set
	 */
	public void setStartIntersection(Intersection startIntersection) {
		this.startIntersection = startIntersection;
	}

}