package dev.panda.hub.commands.network;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StoreCommand implements CommandExecutor {
   public StoreCommand() {
      Hub.getInstance().getCommand("store").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      MessageFile.getConfig().getStringList("STORE").forEach((s) -> {
         sender.sendMessage(CC.set(s.replace("{store}", ConfigFile.getConfig().getString("STORE"))));
      });
      return true;
   }
}
