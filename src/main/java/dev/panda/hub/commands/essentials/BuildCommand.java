package dev.panda.hub.commands.essentials;

import com.google.common.collect.Sets;
import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import java.util.Set;
import java.util.UUID;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

   private static BuildCommand instance;
   private final Set<UUID> buildMode = Sets.newHashSet();
   public static BuildCommand getInstance() {
      return instance;
   }
   public Set<UUID> getBuildMode() {
      return this.buildMode;
   }

   public BuildCommand() {
      instance = this;
      Hub.getInstance().getCommand("build").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.build")) {
            CC.getNoPermission(player);
            return true;
         } else {
            if (this.buildMode.contains(player.getUniqueId())) {
               this.buildMode.remove(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("BUILD.DISABLE")));
            } else {
               this.buildMode.add(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("BUILD.ENABLE")));
            }

            return true;
         }
      }
   }
}
