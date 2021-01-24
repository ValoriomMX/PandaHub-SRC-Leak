package dev.panda.hub.manager.rank.impl;

import dev.panda.hub.manager.rank.RankManager;
import org.bukkit.entity.Player;

public class Default implements RankManager {
   public String getRankPrefix(Player player) {
      return "Default";
   }

   public String getRankName(Player player) {
      return "Default";
   }

   public String getRankSuffix(Player player) {
      return "Default";
   }
}
