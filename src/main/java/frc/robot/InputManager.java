package frc.robot;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class InputManager {

    //map to store the inputs
    private final Map<String, InputEntry<?>> inputs = new HashMap<>();

    //register the input
    public <T> void register(String key, Supplier<T> supplier, Class<T> type) {
        inputs.put(key, new InputEntry<>(supplier, type));
    }

    public <T> T get(String key, Class<T> type) {

        // Check if the input is registered
        if (!inputs.containsKey(key)) {
            throw new IllegalArgumentException("Input not registered: " + key);
            }
        InputEntry<?> entry = inputs.get(key);

        // Check if the key is registered
        if (entry == null) {
            throw new IllegalArgumentException("Key not registered: " + key);
            }
        // Check if the key is registered
        if (!inputs.containsKey(key)) {
    throw new IllegalArgumentException("Input not registered: " + key);
            }   
        //check if the type is correct
        if (!type.equals(entry.type)) {
            throw new ClassCastException("Type mismatch for key '" + key + "'. Expected: " + type + ", Actual: " + entry.type);
            }
        @SuppressWarnings("unchecked")
        //get the value
        T value = ((InputEntry<T>) entry).supplier.get();
        return value;
    }

    //get the inputs
    public HashMap<String, InputEntry<?>> getInputs() {
        return (HashMap<String, InputEntry<?>>) inputs;
    }

    

    //class to store the input
    private static class InputEntry<T> {
        final Supplier<T> supplier;
        final Class<T> type;
    
        InputEntry(Supplier<T> supplier, Class<T> type) {
            this.supplier = supplier;
            this.type = type;
        }
    }
}

