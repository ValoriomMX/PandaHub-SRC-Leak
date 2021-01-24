package dev.panda.hub.provider;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.TablistFile;
import dev.panda.hub.manager.queue.Queue;
import dev.panda.hub.utilities.Bungee;
import dev.panda.hub.utilities.Utilities;
import dev.panda.hub.utilities.tablist.v1_7.adapter.TabAdapter;
import dev.panda.hub.utilities.tablist.v1_7.entry.TabEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TablistProvider_v1_7 implements TabAdapter {
   
   private final Hub plugin = Hub.getInstance();
   private final Pattern serverStatus = Pattern.compile("(%status_((?:[a-z-A-Z][a-z-A-Z0-9_]*))%)");
   private final Pattern playerCount = Pattern.compile("(%count_((?:[a-z-A-Z][a-z-A-Z0-9_]*))%)");

   public List<TabEntry> getLines(Player player) {
      List<TabEntry> entrys = new ArrayList();

      for(int i = 0; i <= 19; ++i) {
         entrys.add(new TabEntry(0, i, this.replace((String)TablistFile.getConfig().getStringList("TABLIST.LEFT").get(i), player)));
         entrys.add(new TabEntry(1, i, this.replace((String)TablistFile.getConfig().getStringList("TABLIST.CENTER").get(i), player)));
         entrys.add(new TabEntry(2, i, this.replace((String)TablistFile.getConfig().getStringList("TABLIST.RIGHT").get(i), player)));
         entrys.add(new TabEntry(3, i, this.replace((String)TablistFile.getConfig().getStringList("TABLIST.EXTERNAL-RIGHT").get(i), player)));
      }

      return entrys;
   }

   public String getHeader(Player player) {
      return TablistFile.getConfig().getString("TABLIST-HEADER");
   }

   public String replace(String input, Player player) {
      Queue queue = this.plugin.getQueueManager().getQueue(player);
      input = input.replace("{name}", player.getName()).replace("{rank}", Hub.getInstance().getRankManager().getRankName(player)).replace("{rank-prefix}", Hub.getInstance().getRankManager().getRankPrefix(player)).replace("{rank-suffix}", Hub.getInstance().getRankManager().getRankSuffix(player)).replace("{online}", String.valueOf(Bungee.getPlayerCount("ALL"))).replace("{slots}", String.valueOf(Bukkit.getMaxPlayers())).replace("{discord}", ConfigFile.getConfig().getString("DISCORD")).replace("{store}", ConfigFile.getConfig().getString("STORE")).replace("{teamspeak}", ConfigFile.getConfig().getString("TEAMSPEAK")).replace("{twitter}", ConfigFile.getConfig().getString("TWITTER")).replace("{website}", ConfigFile.getConfig().getString("WEBSITE")).replace("{ping}", String.valueOf(Utilities.getPlayerPing(player))).replace("{queue-name}", queue != null ? queue.getServer() : "None").replace("{queue-pos}", queue != null ? String.valueOf(queue.getPosition(player)) : String.valueOf(0)).replace("{queue-size}", queue != null ? String.valueOf(queue.getSize()) : String.valueOf(0));
      Matcher matcher = this.playerCount.matcher(input);
      Matcher matcher2 = this.serverStatus.matcher(input);
      String s;
      if (matcher.find()) {
         s = matcher.group(2);
         int players = Bungee.getPlayerCount(s);
         input = input.replace(String.valueOf((new StringBuilder()).append("%count_").append(s).append("%")), String.valueOf(players));
      }

      if (matcher2.find()) {
         s = matcher2.group(2);
         boolean players = Bungee.isOnline(s);
         input = input.replace(String.valueOf((new StringBuilder()).append("%status_").append(s).append("%")), players ? "&aOnline" : "&cOffline");
      }

      return input;
   }

   public String getFooter(Player player) {
      return TablistFile.getConfig().getString("TABLIST-FOOTER");
   }
}
