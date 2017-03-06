package org.usfirst.frc.team4525.robot.controller.impl;

import org.usfirst.frc.team4525.robot.controller.OperatorController;
import org.usfirst.frc.team4525.robot.subsystem.Climber;
import org.usfirst.frc.team4525.robot.subsystem.DriveTrain;
import org.usfirst.frc.team4525.robot.subsystem.Shooter;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.util.AssistedFiring;
import org.usfirst.frc.team4525.robot.util.DashUtil;
import org.usfirst.frc.team4525.robot.util.DriveTrainUtil;
import org.usfirst.frc.team4525.robot.util.DriverAimUtil;
import org.usfirst.frc.team4525.robot.util.XboxController;

public class XboxDriverController implements OperatorController{

	private SubSystemsManager subsystems = SubSystemsManager.getInstance();
	private boolean running = false;
	private double deadY = 0.25;
	private double deadX = 0.2;
	
	public void start() {
		running = true;
		XboxController driver = subsystems.getDriver();
		XboxController mechs = subsystems.getMechanismController();
		DriveTrain driveTrain = subsystems.getDriveTrain();
		Shooter shooter = subsystems.getShooter();
		Climber climb = subsystems.getClimber();
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				boolean rackToggled = false;
				boolean buttonA = false;
				boolean fire = false;
				boolean load = false;
				boolean suck = false;
				boolean assistedFire = false;
				boolean shooterMove = false;
				boolean driverAbutton = false; 
				boolean startPressed = false;
				double startDown = 0;
				
				boolean climbing = false;
				
				while(running){
					
					// Driving
					double power = driver.getAxis(XboxController.Axis.LeftY);
					double rotate = driver.getAxis(XboxController.Axis.RightX);
					
					if((power > 0 && power < deadY) || (power < 0 && power > -deadY)) power = 0;
					if((rotate > 0 && rotate < deadX) || (rotate < 0 && rotate > -deadX)) rotate = 0;	
					
					//Oh wow, yikes let's do some vision aiming
					if(driver.getButton(XboxController.Button.A) && driverAbutton == false) {
						driverAbutton = true;
						DriverAimUtil.AimX(10);
					} else if(driver.getButton(XboxController.Button.A) == false && driverAbutton == true){
						DriverAimUtil.stop();
						driverAbutton = false;
					} else { // Looks like we're just driving now
						driveTrain.arcadeDrive(power, rotate);
					}
					
					// End Game:
					if(driver.getButton(XboxController.Button.BumperL) && driver.getButton(XboxController.Button.BumperR) && !climb.isReady()) {
						climb.prepareToclimb();
					}
					
					if(driver.getButton(XboxController.Button.X) && climb.isReady()) {
						climb.fireHooks();
					} else if(driver.getButton(XboxController.Button.B) && climb.hooksAreReady()) {
						climb.climb();		
					}
					
					// Gyro reset
			/*		if(driver.getButton(XboxController.Button.Start) && startPressed == false) {
						subsystems.getSensors().resetHeading();
						startPressed = true;
					} else if(startPressed == true && driver.getButton(XboxController.Button.Start) == false) {
						startPressed = false;
					} */
					
					// Mechanisms
					
					// Loading
					if(mechs.getAxis(XboxController.Axis.LeftY) < -0.5) {
						shooterMove = true;
						shooter.moveUp();
					} else if(mechs.getAxis(XboxController.Axis.LeftY) > 0.5){
						shooter.moveDown();
						shooterMove = true;
					} else if (mechs.getAxis(XboxController.Axis.LeftY) > -0.5 && mechs.getAxis(XboxController.Axis.LeftY) <0.5 && shooterMove){
						shooter.dontMove();
						shooterMove = false;
					}

					// rack flipping
					if(buttonA == false && mechs.getButton(XboxController.Button.A)){
						buttonA = true;
						if(!rackToggled) {
							rackToggled = true;
							shooter.rackToTop();
						} else {
							shooter.rackToLoadPos();
							rackToggled = false; 
						}
					}else if(buttonA == true && !mechs.getButton(XboxController.Button.A)){							
						buttonA = false;
					}
					
					// loading
					if(mechs.getAxis(XboxController.Axis.TriggerL) > 0.5) {
						rackToggled = true; // toggle the rack so we can flip over
						load = true;
						shooter.startLoad();
					} else if( mechs.getAxis(XboxController.Axis.TriggerL) < 0.5 && load == true)  {
						shooter.stopLoad();
						load = false;
					}
					// loading pull back if ball doesnt get fully sucked in
						
					if(mechs.getButton(XboxController.Button.Y)) {
						suck = true;
						shooter.suckBack();
						rackToggled = true;
					}else if(mechs.getButton(XboxController.Button.Y)==false && suck == true){
						shooter.cancel();
						suck = false;
					}	
					// shooter adjusting
						
					// aim assist fire
					if(mechs.getAxis(XboxController.Axis.TriggerR) > 0.5) {
						fire = true;
						shooter.fire();	
					}
					
					if(mechs.getButton(XboxController.Button.BumperR) && assistedFire ==false){
						assistedFire = true;
						AssistedFiring.findTargetFromTop(); // will fire too
					}else if(!mechs.getButton(XboxController.Button.BumperR) && assistedFire ==true){
						DashUtil.getInstance().log("STOPPPPPPP");
						AssistedFiring.stop();
						assistedFire = false;
					}	
					//	
				}
			}
		});
		t.start();
	}
	
	
	public void stop() {
		DashUtil.getInstance().log("Oper stop requested");
		running = false;
	}
}
