package org.usfirst.frc.team4525.robot.subsystem;

public interface SensorBase extends SubSystem {
	
	// Deal with Encoders:
	public void resetDistance();
	public double getAverageDistance();
	public double getLeftDistance();
	public double getRightDistance();
	
	// Deal with headings
	public void resetHeading();
	public double getHeadingAngle();
	public double distanceRatio(double dist);
}
