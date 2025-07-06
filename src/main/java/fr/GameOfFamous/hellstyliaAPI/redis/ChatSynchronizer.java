package fr.GameOfFamous.hellstyliaAPI.redis;

import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerData;
import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerDataLoader;
import fr.GameOfFamous.hellstyliaAPI.Gestion.RedisWrapper;
import fr.GameOfFamous.hellstyliaAPI.Grades.Rank;
import fr.GameOfFamous.hellstyliaAPI.Grades.RankManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatSynchronizer {

    private final JavaPlugin plugin;
    private final RedisWrapper redis;
    private final PlayerDataLoader loader;
    private final RankManager rankManager;

    public ChatSynchronizer(JavaPlugin plugin, RedisWrapper redis, PlayerDataLoader loader, RankManager rankManager) {
        this.plugin = plugin;
        this.redis = redis;
        this.loader = loader;
        this.rankManager = rankManager;
    }

    public void init() {
        redis.subscribe("global_chat", raw -> {
            Component message = GsonComponentSerializer.gson().deserialize(raw);
            Bukkit.getScheduler().runTask(plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(message);
                }
            });
        });

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onChat(AsyncChatEvent event) {
                Player player = event.getPlayer();
                PlayerData data = loader.getPlayerData(player.getUniqueId());

                if (data == null) {
                    event.setCancelled(true);
                    player.sendMessage(Component.text("Erreur : vos données n'ont pas pu être chargées.", NamedTextColor.RED));
                    return;
                }

                Rank rank = rankManager.getRank(data.getRank());

                // Nettoyage des codes § du prefix
                String cleanPrefix = rank.getPrefix().replaceAll("§[0-9a-fk-or]", "");
                Component prefix = Component.text(cleanPrefix, rank.getColor());
                Component name = Component.text(data.getName(), NamedTextColor.WHITE);
                Component separator = Component.text(":", NamedTextColor.GRAY);
                Component message = event.message().color(NamedTextColor.WHITE);

                Component full = prefix.append(Component.space()).append(name)
                        .append(Component.space()).append(separator)
                        .append(Component.space()).append(message);

                String serialized = GsonComponentSerializer.gson().serialize(full);
                redis.publish("global_chat", serialized);

                event.setCancelled(true);
            }
        }, plugin);
    }

}
