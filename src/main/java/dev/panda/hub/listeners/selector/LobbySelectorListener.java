package dev.panda.hub.listeners.selector;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.utilities.Bungee;
import dev.panda.hub.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class LobbySelectorListener implements Listener {
   @EventHandler
   public void onLobbySelectorClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         if (event.getInventory().getTitle().equals(CC.set(ConfigFile.getConfig().getString("LOBBY-SELECTOR-TITLE")))) {
            event.setCancelled(true);
            ItemStack stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR)) {
               return;
            }

            int slot = event.getSlot() + 1;
            ConfigFile.getConfig().getConfigurationSection("LOBBY-SELECTOR").getKeys(false).forEach((s) -> {
               if (slot == ConfigFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(s).append(".SLOT")))) {
                  ByteArrayDataOutput dataOutput = ByteStreams.newDataOutput();
                  dataOutput.writeUTF("ConnectOther");
                  dataOutput.writeUTF(player.getName());
                  dataOutput.writeUTF(Bungee.getLobby(s));
                  player.sendPluginMessage(Hub.getInstance(), "BungeeCord", dataOutput.toByteArray());
               }

            });
         }

      }
   }

   public LobbySelectorListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }
}
