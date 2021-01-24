package dev.panda.hub.commands.essentials;

import dev.panda.hub.Hub;
import dev.panda.hub.commands.toggle.SoundCommand;
import dev.panda.hub.manager.menu.setting.buttons.ColorMessageButton;
import dev.panda.hub.utilities.CC;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {
   public ReplyCommand() {
      Hub.getInstance().getCommand("reply").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.message")) {
            CC.getNoPermission(player);
            return true;
         } else if (args.length < 1) {
            player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cUsage: /").append(label).append(" <message>"))));
            return true;
         } else {
            Player target = (Player)MessageCommand.getInstance().lastMessage.get(player);
            if (target == null) {
               player.sendMessage(CC.set("&cNothing to reply."));
               return true;
            } else {
               StringBuilder builder = new StringBuilder();

               for(int i = 0; i != args.length; ++i) {
                  builder.append(args[i]).append(" ");
               }

               String i = String.valueOf((new StringBuilder()).append(Hub.getInstance().getRankManager().getRankPrefix(player)).append(player.getName()));
               String s = String.valueOf((new StringBuilder()).append(Hub.getInstance().getRankManager().getRankPrefix(target)).append(target.getName()));
               if (!SoundCommand.getInstance().getPrivateSound().contains(target.getUniqueId())) {
                  target.playSound(target.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
               }

               player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(ColorMessageButton.getColorMessage(player)).append("(To ").append(s).append(ColorMessageButton.getColorMessage(player)).append(") ").append(builder))));
               target.sendMessage(CC.set(String.valueOf((new StringBuilder()).append(ColorMessageButton.getColorMessage(target)).append("(From ").append(i).append(ColorMessageButton.getColorMessage(target)).append(") ").append(builder))));
               MessageCommand.getInstance().lastMessage.put(player, target);
               MessageCommand.getInstance().lastMessage.put(target, player);
               return true;
            }
         }
      }
   }
}
