package fr.GameOfFamous.hellstyliaAPI.Utils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BossbarManager {

    private final Map<String, BossBar> bars = new HashMap<>();

    public void createBar(String id, String title, BarColor color, BarStyle style, BarFlag... flags) {
        if (bars.containsKey(id)) return;
        BossBar bar = Bukkit.createBossBar(title, color, style, flags);
        bars.put(id, bar);
    }

    public void setTitle(String id, String title) {
        BossBar bar = bars.get(id);
        if (bar != null) {
            bar.setTitle(title);
        }
    }

    public void setProgress(String id, double progress) {
        BossBar bar = bars.get(id);
        if (bar != null) {
            bar.setProgress(progress);
        }
    }

    public void addPlayer(String id, Player player) {
        BossBar bar = bars.get(id);
        if (bar != null) {
            bar.addPlayer(player);
        }
    }

    public void removePlayer(String id, Player player) {
        BossBar bar = bars.get(id);
        if (bar != null) {
            bar.removePlayer(player);
        }
    }

    public void showToAll(String id) {
        BossBar bar = bars.get(id);
        if (bar != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                bar.addPlayer(player);
            }
        }
    }

    public void hideAll(String id) {
        BossBar bar = bars.get(id);
        if (bar != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                bar.removePlayer(player);
            }
        }
    }

    public void removeBar(String id) {
        BossBar bar = bars.remove(id);
        if (bar != null) {
            bar.removeAll();
        }
    }

}
