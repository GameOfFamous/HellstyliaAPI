package fr.GameOfFamous.hellstyliaAPI.Grades;

import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class TabManager {

    public static void loadTabRank(Player player, RankManager rankManager, PlayerData data){
        Rank rank = rankManager.getRank(data.getRank());

        Component prefix = Component.text(rank.getPrefix(), rank.getColor());
        Component name = Component.text(data.getName(), TextColor.color(255, 255, 255)); // Blanc

        Component tabName = prefix.append(Component.space()).append(name);

        player.playerListName(tabName);
    }
}
