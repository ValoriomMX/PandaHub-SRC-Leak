package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.teleport")) {
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
               player.teleport(target.getLocation());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("TELEPORT").replace("{target}", target.getName())));
               return true;
            }
         }
      }
   }

   public TeleportCommand() {
      Hub.getInstance().getCommand("teleport").setExecutor(this);
   }
}
