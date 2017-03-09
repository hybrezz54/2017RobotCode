package org.usfirst.frc.team5518.robot.commands;

import org.usfirst.frc.team5518.robot.Robot;
import org.usfirst.frc.team5518.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class LeftAuto extends BaseAuto {
	
	public LeftAuto() {
	}
	
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		if (System.currentTimeMillis() - startTime < forwardTimeMsec) {
			System.out.println("Drive Forward");
			Robot.driveTrain.driveAuto(RobotMap.FAST_SPEED, 0.1);
		}
		else if (System.currentTimeMillis() - (startTime + forwardTimeMsec) < turnTimeMsec) {
			//Turn for some time
			System.out.println("Turning RIGHT");
			Robot.driveTrain.driveAuto(0, -RobotMap.TURN_SPEED);
		}
		else {
			// Place gear
			System.out.println("Timer Expired");
			//Timer.delay(0.25);
			placeGear();
		}
	}
}

