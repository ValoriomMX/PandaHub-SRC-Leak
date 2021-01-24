package dev.panda.hub.manager.menu.gadget;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.menu.gadget.buttons.GadgetButton;
import dev.panda.hub.manager.types.GadgetType;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GadgetMenu {
   public static void getGadget(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 36, "Gadget");
      inventory.setItem(10, GadgetType.BOW_TELEPORT.getItem(player));
      inventory.setItem(11, GadgetType.SNOWMAN.getItem(player));
      inventory.setItem(30, ItemStackManager.getBack());
      inventory.setItem(31, GadgetButton.getRemove(player));
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }
}
