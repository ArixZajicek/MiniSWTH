package cc.arix.miniswth.commands;

import cc.arix.miniswth.MiniSWTH;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class SetSpawn implements CommandExecutor {
    private MiniSWTH ref;

    public SetSpawn(MiniSWTH r) {
        ref = r;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        // First, check permissions
        if (!commandSender.hasPermission("mini.spawn.set")){
            commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "Why would you be able to set the spawn point? No! Bad!");
            return true;
        }

        if (args.length >= 3 && args.length <= 6) {
            // If we have this many arguments, we should check for coordinates.
            // Attempt to parse arguments as a location
            Location loc = ref.extra.argsToLocation(args);

            // Verify that the location is valid
            if (loc == null) {
                commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "Invalid arguments.");
                return false;
            }

            // Set the spawn location or return an error if it failed.
            if (ref.spawnManager.setSpawn(loc)) {
                commandSender.sendMessage(MiniSWTH.SUCCESS_COLOR + "Spawn has been set to " +
                        "(X: " + loc.getX() + ", Y: " + loc.getY() + ", Z: " + loc.getZ() + ") facing " +
                        "(YAW: " + loc.getYaw() + ", PITCH: " + loc.getPitch() + ") in world \"" + loc.getWorld() + "\"!");
            } else {
                commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "There was an error saving the spawn cc.arix.miniswth.data file. Please check your folder/file permissions.");
            }

            return true;
        } else if (args.length == 0) {
            // No arguments, just set the location to the player.
            if (commandSender instanceof Player) {
                Player p = (Player)commandSender;
                if (ref.spawnManager.setSpawn(p.getLocation()))
                    commandSender.sendMessage(MiniSWTH.SUCCESS_COLOR + "Spawn has been set to your current location!");
                else
                    commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "There was an error saving the spawn cc.arix.miniswth.data file. Please check your folder/file permissions.");
                return true;
            } else {
                commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "Please provide spawn coordinates (and optionally yaw & pitch) if you aren't a player.");
                return false;
            }
        } else {
            commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "Not a valid usage of the command.");
            return false;
        }
    }


}
