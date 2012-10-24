package roadmap;
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

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public ArrayList<Connection> getInboundConnections() {
		return inboundConnections;
	}

	public void setInboundConnections(ArrayList<Connection> inboundConnections) {
		this.inboundConnections = inboundConnections;
	}

	public int getIntersectionId() {
		return intersectionId;
	}

	public void setIntersectionId(int intersectionId) {
		this.intersectionId = intersectionId;
	}

}