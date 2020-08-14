package cc.arix.miniswth.events;

import cc.arix.miniswth.MiniSWTH;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class OnJoin implements Listener {
    private MiniSWTH ref;

    public OnJoin(MiniSWTH r) {
        ref = r;
    }

    @EventHandler
    public void OnPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage("You have joined!");
        if (!p.hasPlayedBefore()) {
            p.sendMessage("Welcome for the first time!");
            if (ref.spawnManager.isNewSpawnValid()) {
                ref.spawnManager.teleportToNewSpawn(p);
            } else if (ref.spawnManager.isSpawnValid()) {
                ref.spawnManager.teleportToSpawn(p);
            }
        }
    }
}
