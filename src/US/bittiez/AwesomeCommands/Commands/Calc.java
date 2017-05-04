package US.bittiez.AwesomeCommands.Commands;

import US.bittiez.AwesomeCommands.Math.EvalUtil;
import US.bittiez.AwesomeCommands.STATIC;
import org.bukkit.command.CommandSender;

public class Calc implements Runnable {
    private CommandSender who;
    private String[] args;
    public Calc(CommandSender who, String[] args){

        this.who = who;
        this.args = args;
    }
    @Override
    public void run() {
        try {
            String eval = String.join(" ", args);
            who.sendMessage(STATIC.applyACPrefix(eval + " = " + EvalUtil.eval(eval).toString()));
        } catch (Exception e) {
            who.sendMessage(STATIC.applyACPrefix("That doesn't appear to be a valid math statement."));
        }
    }
}
