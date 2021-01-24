package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.gamemode")) {
            CC.getNoPermission(player);
            return true;
         } else if (args.length < 1) {
            player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cUsage: /").append(label).append(" <mode>"))));
            return true;
         } else {
            if (!args[0].equalsIgnoreCase("0") && !args[0].equalsIgnoreCase("survival")) {
               if (!args[0].equalsIgnoreCase("1") && !args[0].equalsIgnoreCase("creative")) {
                  if (!args[0].equalsIgnoreCase("2") && !args[0].equalsIgnoreCase("adventure")) {
                     player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cArgument '").append(args[0]).append("' not found."))));
                     return true;
                  }

                  if (player.getGameMode() == GameMode.ADVENTURE) {
                     player.sendMessage(CC.set(MessageFile.getConfig().getString("GAMEMODE.ALREADY").replace("{gamemode}", String.valueOf(player.getGameMode()))));
                     return true;
                  }

                  player.setGameMode(GameMode.ADVENTURE);
               } else {
                  if (player.getGameMode() == GameMode.CREATIVE) {
                     player.sendMessage(CC.set(MessageFile.getConfig().getString("GAMEMODE.ALREADY").replace("{gamemode}", String.valueOf(player.getGameMode()))));
                     return true;
                  }

                  player.setGameMode(GameMode.CREATIVE);
               }
            } else {
               if (player.getGameMode() == GameMode.SURVIVAL) {
                  player.sendMessage(CC.set(MessageFile.getConfig().getString("GAMEMODE.ALREADY").replace("{gamemode}", String.valueOf(player.getGameMode()))));
                  return true;
               }

               player.setGameMode(GameMode.SURVIVAL);
            }

            player.sendMessage(CC.set(MessageFile.getConfig().getString("GAMEMODE.CHANGED").replace("{gamemode}", String.valueOf(player.getGameMode()))));
            return true;
         }
      }
   }

   public GamemodeCommand() {
      Hub.getInstance().getCommand("gamemode").setExecutor(this);
   }
}
