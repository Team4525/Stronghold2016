package org.usfirst.frc.team4525.robot.util;

import org.usfirst.frc.team4525.robot.subsystem.DriveTrain;
import org.usfirst.frc.team4525.robot.subsystem.Shooter;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.subsystem.VisionAiming;
import org.usfirst.frc.team4525.robot.subsystem.impl.Target;

import edu.wpi.first.wpilibj.Timer;

public class AssistedFiring {

	private static boolean running = false;
	
	
	public static void stop(){
		running = false;
	}
	
	public static void findTargetFromTop(){
		if(!running){
		running = true;
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				DashUtil dashUtil = DashUtil.getInstance();
				dashUtil.log("ASSIST");
				SubSystemsManager subSystems = SubSystemsManager.getInstance();
				DriveTrain drive =subSystems.getDriveTrain();
				VisionAiming vision =subSystems.getVision();
				Shooter shooter =subSystems.getShooter();
				
				boolean targetFound = false;
				
				Target target = vision.getTarget();
				
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
				while( subSystems.getLimits().shooterHeightBottomLimit() && running){
					shooter.moveDown();
					Timer.delay(0.05);
					shooter.dontMove();
					Timer.delay(0.1);
					target = vision.getTarget();
					if(target !=null){
						DashUtil.getInstance().log("Have Target");
						shooter.dontMove();
						targetFound = true;
						shooter.fire();
						running = false;
						
					}
				}
			}
		});
		t.start();
		}
		
			
		
	}
}
