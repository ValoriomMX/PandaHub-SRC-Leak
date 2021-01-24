package dev.panda.hub.commands.spawn;

import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
   public SpawnCommand() {
      Hub.getInstance().getCommand("spawn").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.spawn")) {
            CC.getNoPermission(player);
            return true;
         } else {
            player.teleport(player.getWorld().getSpawnLocation());
            player.sendMessage(CC.set(MessageFile.getConfig().getString("SPAWN.SPAWNED")));
            return true;
         }
      }
   }
}
