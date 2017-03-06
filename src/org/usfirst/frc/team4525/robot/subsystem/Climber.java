package org.usfirst.frc.team4525.robot.subsystem;

public interface Climber extends SubSystem {
	
	public void prepareToclimb();
	public void fireHooks();
	public void climb();
	
	public boolean isReady();
	public boolean hooksAreReady();
	
}
