package cc.arix.miniswth;

import cc.arix.miniswth.commands.NewSpawn;
import cc.arix.miniswth.commands.SetNewSpawn;
import cc.arix.miniswth.commands.SetSpawn;
import cc.arix.miniswth.commands.Spawn;
import cc.arix.miniswth.data.LanguageManager;
import cc.arix.miniswth.data.SpawnManager;
import cc.arix.miniswth.events.OnJoin;
import cc.arix.miniswth.events.OnRespawn;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniSWTH extends JavaPlugin {
    public static final String ERROR_COLOR = "§c";
    public static final String SUCCESS_COLOR = "§a";

    public LanguageManager lang;
    public SpawnManager spawnManager;
    public Extra extra;

    public FileConfiguration config;

    @Override
    public void onEnable() {
        getLogger().info("MiniSWTH is starting.");
        // Set up language file
        load();

        // Set up thing managers
        extra = new Extra(this);
        spawnManager = new SpawnManager(this);
        lang = new LanguageManager(this);

        // Register Commands
        this.getCommand("setspawn").setExecutor(new SetSpawn(this));
        this.getCommand("spawn").setExecutor(new Spawn(this));
        this.getCommand("setnewspawn").setExecutor(new SetNewSpawn(this));
        this.getCommand("newspawn").setExecutor(new NewSpawn(this));

        // Set up events
        this.getServer().getPluginManager().registerEvents(new OnRespawn(this), this);
        this.getServer().getPluginManager().registerEvents(new OnJoin(this), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("MiniSWTH is stopping.");
    }

    private void load() {
        this.saveDefaultConfig();
        config = this.getConfig();
    }

}
