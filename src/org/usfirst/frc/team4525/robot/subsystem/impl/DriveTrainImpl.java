package org.usfirst.frc.team4525.robot.subsystem.impl;

import org.usfirst.frc.team4525.robot.RobotConstants;
import org.usfirst.frc.team4525.robot.subsystem.DriveTrain;
import org.usfirst.frc.team4525.robot.subsystem.SensorBase;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.util.DashUtil;

import edu.wpi.first.wpilibj.Talon;

public class DriveTrainImpl implements DriveTrain {
	// Motors
	private Talon[] left;
	private Talon[] right;

	// Linear Accelerations
	private double upTime;
	private double speed, normalSpeed, previousPower;

	private boolean isStopped = false;

	public void init() {
		int motors_to_drive = RobotConstants.driveTrain_left.length;
		right = new Talon[motors_to_drive];
		left = new Talon[motors_to_drive];
		//
		for (int motorAt = 0; motorAt < motors_to_drive; motorAt++) {
			right[motorAt] = new Talon(RobotConstants.driveTrain_right[motorAt]);
			left[motorAt] = new Talon(RobotConstants.driveTrain_left[motorAt]);
		}

		// Set drive train default values
		speed = 1;
		normalSpeed = RobotConstants.normalSpeedCap;
		previousPower = 0;
		upTime = 20;
	}

	@Override
	public void setSafety(boolean on) {
		for(Talon t: left){
			t.setSafetyEnabled(on);
		}
		for(Talon t: right){
			t.setSafetyEnabled(on);
		}
	}
	
	
	public void test() {
	}

	public void stop() {
		setLeft(0);
		setRight(0);
		isStopped = true;
	}

	public void start() {
		isStopped = false;
	}

	// This is the arcade algorithm responsible for determining how much power
	// to give each set of motors.
	private double[] arcade(double power, double offset) {
		double leftSpeed, rightSpeed;
		if (power > 0) {
			if (offset > 0) {
				leftSpeed = power - offset;
				rightSpeed = Math.max(power, offset);
			} else {
				leftSpeed = Math.max(power, -offset);
				rightSpeed = power + offset; // could overflow 1
			}
		} else {
			if (offset > 0) {
				leftSpeed = -Math.max(-power, offset);
				rightSpeed = power + offset;
			} else {
				leftSpeed = power - offset;
				rightSpeed = -Math.max(-power, -offset);
			}
		}
		double[] speeds = { leftSpeed, rightSpeed };
		return speeds;
	}

	public void arcadeDrive(double offset, double power) { 
		double[] speeds = arcade(power, offset);
		// Acceleration operations:
		speed = normalSpeed;

		setLeft(speeds[0] * speed);
		setRight(speeds[1] * speed);
	}

//	public void arcadeDriveRaw(double power, double offset) {
//		double[] speeds = arcade(power, offset);
//		// Set the motors || CAUTION NO DEADBANDS!
//		setLeft(speeds[0]);
//		setRight(speeds[1]);
//	}

	public void tankDrive(double l, double r) {
		setLeft(l);
		setRight(r);
	}

	private void setLeft(double value) {
		for (Talon motors : left) {
			motors.set(value);
		}
	}

	private void setRight(double value) {
		for (Talon motors : right) {
			motors.set(value);
		}
	}
}
