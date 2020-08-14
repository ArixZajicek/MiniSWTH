package cc.arix.miniswth.data;

import cc.arix.miniswth.MiniSWTH;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {
    private MiniSWTH ref;
    private Map<String, String> defaultStrings;
    private FileConfiguration langConfig;

    public LanguageManager(MiniSWTH r) {
        ref = r;
        load();
    }

    public void load() {
        // Check if there's a valid key for the language file to use
        String language = ref.config.getString("lang");
        if (language == null) {
            ref.getLogger().warning("No language found in config. Defaulting to en_US.");
            language = "en_US";
        }

        File langfile = new File(ref.getDataFolder(), "lang/" + language + ".yml");

        if (langfile.exists()) {
            ref.getLogger().info("Using language file " + language + ".yml.");
            langConfig = YamlConfiguration.loadConfiguration(langfile);
            return;
        } else {
            ref.getLogger().warning("Language file lang/" + language + ".yml not found, using built in defaults.");
        }
        initDefaults();
    }

    public String get(String key) {
        if (langConfig != null && langConfig.isSet(key)) return langConfig.getString(key);
        if (defaultStrings.get(key) != null) return defaultStrings.get(key);
        return "There was an error.";
    }

    public String get(String key, Player p) {
        return get(key).replace("%player%", p.getDisplayName());
    }

    private void initDefaults() {
        defaultStrings = new HashMap<String, String>();
        defaultStrings.put("server_welcome", "Welcome %player% to the server!");
        defaultStrings.put("spawn_welcome", "Welcome to spawn!");
        defaultStrings.put("new_spawn_welcome", "Welcome to the new player spawn!");

        defaultStrings.put("invalid_permission", "You don't have permission to do that!");
        defaultStrings.put("invalid_player", "%player% is not a valid player! ");
        defaultStrings.put("spawn_not_set", "No spawn is set! If this isn't right, contact an administrator.");
        defaultStrings.put("new_spawn_not_set", "No new player spawn is set! If this isn't right, contact an administrator.");
        defaultStrings.put("console_must_use_on_player", "You may only use that command on a player when running from the console.");
    }
}
