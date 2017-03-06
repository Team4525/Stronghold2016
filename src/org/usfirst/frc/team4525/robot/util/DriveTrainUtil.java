package org.usfirst.frc.team4525.robot.util;

import org.usfirst.frc.team4525.robot.subsystem.DriveTrain;
import org.usfirst.frc.team4525.robot.subsystem.SensorBase;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;

import edu.wpi.first.wpilibj.Talon;

public class DriveTrainUtil {
	
	
	private static  boolean isActive = false; 
	
	// Logic for turns
	public static void driveDistanceSequential(double dist, double speed) {
		if(isActive) return;
		move(dist,speed);
	}

	public static synchronized void driveDistanceParallel(double dist, double speed) {
		if(isActive) return;
		isActive = true;
		DashUtil.getInstance().log("Starting drive parallel");
		Thread t = new Thread(new Runnable() {
			public void run() {
				move(dist, speed);
			}
		});
		t.start();
	}
	
	public static synchronized void spinSequential(double degrees) {
		if(isActive) return;
		spin(degrees);
	}
	
	public static void spinParallel(double degrees) {
		if(isActive) return; // Thread protect
		isActive = true;
		Thread t = new Thread(new Runnable() {
			public void run() {
				spin(degrees);
			}
		});
		t.start();
	}

	public static void cancel() {
		DashUtil.getInstance().log("Stopping DriveTrain threads");
		isActive = false;
	}
	
	private static double lerpSpeed(double current_dist, double final_dist, double max_speed, double min_speed) {
		double speed = ((final_dist - current_dist) / final_dist)*max_speed;
		if(speed > max_speed) speed = max_speed;
		if(speed < min_speed) speed = min_speed;
		return speed;
	}
	
	private static double lerpAngle(double degrees, double angle, double max_speed, double min_speed) {
		double s = ((degrees - angle) / degrees)*max_speed;
		if(s > max_speed) s = max_speed;
		if(s < min_speed) s = min_speed;
		return s;
	}
	
	private static void move(double dist, double max_speed) {
		isActive = true;
		//
		SubSystemsManager subsystems = SubSystemsManager.getInstance();
		SensorBase sensors = subsystems.getSensors();
		DriveTrain dt = subsystems.getDriveTrain();
		
		// Reset sensors
		sensors.resetDistance();
		sensors.resetHeading();
		//
		dist = sensors.distanceRatio(dist);
		//
		if(max_speed < 0) max_speed = max_speed*-1;
		while(isActive && ((dist > 0 && sensors.getAverageDistance() < dist) || (dist < 0 && sensors.getAverageDistance() > dist))) {
			
			double offsetAngle = sensors.getHeadingAngle(); 
			double current = sensors.getAverageDistance();
			double finalD = dist;
			
			// stop if we go out too far
			if(offsetAngle > 0 && offsetAngle > 10) break;
			if(offsetAngle < 0 && offsetAngle < -10) break;
			
			// Make everything positive
			if(finalD < 0) finalD = finalD*-1;
			if(current < 0) current = current*-1;
			
			// Speed controller
			double speed;
			
			if(max_speed > 0.4) {
				speed = lerpSpeed(current, finalD, max_speed, 0.25);
			} else {
				speed = max_speed;
			}
			// Min speed
			if(speed < 0.25) speed = 0.25;
			
			offsetAngle = offsetAngle*0.35;
			// check offset
			if(offsetAngle > 0.35) offsetAngle = 0.2;
			if(offsetAngle < -0.35) offsetAngle = -0.2;
			// reset the speed
			if(dist < 0) speed = speed*-1;
			
			dt.arcadeDrive(-speed,-offsetAngle);
			//dt.arcadeDriveRaw(-speed, -offsetAngle);
			//dt.arcadeDriveRaw(-offsetAngle, -speed);
		}
		dt.tankDrive(0, 0);
		isActive = false;
	}
	
	private static void spin(double degrees) {
		isActive = true;
		SubSystemsManager subsystems = SubSystemsManager.getInstance();
		SensorBase sensors = subsystems.getSensors();
		DriveTrain dt = subsystems.getDriveTrain();
		if(degrees > 0) {
			while (isActive && sensors.getHeadingAngle() < (degrees - 5)){ // RIGHT
				double angle = sensors.getHeadingAngle();
				double speed = lerpAngle(degrees, angle, 0.8, 0.5);
	
				dt.tankDrive(speed, speed);
			}
		} else if(degrees < 0) {
			while (isActive && sensors.getHeadingAngle() > (degrees + 5)){ // LEFT
				double angle = -sensors.getHeadingAngle();
				double speed = -lerpAngle(degrees, angle, 0.8, 0.5);
	
				dt.tankDrive(speed, speed);
			}
		}
		dt.tankDrive(0, 0);
		isActive = false;
	}
	
	
}
