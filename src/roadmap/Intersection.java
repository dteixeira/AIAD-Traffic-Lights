package roadmap;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import roadmap.engine.SimulationEngine;
import roadmap.gui.IntersectionConfigurationDialog;

public class Intersection extends PickableSurface {

	private Coordinates coordinates;
	private ArrayList<Connection> inboundConnections;
	private int intersectionId;

	public Intersection(){
		inboundConnections = new ArrayList<Connection>();
	}

	@Override
	public void drawSelected(Graphics graphics) {
		graphics.setColor(new Color(0.357f, 0.714f, 0.02f, 0.7f));
		graphics.fillPolygon(hitBox);
	}

	@Override
	public void handleSelected() {
		// Create a configuration window if the simulation is set to manual
		if(SimulationEngine.isManualSimulation()) {
			SimulationEngine.getInstance().getGui().setStatusMessage("Redefining world...");
			new IntersectionConfigurationDialog(null, "Intersection configuration", this).setVisible(true);
		}
		else
			SimulationEngine.getInstance().getGui().setStatusMessage("Can only modify street lights in manual simulation");
	}

	@Override
	public boolean isSelected(Point point) {
		return hitBox.contains(point);
	}

	@Override
	public void setHitBox(Polygon polygon) {
		this.hitBox = polygon;
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

	@Override
	public String getInfoText() {
		return null;
	}

}