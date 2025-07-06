package fr.GameOfFamous.hellstyliaAPI.Utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class Border {

    public static void setBorder(){
        World world = Bukkit.getWorld("world");
        WorldBorder worldBorder = world.getWorldBorder();

        worldBorder.setCenter(0, 0);
        worldBorder.setSize(300);
    }

}
