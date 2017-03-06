package org.usfirst.frc.team4525.robot.subsystem;

public interface Shooter extends SubSystem {

	// Loading
	public void startLoad();
	public void stopLoad();
	
	public void suckBack();
	
	public void rackToTop(); // moves rack ontop of shooter
	public void rackToLoadPos(); // moves rack to suck in ball
	
	// Shooting
	public void fire();
	public void cancel();
	
	// Moving
	public void moveUp(); // moves tub up
	public void moveDown(); // moves tub down
	public void dontMove();
	
	
}
