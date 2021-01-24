package dev.panda.hub.commands;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.InventoryFile;
import dev.panda.hub.files.ItemFile;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.files.QueueFile;
import dev.panda.hub.files.ScoreboardFile;
import dev.panda.hub.files.TablistFile;
import dev.panda.hub.manager.JoinItemManager;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.Description;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PandaHubCommand implements CommandExecutor {
   private void reloadFiles() {
      ScoreboardFile.getConfig().reload();
      TablistFile.getConfig().reload();
      ConfigFile.getConfig().reload();
      MessageFile.getConfig().reload();
      ItemFile.getConfig().reload();
      InventoryFile.getConfig().reload();
      QueueFile.getConfig().reload();
      Bukkit.getOnlinePlayers().forEach(JoinItemManager::refreshJoinItems);
   }

   public PandaHubCommand() {
      Hub.getInstance().getCommand("pandahub").setExecutor(this);
   }

   private void getUsage(CommandSender sender, String label) {
      sender.sendMessage(CC.set("&7&m---*---------------*---"));
      sender.sendMessage(CC.set("&4&lPandaHub Help"));
      sender.sendMessage(CC.set(""));
      sender.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7▶ &c/").append(label).append(" info"))));
      sender.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7▶ &c/").append(label).append(" reload"))));
      sender.sendMessage(CC.set("&7&m---*---------------*---"));
   }

   private void getInfo(CommandSender sender) {
      sender.sendMessage(CC.set("&7&m---*-----------------*---"));
      sender.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("     &4❤ &c&l").append(Description.getName()).append(" &4❤"))));
      sender.sendMessage(CC.set(""));
      sender.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7▶ &cAuthor&7: &f").append(Description.getAuthor()))));
      sender.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7▶ &cVersion&7: &f").append(Description.getVersion()))));
      sender.sendMessage(CC.set(" &7▶ &cDiscord&7: &fhttps://discord.io/Panda-Community"));
      sender.sendMessage(CC.set("&7&m---*-----------------*---"));
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!sender.hasPermission("pandahub.plugin")) {
         CC.getNoPermission(sender);
         return true;
      } else if (args.length < 1) {
         this.getUsage(sender, label);
         return true;
      } else {
         if (args[0].equalsIgnoreCase("info")) {
            if (!sender.hasPermission("pandahub.plugin.info")) {
               CC.getNoPermission(sender);
               return true;
            }

            this.getInfo(sender);
         } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("pandahub.plugin.reload")) {
               CC.getNoPermission(sender);
               return true;
            }

            this.reloadFiles();
            sender.sendMessage(CC.set(MessageFile.getConfig().getString("FILE-RELOAD")));
         } else {
            this.getUsage(sender, label);
         }

         return true;
      }
   }
}
