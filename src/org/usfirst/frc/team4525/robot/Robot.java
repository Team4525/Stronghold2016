
package org.usfirst.frc.team4525.robot;

import org.usfirst.frc.team4525.robot.controller.AutonomousController;
import org.usfirst.frc.team4525.robot.controller.OperatorController;
import org.usfirst.frc.team4525.robot.controller.impl.XboxDriverController;
import org.usfirst.frc.team4525.robot.controller.impl.auto.BreachMoatRough;
import org.usfirst.frc.team4525.robot.controller.impl.auto.BreachRamps;
import org.usfirst.frc.team4525.robot.controller.impl.auto.BreachRockWall;
import org.usfirst.frc.team4525.robot.controller.impl.auto.LowbarLowGoal;
import org.usfirst.frc.team4525.robot.controller.impl.auto.LowbarShoot;
import org.usfirst.frc.team4525.robot.subsystem.SubSystemsManager;
import org.usfirst.frc.team4525.robot.util.AutoChooseUtil;
import org.usfirst.frc.team4525.robot.util.AutoChooseUtil.AutoMode;
import org.usfirst.frc.team4525.robot.util.DashUtil;

import edu.wpi.first.wpilibj.Timer;
/*** 
 * @author jmguillemette
 *
 */
public class Robot extends RobotBase {
	
	private SubSystemsManager subSystemsManager = SubSystemsManager.getInstance();
	private DashUtil dashUtil = DashUtil.getInstance();
	
	private OperatorController manual = new XboxDriverController();
	private AutoChooseUtil autos = AutoChooseUtil.getInstance();

	private AutonomousController autoMode;
	
	public Robot() {
		dashUtil.log("==========================");
		dashUtil.log("= Robot 4525 Stating up! =");
	}
    
    public void robotInit() {
		dashUtil.log("ROBOT INIT");
		subSystemsManager.init();
	}

    public void autonomous() {
    	dashUtil.clear();
    	dashUtil.log("AUTO");
    	//subSystemsManager.getDriveTrain().setSafety(false);
    	subSystemsManager.getDriveTrain().start();
    	// AutoMode selector
    	if(autos.getAutoStartDelay() > 0) {
    		dashUtil.log("Delaying Autonomous for " + Double.toString(autos.getAutoStartDelay()) + " seconds");
    		Timer.delay(autos.getAutoStartDelay());
    	}
    	if(autos.getSelectedAuto() == AutoMode.LowBar) {
    		autoMode = new LowbarShoot();
    	} else if(autos.getSelectedAuto() == AutoMode.Moat_RoughTerrain) {
    		autoMode = new BreachMoatRough();
    	} else if(autos.getSelectedAuto() == AutoMode.Rock_Wall) {
    		autoMode = new BreachRockWall();
    	} else if(autos.getSelectedAuto() == AutoMode.Ramps) {
    		autoMode = new BreachRamps();
    	} else if(autos.getSelectedAuto() == AutoMode.LowBar_lowGoal) {
    		autoMode = new LowbarLowGoal();
    	}
    		
    	if(autoMode != null) {
    		dashUtil.log("Running Auto " + autoMode.toString());
    		autoMode.start();
    	}
    }

    public void operatorControl() {
    	dashUtil.clear();
    	dashUtil.log("OPER");
    	//subSystemsManager.getDriveTrain().setSafety(false);
    	subSystemsManager.getDriveTrain().start();
    	manual.start();
    }

    public void test() {
		subSystemsManager.test();
    }
    
    protected void disabled() {
    	//dashUtil.clear();
		dashUtil.log("Robot disabled.");
    	// Disable everything
		if(autoMode != null) autoMode.stop();
		manual.stop();
		//subSystemsManager.getDriveTrain().setSafety(true);
		subSystemsManager.getDriveTrain().stop();
    	super.disabled();
    }
}
