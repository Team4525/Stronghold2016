package org.usfirst.frc.team4525.robot.subsystem.impl;

import org.usfirst.frc.team4525.robot.RobotConstants;
import org.usfirst.frc.team4525.robot.subsystem.Switches;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimitSwitches implements Switches {
	//
	private DigitalInput rackTop;
	private DigitalInput rackLoadPosition;
	//
	private DigitalInput shooterTopHeight;
	private DigitalInput shooterBottomHeight;
	// threads
	private Thread statusReport;
	
	public void init() {
		// Rack
		rackTop = new DigitalInput(RobotConstants.rackTopLimit); //4
		rackLoadPosition = new DigitalInput(RobotConstants.rackLowerLimit); //7
		// Shooter
		shooterTopHeight = new DigitalInput(RobotConstants.shooterRaiseLimit); //5
		shooterBottomHeight = new DigitalInput(RobotConstants.shooterLowerLimit); // 6
		// 
		statusReport = new Thread(new switchStatusReport());
		statusReport.start();
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
	}
	public boolean rackTopSwitch() {
		// TODO Auto-generated method stub
		return rackTop.get();
	}

	@Override
	public boolean rackLoadPositionSwitch() {
		// TODO Auto-generated method stub
		return rackLoadPosition.get();
	}

	@Override
	public boolean shooterHeightTopLimit() {
		// TODO Auto-generated method stub
		return shooterTopHeight.get();
	}

	@Override
	public boolean shooterHeightBottomLimit() {
		// TODO Auto-generated method stub
		return shooterBottomHeight.get();
	}
	
	public class switchStatusReport implements Runnable {
		public void run() {
			if(RobotConstants.debugMode) {
				while(true) {
					SmartDashboard.putBoolean("Rack top limit", rackTopSwitch());
					SmartDashboard.putBoolean("Rack load position", rackLoadPositionSwitch());
					SmartDashboard.putBoolean("Shooter Top", shooterHeightTopLimit());
					SmartDashboard.putBoolean("Shooter Bottom", shooterHeightBottomLimit());
					Timer.delay(1);
				}
			}
		}
		
	}

}
