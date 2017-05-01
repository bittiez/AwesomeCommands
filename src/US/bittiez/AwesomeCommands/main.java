package US.bittiez.AwesomeCommands;

import US.bittiez.AwesomeCommands.Commands.Sit;
import US.bittiez.AwesomeCommands.Commands.UnSit;
import US.bittiez.Config.Configurator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

public class main extends JavaPlugin implements Listener {
    private static Logger log;
    private Configurator configurator = new Configurator();
    private boolean debug = true;

    //NPRestart
    private boolean npRestart = false;
    //NPStop
    private boolean npStop = false;

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
            case "npr":
                return npRestart(who);
            case "npstop":
            case "nps":
                return npStop(who);
            case "search":
                return search(who, args);
            case "sit":
                return sit(who);
            case "unsit":
                return unSit(who);
        }
        return false;
    }

    //Command handlers
    //
    //
    //
    private boolean sit(CommandSender who) {
        if (!configurator.config.getBoolean("Sit", false)) {
            if (debug)
                who.sendMessage("This command has been disabled in the config.");
            return true;
        }
        if (who.hasPermission(PERMISSIONS.PLAYER.SIT))
            if (who instanceof Player)
                new Thread(new Sit((Player) who)).start();
        return true;
    }

    private boolean unSit(CommandSender who) {
        if (!configurator.config.getBoolean("UnSit", false)) {
            if (debug)
                who.sendMessage("This command has been disabled in the config.");
            return true;
        }
        if (who.hasPermission(PERMISSIONS.PLAYER.UNSIT))
            if (who instanceof Player)
                new Thread(new UnSit((Player) who)).start();
        return true;
    }

    private boolean search(CommandSender who, String[] args) {
        if (!configurator.config.getBoolean("Search", false)) {
            if (debug)
                who.sendMessage("This command has been disabled in the config.");
            return true;
        }

        if (who.hasPermission(PERMISSIONS.PLAYER.SEARCH) && args.length > 0) {
            List<String> urls = configurator.config.getStringList("SearchUrls");
            List<String> replacement = configurator.config.getStringList("SearchSpace");
            List<String> message = configurator.config.getStringList("SearchMsg");

            for (int i = 0; i < urls.size(); i++) {
                if (replacement.size() >= i && message.size() >= i) {
                    String link = urls.get(i).replace("[SEARCH]", String.join(replacement.get(i), args));
                    who.sendMessage(ChatColor.translateAlternateColorCodes('&', message.get(i).replace("[LINK]", link)));
                }
            }
        }

        return true;
    }

    private boolean npRestart(CommandSender who) {
        if (!configurator.config.getBoolean("NPRestart", false)) {
            if (debug)
                who.sendMessage("This command has been disabled in the config.");
            return true;
        }

        if (who.hasPermission(PERMISSIONS.ADMIN.NP_RESTART)) {
            npRestart = !npRestart;
            if (npRestart)
                who.sendMessage(STATIC.applyACPrefix("&aThe server will restart when the last player logs off."));
            else
                who.sendMessage(STATIC.applyACPrefix("&aThe server will &6not &arestart when the last player logs off."));
            return true;
        }
        return true;
    }

    private boolean npStop(CommandSender who) {
        if (!configurator.config.getBoolean("NPStop", false)) {
            if (debug)
                who.sendMessage("This command has been disabled in the config.");
            return true;
        }

        if (who.hasPermission(PERMISSIONS.ADMIN.NP_STOP)) {
            npStop = !npStop;
            if (npStop)
                who.sendMessage(STATIC.applyACPrefix("&aThe server will be stopped when the last player logs off."));
            else
                who.sendMessage(STATIC.applyACPrefix("&aThe server will &6not &abe stopped when the last player logs off."));
            return true;
        }
        return true;
    }


    //Event handlers
    //
    //
    //
    @EventHandler
    public void onPlayerLogOff(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTask(this, new Runnable() {
            @Override
            public void run() {
                if (getServer().getOnlinePlayers().size() < 1 && npRestart) {
                    getServer().spigot().restart();
                }
                if (getServer().getOnlinePlayers().size() < 1 && npStop) {
                    getServer().shutdown();
                }
                if(debug)
                    log.info(getServer().getOnlinePlayers().size() + " online players.");
            }
        });
    }
}
