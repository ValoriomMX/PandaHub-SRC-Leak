package dev.panda.hub.manager.menu.trail;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.menu.trail.buttons.TrailButton;
import dev.panda.hub.manager.types.TrailType;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class TrailMenu {
   public static void getTrail(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 36, "Trail");
      inventory.setItem(10, TrailType.HEART.getItem(player));
      inventory.setItem(11, TrailType.FLAME.getItem(player));
      inventory.setItem(12, TrailType.SLIME.getItem(player));
      inventory.setItem(13, TrailType.NOTE.getItem(player));
      inventory.setItem(14, TrailType.CLOUD.getItem(player));
      inventory.setItem(15, TrailType.SMOKE.getItem(player));
      inventory.setItem(16, TrailType.VILLAGER.getItem(player));
      inventory.setItem(19, TrailType.CRITICAL.getItem(player));
      inventory.setItem(20, TrailType.EXPLOSION.getItem(player));
      inventory.setItem(21, TrailType.FIREWORK.getItem(player));
      inventory.setItem(22, TrailType.LAVA.getItem(player));
      inventory.setItem(23, TrailType.WATER.getItem(player));
      inventory.setItem(24, TrailType.SNOW.getItem(player));
      inventory.setItem(25, TrailType.SPELL.getItem(player));
      inventory.setItem(30, ItemStackManager.getBack());
      inventory.setItem(31, TrailButton.getRemove(player));
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }
}
