package roadmap.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import roadmap.engine.SimulationEngine;
import roadmap.parser.RoadMapInfo;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String FRAME_TITLE = "Traffic Light Planner";
	private MainPanel mainPanel = null;
	
	public MainFrame(RoadMapInfo roadMap) {
		setupFrame();
		setupMainPanel(roadMap);
		setupMenuBar();
	}
	
	public void redrawMap() {
		mainPanel.redrawMap();
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
	
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// Simulation menu
		JMenu simulationMenu = new JMenu("Simulation");
		JMenuItem evolveOption = new JMenuItem("Evolve");
		simulationMenu.add(evolveOption);
		evolveOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SimulationEngine.getInstance().evolveWorld();
			}
		});
		
		// Options menu
		JMenu optionsMenu = new JMenu("Options");
		JMenuItem closeOption = new JMenuItem("Close");
		optionsMenu.add(closeOption);
		closeOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.dispose();
				System.exit(0);
			}
		});
		
		// Add menus
		menuBar.add(simulationMenu);
		menuBar.add(optionsMenu);
		setJMenuBar(menuBar);
	}
	
	public static void main(String[] args) {
		SimulationEngine engine = SimulationEngine.initInstance("maps/mapWidth3.xml");
		engine.setVisible(true);
	}

}
