package roadmap.gui;

import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import roadmap.parser.RoadMapInfo;
import roadmap.statistics.Stats;

public class StatsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private RoadMapInfo roadMapInfo;
	private Stats stats;
	private JLabel infoDisplay;
	
	public StatsDialog(JFrame parent, String title, RoadMapInfo roadMapInfo) {
		super(parent, title, false);
		this.roadMapInfo = roadMapInfo;
		this.stats = new Stats(roadMapInfo);
		setupDialog();
	}
	
	public void updateStats() {
		stats.updateStats(roadMapInfo);
		infoDisplay.setText(makeInfoHtml());
	}
	
	private void setupDialog() {
		infoDisplay = new JLabel();
		infoDisplay.setText(makeInfoHtml());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().add(infoDisplay);
		setResizable(false);
		setPreferredSize(new Dimension(250, 320));
		pack();
	}
	
	private String makeInfoHtml() {
		DecimalFormat formatter = new DecimalFormat("#.##");
		String html = "<html>";
		html += "<h2><strong>Global Stats</strong></h2>";
		html += "<p><strong>Total cycles: </strong>" + stats.getTotalSimulationCycles() + "</p>";
		html += "<p><strong>Total cars: </strong>" + stats.getTotalCars() + "</p>";
		html += "<p><strong>Total roads: </strong>" + stats.getTotalRoads() + "</p>";
		html += "<p><strong>Total intersections: </strong>" + stats.getTotalIntersections() + "</p>";
		html += "<p><strong>Total traffic lights: </strong>" + stats.getTotalTrafficLights() + "</p>";
		html += "<h2><strong>Car Stats</strong></h2>";
		html += "<p><strong>Avg cycles moving: </strong>" + formatter.format(stats.getAvgTimeMoving()) + "</p>";
		html += "<p><strong>Avg cycles waiting: </strong>" + formatter.format(stats.getAvgTimeWaiting()) + "</p>";
		html += "<p><strong>Lifetime moving: </strong>" + formatter.format(stats.getPercentageTimeMoving() * 100.0) + "%</p>";
		html += "<h2><strong>Cycle Stats</strong></h2>";
		html += "<p><strong>Cars moved last cycle: </strong>" + stats.getMovedCars() + "</p>";
		html += "<p><strong>Percentage moved: </strong>" + formatter.format(stats.getPercentageMovedCars() * 100.0) + "%</p>";
		html += "</html>";
		return html;
	}

}
