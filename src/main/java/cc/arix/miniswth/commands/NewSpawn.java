package cc.arix.miniswth.commands;


import cc.arix.miniswth.MiniSWTH;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NewSpawn implements CommandExecutor {
    private MiniSWTH ref;

    public NewSpawn(MiniSWTH r) {
        ref = r;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String base, String[] args) {
        if (!ref.spawnManager.isNewSpawnValid()) {
            commandSender.sendMessage(MiniSWTH.ERROR_COLOR + ref.lang.get("spawn_not_set"));
            return true;
        }

        if (args.length > 0) {
            // Verify "others" permission.
            if (!commandSender.hasPermission("mini.newspawn.others")) {
                commandSender.sendMessage(MiniSWTH.ERROR_COLOR + ref.lang.get("invalid_permission"));
                return true;
            }

            // Verify each argument is a player
            for (String s : args) {
                if (ref.getServer().getPlayer(s) == null) {
                    commandSender.sendMessage(MiniSWTH.ERROR_COLOR + ref.lang.get("invalid_player").replace("%player%", s));
                    return true;
                }
            }

            // Send players to spawn
            for (String s : args) {
                Player p = ref.getServer().getPlayer(s);
                ref.spawnManager.teleportToNewSpawn(p);
            }
            return true;
        } else if (commandSender instanceof Player) {
            if (!commandSender.hasPermission("mini.newspawn")) {
                commandSender.sendMessage(MiniSWTH.ERROR_COLOR + ref.lang.get("invalid_permission"));
                return true;
            }
            Player p = (Player)commandSender;
            ref.spawnManager.teleportToNewSpawn(p);
            return true;
        } else {
            commandSender.sendMessage(MiniSWTH.ERROR_COLOR + ref.lang.get("console_must_use_on_player"));
            return true;
        }
    }
}
