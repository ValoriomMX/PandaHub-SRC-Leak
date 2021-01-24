package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAllCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.teleportall")) {
            CC.getNoPermission(player);
            return true;
         } else {
            Bukkit.getServer().getOnlinePlayers().forEach((p) -> {
               p.teleport(player.getLocation());
            });
            player.sendMessage(CC.set(MessageFile.getConfig().getString("TELEPORTALL")));
            return true;
         }
      }
   }

   public TeleportAllCommand() {
      Hub.getInstance().getCommand("teleportall").setExecutor(this);
   }
}
