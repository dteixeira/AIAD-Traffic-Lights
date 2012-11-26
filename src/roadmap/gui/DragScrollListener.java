package roadmap.gui;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

public class DragScrollListener extends MouseAdapter implements
		HierarchyListener {

	private final Cursor defaultCursor;
	private final Cursor moveCursor = Cursor
			.getPredefinedCursor(Cursor.MOVE_CURSOR);
	private Point startingPoint = new Point();

	public DragScrollListener(JComponent component) {
		defaultCursor = component.getCursor();
	}

	@Override
	public void hierarchyChanged(HierarchyEvent arg0) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		JComponent component = (JComponent) e.getSource();
		Container container = component.getParent();
		if (container instanceof JViewport) {
			JViewport viewport = (JViewport) component.getParent();
			Point convertedPoint = SwingUtilities.convertPoint(component,
					e.getPoint(), viewport);
			int dx = startingPoint.x - convertedPoint.x;
			int dy = startingPoint.y - convertedPoint.y;
			Point viewPoint = viewport.getViewPosition();
			viewPoint.translate(dx, dy);
			component.scrollRectToVisible(new Rectangle(viewPoint, viewport
					.getSize()));
			startingPoint.setLocation(convertedPoint);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JComponent component = (JComponent) e.getSource();
		component.setCursor(moveCursor);
		Container container = component.getParent();
		if (container instanceof JViewport) {
			JViewport viewport = (JViewport) container;
			Point convertedPoint = SwingUtilities.convertPoint(component,
					e.getPoint(), viewport);
			startingPoint.setLocation(convertedPoint);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JComponent jc = (JComponent) e.getSource();
		jc.setCursor(defaultCursor);
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}