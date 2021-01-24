package dev.panda.hub.manager.menu.setting;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.InventoryFile;
import dev.panda.hub.manager.menu.setting.buttons.SettingButton;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SettingMenu {
   public static void getSetting(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, InventoryFile.getConfig().getInt("SETTINGS.SIZE") * 9, CC.set(InventoryFile.getConfig().getString("SETTINGS.TITLE")));
      inventory.setItem(InventoryFile.getConfig().getInt("SETTINGS.SCOREBOARD.SLOT") - 1, SettingButton.getScoreboard(player));
      inventory.setItem(InventoryFile.getConfig().getInt("SETTINGS.PRIVATE-MESSAGE.SLOT") - 1, SettingButton.getPrivateMessage(player));
      inventory.setItem(InventoryFile.getConfig().getInt("SETTINGS.SOUND-MESSAGE.SLOT") - 1, SettingButton.getSoundMessage(player));
      inventory.setItem(InventoryFile.getConfig().getInt("SETTINGS.COLOR-MESSAGE.SLOT") - 1, SettingButton.getColorMessage());
      inventory.setItem(InventoryFile.getConfig().getInt("SETTINGS.COLOR-CHAT.SLOT") - 1, SettingButton.getColorChat());
      inventory.setItem(InventoryFile.getConfig().getInt("SETTINGS.TAG.SLOT") - 1, SettingButton.getTag());
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }
}
