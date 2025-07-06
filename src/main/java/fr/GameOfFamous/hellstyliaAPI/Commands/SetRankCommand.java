package fr.GameOfFamous.hellstyliaAPI.Commands;

import com.google.gson.Gson;
import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerData;
import fr.GameOfFamous.hellstyliaAPI.DataManager.PlayerDataLoader;
import fr.GameOfFamous.hellstyliaAPI.Gestion.RedisWrapper;
import fr.GameOfFamous.hellstyliaAPI.Grades.Rank;
import fr.GameOfFamous.hellstyliaAPI.Grades.RankManager;
import fr.GameOfFamous.hellstyliaAPI.redis.TabPublisher;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SetRankCommand implements CommandExecutor {

    private final PlayerDataLoader loader;
    private final RankManager rankManager;
    private final RedisWrapper redis;
    private TabPublisher tabPublisher;
    private final Gson gson = new Gson();

    public SetRankCommand(PlayerDataLoader loader, RankManager rankManager, RedisWrapper redis, TabPublisher tabPublisher) {
        this.loader = loader;
        this.rankManager = rankManager;
        this.redis = redis;
        this.tabPublisher = tabPublisher;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("hubmanager.setrank")) {
            sender.sendMessage("§cTu n'as pas la permission d'utiliser cette commande.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage("§cUtilisation : /setrank <joueur> <rank>");
            return true;
        }

        String targetName = args[0];
        String rankId = args[1];

        Rank rank = rankManager.getRank(rankId);
        if (rank == null) {
            sender.sendMessage("§cLe rang '" + rankId + "' n'existe pas.");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        UUID uuid = target.getUniqueId();

        PlayerData oldData = loader.getPlayerData(uuid);
        if (oldData == null) {
            sender.sendMessage("§cImpossible de trouver les données pour '" + targetName + "'");
            return true;
        }

        PlayerData updatedData = new PlayerData(oldData.getName(), uuid, rank.getId());

        // Mise à jour Redis
        redis.set("player:" + uuid, gson.toJson(updatedData));

        // Mise à jour en ligne si nécessaire
        Player online = Bukkit.getPlayer(uuid);
        if (online != null) {
            online.sendMessage("§aTon rang a été mis à jour : " + rank.getPrefix());
            online.playerListName(Component.text(rank.getPrefix() + " " + updatedData.getName()).color(rank.getColor()));
            tabPublisher.updatePlayerTab(target.getUniqueId(), target.getName(), rank.getId());
        }

        sender.sendMessage("§aLe rang de §f" + updatedData.getName() + "§a a été défini sur " + rank.getPrefix());
        return true;
    }

}
