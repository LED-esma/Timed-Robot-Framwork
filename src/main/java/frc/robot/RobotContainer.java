package frc.robot;

import com.team5430.control.ControlBoard;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive;

public class RobotContainer {
    
    // Input manager
    private final InputManager inputManager = new InputManager();

    // Joystick on USB port 0
    private final Joystick joystick = new Joystick(0);

    //ControlBoard on USB port 1
    private final ControlBoard controlBoard = new ControlBoard(1);
    
    @Logged
    protected Drive drive;

        public RobotContainer() {
        
        // Register inputs before subsystems init
            registerInputs();

            drive = new Drive(inputManager);

        }

    private void registerInputs() {
        inputManager.register("joystick/x", () -> joystick.getX(), Double.class);
        inputManager.register("joystick/y", () -> joystick.getY(), Double.class);

        inputManager.register("controlBoard/buttonA", () -> controlBoard.AlgaeOut(), Boolean.class);
        inputManager.register("controlBoard/buttonB", () -> controlBoard.AlgaeIn(), Boolean.class);
        inputManager.register("controlBoard/axisX", () -> controlBoard.ConfirmShot(), Boolean.class);
        inputManager.register("controlBoard/axisY", () -> controlBoard.ConfirmShot(), Boolean.class);
    }

    
    public void updateDashboard(){
        SmartDashboard.putNumber("MATCH TIME", DriverStation.getMatchTime());
        SmartDashboard.putString("ROBOT MODE", Constants.getRobotMode().toString());
        
    }

}
