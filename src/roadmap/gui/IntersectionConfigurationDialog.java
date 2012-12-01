package roadmap.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import roadmap.Connection;
import roadmap.Intersection;
import roadmap.Orientation;
import roadmap.TrafficLight;
import roadmap.engine.SimulationEngine;

public class IntersectionConfigurationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Intersection intersection;
	private HashMap<JCheckBox, TrafficLight> horizontalCheckBox;
	private HashMap<JCheckBox, TrafficLight> verticalCheckBox;
	
	public IntersectionConfigurationDialog(JFrame parent, String title, Intersection intersection) {
		super(parent, title, true);
		this.intersection = intersection;
		setupDialog();
	}
	
	private void setupDialog() {
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		setResizable(false);
		
		// Checkboxes
		setupCheckBoxes(optionsPanel);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeIntersectionValues();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				IntersectionConfigurationDialog.this.dispose();
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		// Pack dialog
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(optionsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    pack();
	}
	
	private void setupCheckBoxes(JPanel panel) {
		horizontalCheckBox = new HashMap<JCheckBox, TrafficLight>();
		verticalCheckBox = new HashMap<JCheckBox, TrafficLight>();
		for(Connection connect : intersection.getInboundConnections()) {
			JCheckBox checkbox = new JCheckBox();
			checkbox.setText(connect.getConnectedRoad().getRoadOrientation() + " to " + getCorrectOrientation(connect));
			checkbox.setSelected(connect.getTrafficLight().isTrafficAllowed());
			checkbox.setAlignmentX(LEFT_ALIGNMENT);
			if(connect.getConnectedRoad().getRoadOrientation() == Orientation.UP || connect.getConnectedRoad().getRoadOrientation() == Orientation.DOWN) {
				verticalCheckBox.put(checkbox, connect.getTrafficLight());
				checkbox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						if(((JCheckBox)arg0.getItem()).isSelected())
							for(Entry<JCheckBox, TrafficLight> entry : horizontalCheckBox.entrySet())
								entry.getKey().setSelected(false);
					}
				});
			} else {
				horizontalCheckBox.put(checkbox, connect.getTrafficLight());
				checkbox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent arg0) {
						if(((JCheckBox)arg0.getItem()).isSelected())
							for(Entry<JCheckBox, TrafficLight> entry : verticalCheckBox.entrySet())
								entry.getKey().setSelected(false);
					}
				});
			}
			panel.add(checkbox);
		}
	}
	
	private Orientation getCorrectOrientation(Connection connect) {
		switch(connect.getTrafficLight().getDestinationRoad().getRoadOrientation()) {
		case UP:
			return Orientation.DOWN;
		case DOWN:
			return Orientation.UP;
		case LEFT:
			return Orientation.RIGHT;
		case RIGHT:
			return Orientation.LEFT;
		default:
			return null;
		}
	}
	
	private void changeIntersectionValues() {
		for(Entry<JCheckBox, TrafficLight> entry : horizontalCheckBox.entrySet())
			entry.getValue().setTrafficAllowed(entry.getKey().isSelected());
		for(Entry<JCheckBox, TrafficLight> entry : verticalCheckBox.entrySet())
			entry.getValue().setTrafficAllowed(entry.getKey().isSelected());
		this.dispose();
		SimulationEngine.getInstance().getGui().redrawMap();
	}
	
}
