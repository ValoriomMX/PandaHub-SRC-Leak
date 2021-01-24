package dev.panda.hub.manager.rank;

import org.bukkit.entity.Player;

public interface RankManager {
   String getRankSuffix(Player player);

   String getRankPrefix(Player player);

   String getRankName(Player player);
}
