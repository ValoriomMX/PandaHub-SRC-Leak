package dev.panda.hub.manager.menu.tag;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.menu.tag.buttons.TagButton;
import dev.panda.hub.manager.types.TagType;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class TagMenu {
   public static void getTag(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 36, "Tags");
      inventory.setItem(10, TagType.HEART.getItem(player));
      inventory.setItem(11, TagType.MUSIC.getItem(player));
      inventory.setItem(12, TagType.STAR.getItem(player));
      inventory.setItem(13, TagType.FROZEN.getItem(player));
      inventory.setItem(14, TagType.FLOWER.getItem(player));
      inventory.setItem(15, TagType.SNOWMAN.getItem(player));
      inventory.setItem(16, TagType.TOXIC.getItem(player));
      inventory.setItem(30, ItemStackManager.getBack());
      inventory.setItem(31, TagButton.getRemove(player));
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }
}
