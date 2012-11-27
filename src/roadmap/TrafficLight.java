package roadmap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class TrafficLight {

	private Road destinationRoad;
	private boolean trafficAllowed;
	private Coordinates coordinates;
	private Polygon light;
	
	public TrafficLight(){
	}
	
	public void setLight(Orientation orientation) {
		int[] x, y;
		switch(orientation) {
		case UP:
			x = new int[] {12, 0, 0, 10, 10, 15, 15, 25, 25, 13};
			y = new int[] {0, 8, 13, 6, 25, 25, 6, 13, 8, 0};
			light = new Polygon(x, y, x.length);
			break;
		case DOWN:
			x = new int[] {10, 10, 0, 0, 12, 13, 25, 25, 15, 15};
			y = new int[] {0, 19, 12, 17, 25, 25, 17, 12, 19, 0};
			light = new Polygon(x, y, x.length);
			break;
		case RIGHT:
			x = new int[] {0, 19, 12, 17, 25, 25, 17, 12, 19, 0};
			y = new int[] {10, 10, 0, 0, 12, 13, 25, 25, 15, 15};
			light = new Polygon(x, y, x.length);
			break;
		case LEFT:
			x = new int[] {0, 8, 13, 6, 25, 25, 6, 13, 8, 0};
			y = new int[] {12, 0, 0, 10, 10, 15, 15, 25, 25, 13};
			light = new Polygon(x, y, x.length);
			break;
		}
		light.translate(coordinates.getxCoord(), coordinates.getyCoord());
	}
	
	public void drawTrafficLight(Graphics g) {
		if(trafficAllowed)
			g.setColor(new Color(0.0f, 0.7f, 0.3f));
		else
			g.setColor(Color.red);
		g.fillPolygon(light);
		g.setColor(Color.white);
		g.drawPolygon(light);
	}
	
	public void setLights(Connection connect) {
		Orientation orientation = null;
		Orientation source = connect.getConnectedRoad().getRoadOrientation();
		Orientation dest = connect.getTrafficLight().getDestinationRoad().getRoadOrientation();
		if(dest == source) {
			switch(dest) {
			case UP:
				orientation = Orientation.DOWN;
				break;
			case DOWN:
				orientation = Orientation.UP;
				break;
			case LEFT:
				orientation = Orientation.RIGHT;
				break;
			case RIGHT:
				orientation = Orientation.LEFT;
				break;
			}
		}
		else {
			switch(source) {
			case UP:
				orientation = Orientation.LEFT;
				break;
			case DOWN:
				orientation = Orientation.RIGHT;
				break;
			case LEFT:
				orientation = Orientation.DOWN;
				break;
			case RIGHT:
				orientation = Orientation.UP;
				break;
			}
		}
		setLight(orientation);
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public Road getDestinationRoad() {
		return destinationRoad;
	}

	public void setDestinationRoad(Road destinationRoad) {
		this.destinationRoad = destinationRoad;
	}

	public boolean isTrafficAllowed() {
		return trafficAllowed;
	}

	public void setTrafficAllowed(boolean trafficAllowed) {
		this.trafficAllowed = trafficAllowed;
	}

}