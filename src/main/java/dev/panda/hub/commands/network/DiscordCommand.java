package dev.panda.hub.commands.network;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      MessageFile.getConfig().getStringList("DISCORD").forEach((s) -> {
         sender.sendMessage(CC.set(s.replace("{discord}", ConfigFile.getConfig().getString("DISCORD"))));
      });
      return true;
   }

   public DiscordCommand() {
      Hub.getInstance().getCommand("discord").setExecutor(this);
   }
}
