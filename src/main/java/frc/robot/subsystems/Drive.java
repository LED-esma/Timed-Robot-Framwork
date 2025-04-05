package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import frc.robot.Constants;
import frc.robot.InputManager;

public class Drive {

//motor controllers
    private TalonSRX BL;
    private TalonSRX BR;
    private TalonSRX FL;
    private TalonSRX FR;

//kinematics object to calculate wheel speeds
private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(0.5);

private ChassisSpeeds kZero = new ChassisSpeeds();


//suppliers for inputs
    @Logged(name = "inputs/x")
    private DoubleSupplier x;

    @Logged(name = "inputs/y")
    private DoubleSupplier y;

    // supplier for the robot mode


//constructor that takes an InputManager object
    public Drive(InputManager im) {

        // suppliers that get the x and y values from the joystick when called
        x = () -> im.get("joystick/x" , Double.class);
        y = () -> im.get("joystick/y", Double.class);


        // Initialize the motors
        BL = new TalonSRX(Constants.DriveConstants.BL);
        BR = new TalonSRX(Constants.DriveConstants.BR);
        FL = new TalonSRX(Constants.DriveConstants.FL);
        FR = new TalonSRX(Constants.DriveConstants.FR);

        motorConfig();
    }

//method to configure the motors
    private void motorConfig(){

        // Set the back motors to follow the front motors   
        BL.set(ControlMode.Follower, FL.getDeviceID());
        BR.set(ControlMode.Follower, FR.getDeviceID());

        // Set the motors to be inverted
        BL.setInverted(true);
        FL.setInverted(true);

    }


//method to control the motors
    private void control(ChassisSpeeds speeds) {

    // Calculate the speed for each motor based on the chassis speeds
        var wheels = kinematics.toWheelSpeeds(speeds);

    // Set the motor speeds
        FL.set(ControlMode.PercentOutput, wheels.leftMetersPerSecond);
        FR.set(ControlMode.PercentOutput, wheels.rightMetersPerSecond);

    }


// Method to handle TELEOP mode
    private void handleTeleop() {
        // Get the x and y values from the joystick
        double xValue = x.getAsDouble();
        double yValue = y.getAsDouble();

        // convert the joystick values to chassis speeds and plug them into the control method
        var speeds = new ChassisSpeeds(yValue, 0, xValue);
        control(speeds);
    }

// Method to handle AUTO mode
    private void handleAuto() {
        //TODO: Implement auto mode
        var autoSpeeds = new ChassisSpeeds();
        control(autoSpeeds);
    }

// Method to handle DISABLED mode
    private void handleDisabled() {
        // Stop the motors
        control(kZero);
    }

    // execute method that is called periodically
    public void execute() {
        switch (Constants.getRobotMode()) {
            case TELEOP -> handleTeleop();
            case AUTO -> handleAuto();
            case DISABLED -> handleDisabled();
        }
    }

}


