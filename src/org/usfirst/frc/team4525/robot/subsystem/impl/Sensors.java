package org.usfirst.frc.team4525.robot.subsystem.impl;

import org.usfirst.frc.team4525.robot.RobotConstants;
import org.usfirst.frc.team4525.robot.subsystem.SensorBase;
import org.usfirst.frc.team4525.robot.subsystem.SubSystem;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors implements SensorBase {

	private Encoder left,right;
	private AnalogGyro gyro;
	
	public double distanceRatio(double inches) {
		return inches*RobotConstants.inchToPulseRatio;
	}
	
	public void init() {
		left = new Encoder(RobotConstants.left_encoder[0], RobotConstants.left_encoder[1], true, EncodingType.k4X);
		right = new Encoder(RobotConstants.right_encoder[0], RobotConstants.right_encoder[1], false, EncodingType.k4X);
		
		left.setDistancePerPulse(RobotConstants.encoderPulsePerRevolution);
		right.setDistancePerPulse(RobotConstants.encoderPulsePerRevolution);
		
		//
		gyro = new AnalogGyro(RobotConstants.analogGyroInput);
		gyro.calibrate();
		gyro.setSensitivity(0.0075);
		
		Thread op = new Thread(new Output());
		op.start();
	}

	public void test() {
	}

	public void resetDistance() {
		left.reset();
		right.reset();
		Timer.delay(0.2);
	}

	public double getAverageDistance() {
		return (left.getDistance() + right.getDistance())/2;
	}

	public double getLeftDistance() {
		return left.getDistance();
	}

	public double getRightDistance() {
		return right.getDistance();
	}

	public void resetHeading() {
		gyro.reset();
		Timer.delay(0.2);
	}

	public double getHeadingAngle() {
		return gyro.getAngle();
	}
	
	// Output stream our data
	// TODO: thread may or may not crash dashboard.
	public class Output implements Runnable {
		public void run() {
			if(RobotConstants.debugMode == false) return; 
			// I guess we're in debug mode:
			while(true) {
				SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
				SmartDashboard.putString("Left Encoder", Double.toString(left.getDistance()));
				SmartDashboard.putString("Right Encoder", Double.toString(right.getDistance()));
				SmartDashboard.putString("Average Distance", Double.toString(getAverageDistance()));
				// I want to update our data to network tables
				Timer.delay(0.5); // Every second. It appears the SFX dashboard IS NOT thread protected.
			}		
		}
	}

}
