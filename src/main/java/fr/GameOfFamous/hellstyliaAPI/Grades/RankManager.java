package fr.GameOfFamous.hellstyliaAPI.Grades;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class RankManager {

    private final Map<String, Rank> rankMap = new HashMap<>();
    private final Map<UUID, String> playerToRankId = new HashMap<>();

    public void loadRanks(FileConfiguration config){
        for(String key : config.getKeys(false)){
            String prefix = ChatColor.translateAlternateColorCodes('&', config.getString(key + ".prefix"));
            String displayName = config.getString(key + ".displayName");
            String colorName = config.getString(key + ".color").toUpperCase();
            TextColor color = NamedTextColor.NAMES.value(colorName.toLowerCase());
            List<String> perms = config.getStringList(key + ".permissions");

            rankMap.put(key, new Rank(key, displayName, prefix, color, perms));
        }
    }

    public void assignPlayer(UUID uuid, String rankId) {
        playerToRankId.put(uuid, rankId);
    }

    public Rank getRank(String rankId) {
        return rankMap.getOrDefault(rankId, rankMap.get("default"));
    }

    public Rank getPlayerRank(Player player) {
        String id = playerToRankId.getOrDefault(player.getUniqueId(), "default");
        return getRank(id);
    }

    public List<String> getAllRankIds() {
        return new ArrayList<>(rankMap.keySet());
    }

}
