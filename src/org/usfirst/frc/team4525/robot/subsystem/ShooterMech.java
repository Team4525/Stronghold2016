package org.usfirst.frc.team4525.robot.subsystem;

public interface ShooterMech extends SubSystem {
	
	// This interface deals entirely with the motors mounted on the shooter that actually fire the ball.
	
	
	public void fire(); // Actually shoots ball
	public void load(); // Sucks in ball
	public void fireStop(); // stops the ball sucker inner
	//
	public void up();
	public void down();
	public void stopMoving();
}
