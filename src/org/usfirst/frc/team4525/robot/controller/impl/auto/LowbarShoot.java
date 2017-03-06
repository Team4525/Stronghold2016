package org.usfirst.frc.team4525.robot.controller.impl.auto;

import org.usfirst.frc.team4525.robot.controller.AutonomousController;
import org.usfirst.frc.team4525.robot.subsystem.Shooter;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.subsystem.Switches;
import org.usfirst.frc.team4525.robot.util.AssistedFiring;
import org.usfirst.frc.team4525.robot.util.AutoChooseUtil;
import org.usfirst.frc.team4525.robot.util.DashUtil;
import org.usfirst.frc.team4525.robot.util.DriveTrainUtil;

public class LowbarShoot implements AutonomousController {

	private boolean running;
	@Override
	public void start() {
		//if(run) return;
		running = true;
		
		SubSystemsManager systems = SubSystemsManager.getInstance();
		Switches limits = systems.getLimits();
		Shooter shooter = systems.getShooter(); 
		
		DashUtil dashUtil = DashUtil.getInstance();
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				// 
				// Wait for tub to lower
				// NEW STUFF:
				if(limits.shooterHeightBottomLimit()) {
					dashUtil.log("Waiting for tub to lower");
					shooter.moveDown();
					while(limits.shooterHeightBottomLimit() && running);//
				}
				
				dashUtil.log("Driving under lowbar");
				
				// Old Logic
				if(running) DriveTrainUtil.driveDistanceSequential(204, 0.3);
				if(AutoChooseUtil.getInstance().getFireSettings() != AutoChooseUtil.AutoFireDecision.DontFire) {
					if(running) DriveTrainUtil.spinSequential(55);
					// Run
					if(running) AssistedFiring.findTargetFromTop();
					// Turn around
					if(running) DriveTrainUtil.spinSequential(180); // Turn around -- NEW (the drivetrain util wont reset heading unless told to drive strait)
				}
				//
			}			
		});
		t.start();
	}

	@Override
	public void stop() {
		DriveTrainUtil.cancel();
		AssistedFiring.stop();
		running = false;
	}
	
}
