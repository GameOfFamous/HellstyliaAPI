package fr.GameOfFamous.hellstyliaAPI.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

public class NpcCustom {

    public static void createNPC(Location location, String name, Villager.Profession profession) {
        World world = location.getWorld();
        if (world == null) return;

        Villager npc = (Villager) world.spawnEntity(location, EntityType.VILLAGER);

        npc.setCustomName(ChatColor.AQUA + name);
        npc.setCustomNameVisible(true);
        npc.setAI(false);
        npc.setInvulnerable(true);
        npc.setCollidable(false);
        npc.setProfession(profession != null ? profession : Villager.Profession.NONE);
        npc.setPersistent(true);
        npc.setSilent(true);
        npc.setRemoveWhenFarAway(false);
        npc.setCanPickupItems(false);
    }

    public static void removeNPC(String name, World world) {
        if (world == null) return;

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Villager villager && name.equals(ChatColor.stripColor(villager.getCustomName()))) {
                villager.remove();
            }
        }
    }

}
