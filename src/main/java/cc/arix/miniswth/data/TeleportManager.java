package cc.arix.miniswth.data;

import cc.arix.miniswth.MiniSWTH;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager {
    private MiniSWTH ref;

    private Map<UUID, BukkitRunnable> toExecute;

    public enum TeleportReason {
        SPAWN,
        HOME,
        WARP,
        TP_TO,
        TP_HERE,
        OTHER
    };

    public TeleportManager(MiniSWTH r) {
        ref = r;
        toExecute = new HashMap<UUID, BukkitRunnable>();
    }

    public void teleportPlayer(Player p, Location l, TeleportReason reason) {
        // Check
    }
}
