package dev.panda.hub.commands.spawn;

import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.setspawn")) {
            CC.getNoPermission(player);
            return true;
         } else {
            player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
            player.sendMessage(CC.set(MessageFile.getConfig().getString("SPAWN.SET").replace("{world}", player.getWorld().getName()).replace("{x}", String.valueOf(Math.round(player.getLocation().getX()))).replace("{y}", String.valueOf(Math.round(player.getLocation().getY()))).replace("{z}", String.valueOf(Math.round(player.getLocation().getZ())))));
            return true;
         }
      }
   }

   public SetSpawnCommand() {
      Hub.getInstance().getCommand("setspawn").setExecutor(this);
   }
}
