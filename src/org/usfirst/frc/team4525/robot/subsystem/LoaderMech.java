package org.usfirst.frc.team4525.robot.subsystem;

public interface LoaderMech extends SubSystem {
	
	public void off(); // turns the ball suckin thing off
	public void suckInBall();
	public void pushOutBall();
	
	public void flipOntoShooter();
	public void flipToSuckInBall();
}
