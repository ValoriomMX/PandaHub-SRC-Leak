package dev.panda.hub.manager.menu.cosmetic;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.InventoryFile;
import dev.panda.hub.manager.menu.cosmetic.buttons.CosmeticButton;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CosmeticMenu {
   public static void getCosmetic(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, InventoryFile.getConfig().getInt("COSMETICS.SIZE") * 9, CC.set(InventoryFile.getConfig().getString("COSMETICS.TITLE")));
      inventory.setItem(InventoryFile.getConfig().getInt("COSMETICS.OUTFIT.SLOT") - 1, CosmeticButton.getOutfit());
      inventory.setItem(InventoryFile.getConfig().getInt("COSMETICS.TRAIL.SLOT") - 1, CosmeticButton.getTrail());
      inventory.setItem(InventoryFile.getConfig().getInt("COSMETICS.GADGET.SLOT") - 1, CosmeticButton.getGadget());
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }
}
