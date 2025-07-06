package fr.GameOfFamous.hellstyliaAPI.redis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.GameOfFamous.hellstyliaAPI.Gestion.RedisWrapper;
import fr.GameOfFamous.hellstyliaAPI.Grades.Rank;
import fr.GameOfFamous.hellstyliaAPI.Grades.RankManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TabSynchronizer {

    private final RedisWrapper redis;
    private final RankManager rankManager;
    private final Map<UUID, Component> tabMap = new HashMap<>();

    public TabSynchronizer(RedisWrapper redis, RankManager rankManager) {
        this.redis = redis;
        this.rankManager = rankManager;
    }

    public void start(JavaPlugin plugin) {
        // 1. Écoute Redis
        redis.subscribe("global_tab", raw -> {
            JsonObject obj = JsonParser.parseString(raw).getAsJsonObject();
            UUID uuid = UUID.fromString(obj.get("uuid").getAsString());
            String name = obj.get("name").getAsString();
            String rankId = obj.get("rank").getAsString();

            Rank rank = rankManager.getRank(rankId);
            Component prefix = Component.text(rank.getPrefix(), rank.getColor());
            Component display = prefix.append(Component.text(" " + name, NamedTextColor.WHITE));
            tabMap.put(uuid, display);
        });

        // 2. Applique périodiquement aux joueurs présents
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Component tab = tabMap.get(player.getUniqueId());
                if (tab != null) {
                    player.playerListName(tab);
                }
            }
        }, 0L, 100L);
    }

}
