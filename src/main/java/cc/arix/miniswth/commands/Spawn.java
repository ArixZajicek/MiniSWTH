package cc.arix.miniswth.commands;

import cc.arix.miniswth.MiniSWTH;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    private MiniSWTH ref;

    public Spawn(MiniSWTH r) {
        ref = r;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String base, String[] args) {
        if (!ref.spawnManager.isSpawnValid()) {
            commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "No spawn is set! If this isn't right, contact an administrator.");
            return true;
        }

        if (args.length > 0) {
            // Verify "others" permission.
            if (!commandSender.hasPermission("mini.spawn.others")) {
                commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "You are not allowed to teleport others to spawn.");
                return true;
            }

            // Verify each argument is a player
            for (String s : args) {
                if (ref.getServer().getPlayer(s) == null) {
                    commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "\"" + s + "\" is not a valid player.");
                    return true;
                }
            }

            // Send players to spawn
            for (String s : args) {
                Player p = ref.getServer().getPlayer(s);
                ref.spawnManager.teleportToSpawn(p);
            }
            return true;
        } else if (commandSender instanceof Player) {
            if (!commandSender.hasPermission("mini.spawn")) {
                commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "You are not allowed to teleport to spawn.");
                return true;
            }
            Player p = (Player)commandSender;
            ref.spawnManager.teleportToSpawn(p);
            return true;
        } else {
            commandSender.sendMessage(MiniSWTH.ERROR_COLOR + "You may only teleport players from console. You fool!");
            return true;
        }
    }
}
