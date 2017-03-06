package org.usfirst.frc.team4525.robot.subsystem;



public interface DriveTrain extends SubSystem{
	
	public void setSafety(boolean on);
	
	// move forward / backwards at given speed
	
	public void arcadeDrive(double offset, double power);
	public void tankDrive(double l, double r);
	
	// make subsystem do stuff again
	public void start();
	
	// all stop
	public void stop();
	// move exactly distance (in meters, centimeter.. heck well decide :P) and stop
	// Move exactly the distance specified either positive (forwards) or negative (back) in Inches
}
