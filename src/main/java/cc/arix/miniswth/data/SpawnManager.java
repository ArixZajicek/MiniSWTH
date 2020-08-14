package cc.arix.miniswth.data;

import cc.arix.miniswth.MiniSWTH;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class SpawnManager {
    private File spawnFile;
    private FileConfiguration spawn;
    private Boolean spawnValid, newSpawnValid;
    private Location spawnLocation, newSpawnLocation;
    private MiniSWTH ref;

    public SpawnManager(MiniSWTH r) {
        // Init spawn stuff
        ref = r;
        load();
    }

    public void load() {
        spawnFile = new File(ref.getDataFolder(), "spawn.yml");
        spawn = YamlConfiguration.loadConfiguration(spawnFile);

        // Check if spawn data is valid.
        if (
                spawn.isString("world") &&
                ref.getServer().getWorld(spawn.getString("world")) != null &&
                spawn.isDouble("x") &&
                spawn.isDouble("y") &&
                spawn.isDouble("z") &&
                spawn.isDouble("yaw") &&
                spawn.isDouble("pitch")
        ) {
            spawnValid = true;
            spawnLocation = new Location(ref.getServer().getWorld(spawn.getString("world")), spawn.getDouble("x"), spawn.getDouble("y"), spawn.getDouble("z"), (float)spawn.getDouble("yaw"), (float)spawn.getDouble("pitch"));
        } else {
            spawnValid = false;
        }

        // Check if new spawn data is valid.
        if (
                spawn.isString("new_world") &&
                ref.getServer().getWorld(spawn.getString("new_world")) != null &&
                spawn.isDouble("new_x") &&
                spawn.isDouble("new_y") &&
                spawn.isDouble("new_z") &&
                spawn.isDouble("new_yaw") &&
                spawn.isDouble("new_pitch")
        ) {
            newSpawnValid = true;
            newSpawnLocation = new Location(ref.getServer().getWorld(spawn.getString("new_world")), spawn.getDouble("new_x"), spawn.getDouble("new_y"), spawn.getDouble("new_z"), (float)spawn.getDouble("new_yaw"), (float)spawn.getDouble("new_pitch"));
        } else {
            newSpawnValid = false;
        }
    }

    public boolean setSpawn(Location loc) {
        spawn.set("world", loc.getWorld().getName());
        spawn.set("x", loc.getX());
        spawn.set("y", loc.getY());
        spawn.set("z", loc.getZ());
        spawn.set("yaw", loc.getYaw());
        spawn.set("pitch", loc.getPitch());
        try {
            spawn.save(spawnFile);
            loc.getWorld().setSpawnLocation(loc);
            spawnLocation = loc;
            spawnValid = true;
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    public void teleportToSpawn(final Player player) {
        if (spawnValid) {
            // Countdown?

            // Teleport first
            player.teleport(spawnLocation);

            // Send message
            if (ref.config.isSet("spawns.message") && ref.config.getBoolean("spawns.message"))
                player.sendMessage(MiniSWTH.SUCCESS_COLOR + ref.lang.get("spawn_welcome", player));

            // Twinkle Effect
            if (ref.config.isSet("spawns.effect") && ref.config.getBoolean("spawns.effect"))
                new BukkitRunnable () {
                    public void run() {
                        player.playEffect(EntityEffect.TELEPORT_ENDER);
                    }
                }.runTaskLater(ref, 5);

            // Play sound
            if (ref.config.isSet("spawns.sound") && ref.config.getBoolean("spawns.sound"))
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0f);

        }
    }

    public boolean isSpawnValid() {
        return spawnValid;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public boolean setNewSpawn(Location loc) {
        spawn.set("new_world", loc.getWorld().getName());
        spawn.set("new_x", loc.getX());
        spawn.set("new_y", loc.getY());
        spawn.set("new_z", loc.getZ());
        spawn.set("new_yaw", loc.getYaw());
        spawn.set("new_pitch", loc.getPitch());
        try {
            spawn.save(spawnFile);
            newSpawnLocation = loc;
            newSpawnValid = true;
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    public boolean clearNewSpawn() {
        spawn.set("new_world", null);
        spawn.set("new_x", null);
        spawn.set("new_y", null);
        spawn.set("new_z", null);
        spawn.set("new_yaw", null);
        spawn.set("new_pitch", null);
        try {
            spawn.save(spawnFile);
            newSpawnLocation = null;
            newSpawnValid = false;
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    public void teleportToNewSpawn(final Player player) {
        if (newSpawnValid) {
            // Countdown?

            // Teleport First
            player.teleport(newSpawnLocation);

            // Send message
            if (ref.config.isSet("spawns.message") && ref.config.getBoolean("spawns.message"))
                player.sendMessage(MiniSWTH.SUCCESS_COLOR + ref.lang.get("new_spawn_welcome", player));

            // Wait for a moment before playing twinkle effect since the client needs to be in range for it to shows
            // Twinkle Effect
            if (ref.config.isSet("spawns.effect") && ref.config.getBoolean("spawns.effect"))
                new BukkitRunnable () {
                    public void run() {
                        player.playEffect(EntityEffect.TELEPORT_ENDER);
                    }
                }.runTaskLater(ref, 5);

            // Play sound
            if (ref.config.isSet("spawns.sound") && ref.config.getBoolean("spawns.sound"))
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 0f);

        }
    }

    public boolean isNewSpawnValid() {
        return newSpawnValid;
    }

    public Location getNewSpawnLocation() {
        return newSpawnLocation;
    }
}
