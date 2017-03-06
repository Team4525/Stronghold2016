package org.usfirst.frc.team4525.robot.controller.impl.auto;

import org.usfirst.frc.team4525.robot.controller.AutonomousController;
import org.usfirst.frc.team4525.robot.subsystem.DriveTrain;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.util.AssistedFiring;
import org.usfirst.frc.team4525.robot.util.AutoChooseUtil;
import org.usfirst.frc.team4525.robot.util.DashUtil;
import org.usfirst.frc.team4525.robot.util.AutoChooseUtil.AutoFireDecision;
import org.usfirst.frc.team4525.robot.util.DriveTrainUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class BreachRockWall implements AutonomousController {

	private boolean running;
	@Override
	public void start() {
		//if(run) return;
		running = true;
		
		SubSystemsManager subsystems = SubSystemsManager.getInstance();
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				// 
				if(running) subsystems.getShooter().moveUp();
				DashUtil.getInstance().log("Moving tub up...");
				while(subsystems.getLimits().shooterHeightTopLimit() && running); // Ensure the tub is up (TODO: DEBUG)
					// limit switch logic seems like its true while open
				
				DashUtil.getInstance().log("Destroying rock wall...");
				
				// Ok now let's beast this thing
				if(running) DriveTrainUtil.driveDistanceSequential(204, 1); // bound to get some air

				if(AutoChooseUtil.getInstance().getFireSettings() == AutoFireDecision.FireRight) {
					if(running) DriveTrainUtil.spinSequential(35);
					if(running) AssistedFiring.findTargetFromTop();
					if(running)DriveTrainUtil.spinSequential(180);
				} else if(AutoChooseUtil.getInstance().getFireSettings() == AutoFireDecision.FireLeft) {
					if(running) DriveTrainUtil.spinSequential(-35);
					if(running) AssistedFiring.findTargetFromTop();
					// Turn around
					if(running)DriveTrainUtil.spinSequential(-180);
				}
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
