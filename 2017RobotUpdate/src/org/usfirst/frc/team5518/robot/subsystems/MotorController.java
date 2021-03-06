package org.usfirst.frc.team5518.robot.subsystems;

import org.usfirst.frc.team5518.robot.RobotMap;
import org.usfirst.frc.team5518.robot.commands.RunMotor;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 */
public class MotorController extends Subsystem { //for motors
	public VictorSP intakeMotor;
//	public VictorSP shooterMotor;
	public VictorSP winchMotor;
	public VictorSP loadMotor;
	
	
	public double intakeMotorSpeed;
//	public double shooterMotorSpeed;
	public double loadMotorSpeed;
	
	public Servo leftServo;
	public Servo rightServo;
	
	public boolean doorState;
	
	public MotorController() {
		intakeMotor = new VictorSP(RobotMap.INTAKE_PORT_NUMBER);
//		shooterMotor = new VictorSP(RobotMap.SHOOTER_PORT_NUMBER);
		winchMotor = new VictorSP(RobotMap.WINCH_PORT_NUMBER);
		loadMotor = new VictorSP(RobotMap.LOAD_PORT_NUMBER);
		
		intakeMotorSpeed = 2/3;
//		shooterMotorSpeed = .65;
		loadMotorSpeed = -0.25;
		
		rightServo = new Servo(RobotMap.RIGHT_DOOR_SERVO);
		leftServo = new Servo(RobotMap.LEFT_DOOR_SERVO);
	}
	
	public void initDefaultCommand() {
	    // Set the default command for a subsystem here.
	    setDefaultCommand(new RunMotor());
	}
	
	public void runWinchMotor(double speed, boolean slow) { //WINCH
		if (speed != 0) {
			System.out.println("Run winch motor");
			if (!slow) {
				winchMotor.set(speed);
			}
			else if (slow) {
				winchMotor.set(speed / 2);
			}
		}
	}
	
	public void runIntakeMotor(double speed, boolean reverse) { //INTAKE
		if (!reverse) {
			intakeMotor.set(speed);
		}
		else if (reverse) {
			intakeMotor.set(speed * -1);
		}
	}
	
	public void toggleDoors(boolean open) {
    	if (open) {
    		openDoors();
    	}
    	else {
    		closeDoors();
    	}
    }
	
	public void openDoors() {
		leftServo.setAngle(35); //Decrease to open more
		rightServo.setAngle(165); //Increase to open more
		doorState = true;
		System.out.println("-----------------OPEN DOORS-----------------");
	}
	public void closeDoors() {
		leftServo.setAngle(115); //Increase to close more
		rightServo.setAngle(85); //Decrease to closes more
		doorState = false;
		System.out.println("-----------------CLOSE DOORS-----------------");
	}
	
//	public void runShooterMotor(double speed) { //SHOOTER
//		shooterMotor.set(speed * shooterMotorSpeed);
//	}
	
	public void runLoadingMotor(int go) { //LOAD
		loadMotor.set(loadMotorSpeed * go);
	}
	
	public void getData(boolean isLocked) {
//		SmartDashboard.putNumber("Intake motor speed", intakeMotor.get());
//		SmartDashboard.putNumber("Shooter motor speed", shooterMotor.get());
//		SmartDashboard.putNumber("Load motor speed", loadMotor.get());
//		SmartDashboard.putNumber("Winch motor speed", winchMotor.get());
//		SmartDashboard.putNumber("Left Servo Value", leftServo.getAngle());
//		SmartDashboard.putNumber("Right Servo Value", rightServo.getAngle());
		if (doorState) {
			SmartDashboard.putString("Gear door state", "open");
		}
		else if (!doorState) {
			SmartDashboard.putString("Gear door state", "closed");
		}
		if (isLocked) {
			SmartDashboard.putString("Winch Lock state", "locked");
		}
		else {
			SmartDashboard.putString("Winch Lock state", "not locked");
		}
	}
}

