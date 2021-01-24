package dev.panda.hub.manager.rank.impl;

import dev.panda.hub.Hub;
import dev.panda.hub.manager.rank.RankManager;
import org.bukkit.entity.Player;

public class Vault implements RankManager {
   public String getRankPrefix(Player player) {
      return Hub.getInstance().getChat().getPlayerPrefix(player);
   }

   public String getRankSuffix(Player player) {
      return Hub.getInstance().getChat().getPlayerSuffix(player);
   }

   public String getRankName(Player player) {
      return Hub.getInstance().getChat().getPrimaryGroup(player);
   }
}
