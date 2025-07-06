package fr.GameOfFamous.hellstyliaAPI.Grades;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

import java.util.List;

public class Rank {

    private final String id;
    private final String displayName;
    private final String prefix;
    private final TextColor color;
    private final List<String> permissions;

    public Rank(String id, String displayName, String prefix, TextColor color, List<String> permissions){
        this.id = id;
        this.displayName = displayName;
        this.prefix = prefix;
        this.color = color;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPrefix() {
        return prefix;
    }

    public TextColor getColor() {
        return color;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
