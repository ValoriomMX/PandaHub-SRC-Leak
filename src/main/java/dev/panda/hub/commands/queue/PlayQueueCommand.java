package dev.panda.hub.commands.queue;

import dev.panda.hub.Hub;
import dev.panda.hub.files.QueueFile;
import dev.panda.hub.manager.queue.Queue;
import dev.panda.hub.utilities.CC;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayQueueCommand implements CommandExecutor {

   private Hub plugin = Hub.getInstance();

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (args.length == 0) {
            player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cUsage: /").append(label).append(" <server>"))));
            return true;
         } else {
            List<String> list = new ArrayList(QueueFile.getConfig().getStringList("QUEUE.SERVERS"));
            Queue queue = this.plugin.getQueueManager().getQueue(args[0]);

            try {
               queue.addEntry(player);
            } catch (Exception var9) {
               player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&cAvailables Servers: ").append(label).append(" ").append(list.toString().replace("[", "").replace("]", "")))));
            }

            return true;
         }
      }
   }

   public PlayQueueCommand() {
      this.plugin.getCommand("play").setExecutor(this);
   }
}
