package dev.panda.hub.manager.queue;

import dev.panda.hub.Hub;
import dev.panda.hub.files.QueueFile;
import dev.panda.hub.utilities.Bungee;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class QueueManager implements Listener {
   
   private Hub plugin = Hub.getInstance();
   private final List<Queue> queues = new ArrayList();

   public Queue getQueue(Player player) {
      Iterator iterator = this.queues.iterator();

      Queue queue;
      do {
         if (!iterator.hasNext()) {
            return null;
         }

         queue = (Queue)iterator.next();
      } while(!queue.getPlayers().contains(player));

      return queue;
   }

   public int getPriority(Player player) {
      return QueueFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("QUEUE.PRIORITY.").append(this.plugin.getRankManager().getRankName(player))));
   }

   public QueueManager() {
      Bukkit.getPluginManager().registerEvents(this, Hub.getInstance());
      QueueFile.getConfig().getStringList("QUEUE.SERVERS").forEach((s) -> {
         this.queues.add(new Queue(s));
      });
      (new BukkitRunnable() {
         public void run() {
            queues.forEach((q) -> {
               int players = Bungee.getPlayerCount(q.getServer());
               if (!q.isPaused() && !q.getPlayers().isEmpty() && players < q.getLimit()) {
                  if (q.getPlayerAt(0).isOnline()) {
                     q.sendFirst();
                  }

                  if (q.getPlayerAt(0).isOnline()) {
                     return;
                  }

                  Player player = q.getPlayerAt(0);
                  if (q.getPlayers().contains(player)) {
                     q.getPlayers().remove(player);
                  }

                  if (!q.getTaskMap().containsKey(player)) {
                     return;
                  }

                  ((BukkitTask)q.getTaskMap().get(player)).cancel();
                  q.getTaskMap().remove(player);
               }

            });
         }
      }).runTaskTimerAsynchronously(Hub.getInstance(), 0L, (long)QueueFile.getConfig().getInt("QUEUE.DELAY"));
   }

   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      Iterator iterator = this.queues.iterator();

      while(iterator.hasNext()) {
         Queue queue = (Queue)iterator.next();
         if (queue.getPlayers().contains(player)) {
            queue.removeEntry(player);
         }
      }

   }

   public Queue getQueue(String input) {
      Iterator iterator = this.queues.iterator();

      Queue queue;
      do {
         if (!iterator.hasNext()) {
            return null;
         }

         queue = (Queue)iterator.next();
      } while(!queue.getServer().equalsIgnoreCase(input));

      return queue;
   }

   public String getQueueName(Player player) {
      return this.getQueue(player).getServer();
   }

   @EventHandler
   public void onKick(PlayerKickEvent event) {
      Player player = event.getPlayer();
      Iterator iterator = this.queues.iterator();

      while(iterator.hasNext()) {
         Queue queue = (Queue)iterator.next();
         if (queue.getPlayers().contains(player)) {
            queue.removeEntry(player);
         }
      }

   }
}
