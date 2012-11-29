package roadmap.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import roadmap.Road;

public class RoadConfigurationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Road road;
	private JSpinner speedSpinner, countSpinner, capacitySpinner;
	
	public RoadConfigurationDialog(JFrame parent, String title, Road road) {
		super(parent, title, true);
		this.road = road;
		setupDialog();
	}
	
	private void setupDialog() {
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		setResizable(false);
		
		// Car speed
		speedSpinner = new JSpinner((SpinnerModel) new SpinnerNumberModel(
				road.getCarSpeed(), 0, 999999, 1)
		);
		speedSpinner.setAlignmentX(LEFT_ALIGNMENT);
		JLabel speedLabel = new JLabel("Car speed:");
		speedLabel.setAlignmentX(LEFT_ALIGNMENT);
		optionsPanel.add(speedLabel);
		optionsPanel.add(speedSpinner);
		
		// Car number
		countSpinner = new JSpinner((SpinnerModel) new SpinnerNumberModel(
				road.getCurrentCarCount(), 0, 999999, 1)
		);
		countSpinner.setAlignmentX(LEFT_ALIGNMENT);
		JLabel countLabel = new JLabel("Car count:");
		countLabel.setAlignmentX(LEFT_ALIGNMENT);
		optionsPanel.add(countLabel);
		optionsPanel.add(countSpinner);
		
		// Car number
		capacitySpinner = new JSpinner((SpinnerModel) new SpinnerNumberModel(
				road.getMaxCarCapacity(), 0, 999999, 1));
		capacitySpinner.setAlignmentX(LEFT_ALIGNMENT);
		JLabel capacitytLabel = new JLabel("Capacity:");
		countLabel.setAlignmentX(LEFT_ALIGNMENT);
		optionsPanel.add(capacitytLabel);
		optionsPanel.add(capacitySpinner);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeRoadValues();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RoadConfigurationDialog.this.dispose();
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
	
	private void changeRoadValues() {
		int speed, count, capacity;
		try {
			speed = (Integer) speedSpinner.getValue();
			count = (Integer) countSpinner.getValue();
			capacity = (Integer) capacitySpinner.getValue();
			if(speed < 0 || count < 0 || capacity < 0)
				throw new ClassCastException();
			if(count > capacity)
				throw new Exception();
			road.setCarSpeed(speed);
			road.setMaxCarCapacity(capacity);
			road.setCurrentCarCount(count);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Car count can't be greater than the maximum capacity.", "Logic error", JOptionPane.ERROR_MESSAGE);
		} finally {
			this.dispose();
		}
	}

}
