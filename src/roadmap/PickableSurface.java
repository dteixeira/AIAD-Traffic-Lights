package roadmap;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public abstract class PickableSurface {

	protected Polygon hitBox;

	public abstract void drawSelected(Graphics graphics);
	public abstract void handleSelected();
	public abstract boolean isSelected(Point point);
	public abstract void setHitBox(Polygon polygon);
	public abstract String getInfoText();

}