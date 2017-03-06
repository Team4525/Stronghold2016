package org.usfirst.frc.team4525.robot.subsystem;

import org.usfirst.frc.team4525.robot.RobotConstants;
import org.usfirst.frc.team4525.robot.subsystem.impl.Cannon;
import org.usfirst.frc.team4525.robot.subsystem.impl.Climbing;
import org.usfirst.frc.team4525.robot.subsystem.impl.DriveTrainImpl;
import org.usfirst.frc.team4525.robot.subsystem.impl.LimitSwitches;
import org.usfirst.frc.team4525.robot.subsystem.impl.LoadingMechanism;
import org.usfirst.frc.team4525.robot.subsystem.impl.Sensors;
import org.usfirst.frc.team4525.robot.subsystem.impl.ShooterMechanism;
import org.usfirst.frc.team4525.robot.subsystem.impl.VisionImpl;
import org.usfirst.frc.team4525.robot.util.DashUtil;
import org.usfirst.frc.team4525.robot.util.XboxController;

public class SubSystemsManager {

	// instantiate subsystems

	private static SubSystemsManager instance = new SubSystemsManager();
	
	private XboxController driver = new XboxController(RobotConstants.Driver_Stick_Port);
	private XboxController mechanism = new XboxController(RobotConstants.Mech_Stick_Port);
	// 
	private DriveTrain driveTrain = new DriveTrainImpl();
	private Shooter cannon = new Cannon();
	//
	private ShooterMech shooterController = new ShooterMechanism();
	private LoaderMech loaderController = new LoadingMechanism();
	// Sensor related
	private VisionAiming vision = new VisionImpl();
	private SensorBase sensors = new Sensors();
	private Switches limits = new LimitSwitches();
	
	private Climber climb = new Climbing();
	
	public static SubSystemsManager getInstance(){
		return instance;
	}
	
	private SubSystemsManager(){
	 //singleton pattern	
	}
	
	
	// call init on each subsystem
	public void init(){
		DashUtil.getInstance().log("initializing subsystems");
		
		DashUtil.getInstance().log("drivetrain");
		driveTrain.init();
		
		DashUtil.getInstance().log("Vision");
		vision.init();
		
		DashUtil.getInstance().log("sensors");
		sensors.init();
		
		DashUtil.getInstance().log("loader Controller");
		loaderController.init();

		DashUtil.getInstance().log("Shooter Controller");
		shooterController.init();
		
		DashUtil.getInstance().log("Cannon");
		
		cannon.init();
		
		DashUtil.getInstance().log("Climber");
		climb.init();
		
		DashUtil.getInstance().log("attempting to load limit switches");
		limits.init();
	}
	
	public void test(){
		driveTrain.test();
		//joystick.test();
	}
	
	
	// provide getters for every substem that a control is allowed to play with.
	
	/*public Joystick getJoystick() {
		return joystick;
	} */
	
	public DriveTrain getDriveTrain() {
		return driveTrain;
	}
	
	public Shooter getShooter() {
		return cannon;
	}
	
	public VisionAiming getVision() {
		return vision;
	}
	
	public ShooterMech getShooterMech() {
		return shooterController;
	}
	
	public LoaderMech getLoaderMech() {
		return loaderController;
	}
	
	public SensorBase getSensors() {
		return sensors;
	}
	
	public Switches getLimits() {
		return limits;
	}
	
	public XboxController getDriver() {
		return driver;
	}
	
	public XboxController getMechanismController() {
		return mechanism;
	}
	
	public Climber getClimber() {
		return climb;
	}
	
}
