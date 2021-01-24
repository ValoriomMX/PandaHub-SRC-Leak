package dev.panda.hub.commands;

import dev.panda.hub.Hub;
import dev.panda.hub.manager.menu.setting.SettingMenu;
import dev.panda.hub.utilities.CC;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {
   public SettingsCommand() {
      Hub.getInstance().getCommand("settings").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.settings")) {
            CC.getNoPermission(player);
            return true;
         } else {
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            SettingMenu.getSetting(player);
            return true;
         }
      }
   }
}
