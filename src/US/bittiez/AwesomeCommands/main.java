package US.bittiez.AwesomeCommands;

import US.bittiez.Config.Configurator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class main extends JavaPlugin implements Listener {
    private static Logger log;
    private Configurator configurator = new Configurator();

    //NPRestart
    private boolean npRestart = false;

    @Override
    public void onEnable() {
        log = getLogger();
        configurator.saveDefaultConfig(this);
        configurator.setConfig(this);

        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender who, Command cmd, String label, String[] args) {
        switch (cmd.getName().toLowerCase()) {
            case "nprestart":
                return npRestart(who);
            case "npr":
                return npRestart(who);
        }
        return false;
    }

    //Command handlers
    //
    //
    //
    private boolean npRestart(CommandSender who) {
        if (!configurator.config.getBoolean("NPRestart"))
            return false;

        if (who.hasPermission(PERMISSIONS.ADMIN.NP_RESTART)) {
            npRestart = !npRestart;
            if(npRestart)
                who.sendMessage(STATIC.applyACPrefix("&aThe server will restart when the last player logs off."));
            else
                who.sendMessage(STATIC.applyACPrefix("&aThe server will &6not &a restart when the last player logs off."));
        }
        return false;
    }



    //Event handlers
    //
    //
    //
    @EventHandler
    public void onPlayerLogOff(PlayerQuitEvent e){
        if(getServer().getOnlinePlayers().size() < 1){
            getServer().spigot().restart();
        }
    }
}
