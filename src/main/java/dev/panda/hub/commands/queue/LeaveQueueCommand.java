package dev.panda.hub.commands.queue;

import dev.panda.hub.Hub;
import dev.panda.hub.files.QueueFile;
import dev.panda.hub.utilities.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveQueueCommand implements CommandExecutor {
   private Hub plugin = Hub.getInstance();

   public LeaveQueueCommand() {
      this.plugin.getCommand("leavequeue").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         return true;
      } else {
         Player player = (Player)sender;
         if (this.plugin.getQueueManager().getQueue(player) == null) {
            player.sendMessage(CC.set(QueueFile.getConfig().getString("NOT_IN_QUEUE")));
            return true;
         } else if (this.plugin.getQueueManager().getQueue(player) != null) {
            player.sendMessage(CC.set(QueueFile.getConfig().getString("LEAVE_QUEUE").replace("{queue}", this.plugin.getQueueManager().getQueueName(player))));
            this.plugin.getQueueManager().getQueue(player).removeEntry(player);
            return true;
         } else {
            return true;
         }
      }
   }
}
