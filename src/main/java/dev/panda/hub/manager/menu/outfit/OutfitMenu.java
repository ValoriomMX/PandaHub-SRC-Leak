package dev.panda.hub.manager.menu.outfit;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.menu.outfit.buttons.OutfitButton;
import dev.panda.hub.utilities.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class OutfitMenu {
   public static void getOutfitEditor(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, "Outfit Editor");
      inventory.setItem(4, OutfitButton.getOutfits());
      inventory.setItem(13, OutfitButton.getEquipHelmet(player));
      inventory.setItem(22, OutfitButton.getEquipChestplate(player));
      inventory.setItem(31, OutfitButton.getEquipLeggings(player));
      inventory.setItem(40, OutfitButton.getEquipBoots(player));
      inventory.setItem(48, ItemStackManager.getBack());
      inventory.setItem(49, OutfitButton.getRemove());
      if (ConfigFile.getConfig().getBoolean("FILL-MENUS")) {
         Utilities.getFill(inventory);
      }

      player.openInventory(inventory);
   }

   public static void getOutfitChestplate(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 18, "Select Chestplate");
      inventory.setItem(0, OutfitButton.getChestplate("&cRed", Color.RED));
      inventory.setItem(1, OutfitButton.getChestplate("&6Orange", Color.ORANGE));
      inventory.setItem(2, OutfitButton.getChestplate("&eYellow", Color.YELLOW));
      inventory.setItem(3, OutfitButton.getChestplate("&2Green", Color.GREEN));
      inventory.setItem(4, OutfitButton.getChestplate("&aLime", Color.LIME));
      inventory.setItem(5, OutfitButton.getChestplate("&1Blue", Color.BLUE));
      inventory.setItem(6, OutfitButton.getChestplate("&3Teal", Color.TEAL));
      inventory.setItem(7, OutfitButton.getChestplate("&bAqua", Color.AQUA));
      inventory.setItem(8, OutfitButton.getChestplate("&5Purple", Color.PURPLE));
      inventory.setItem(9, OutfitButton.getChestplate("&dPink", Color.FUCHSIA));
      inventory.setItem(10, OutfitButton.getChestplate("&fWhite", Color.WHITE));
      inventory.setItem(11, OutfitButton.getChestplate("&7Gray", Color.GRAY));
      inventory.setItem(12, OutfitButton.getChestplate("&8Black", Color.BLACK));
      player.openInventory(inventory);
   }

   public static void getOutfitBoots(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 18, "Select Boots");
      inventory.setItem(0, OutfitButton.getBoots("&cRed", Color.RED));
      inventory.setItem(1, OutfitButton.getBoots("&6Orange", Color.ORANGE));
      inventory.setItem(2, OutfitButton.getBoots("&eYellow", Color.YELLOW));
      inventory.setItem(3, OutfitButton.getBoots("&2Green", Color.GREEN));
      inventory.setItem(4, OutfitButton.getBoots("&aLime", Color.LIME));
      inventory.setItem(5, OutfitButton.getBoots("&1Blue", Color.BLUE));
      inventory.setItem(6, OutfitButton.getBoots("&3Teal", Color.TEAL));
      inventory.setItem(7, OutfitButton.getBoots("&bAqua", Color.AQUA));
      inventory.setItem(8, OutfitButton.getBoots("&5Purple", Color.PURPLE));
      inventory.setItem(9, OutfitButton.getBoots("&dPink", Color.FUCHSIA));
      inventory.setItem(10, OutfitButton.getBoots("&fWhite", Color.WHITE));
      inventory.setItem(11, OutfitButton.getBoots("&7Gray", Color.GRAY));
      inventory.setItem(12, OutfitButton.getBoots("&8Black", Color.BLACK));
      player.openInventory(inventory);
   }

   public static void getOutfits(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 18, "Select Outfit");
      inventory.setItem(0, OutfitButton.getOutfit("&cRed", Color.RED));
      inventory.setItem(1, OutfitButton.getOutfit("&6Orange", Color.ORANGE));
      inventory.setItem(2, OutfitButton.getOutfit("&eYellow", Color.YELLOW));
      inventory.setItem(3, OutfitButton.getOutfit("&2Green", Color.GREEN));
      inventory.setItem(4, OutfitButton.getOutfit("&aLime", Color.LIME));
      inventory.setItem(5, OutfitButton.getOutfit("&1Blue", Color.BLUE));
      inventory.setItem(6, OutfitButton.getOutfit("&3Teal", Color.TEAL));
      inventory.setItem(7, OutfitButton.getOutfit("&bAqua", Color.AQUA));
      inventory.setItem(8, OutfitButton.getOutfit("&5Purple", Color.PURPLE));
      inventory.setItem(9, OutfitButton.getOutfit("&dPink", Color.FUCHSIA));
      inventory.setItem(10, OutfitButton.getOutfit("&fWhite", Color.WHITE));
      inventory.setItem(11, OutfitButton.getOutfit("&7Gray", Color.GRAY));
      inventory.setItem(12, OutfitButton.getOutfit("&8Black", Color.BLACK));
      inventory.setItem(13, OutfitButton.getOutfit("&cR&6a&ei&2n&9b&5o&dw", Color.OLIVE));
      player.openInventory(inventory);
   }

   public static void getOutfitLeggings(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 18, "Select Leggings");
      inventory.setItem(0, OutfitButton.getLeggings("&cRed", Color.RED));
      inventory.setItem(1, OutfitButton.getLeggings("&6Orange", Color.ORANGE));
      inventory.setItem(2, OutfitButton.getLeggings("&eYellow", Color.YELLOW));
      inventory.setItem(3, OutfitButton.getLeggings("&2Green", Color.GREEN));
      inventory.setItem(4, OutfitButton.getLeggings("&aLime", Color.LIME));
      inventory.setItem(5, OutfitButton.getLeggings("&1Blue", Color.BLUE));
      inventory.setItem(6, OutfitButton.getLeggings("&3Teal", Color.TEAL));
      inventory.setItem(7, OutfitButton.getLeggings("&bAqua", Color.AQUA));
      inventory.setItem(8, OutfitButton.getLeggings("&5Purple", Color.PURPLE));
      inventory.setItem(9, OutfitButton.getLeggings("&dPink", Color.FUCHSIA));
      inventory.setItem(10, OutfitButton.getLeggings("&fWhite", Color.WHITE));
      inventory.setItem(11, OutfitButton.getLeggings("&7Gray", Color.GRAY));
      inventory.setItem(12, OutfitButton.getLeggings("&8Black", Color.BLACK));
      player.openInventory(inventory);
   }

   public static void getOutfitHelmet(Player player) {
      Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 18, "Select Helmet");
      inventory.setItem(0, OutfitButton.getHelmet("&cRed", Color.RED));
      inventory.setItem(1, OutfitButton.getHelmet("&6Orange", Color.ORANGE));
      inventory.setItem(2, OutfitButton.getHelmet("&eYellow", Color.YELLOW));
      inventory.setItem(3, OutfitButton.getHelmet("&2Green", Color.GREEN));
      inventory.setItem(4, OutfitButton.getHelmet("&aLime", Color.LIME));
      inventory.setItem(5, OutfitButton.getHelmet("&1Blue", Color.BLUE));
      inventory.setItem(6, OutfitButton.getHelmet("&3Teal", Color.TEAL));
      inventory.setItem(7, OutfitButton.getHelmet("&bAqua", Color.AQUA));
      inventory.setItem(8, OutfitButton.getHelmet("&5Purple", Color.PURPLE));
      inventory.setItem(9, OutfitButton.getHelmet("&dPink", Color.FUCHSIA));
      inventory.setItem(10, OutfitButton.getHelmet("&fWhite", Color.WHITE));
      inventory.setItem(11, OutfitButton.getHelmet("&7Gray", Color.GRAY));
      inventory.setItem(12, OutfitButton.getHelmet("&8Black", Color.BLACK));
      player.openInventory(inventory);
   }
}
