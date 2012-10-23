import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

public class Intersection extends PickableSurface {

	private Coordinates coordinates;
	private ArrayList<Connection> inboundConnections;
	private int intersectionId;

	public Intersection(){

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
	 * @return the coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the inboundConnections
	 */
	public ArrayList<Connection> getInboundConnections() {
		return inboundConnections;
	}

	/**
	 * @param inboundConnections the inboundConnections to set
	 */
	public void setInboundConnections(ArrayList<Connection> inboundConnections) {
		this.inboundConnections = inboundConnections;
	}

	/**
	 * @return the intersectionId
	 */
	public int getIntersectionId() {
		return intersectionId;
	}

	/**
	 * @param intersectionId the intersectionId to set
	 */
	public void setIntersectionId(int intersectionId) {
		this.intersectionId = intersectionId;
	}

}