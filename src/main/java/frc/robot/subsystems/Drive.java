package frc.robot.subsystems;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import frc.robot.InputManager;
import frc.robot.Constants.ROBOT_MODE;

public class Drive {

//motor controllers
    private TalonSRX BL;
    private TalonSRX BR;
    private TalonSRX FL;
    private TalonSRX FR;

private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(0.5);

private ChassisSpeeds kZero = new ChassisSpeeds();


//suppliers for inputs
    @Logged(name = "inputs/x")
    private DoubleSupplier x;

    @Logged(name = "inputs/y")
    private DoubleSupplier y;

    // supplier for the robot mode
    private Supplier<ROBOT_MODE> mode;


//constructor that takes an InputManager object
    public Drive(InputManager im) {

        // suppliers that get the x and y values from the joystick when called
        x = () -> im.get("joystick/x" , Double.class);
        y = () -> im.get("joystick/y", Double.class);
        mode = () -> im.get("Constants/ROBOT_MODE", ROBOT_MODE.class);


        // Initialize the motors
        BL = new TalonSRX(0);
        BR = new TalonSRX(1);
        FL = new TalonSRX(2);
        FR = new TalonSRX(3);

        motorConfig();
    }

//method to configure the motors
    private void motorConfig(){

        // Set the motors to follow each other
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
        BL.set(ControlMode.PercentOutput, wheels.leftMetersPerSecond);
        BR.set(ControlMode.PercentOutput, wheels.rightMetersPerSecond);
        FL.set(ControlMode.PercentOutput, wheels.leftMetersPerSecond);
        FR.set(ControlMode.PercentOutput, wheels.rightMetersPerSecond);

    }


// execute method that is called periodically
    public void execute() {
        switch (mode.get()) {
                case TELEOP -> {
                    // Get the x and y values from the joystick
                    double xValue = x.getAsDouble();
                    double yValue = y.getAsDouble();

                    // Create a ChassisSpeeds object with the x and y values
                    var speeds = new ChassisSpeeds(yValue, 0, xValue);

                    // Call the control method with the ChassisSpeeds object
                    control(speeds);
                }
                case AUTO -> {
                    // Needs setup with some sensors

                    var autoSpeeds = new ChassisSpeeds();

                    // Call the control method with the auto speeds
                    control(autoSpeeds);

                }
                case DISABLED -> {
                    // If the robot is disabled, set the motor speeds to zero
                    control(kZero);
                }
            }
        }

    }
    

