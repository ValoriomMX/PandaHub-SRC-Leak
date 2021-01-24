package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkullCommand implements CommandExecutor {
   public SkullCommand() {
      Hub.getInstance().getCommand("skull").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.skull")) {
            CC.getNoPermission(player);
            return true;
         } else if (args.length < 1) {
            player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cUsage: /").append(label).append(" <name>"))));
            return true;
         } else {
            player.getInventory().addItem(new ItemStack[]{ItemStackManager.getSkull(args[0])});
            return true;
         }
      }
   }
}
