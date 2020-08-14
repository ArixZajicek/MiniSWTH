package cc.arix.miniswth.events;

import cc.arix.miniswth.MiniSWTH;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnRespawn implements Listener {

    private MiniSWTH ref;

    public OnRespawn(MiniSWTH r) {
        ref = r;
    }

    @EventHandler
    public void OnPlayerRespawnEvent(PlayerRespawnEvent e) {
        // Only change the event if our spawn is valid.
        if (ref.spawnManager.isSpawnValid()) {
            // Only modify the spawn location if the player is not spawning by a bed or anchor
            if (!e.isBedSpawn() && !e.isAnchorSpawn()) {
                e.setRespawnLocation(ref.spawnManager.getSpawnLocation());
            }
        }
    }
}
