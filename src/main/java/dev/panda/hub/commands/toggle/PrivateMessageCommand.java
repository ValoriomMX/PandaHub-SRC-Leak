package dev.panda.hub.commands.toggle;

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

public class PrivateMessageCommand implements CommandExecutor {

   private static PrivateMessageCommand instance;
   private Set<UUID> privateMessage = Sets.newHashSet();
   public static PrivateMessageCommand getInstance() {
      return instance;
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.toggleprivatemessage")) {
            CC.getNoPermission(player);
            return true;
         } else {
            if (this.privateMessage.contains(player.getUniqueId())) {
               this.privateMessage.remove(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("PRIVATE-MESSAGE.ENABLE")));
            } else {
               this.privateMessage.add(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("PRIVATE-MESSAGE.DISABLE")));
            }

            return true;
         }
      }
   }

   public PrivateMessageCommand() {
      instance = this;
      Hub.getInstance().getCommand("toggleprivatemessage").setExecutor(this);
   }

   public Set<UUID> getPrivateMessage() {
      return this.privateMessage;
   }
}
