package dev.panda.hub.listeners.selector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.queue.Queue;
import dev.panda.hub.utilities.Bungee;
import dev.panda.hub.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ServerSelectorListener implements Listener {
   
   private Hub plugin = Hub.getInstance();

   @EventHandler
   public void onServerSelectorClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         if (event.getInventory().getTitle().equals(CC.set(ConfigFile.getConfig().getString("SERVER-SELECTOR-TITLE")))) {
            event.setCancelled(true);
            ItemStack stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR)) {
               return;
            }

            int slot = event.getSlot() + 1;
            ConfigFile.getConfig().getConfigurationSection("SERVER-SELECTOR").getKeys(false).forEach((s) -> {
               if (slot == ConfigFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".SLOT")))) {
                  Queue queue;
                  if (ConfigFile.getConfig().getBoolean(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".EXECUTE-COMMAND.ENABLE")))) {
                     if (ConfigFile.getConfig().getBoolean(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".QUEUE")))) {
                        queue = this.plugin.getQueueManager().getQueue(ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".SERVER"))));
                        queue.addEntry(player);
                        player.closeInventory();
                     }

                     ConfigFile.getConfig().getStringList(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".EXECUTE-COMMAND.COMMANDS"))).forEach((s1) -> {
                        Bukkit.dispatchCommand(player, s1.replace("{server}", Bungee.getServer(s)));
                     });
                  } else if (ConfigFile.getConfig().getBoolean(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".QUEUE")))) {
                     queue = this.plugin.getQueueManager().getQueue(ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".SERVER"))));
                     queue.addEntry(player);
                     player.closeInventory();
                  } else {
                     ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
                     dataOutput.writeUTF("ConnectOther");
                     dataOutput.writeUTF(player.getName());
                     dataOutput.writeUTF(Bungee.getServer(s));
                     player.sendPluginMessage(Hub.getInstance(), "BungeeCord", dataOutput.toByteArray());
                  }
               }

            });
         }

      }
   }

   public ServerSelectorListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }
}
