package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.commands.toggle.PrivateMessageCommand;
import dev.panda.hub.commands.toggle.SoundCommand;
import dev.panda.hub.manager.menu.setting.buttons.ColorMessageButton;
import dev.panda.hub.utilities.CC;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
   
   public Map<Player, Player> lastMessage;
   private static MessageCommand instance;

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.message")) {
            CC.getNoPermission(player);
            return true;
         } else if (args.length < 2) {
            player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cUsage: /").append(label).append(" <player> <message>"))));
            return true;
         } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
               CC.getPlayerNotFound(sender, args[0]);
               return true;
            } else if (PrivateMessageCommand.getInstance().getPrivateMessage().contains(target.getUniqueId())) {
               player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&c").append(target.getName()).append(" has private messages disabled."))));
               return true;
            } else {
               StringBuilder builder = new StringBuilder();

               for(int i = 1; i != args.length; ++i) {
                  builder.append(args[i]).append(" ");
               }

               String s = String.valueOf((new StringBuilder()).append(Hub.getInstance().getRankManager().getRankPrefix(player)).append(player.getName()));
               byte b = Byte.parseByte(String.valueOf((new StringBuilder()).append(Hub.getInstance().getRankManager().getRankPrefix(target)).append(target.getName())));
               if (!SoundCommand.getInstance().getPrivateSound().contains(target.getUniqueId())) {
                  target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
               }

               player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(ColorMessageButton.getColorMessage(player)).append("(To ").append(b).append(ColorMessageButton.getColorMessage(player)).append(") ").append(builder))));
               target.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(ColorMessageButton.getColorMessage(target)).append("(From ").append(s).append(ColorMessageButton.getColorMessage(target)).append(") ").append(builder))));
               this.lastMessage.put(player, target);
               this.lastMessage.put(target, player);
               return true;
            }
         }
      }
   }

   public MessageCommand() {
      instance = this;
      this.lastMessage = new HashMap();
      Hub.getInstance().getCommand("message").setExecutor(this);
   }

   public static MessageCommand getInstance() {
      return instance;
   }
}
