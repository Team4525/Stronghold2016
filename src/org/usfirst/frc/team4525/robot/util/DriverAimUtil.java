package org.usfirst.frc.team4525.robot.util;

import org.usfirst.frc.team4525.robot.subsystem.DriveTrain;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.subsystem.VisionAiming;
import org.usfirst.frc.team4525.robot.subsystem.impl.Target;

import edu.wpi.first.wpilibj.Timer;

public class DriverAimUtil {

/*
 * The way this SHOULD work will allow the drivers to relatively get close to X
 * 
 * This will run while a button is held.
 * 
 */
	
	private static boolean isRun = false;
	
	private static double lerpToTarget(double cenX, double imgW, double deadZone, double camOff, double minSpeed, double maxSpeed) {
		imgW = (imgW/2) + camOff;
		
		double delta = cenX - imgW;
		if(Math.abs(delta) < deadZone) return 0;
		
		double speed = (delta/imgW*maxSpeed);
		
		if(Math.abs(speed) < minSpeed) { // thanks for math.abs <3
			if(speed < 0){
				speed = -minSpeed;
			} else {
				speed = minSpeed;
			}
//		else if(Math.abs(speed) > maxSpeed) {
//			if(speed < 0) {
//				speed = -maxSpeed;
//			} else {
//				speed = maxSpeed;
//			}
		}
		return speed;
	}
	
	public static void AimX(double deadZone) {
		if(!isRun) {
			isRun = true;
			double camOffset = 5;
			
			DashUtil.getInstance().log("Started Driver Assisted Aim");
			
			DriveTrain dt = SubSystemsManager.getInstance().getDriveTrain();
			VisionAiming vision = SubSystemsManager.getInstance().getVision();
			Thread pos = new Thread(new Runnable() {
				public void run() {
					while(isRun) {
						// Track Target
						Target target = vision.getTarget();
						
						if(target != null) {
							// Vision Aim:
//							double hBoundLow = (target.getImageWidth()/2)+5; // offset for camera not being centered
//							double hBoundHigh = (target.getImageWidth()/2)+5;
//							double deltaX = Math.abs(target.getCenterX() - (target.getImageWidth()/2));
							
							double offset = lerpToTarget(target.getCenterX(),target.getImageWidth(), deadZone, camOffset,0.4, 0.6);
							if(offset != 0) {
								dt.arcadeDrive(offset, 0);
							} else {
								DashUtil.getInstance().log("Aimed & Locked Onto X");
								isRun = false;
							}
							
							// My thinking here is we let it lerp based on updating values pushed to the network tables
							// Rather than stopping it because I don't feel the movement will be a huge amount that will
							// change the outcome of our camera
							
							Timer.delay(0.2);// Hold
							//dt.stop(); // Capture another image
							//Timer.delay(0.1);
						} else {
							dt.stop();
						}
					}
					// Stop the drivetrain
					dt.stop();
				} 
				
			});
			pos.start();
			
		}
	}
	
	public static void stop() {
		isRun = false;
	}
	
}
