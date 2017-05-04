package US.bittiez.AwesomeCommands.Math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvalUtil {
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    public static Object eval(String input) throws Exception {
        try {
            if (input.matches(".*([;])|(new)+.*")) {
                throw new Exception("Invalid expression : " + input);
            }
            return engine.eval(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

