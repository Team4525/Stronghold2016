package org.usfirst.frc.team4525.robot.subsystem.impl;

import org.usfirst.frc.team4525.robot.subsystem.LoaderMech;
import org.usfirst.frc.team4525.robot.subsystem.Shooter;
import org.usfirst.frc.team4525.robot.subsystem.ShooterMech;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.util.DashUtil;

import edu.wpi.first.wpilibj.Timer;

public class Cannon implements Shooter {

	private ShooterMech shooter;
	private LoaderMech loader;
	private boolean firing = false;
	
	
	@Override
	public void init() {
		// Start the other subsystems
		SubSystemsManager subsystems = SubSystemsManager.getInstance();
		shooter = subsystems.getShooterMech();
		loader = subsystems.getLoaderMech();
	}
	
	@Override
	public void test() {
		// TODO Auto-generated method stub

	}

	@Override
	public void startLoad() {
		loader.flipToSuckInBall();
		shooter.load();
		loader.suckInBall();
	}

	@Override
	public void stopLoad() {
		shooter.fireStop();
		loader.off();
	}
	
	public void suckBack() {
		loader.flipOntoShooter();
		shooter.load();
		loader.suckInBall();
	}

	// kills thread after firing
	public synchronized void fire() {
			shooter.fire();
			DashUtil.getInstance().log("FIRE1");
			Timer.delay(1);
			loader.pushOutBall();
			DashUtil.getInstance().log("FIRE2");
			Timer.delay(1);
			shooter.fireStop();
			loader.off();
		
	}

	// so when we stop pressing the trigger the firing also stops
	public void cancel() {
		loader.off();
		shooter.fireStop();
	}

	// moves shooter tub up
	public void moveUp() {
		shooter.up();
	}

	// moves shooter tub down
	public void moveDown() {
		shooter.down();
	}

	// stops tub from moving
	public void dontMove() {
		shooter.stopMoving();
	}
	
	
	

	// flips rack onto shooter
	public void rackToTop() {
		loader.flipOntoShooter();
	}

	// moves rack to load position (out front of robot)
	public void rackToLoadPos() {
		loader.flipToSuckInBall();
	}	
	
	

}
