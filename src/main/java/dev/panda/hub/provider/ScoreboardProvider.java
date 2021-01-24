package dev.panda.hub.provider;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ScoreboardFile;
import dev.panda.hub.manager.queue.Queue;
import dev.panda.hub.utilities.Bungee;
import dev.panda.hub.utilities.scoreboard.BoardAdapter;
import dev.panda.hub.utilities.scoreboard.BoardStyle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardProvider implements BoardAdapter {
   
   private Hub plugin = Hub.getInstance();

   public BoardStyle getBoardStyle(Player player) {
      return BoardStyle.DEFAULT;
   }

   public List<String> getLines(Player player) {
      List<String> list = new ArrayList();
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
      SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
      ScoreboardFile.getConfig().getStringList("SCOREBOARD").forEach((s) -> {
         if (s.contains("{queue}")) {
            if (this.plugin.getQueueManager().getQueue(player) != null) {
               Queue queue = this.plugin.getQueueManager().getQueue(player);
               ScoreboardFile.getConfig().getStringList("QUEUE-SECTION").forEach((s1) -> {
                  list.add(s1.replace("{name}", queue.getServer()).replace("{position}", String.valueOf(queue.getPosition(player))).replace("{size}", String.valueOf(queue.getSize())));
               });
            }

         } else {
            list.add(s.replace("{name}", player.getName()).replace("{rank}", Hub.getInstance().getRankManager().getRankName(player)).replace("{rank-prefix}", Hub.getInstance().getRankManager().getRankPrefix(player)).replace("{rank-suffix}", Hub.getInstance().getRankManager().getRankSuffix(player)).replace("{date}", format.format(date)).replace("{time}", format2.format(date)).replace("{online}", String.valueOf(Bungee.getPlayerCount("ALL"))).replace("{slots}", String.valueOf(Bukkit.getMaxPlayers())));
         }
      });
      return (List)(Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, list) : list);
   }

   public String getTitle(Player player) {
      return ScoreboardFile.getConfig().getString("SCOREBOARD-TITLE");
   }
}
