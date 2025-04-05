package frc.robot;

import java.util.function.BooleanSupplier;

public class Constants {

    private static ROBOT_MODE currentRobotMode = ROBOT_MODE.DISABLED;

    public enum ROBOT_MODE {
        TELEOP,
        AUTO,
        DISABLED
    }

    public static ROBOT_MODE getRobotMode() {
        return currentRobotMode;
    }

    public static void setRobotMode(ROBOT_MODE mode) {
        currentRobotMode = mode;
    }

    public BooleanSupplier isTeleopMode() {
        return () -> getRobotMode() == ROBOT_MODE.TELEOP;
    }

    public BooleanSupplier isAutoMode() {
        return () -> getRobotMode() == ROBOT_MODE.AUTO;
    }

    public BooleanSupplier isDisabledMode() {
        return () -> getRobotMode() == ROBOT_MODE.DISABLED;
    }

    public static class DriveConstants {
        public static final int BL = 0;
        public static final int BR = 1;
        public static final int FL = 2;
        public static final int FR = 3;
    }
}
