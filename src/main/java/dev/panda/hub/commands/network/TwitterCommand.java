package dev.panda.hub.commands.network;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TwitterCommand implements CommandExecutor {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      MessageFile.getConfig().getStringList("TWITTER").forEach((s) -> {
         sender.sendMessage(CC.set(s.replace("{twitter}", ConfigFile.getConfig().getString("TWITTER"))));
      });
      return true;
   }

   public TwitterCommand() {
      Hub.getInstance().getCommand("twitter").setExecutor(this);
   }
}
