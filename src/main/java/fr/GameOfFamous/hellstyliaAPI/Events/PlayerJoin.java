package fr.GameOfFamous.hellstyliaAPI.Events;

import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerData;
import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerDataLoader;
import fr.GameOfFamous.hellstyliaAPI.Grades.Rank;
import fr.GameOfFamous.hellstyliaAPI.Grades.RankManager;
import fr.GameOfFamous.hellstyliaAPI.redis.TabPublisher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoin implements Listener {

    private final PlayerDataLoader loader;
    private final RankManager rankManager;
    private final TabPublisher tabPublisher;
    private final JavaPlugin plugin;

    public PlayerJoin(JavaPlugin plugin, PlayerDataLoader loader, RankManager rankManager, TabPublisher tabPublisher) {
        this.plugin = plugin;
        this.loader = loader;
        this.rankManager = rankManager;
        this.tabPublisher = tabPublisher;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        PlayerData data = loader.getPlayerData(player.getUniqueId());
        if (data == null) {
            player.kick(Component.text("Impossible de charger vos données.", NamedTextColor.RED));
            return;
        }

        // Appliquer le rank
        Rank rank = rankManager.getRank(data.getRank());

        // Affichage dans le TAB (via Redis)
        tabPublisher.updatePlayerTab(player.getUniqueId(), data.getName(), data.getRank());
    }

}
