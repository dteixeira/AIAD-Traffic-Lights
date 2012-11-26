package roadmap.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import roadmap.parser.RoadMapInfo;

public class MapDrawPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private RoadMapInfo roadMap = null;
	
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
				//MainPanel.this.repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//MainPanel.this.repaint();
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
		g.dispose();
	}

}
