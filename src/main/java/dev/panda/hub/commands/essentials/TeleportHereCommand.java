package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHereCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.teleporthere")) {
            CC.getNoPermission(player);
            return true;
         } else if (args.length < 1) {
            player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cUsage: /").append(label).append(" <player>"))));
            return true;
         } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
               CC.getPlayerNotFound(sender, args[0]);
               return true;
            } else {
               target.teleport(player.getLocation());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("TELEPORTHERE.PLAYER").replace("{target}", target.getName())));
               target.sendMessage(CC.set(MessageFile.getConfig().getString("TELEPORTHERE.TARGET").replace("{player}", player.getName())));
               return true;
            }
         }
      }
   }

   public TeleportHereCommand() {
      Hub.getInstance().getCommand("teleporthere").setExecutor(this);
   }
}
