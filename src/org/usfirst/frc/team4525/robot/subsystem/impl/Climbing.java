package org.usfirst.frc.team4525.robot.subsystem.impl;

import org.usfirst.frc.team4525.robot.RobotConstants;
import org.usfirst.frc.team4525.robot.subsystem.Climber;
import org.usfirst.frc.team4525.robot.util.Piston;

public class Climbing implements Climber {

	private Piston armRelease;
	private Piston armHooker;
	
	private boolean canClimb = false;
	private boolean hooksReady = false;
	
	public void init() {
		armRelease = new Piston(RobotConstants.gasPistonRelease[0], RobotConstants.gasPistonRelease[1], true);
		armHooker = new Piston(RobotConstants.hookPistons[0], RobotConstants.hookPistons[1], false); // Jamie this might of been the issue, the arrays were 2/3 not 0/1
		canClimb = false;
		hooksReady = false;
	}

	public void test() {
	
	}

	public void climb() {
		// Climb the tower
		armHooker.retract();
	}

	public void prepareToclimb() {
		armRelease.retract();
		canClimb = true;
	}
	
	public void fireHooks() {
		armHooker.extend();
		hooksReady = true;
	}

	public boolean isReady() {
		return canClimb;
	}
	
	public boolean hooksAreReady() {
		return hooksReady;
	}

}
