package org.usfirst.frc.team4525.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooseUtil {
	
	private SendableChooser autoModes;
	private SendableChooser fireMode;
	private SendableChooser whatDefense;
	
	public enum AutoMode {
		Nothing, LowBar, LowBar_lowGoal, Moat_RoughTerrain, Rock_Wall, Ramps;
	}
	
	public enum AutoFireDecision {
		FireLeft, FireRight, DontFire;
	}

	private AutoChooseUtil() {
		// Autonomous modes:
		autoModes = new SendableChooser();
		autoModes.addDefault("Do Nothing", AutoMode.Nothing);
		autoModes.addObject("Low bar", AutoMode.LowBar);
		autoModes.addObject("Low bar low goal shoot", AutoMode.LowBar_lowGoal);
		autoModes.addObject("Breach Moat/Rough Terrain", AutoMode.Moat_RoughTerrain);
		autoModes.addObject("Breach Rock Wall", AutoMode.Rock_Wall);
		autoModes.addObject("Breach ramps", AutoMode.Ramps);
		
		// Autonomous setting:
		fireMode = new SendableChooser();
		fireMode.addDefault("Fire Right",AutoFireDecision.FireRight);
		fireMode.addObject("Fire Left", AutoFireDecision.FireLeft);
		fireMode.addObject("Dont Fire", AutoFireDecision.DontFire);
		
		//Delay getter
		SmartDashboard.putString("Autonomous Start Delay", "0");
		//SmartDashboard.putString("Autonomous Shoot at Angle", "");
		
		SmartDashboard.putData("Autonomous Chooser", autoModes);
		SmartDashboard.putData("Fire Mode", fireMode);
	};
	
	private static AutoChooseUtil instance = new AutoChooseUtil();
	
	
	public static AutoChooseUtil getInstance() {
		return instance;
	}
	
	public double getAutoStartDelay() {
		double delay = Double.parseDouble(SmartDashboard.getString("Autonomous Start Delay"));
		return delay;
	}	
	
	public AutoMode getSelectedAuto() {
		return (AutoMode) autoModes.getSelected();
	}

	public AutoFireDecision getFireSettings() {
		return (AutoFireDecision) fireMode.getSelected();
	}
}
