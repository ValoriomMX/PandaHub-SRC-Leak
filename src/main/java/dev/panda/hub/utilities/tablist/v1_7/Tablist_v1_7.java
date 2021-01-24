package dev.panda.hub.utilities.tablist.v1_7;

import dev.panda.hub.utilities.tablist.v1_7.adapter.TabAdapter;
import dev.panda.hub.utilities.tablist.v1_7.listener.TabListener;
import dev.panda.hub.utilities.tablist.v1_7.packet.TabPacket;
import dev.panda.hub.utilities.tablist.v1_7.runnable.TabRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Tablist_v1_7 {
   
   private TabAdapter adapter;
   private static Tablist_v1_7 instance;
   public static Tablist_v1_7 getInstance() {
      return instance;
   }

   public TabAdapter getAdapter() {
      return this.adapter;
   }

   public Tablist_v1_7(TabAdapter tabAdapter, JavaPlugin plugin) {
      instance = this;
      this.adapter = tabAdapter;
      new TabPacket(plugin);
      Bukkit.getServer().getPluginManager().registerEvents(new TabListener(this), plugin);
      Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new TabRunnable(tabAdapter), 20L, 20L);
   }
}
