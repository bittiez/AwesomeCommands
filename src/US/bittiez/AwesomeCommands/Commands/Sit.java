package US.bittiez.AwesomeCommands.Commands;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;

import java.util.List;

public class Sit implements Runnable {
    private Player player;

    public Sit(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        List<Entity> entities = player.getWorld().getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Tameable && ((Tameable) entity).isTamed() && ((Tameable) entity).getOwner().equals(player)) {
                if (entity instanceof Wolf)
                    ((Wolf) entity).setSitting(true);
                if (entity instanceof Ocelot)
                    ((Ocelot) entity).setSitting(true);
            }
        }
    }
}
