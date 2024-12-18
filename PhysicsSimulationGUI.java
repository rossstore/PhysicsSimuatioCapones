import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Projectile {
    private final double initialVelocity;
    private final double launchAngle;
    private final double gravity;

    public Projectile(double initialVelocity, double launchAngle, double gravity) {
        this.initialVelocity = initialVelocity;
        this.launchAngle = launchAngle;
        this.gravity = gravity;
    }

    
    public double calculateMaxHeight() {
        double theta = Math.toRadians(launchAngle);
        return (Math.pow(initialVelocity * Math.sin(theta), 2)) / (2 * gravity);
    }

    public double calculateTimeOfFlight() {
        double theta = Math.toRadians(launchAngle);
        return (2 * initialVelocity * Math.sin(theta)) / gravity;
    }

    public double calculateRange() {
        double theta = Math.toRadians(launchAngle);
        return (Math.pow(initialVelocity, 2) * Math.sin(2 * theta)) / gravity;
    }

    public String getProjectileInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Initial Velocity: ").append(initialVelocity).append(" m/s\n");
        info.append("Launch Angle: ").append(launchAngle).append(" degrees\n");
        info.append("Gravity: ").append(gravity).append(" m/s²\n");
        info.append("Max Height: ").append(calculateMaxHeight()).append(" meters\n");
        info.append("Time of Flight: ").append(calculateTimeOfFlight()).append(" seconds\n");
        info.append("Range: ").append(calculateRange()).append(" meters\n");
        return info.toString();
    }

    public String getTrajectoryPoints() {
        double timeOfFlight = calculateTimeOfFlight();
        double theta = Math.toRadians(launchAngle);
        StringBuilder trajectoryData = new StringBuilder();
        
        for (double t = 0; t <= timeOfFlight; t += 0.1) {
            double x = initialVelocity * Math.cos(theta) * t;
            double y = (initialVelocity * Math.sin(theta) * t) - (0.5 * gravity * Math.pow(t, 2));
            if (y < 0) y = 0;
            trajectoryData.append(String.format("Time: %.1f s, X: %.2f m, Y: %.2f m%n", t, x, y));
        }
        return trajectoryData.toString();
    }
}

public class PhysicsSimulationGUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Projectile Motion Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Create a panel for the inputs and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        // Create labels and text fields for user input
        JLabel velocityLabel = new JLabel("Initial Velocity (m/s): ");
        JTextField velocityField = new JTextField();
        JLabel angleLabel = new JLabel("Launch Angle (degrees): ");
        JTextField angleField = new JTextField();
        JLabel gravityLabel = new JLabel("Gravity (m/s²): ");
        JTextField gravityField = new JTextField("9.8"); // default value

        inputPanel.add(velocityLabel);
        inputPanel.add(velocityField);
        inputPanel.add(angleLabel);
        inputPanel.add(angleField);
        inputPanel.add(gravityLabel);
        inputPanel.add(gravityField);

        // Create a button to calculate and display results
        JButton calculateButton = new JButton("Calculate");

        // MAKE A TEXT AREA
        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(calculateButton, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Action listener for the calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get input values from the text fields
                    double velocity = Double.parseDouble(velocityField.getText());
                    double angle = Double.parseDouble(angleField.getText());
                    double gravity = Double.parseDouble(gravityField.getText());

                    // Create a Projectile object
                    Projectile projectile = new Projectile(velocity, angle, gravity);

                    // Get and display the projectile info
                    String projectileInfo = projectile.getProjectileInfo();
                    resultArea.setText(projectileInfo);

                    // Get and display the trajectory points
                    String trajectoryPoints = projectile.getTrajectoryPoints();
                    resultArea.append("\nTrajectory Points:\n" + trajectoryPoints);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for velocity, angle, and gravity.");
                }
            }
        });

        // Show the frame
        frame.setVisible(true);
    }
}
