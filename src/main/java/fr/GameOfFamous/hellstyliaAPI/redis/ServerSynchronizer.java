package fr.GameOfFamous.hellstyliaAPI.redis;

import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerDataLoader;
import fr.GameOfFamous.hellstyliaAPI.Gestion.RedisWrapper;
import fr.GameOfFamous.hellstyliaAPI.Grades.RankManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerSynchronizer {

    private final ChatSynchronizer chat;
    private final TabSynchronizer tab;

    public ServerSynchronizer(JavaPlugin plugin, RedisWrapper redis, PlayerDataLoader loader, RankManager rankManager) {
        this.chat = new ChatSynchronizer(plugin, redis, loader, rankManager);
        this.tab = new TabSynchronizer(redis, rankManager);
    }

    public void start(JavaPlugin plugin) {
        chat.init();
        tab.start(plugin);
    }

}
