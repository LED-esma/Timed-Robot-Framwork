package com.team5430.control;

import edu.wpi.first.wpilibj.GenericHID;

public class ControlBoard {

    private GenericHID USBencoder;

    public ControlBoard(int port) {
        USBencoder = new GenericHID(port);
    }

    public boolean L1() {
        return USBencoder.getRawButton(0);
    }

    public boolean L2() {
        return USBencoder.getRawButton(3);
    }

    public boolean L3() {
        return USBencoder.getRawButton(5);
    }

    public boolean L4() {
        return USBencoder.getRawButton(1);
    }

    public boolean ConfirmShot() {
        return USBencoder.getRawButton(2);
    }

    public boolean AlgaeIn() {
        return USBencoder.getRawButton(4);
    }

    public boolean AlgaeOut() {
        return USBencoder.getRawButton(12);
    }

    public boolean OverrideSwitch() {
        return USBencoder.getRawButton(12);
    }
}