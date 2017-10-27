
package org.usfirst.frc.team5518.robot;

import org.usfirst.frc.team5518.robot.commands.BasicDrive;
import org.usfirst.frc.team5518.robot.commands.CenterAuto;
import org.usfirst.frc.team5518.robot.commands.DriveForwardAuto;
import org.usfirst.frc.team5518.robot.commands.BaseAuto;
import org.usfirst.frc.team5518.robot.commands.LeftAuto;
import org.usfirst.frc.team5518.robot.commands.RightAuto;
import org.usfirst.frc.team5518.robot.subsystems.DriveTrain;
//import org.usfirst.frc.team5518.robot.subsystems.FuelShooter;
//import org.usfirst.frc.team5518.robot.subsystems.GearTransfer;
import org.usfirst.frc.team5518.robot.subsystems.MotorController;
//import org.usfirst.frc.team5518.robot.OI;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * @author Hamzah Chaudhry
 */
public class Robot extends IterativeRobot {
	
	// Robot subsystems
	public static DriveTrain driveTrain;
	public static MotorController motorController;
	
//	public static UsbCamera camera;
	
	// Camera properties
	/*public static final int IMG_WIDTH = 1280;
	public static final int IMG_HEIGHT = 720;*/
	
	// Robot autonomous commands
	Command auto;
	Command basicDrive;
	Command driveForwardAuto;
	Command runMotor;
	
	// Autonomous command selector for driver dashboard
	SendableChooser<Command> chooser;
	
	// proximity range finder sensor
	public static Ultrasonic ultra;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// init drivetrain subsystem
		driveTrain = new DriveTrain();
		motorController = new MotorController();
		basicDrive = new BasicDrive();
		
//		camera = CameraServer.getInstance().startAutomaticCapture();
//		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
//		camera.setExposureAuto();
//		camera.setExposureManual(60);
//		camera.setExposureHoldCurrent();
		
		// init autonomous command selector
		chooser = new SendableChooser<>();
		
		// add automous commands to chooser
		chooser.addObject("Drive Forward", new DriveForwardAuto());
		chooser.addDefault("Center Auto", new CenterAuto());
		chooser.addObject("Left Auto", new LeftAuto());
		chooser.addObject("Right Auto", new RightAuto());
		
		SmartDashboard.putData("Choose an auto mode: ", chooser);
		
		// set up ultrasonic sensor
		ultra = new Ultrasonic(1, 0);
    	ultra.setAutomaticMode(true);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
//		System.out.println("disableInit()");
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		System.out.println("-----------------------------AUTO INIT-----------------------------");
		
//		System.out.println("-----------------CLOSE DOORS-----------------");
		
		Robot.motorController.closeDoors();
		
//		for (int i = 0; i < 10; i++) {
//			Robot.motorController.openDoors();
//		}
		
		auto = chooser.getSelected();
		//auto = new LeftAuto();
		
//		String autoSelected = SmartDashboard.getString("Auto Selector", "Default Auto");
//		switch(autoSelected) {
//			case "Default Auto":
//				auto = new DriveForwardAuto();
//				System.out.println("START DEFAULT AUTO");
//				break;
//		}
		
//		System.out.println("autonomousInit()  " + auto.getName());
		// schedule the autonomous command (example)
		if (auto != null) {
			auto.start();
			((BaseAuto)chooser.getSelected()).reset();
//			System.out.println("Base Auto reset called");
//			basicDrive.cancel();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
//		System.out.println("autoPeriodic()");
//		System.out.println("THIS IS OUR CODE");
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() { //
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		System.out.println("teleopInit()");
//		if (autonomousCommand != null)
//			autonomousCommand.cancel();
		
		
		// cancel autonomous command
		if (auto != null) {
			auto.cancel();
//			basicDrive.start();
		}
		
//		camera.setExposureAuto();
//		camera.setExposureHoldCurrent();
		// closes gear doors
		Robot.motorController.closeDoors();
		//ultra.free();
		
		System.out.println("-----------------------------TELEOP INIT-----------------------------");
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		// If you don't call this the commands won't run. The commands are registered
		// when the subsystems are created.
		Scheduler.getInstance().run();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

		LiveWindow.run();
	}
}
