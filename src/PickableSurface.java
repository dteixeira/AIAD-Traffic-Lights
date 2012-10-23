import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public abstract class PickableSurface {

	@SuppressWarnings("unused")
	private Polygon hitBox;

	public PickableSurface(){

	}

	/**
	 * 
	 * @param graphics
	 */
	public abstract void drawSelected(Graphics graphics);

	public abstract void handleSelected();

	/**
	 * 
	 * @param point
	 */
	public abstract boolean isSelected(Point point);

	/**
	 * 
	 * @param polygon
	 */
	public abstract void setHitBox(Polygon polygon);

}