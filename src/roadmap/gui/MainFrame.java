package roadmap.gui;

import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import roadmap.builder.RoadMapBuilder;
import roadmap.parser.RoadMapInfo;
import roadmap.parser.RoadMapParser;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String FRAME_TITLE = "Traffic Light Planner";
	private MainPanel mainPanel = null;
	
	public MainFrame(RoadMapInfo roadMap) {
		// TODO
		setupFrame();
		setupMainPanel(roadMap);
	}
	
	private void setupFrame() {
		// Window behavior and look settings
		try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e) {
            e.printStackTrace();
        }
		setTitle(FRAME_TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Global tool tip settings
        ToolTipManager.sharedInstance().setDismissDelay(100000);
		ToolTipManager.sharedInstance().setReshowDelay(0);
		ToolTipManager.sharedInstance().setInitialDelay(0); 
	}
	
	private void setupMainPanel(RoadMapInfo roadMap) {
		mainPanel = new MainPanel(roadMap);
		getContentPane().add(mainPanel);
        pack();
	}
	
	public static void main(String[] args) {
		RoadMapInfo roadMap = RoadMapParser.parseRoadMapXML("maps/mapWidth4.xml");
		RoadMapBuilder.buildAdvancedInfo(roadMap);
		new MainFrame(roadMap).setVisible(true);
	}

}
