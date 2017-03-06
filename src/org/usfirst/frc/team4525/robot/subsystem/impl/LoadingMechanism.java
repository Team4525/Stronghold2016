package org.usfirst.frc.team4525.robot.subsystem.impl;

import java.util.Date;

import org.usfirst.frc.team4525.robot.RobotConstants;
import org.usfirst.frc.team4525.robot.subsystem.LoaderMech;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.subsystem.Switches;

import edu.wpi.first.wpilibj.Victor;

public class LoadingMechanism implements LoaderMech {

	// Spike Relays:
	//private DigitalInput loaderOnOff = new DigitalInput(0); // dont know the number yet but dio 0 is for encoders so
	
	private SubSystemsManager subsystems;
	
	// rack logic
	private Victor rackPositioner; // motor that changes the position of the rack
	private Victor rackSpinner; // motor that spins the ball shooter rack thing
	
	 
	// The Nice Rack booleans
	private boolean movingRackDown = false;
	private boolean movingRackUp = false;
	
	
	//
	private Switches limits;
	
	public void init() {
		subsystems = SubSystemsManager.getInstance(); 
		
		rackPositioner = new Victor(RobotConstants.rackAdjustMotor);
		rackSpinner = new Victor(RobotConstants.rackSpinnerMotor);
		
		limits = subsystems.getLimits();
	}
	
	public void test() {

	}

	public synchronized void flipOntoShooter() {
		if(!movingRackUp){
			movingRackUp = true;
			movingRackDown = false; // in case it was;
			Thread t = new Thread(new Runnable() { // inline code
				
				@Override
				public void run() {
					long startTime = new Date().getTime();
					
					while(limits.rackTopSwitch() && movingRackUp){
						rackPositioner.set(-0.9);
						long currentTime = new Date().getTime();
						if( (currentTime - startTime) > 2000 ){
							movingRackUp = false;
						}
					}
					rackPositioner.set(0);
					movingRackUp = false;
				}
			});
			t.start();
			
		}
		
//		if(movingRackUp) movingRackUp = false; //stop up thread
//		if(!limits.rackTopSwitch()) return; // false is switch closed.
//		//
//		if(!movingRackDown && !movingRackUp) {
//			extendRack = new Thread(new RackIn());
//			extendRack.start();
//		}
	}

	public synchronized void flipToSuckInBall() {
		if(!movingRackDown){
			movingRackDown = true;
			movingRackUp = false; // in case it was
			Thread t = new Thread(new Runnable() { // inline coding style
				long startTime = new Date().getTime();
				
				@Override
				public void run() {
					while(!limits.rackLoadPositionSwitch() && movingRackDown){
						rackPositioner.set(0.9);
						long currentTime = new Date().getTime();
						if( (currentTime - startTime) > 2000 ){
							movingRackDown = false;
						}
					}
					rackPositioner.set(0);
					movingRackDown = false;
				}
			});
			t.start();
		}
		
		
		
//		if(movingRackDown) movingRackDown = false;
//		// Check
//		if(limits.rackLoadPositionSwitch() ) return; // false is switch closed
//		//
//		if(!movingRackDown && !movingRackUp) {
//			retractRack = new Thread(new RackOut());
//			retractRack.start();
//		}
	}

	public void off() {
		rackSpinner.set(0);
	}	
	
	public void suckInBall() {
		rackSpinner.set(-1); // suppose to be positive
	}

	public void pushOutBall() {
		rackSpinner.set(1);
	}

//	public class RackOut implements Runnable {
//		// moves rack down onto bottom
//		public void run() {
//			movingRackDown = true; // shooter needs to be down to trigger the rack switch
//			while (movingRackDown && limits.rackLoadPositionSwitch() && !limits.shooterHeightBottomLimit()){
//				rackPositioner.set(1); // puts rack to load position
//			}
//			rackPositioner.set(0);
//			movingRackDown = false;
//		}
//			
//	}
//	
//	public class RackIn implements Runnable {
//		// moves rack up on top
//		public void run() {
//			movingRackUp = true;
//			while (movingRackUp && limits.rackTopSwitch()){
//				rackPositioner.set(-1); // moves rack to top of shooter
//			}
//			rackPositioner.set(0);
//			movingRackUp = false;
//		}
//		
//	}

}
