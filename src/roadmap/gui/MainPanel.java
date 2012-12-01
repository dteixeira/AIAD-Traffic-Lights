package roadmap.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import roadmap.parser.RoadMapInfo;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private RoadMapInfo roadMap = null;
	private MapDrawPanel mapDrawPanel = null;
	private static int MAX_WIDTH = 800;
	private static int MAX_HEIGHT = 600;
	
	public MainPanel(RoadMapInfo roadMap) {
		
        super(new BorderLayout());
        this.roadMap = roadMap;
        
        // Draw panel setup
        mapDrawPanel = new MapDrawPanel(true, roadMap);
        
        // Viewport and scroll panel setup
        JViewport viewport = new JViewport();
        viewport.setView(mapDrawPanel);
        viewport.setPreferredSize(getPanelSize());
        JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setViewport(viewport);
        add(scroll);
        setOpaque(true);
    }
	
	private Dimension getPanelSize() {
		if(roadMap.getBackgroundImage().getWidth() <= MAX_WIDTH && roadMap.getBackgroundImage().getHeight() <= MAX_HEIGHT)
			return new Dimension(roadMap.getBackgroundImage().getWidth() + 0, roadMap.getBackgroundImage().getHeight() + 0);
		else
			return new Dimension(MAX_WIDTH, MAX_HEIGHT);
		
	}
	
	protected void redrawMap() {
		mapDrawPanel.repaint();
	}

}
