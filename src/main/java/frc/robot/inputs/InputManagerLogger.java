package frc.robot.inputs;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.logging.ClassSpecificLogger;
import edu.wpi.first.epilogue.logging.EpilogueBackend;

@CustomLoggerFor(InputManager.class)
public class InputManagerLogger extends ClassSpecificLogger<InputManager>{

    public InputManagerLogger() {
        super(InputManager.class);
    }

    @Override
    protected void update(EpilogueBackend backend, InputManager im) {
        // Log the inputs
        im.getInputs().forEach((key, entry) -> {
            var value = entry.supplier.get();
            if (value != null) {
                // Log the value based on its type
                    if (value instanceof Integer) {
                        backend.log(key, (int) value);
                    } else if (value instanceof Double) {
                        backend.log(key, (double) value);
                    } else if (value instanceof Boolean) {
                        backend.log(key, (boolean) value);
                    } else {
                        backend.log(key, value.toString());
                    }
                        } else {
                // Log null values
                backend.log(key, "null");
                        }
            }
        );
    }
    
}