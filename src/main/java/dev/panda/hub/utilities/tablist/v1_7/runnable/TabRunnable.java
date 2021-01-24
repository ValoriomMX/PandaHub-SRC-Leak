package dev.panda.hub.utilities.tablist.v1_7.runnable;

import dev.panda.hub.utilities.tablist.v1_7.adapter.TabAdapter;
import dev.panda.hub.utilities.tablist.v1_7.entry.TabEntry;
import dev.panda.hub.utilities.tablist.v1_7.layout.TabLayout;
import dev.panda.hub.utilities.tablist.v1_7.skin.Skin;
import java.beans.ConstructorProperties;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TabRunnable implements Runnable {
   
   private TabAdapter adapter;

   public void run() {
      Iterator iterator = Bukkit.getServer().getOnlinePlayers().iterator();

      while(iterator.hasNext()) {
         Player player = (Player)iterator.next();
         TabLayout layout = (TabLayout)TabLayout.getLayoutMapping().get(player.getUniqueId());
         Iterator iterator2 = this.adapter.getLines(player).iterator();

         while(iterator2.hasNext()) {
            TabEntry entry = (TabEntry)iterator2.next();
            layout.update(entry.getColumn(), entry.getRow(), entry.getText(), entry.getPing(), entry.getSkin());
         }

         for(int i = 0; i < 20; ++i) {
            for(int t = 0; t < 3; ++t) {
               if (layout.getByLocation(this.adapter.getLines(player), t, i) == null) {
                  layout.update(t, i, "", 0, Skin.DEFAULT_SKIN);
               }
            }
         }
      }

   }

   @ConstructorProperties({"adapter"})
   public TabRunnable(TabAdapter adapter) {
      this.adapter = adapter;
   }
}
