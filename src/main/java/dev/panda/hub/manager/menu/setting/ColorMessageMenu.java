package dev.panda.hub.manager.menu.setting;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.menu.setting.buttons.ColorMessageButton;
import dev.panda.hub.manager.types.ColorMessageType;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ColorMessageMenu {
   public static void getColorMessage(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 27, "Color Message");
      ColorMessageType[] types = ColorMessageType.values();
      int length = types.length;

      for(int i = 0; i < length; ++i) {
         ColorMessageType type = types[i];
         inventory.addItem(new ItemStack[]{type.getItem(player)});
      }

      inventory.setItem(21, ItemStackManager.getBack());
      inventory.setItem(22, ColorMessageButton.getRemove(player));
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }
}
