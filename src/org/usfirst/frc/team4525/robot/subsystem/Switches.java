package org.usfirst.frc.team4525.robot.subsystem;

public interface Switches extends SubSystem {
	
	public boolean rackTopSwitch();
	public boolean rackLoadPositionSwitch();
	
	public boolean shooterHeightTopLimit();
	public boolean shooterHeightBottomLimit();
	
}
