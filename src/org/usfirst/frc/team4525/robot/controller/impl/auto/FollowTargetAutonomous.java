package org.usfirst.frc.team4525.robot.controller.impl.auto;

import org.usfirst.frc.team4525.robot.controller.AutonomousController;
import org.usfirst.frc.team4525.robot.subsystem.DriveTrain;
import org.usfirst.frc.team4525.robot.subsystem.Shooter;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.subsystem.VisionAiming;
import org.usfirst.frc.team4525.robot.subsystem.impl.Target;
import org.usfirst.frc.team4525.robot.util.DashUtil;
import org.usfirst.frc.team4525.robot.util.XboxController;

import edu.wpi.first.wpilibj.Timer;

public class FollowTargetAutonomous implements AutonomousController{

	private SubSystemsManager subSystems = SubSystemsManager.getInstance();
	
	private DashUtil dashUtil = DashUtil.getInstance();
	private boolean running = false;
	private DriveTrain drive;
	private VisionAiming vision;
	private Shooter shooter;
	private XboxController driver;
	
	private int headingDeadband = 10; // The maximum offset from the center in positive or negative value
	private int image_center_x = 80;
	//private int image_center_y = 60;
	
	// keeping in mind we do have the ability to adjust the resolution at which the image is processed
	
	public void start() {
		drive = subSystems.getDriveTrain();
		vision = subSystems.getVision();
		shooter = subSystems.getShooter();
		driver =  subSystems.getDriver();
			//
			//double rotate = 0;
		running = true;
		Thread t = new Thread(new AutoThread());
		t.start();
	}
	
	
	private class AutoThread implements Runnable{
		@Override
		public void run() {
			DashUtil.getInstance().clear();
			DashUtil.getInstance().log("AUTONOMOUS MODE");
			
			boolean targetFound = false;
		
			
			while(running) {
				Target target = vision.getTarget();
				if(!targetFound){
					while(subSystems.getLimits().rackLoadPositionSwitch() && running){
						shooter.rackToTop();
					}
					DashUtil.getInstance().log("Searching For Target");;
					// first move shooter to lowest position
					DashUtil.getInstance().log("Phase 1");
					while(subSystems.getLimits().shooterHeightTopLimit() && running){
						shooter.moveUp();
						DashUtil.getInstance().log("Moving Up");
					}
					DashUtil.getInstance().log("Phase 2");
					while(target == null && subSystems.getLimits().shooterHeightBottomLimit() && running){
						shooter.moveDown();
						Timer.delay(0.05);
						shooter.dontMove();
						Timer.delay(0.1);
						target = vision.getTarget();
						if(target !=null){
							DashUtil.getInstance().log("Have Target");
							shooter.dontMove();
							targetFound = true;
						}
					}
				}
				
				
				
				
				
				
//				Target target = vision.getTarget();
//				if(!targetFound){
//					target = vision.getTarget();
//					while(subSystems.getLimits().rackLoadPositionSwitch() && running){
//						shooter.rackToTop();
//					}
//					
//					if(target == null){
//						DashUtil.getInstance().log("Searching For Target");;
//						// first move shooter to lowest position
//						DashUtil.getInstance().log("Phase 1");
//						while(subSystems.getLimits().shooterHeightTopLimit() && running){
//							shooter.moveUp();
//							DashUtil.getInstance().log("Moving Up");
//						}
//						
//						
//						DashUtil.getInstance().log("Phase 2");
//						while(target == null && subSystems.getLimits().shooterHeightBottomLimit() && running){
//							shooter.moveDown();
//							Timer.delay(0.05);
//							shooter.dontMove();
//							Timer.delay(0.1);
//							target = vision.getTarget();
//							if(target !=null){
//								DashUtil.getInstance().log("Have Target");
//								shooter.dontMove();
//								targetFound = true;
//							}
//						}
//						
//						DashUtil.getInstance().log("Phase 3");
//						if(target == null){
//							DashUtil.getInstance().log("Unable to find target");
//							running = false;
//						}
//						
//					}
//				}
//				if(target!=null && targetFound && !sweetSpotX){
//					DashUtil.getInstance().log("Horizontal Mode");
//					double hBoundLow = (target.getImageWidth()/2)+5; // offset for camera not being centered
//					double hBoundHigh = (target.getImageWidth()/2)+5;
//					double deltaX = Math.abs(target.getCenterX() - (target.getImageWidth()/2));
//					
//					if(target.getCenterX() < hBoundLow){
//						// turn
//						DashUtil.getInstance().log(">>");
//						if(deltaX < 20){
//							drive.arcadeDrive(0,-0.3);
//						}else{
//							drive.arcadeDrive(0,-0.5);
//						}
//						Timer.delay(0.25);
//						drive.stop();
//						Timer.delay(0.25);
//					}else if(target.getCenterX() > hBoundHigh){
//						// turn
//						DashUtil.getInstance().log("<<");
//						if(deltaX < 20){
//							drive.arcadeDrive(0,0.3);
//						}else{
//	\						drive.arcadeDrive(0,0.5);
//						}
//						Timer.delay(0.25);
//						drive.stop();
//						Timer.delay(0.25);
//						
//					}else{
//						sweetSpotX = true;
//						DashUtil.getInstance().log("--");
//						drive.stop();
//					}
//				
//				}else if(target !=null && targetFound && sweetSpotX && !sweetSpotY){
//					dashUtil.log("Centering Y");
//					double vBoundLow = (target.getImageHeight()/2-10);
//					double vBoundHigh = (target.getImageHeight()/2+10);
//					
//					if(target.getCenterY() < vBoundLow){
//						shooter.moveDown();
//						Timer.delay(0.01);
//						shooter.dontMove();
//							
//					}else if(target.getCenterY() > vBoundHigh){
//						shooter.moveUp();
//						Timer.delay(0.01);
//						shooter.dontMove();
//							
//					}else if(target.getCenterY() > vBoundLow  && target.getCenterY() < vBoundHigh){
//						sweetSpotY =true;
//					}
//					if((!subSystems.getLimits().shooterHeightTopLimit() || !subSystems.getLimits().shooterHeightBottomLimit()) && !sweetSpotY){
//						//we have hit the limit of y and were not in the sweet spot.
//						DashUtil.getInstance().log("OUT OF SWEET SPOT");
//						running = false;
//					}
//						
//				}
//					
//					//do we shoot?
//					if(sweetSpotX == true && sweetSpotY == true){
//						DashUtil.getInstance().log("AUTO FIRE");
//						shooter.fire();
//						Timer.delay(2);
//						shooter.cancel();// stop firing
//					}
//					
//				} else { // stop if we see nothing
//					drive.stop();
//					DashUtil.getInstance().log("AUTONOMOUS END");
//				}
			}
		}
	}
	
	
	
	
	
	// kill the loop if need be
	public void stop() {
		DashUtil.getInstance().log("auto stop requestedT");
		running = false;
	}
	
	


}
