package dev.panda.hub.utilities.tablist.v1_7.adapter;

import dev.panda.hub.utilities.tablist.v1_7.entry.TabEntry;
import java.util.List;
import org.bukkit.entity.Player;

public interface TabAdapter {
   List<TabEntry> getLines(Player player);

   String getFooter(Player player);

   String getHeader(Player player);
}
