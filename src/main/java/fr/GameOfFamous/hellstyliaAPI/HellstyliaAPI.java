package fr.GameOfFamous.hellstyliaAPI;

import fr.GameOfFamous.hellstyliaAPI.Commands.HubCommand;
import fr.GameOfFamous.hellstyliaAPI.Commands.SetRankCommand;
import fr.GameOfFamous.hellstyliaAPI.Commands.SetRankCompleter;
import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerDataLoader;
import fr.GameOfFamous.hellstyliaAPI.Events.PlayerJoin;
import fr.GameOfFamous.hellstyliaAPI.Gestion.RedisWrapper;
import fr.GameOfFamous.hellstyliaAPI.Grades.RankManager;
import fr.GameOfFamous.hellstyliaAPI.redis.TabPublisher;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class HellstyliaAPI extends JavaPlugin {

    private RankManager rankManager;
    private PlayerDataLoader loader;
    private static HellstyliaAPI instance;
    public RedisWrapper redis;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultRanksFile();

        redis = new RedisWrapper("redis://:00066B81a@localhost:6379");

        FileConfiguration rankConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "ranks.yml"));
        rankManager = new RankManager();
        loader = new PlayerDataLoader(redis);
        rankManager.loadRanks(rankConfig);
        TabPublisher tabPublisher = new TabPublisher(redis);

        getServer().getPluginManager().registerEvents(new PlayerJoin(this, loader, rankManager, tabPublisher), this);

        getCommand("setrank").setExecutor(new SetRankCommand(loader, rankManager, redis, new TabPublisher(redis)));
        getCommand("setrank").setTabCompleter(new SetRankCompleter(rankManager));
        getCommand("hub").setExecutor(new HubCommand(this));

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void saveDefaultRanksFile(){
        File file = new File(getDataFolder(), "ranks.yml");
        if(!file.exists()){
            saveResource("ranks.yml", false);
        }
    }

    public RankManager getRankManager(){
        return rankManager;
    }

    public static HellstyliaAPI getInstance(){
        return instance;
    }
}
