package org.usfirst.frc.team4525.robot;

public class RobotConstants {

	/***
	 * Constants Pattern!
	 * Do not make an instance of this class.. instead access the variables directly
	 * example:  RobotConstant.MY_VALUE
	 */
	private RobotConstants(){}
	// Mode:
	public static final boolean debugMode = true; // Outputs all sensor data to smart dashboard.
	
	// Controller settings
	public static final int Driver_Stick_Port = 0;
	public static final int Mech_Stick_Port = 1;
	
	// Drivetrain settings
	public static final int[] driveTrain_left = {0,1};
	public static final int[] driveTrain_right = {2,3};
	
	public static final double normalSpeedCap = 1;
	
	// Mechanism Settings
	public static final int[] shooterMotors = {6,7}; // the motors on the front shooter
	public static final double suckerInnerRate = 0.85; // rate that the shooter sucks the ball in
	
	public static final int rackAdjustMotor = 4;	
	public static final int heightAdjustMotor = 5; // shooter height adjust
	
	public static final int rackSpinnerMotor = 8;
	
	// Solenoids
	
	public static final int[] gasPistonRelease = {0,1};
	public static final int[] hookPistons = {2,3};
	
	// Limit Switches
	public static final int rackTopLimit = 4;
	public static final int rackLowerLimit = 7;
	public static final int shooterLowerLimit = 6;
	public static final int shooterRaiseLimit = 5;
	
	// Sensor settings
	public static final int analogGyroInput = 0;
	
	public static final int[] left_encoder = {0,1}; // DIO (DigitalInputOutput)
	public static final int[] right_encoder = {2,3};
	
	// Inches to Encoder Pulses Ratio:
	public static final double inchToPulseRatio = 0.555555556;//0.62305296;
	public static final double encoderPulsePerRevolution = 0.051;
	
	// Other settings
	public static final double spinDeadzone = 5;
	
}
