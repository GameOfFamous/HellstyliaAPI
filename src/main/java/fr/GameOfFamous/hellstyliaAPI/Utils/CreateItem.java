package fr.GameOfFamous.hellstyliaAPI.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class CreateItem {

    public static ItemStack newItem(Material material, int amount, String displayName, Boolean hasEnchant, String[] lore) {
        if (material == null || amount <= 0) {
            throw new IllegalArgumentException("Material cannot be null, and amount must be greater than 0.");
        }

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta != null) {
            // Nom avec couleurs
            if (displayName != null && !displayName.isEmpty()) {
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            }

            // Enchantement visible (optionnel)
            if (Boolean.TRUE.equals(hasEnchant)) {
                itemMeta.addEnchant(Enchantment.EFFICIENCY, 1, true);
            }

            // Lore avec couleurs
            if (lore != null && lore.length > 0) {
                List<String> coloredLore = Arrays.stream(lore)
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .toList();
                itemMeta.setLore(coloredLore);
            }

            // Cache les effets visuels
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);

            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }

    public static ItemStack getPlayerSkull(String playerName, String[] lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        if (meta != null) {
            // Définit le propriétaire de la tête (utilise le skin si dispo)
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
            meta.setOwningPlayer(offlinePlayer);

            // Nom coloré
            meta.setDisplayName(ChatColor.YELLOW + playerName);

            // Lore coloré
            if (lore != null && lore.length > 0) {
                List<String> coloredLore = Arrays.stream(lore)
                        .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                        .toList();
                meta.setLore(coloredLore);
            }

            item.setItemMeta(meta);
        }

        return item;
    }

}
