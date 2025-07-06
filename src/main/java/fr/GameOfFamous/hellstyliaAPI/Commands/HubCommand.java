package fr.GameOfFamous.hellstyliaAPI.Commands;

import fr.GameOfFamous.hellstyliaAPI.HellstyliaAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class HubCommand implements CommandExecutor {

    private final HellstyliaAPI plugin;

    public HubCommand(HellstyliaAPI plugin){
        this.plugin = plugin;
    }

    private static final String TARGET_SERVER = "hub"; // nom exact du serveur dans Velocity

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCette commande est réservée aux joueurs.");
            return true;
        }

        Player player = (Player) sender;

        if (player.getServer().getName().equalsIgnoreCase(TARGET_SERVER)) {
            player.sendMessage("§eTu es déjà au hub !");
            return true;
        }

        // Envoyer une redirection via le channel BungeeCord/Velocity
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteOut);

        try {
            out.writeUTF("Connect");
            out.writeUTF(TARGET_SERVER);
        } catch (Exception e) {
            player.sendMessage("§cErreur lors de la connexion au hub.");
            return true;
        }

        player.sendPluginMessage(plugin, "BungeeCord", byteOut.toByteArray());
        player.sendMessage("§aConnexion vers le hub...");

        return true;
    }

}
