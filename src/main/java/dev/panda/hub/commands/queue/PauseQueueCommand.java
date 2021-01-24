package dev.panda.hub.commands.queue;

import dev.panda.hub.Hub;
import dev.panda.hub.files.QueueFile;
import dev.panda.hub.manager.queue.Queue;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PauseQueueCommand implements CommandExecutor {

   private Hub plugin = Hub.getInstance();

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!sender.hasPermission("pandahub.queue.pause")) {
         CC.getNoPermission(sender);
         return true;
      } else if (args.length != 1) {
         sender.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cUsage: /").append(label).append(" <queue>"))));
         return true;
      } else {
         String s = args[0];
         if (this.plugin.getQueueManager().getQueue(s) == null) {
            sender.sendMessage(CC.set(QueueFile.getConfig().getString("INVALID_QUEUE")));
            return true;
         } else {
            Queue queue = this.plugin.getQueueManager().getQueue(s);
            if (queue.isPaused()) {
               sender.sendMessage(CC.set(QueueFile.getConfig().getString("UNPAUSED_QUEUE").replace("{queue}", queue.getServer())));
            } else {
               sender.sendMessage(CC.set(QueueFile.getConfig().getString("PAUSED_QUEUE").replace("{queue}", queue.getServer())));
            }

            queue.setPaused(!queue.isPaused());
            return true;
         }
      }
   }

   public PauseQueueCommand() {
      this.plugin.getCommand("pausequeue").setExecutor(this);
   }
}
