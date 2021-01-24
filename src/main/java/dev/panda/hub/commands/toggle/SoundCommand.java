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

public class SoundCommand implements CommandExecutor {

   private Set<UUID> privateSound = Sets.newHashSet();
   private static SoundCommand instance;

   public SoundCommand() {
      instance = this;
      Hub.getInstance().getCommand("togglesound").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.togglesound")) {
            CC.getNoPermission(player);
            return true;
         } else {
            if (this.privateSound.contains(player.getUniqueId())) {
               this.privateSound.remove(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("SOUND.ENABLE")));
            } else {
               this.privateSound.add(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("SOUND.DISABLE")));
            }

            return true;
         }
      }
   }

   public Set<UUID> getPrivateSound() {
      return this.privateSound;
   }

   public static SoundCommand getInstance() {
      return instance;
   }
}
