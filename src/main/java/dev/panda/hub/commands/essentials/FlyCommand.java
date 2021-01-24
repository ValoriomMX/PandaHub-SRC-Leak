package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

   public static List<Player> fly = new ArrayList();

   public FlyCommand() {
      Hub.getInstance().getCommand("fly").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.fly")) {
            CC.getNoPermission(player);
            return true;
         } else {
            if (fly.contains(player)) {
               fly.remove(player);
               player.setAllowFlight(false);
               player.sendMessage(CC.set(MessageFile.getConfig().getString("FLY.DISABLE")));
            } else {
               fly.add(player);
               player.setAllowFlight(true);
               player.sendMessage(CC.set(MessageFile.getConfig().getString("FLY.ENABLE")));
            }

            return true;
         }
      }
   }
}
