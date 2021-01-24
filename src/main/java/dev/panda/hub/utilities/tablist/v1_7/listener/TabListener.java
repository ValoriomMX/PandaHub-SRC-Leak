package dev.panda.hub.utilities.tablist.v1_7.listener;

import dev.panda.hub.utilities.tablist.v1_7.Tablist_v1_7;
import dev.panda.hub.utilities.tablist.v1_7.layout.TabLayout;
import java.beans.ConstructorProperties;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TabListener implements Listener {

   private Tablist_v1_7 tablist;

   @EventHandler
   public void onKick(PlayerKickEvent event) {
      Player player = event.getPlayer();
      TabLayout.getLayoutMapping().remove(player.getUniqueId());
   }

   @ConstructorProperties({"tablist"})
   public TabListener(Tablist_v1_7 tablist) {
      this.tablist = tablist;
   }

   @EventHandler
   public void onQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      TabLayout.getLayoutMapping().remove(player.getUniqueId());
   }

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      TabLayout tabLayout = new TabLayout(this.tablist, player);
      tabLayout.create();
      tabLayout.setHeaderAndFooter();
      TabLayout.getLayoutMapping().put(player.getUniqueId(), tabLayout);
   }
}
