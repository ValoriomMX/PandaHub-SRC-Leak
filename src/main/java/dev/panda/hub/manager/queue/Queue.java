package dev.panda.hub.manager.queue;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.panda.hub.Hub;
import dev.panda.hub.files.QueueFile;
import dev.panda.hub.utilities.CC;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Queue {
   
   private boolean paused;
   private Map<Player, BukkitTask> taskMap;
   private String server;
   private List<Player> list;
   private int limit;

   public boolean isPaused() {
      return this.paused;
   }

   public int getLimit() {
      return this.limit;
   }

   public void sendFirst() {
      if (!this.list.isEmpty()) {
         Player player = ((Player)this.list.get(0)).getPlayer();
         ByteArrayDataOutput byteArray = ByteStreams.newDataOutput();
         byteArray.writeUTF("ConnectOther");
         byteArray.writeUTF(player.getName());
         byteArray.writeUTF(this.server);
         player.sendPluginMessage(Hub.getInstance(), "BungeeCord", byteArray.toByteArray());
      }

   }

   public void setPaused(boolean type) {
      this.paused = type;
   }

   public List<Player> getPlayers() {
      return this.list;
   }

   public void addEntry(Player player) {
      if (!this.list.contains(player)) {
         if (Hub.getInstance().getQueueManager().getPriority(player) == 0) {
            this.sendDirect(player);
            player.sendMessage(CC.set(QueueFile.getConfig().getString("SEND")));
         } else {
            this.list.add(player);
            Iterator iterator = this.list.iterator();

            while(iterator.hasNext()) {
               Player p = (Player)iterator.next();
               int i = this.list.indexOf(p);
               if (player != p && Hub.getInstance().getQueueManager().getPriority(player) < Hub.getInstance().getQueueManager().getPriority(p)) {
                  Collections.swap(this.list, i, this.list.size() - 1);
               }
            }

         }
      }
   }

   public void setServer(String input) {
      this.server = input;
   }

   public void setList(List<Player> input) {
      this.list = input;
   }

   public void setLimit(int input) {
      this.limit = input;
   }

   public void sendDirect(Player player) {
      ByteArrayDataOutput byteArray = ByteStreams.newDataOutput();
      byteArray.writeUTF("ConnectOther");
      byteArray.writeUTF(player.getName());
      byteArray.writeUTF(this.server);
      player.sendPluginMessage(Hub.getInstance(), "BungeeCord", byteArray.toByteArray());
   }

   public Player getPlayerAt(int input) {
      return (Player)this.list.get(input);
   }

   public int getSize() {
      return this.list.size();
   }

   public Map<Player, BukkitTask> getTaskMap() {
      return this.taskMap;
   }

   public List<Player> getList() {
      return this.list;
   }

   public int getPosition(Player player) {
      return this.list.indexOf(player) + 1;
   }

   public void removeEntry(Player player) {
      this.list.remove(player);
   }

   public String getServer() {
      return this.server;
   }

   public Queue(final String input) {
      this.server = input;
      this.list = new ArrayList();
      this.taskMap = new HashMap();
      this.paused = false;
      this.limit = 1000;
      (new BukkitRunnable() {
         public void run() {
            list.forEach((p) -> {
               if (p.isOnline()) {
                  QueueFile.getConfig().getStringList("POSITION").forEach((p2) -> {
                     p.sendMessage(CC.set(p2.replace("{position}", String.valueOf(getPosition(p))).replace("{size}", String.valueOf(getSize())).replace("{server}", input)));
                  });
               } else {
                  list.remove(p);
               }

            });
         }
      }).runTaskTimerAsynchronously(Hub.getInstance(), (long)QueueFile.getConfig().getInt("QUEUE.POSITION-INTERVAL"), (long)QueueFile.getConfig().getInt("QUEUE.POSITION-INTERVAL"));
   }

   public void setTaskMap(Map<Player, BukkitTask> input) {
      this.taskMap = input;
   }
}
