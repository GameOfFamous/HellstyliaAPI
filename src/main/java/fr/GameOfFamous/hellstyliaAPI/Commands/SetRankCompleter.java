package fr.GameOfFamous.hellstyliaAPI.Commands;

import fr.GameOfFamous.hellstyliaAPI.Grades.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SetRankCompleter implements TabCompleter {

    private final RankManager rankManager;

    public SetRankCompleter(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Auto-complétion des pseudos de joueurs en ligne
            for (var player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        } else if (args.length == 2) {
            // Auto-complétion des noms de rangs disponibles
            completions.addAll(rankManager.getAllRankIds()); // méthode à créer si tu ne l’as pas encore
        }

        return completions;
    }

}
