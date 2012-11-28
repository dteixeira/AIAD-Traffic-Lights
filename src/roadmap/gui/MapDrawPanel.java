package roadmap.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import roadmap.Intersection;
import roadmap.PickableSurface;
import roadmap.TrafficLight;
import roadmap.parser.RoadMapInfo;
import roadmap.Road;
import java.util.Map.Entry;

public class MapDrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private RoadMapInfo roadMap = null;
	private PickableSurface pickedSurface = null;
	
	public MapDrawPanel(boolean db, RoadMapInfo roadMap) {
		super(db);
		this.roadMap = roadMap;
		setPreferredSize(new Dimension(roadMap.getBackgroundImage().getWidth(), roadMap.getBackgroundImage().getHeight()));
		setupListeners();
	}
	
	private void setupListeners() {
		DragScrollListener drag = new DragScrollListener(this);
        addMouseMotionListener(drag);
        addMouseListener(drag);
        addHierarchyListener(drag);
        addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				boolean picked = false;
				pickedSurface = null;
				
				// Check for picked roads
				for(Entry<Integer, Road> entry : roadMap.getRoads().entrySet()) {
					if(entry.getValue().isSelected(arg0.getPoint())) {
						pickedSurface = entry.getValue();
						picked = true;
						break;
					}
				}
				// Check for picked intersections
				if(!picked) {
					for(Entry<Integer, Intersection> entry : roadMap.getIntersections().entrySet()) {
						if(entry.getValue().isSelected(arg0.getPoint())) {
							pickedSurface = entry.getValue();
							picked = true;
							break;
						}
					}
				}
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
			}
			
		});
        addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// Call handle for clicked events
				if(pickedSurface != null)
					pickedSurface.handleSelected();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Clear background
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		
		// Draw background image
		g.drawImage(roadMap.getBackgroundImage(), 0, 0, null);
		
		// Draw picked surface shade
		if(pickedSurface != null) {
			setToolTipText(pickedSurface.getInfoText());
			pickedSurface.drawSelected(g);
		} else {
			setToolTipText(null);
		}
		
		// Draw road car count
		g.setColor(Color.white);
		for(Entry<Integer, Road> entry : roadMap.getRoads().entrySet()) {
			entry.getValue().drawCarCount(g);
		}
		
		// Draw traffic lights
		for(TrafficLight trafficLight : roadMap.getTrafficLights()) {
			trafficLight.drawTrafficLight(g);
		}
		
		// Dispose of paint context
		g.dispose();
	}

}
