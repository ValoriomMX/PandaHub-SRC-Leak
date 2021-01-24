package dev.panda.hub.manager.menu;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuManager {
   public static void getServerSelector(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, ConfigFile.getConfig().getInt("SERVER-SELECTOR-SIZE") * 9, CC.set(ConfigFile.getConfig().getString("SERVER-SELECTOR-TITLE")));
      ConfigFile.getConfig().getConfigurationSection("SERVER-SELECTOR").getKeys(false).forEach((s) -> {
         int i = ConfigFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(s).append(".SLOT"))) - 1;
         inventory.setItem(i, ItemStackManager.getServerSelector(s));
      });
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }

   public static void getLobbySelector(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, ConfigFile.getConfig().getInt("LOBBY-SELECTOR-SIZE") * 9, CC.set(ConfigFile.getConfig().getString("LOBBY-SELECTOR-TITLE")));
      ConfigFile.getConfig().getConfigurationSection("LOBBY-SELECTOR").getKeys(false).forEach((s) -> {
         int i = ConfigFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(s).append(".SLOT"))) - 1;
         inventory.setItem(i, ItemStackManager.getLobbySelector(s));
      });
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }
}
