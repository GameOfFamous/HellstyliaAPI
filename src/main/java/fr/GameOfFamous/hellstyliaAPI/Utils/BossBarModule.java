package fr.GameOfFamous.hellstyliaAPI.Utils;

import org.bukkit.entity.Player;

public interface BossBarModule {

    void create(BossbarManager manager);
    void start();
    void stop();
    void addPlayer(Player player);

}
