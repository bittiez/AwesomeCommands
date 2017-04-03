package US.bittiez.AwesomeCommands;

import org.bukkit.ChatColor;

public class STATIC {
    public static String Colorize(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static String applyACPrefix(String text){
        return Colorize("&6[AC] " + text);
    }
}
