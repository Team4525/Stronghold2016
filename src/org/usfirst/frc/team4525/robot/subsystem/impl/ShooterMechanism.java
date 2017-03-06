package org.usfirst.frc.team4525.robot.subsystem.impl;

import org.usfirst.frc.team4525.robot.RobotConstants;
import org.usfirst.frc.team4525.robot.subsystem.ShooterMech;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.subsystem.Switches;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

public class ShooterMechanism implements ShooterMech {
	
	private Victor sLeft;
	private Victor sRight;
	private Victor heightAdjust;
	
	// Limits
	private Switches limits; 
	private boolean heightAdjusting = false;
	
	@Override
	public void init() {
		
		sLeft = new Victor(RobotConstants.shooterMotors[0]);
		sRight = new Victor(RobotConstants.shooterMotors[1]);
		heightAdjust = new Victor(RobotConstants.heightAdjustMotor);
		SubSystemsManager subsystems = SubSystemsManager.getInstance();
		limits = subsystems.getLimits();
		
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
	}
	
	// Firing

	public void fire() {
		sLeft.set(1);
		sRight.set(-1);
	}
	
	// Turns off shooting mechanism
	public void fireStop() {
		sLeft.set(0);
		sRight.set(0);
	}

	@Override
	public void load() {
		sLeft.set(-RobotConstants.suckerInnerRate);
		sRight.set(RobotConstants.suckerInnerRate);
	}
	
	
	

	
	@Override
	public void up() {
		if(limits.shooterHeightTopLimit() && !heightAdjusting) {
				Thread height = new Thread(new adjustUp());
				heightAdjusting = true;
				height.start();
		}
		
	}

	@Override
	public void down() {
		if( limits.shooterHeightBottomLimit() && !heightAdjusting) {
				Thread height = new Thread(new adjustDown()); 
				heightAdjusting = true;
				height.start();
		}
	}
	
	@Override
	public void stopMoving() {
		heightAdjusting = false;
	}
	
	public class adjustUp implements Runnable {
		public void run() {
			while(limits.shooterHeightTopLimit() && heightAdjusting) {
				heightAdjust.set(1);
			}
			heightAdjust.set(0);
			heightAdjusting = false;
		}
	}
	
	public class adjustDown implements Runnable {
		public void run() {
			while(limits.shooterHeightBottomLimit() && heightAdjusting) {
				heightAdjust.set(-1);
			}
			heightAdjust.set(0);
			heightAdjusting = false;
		}
	}
}
