package dev.panda.hub.manager.menu.cosmetic.buttons;

import dev.panda.hub.files.InventoryFile;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.inventory.ItemStack;

public class CosmeticButton {
   public static ItemStack getOutfit() {
      return (new ItemCreator(InventoryFile.getConfig().getString("COSMETICS.OUTFIT.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("COSMETICS.OUTFIT.ITEM.DATA")).setName(InventoryFile.getConfig().getString("COSMETICS.OUTFIT.NAME")).setLore(InventoryFile.getConfig().getStringList("COSMETICS.OUTFIT.LORE")).setGlow(InventoryFile.getConfig().getBoolean("COSMETICS.OUTFIT.ITEM.GLOW")).get();
   }

   public static ItemStack getTrail() {
      return (new ItemCreator(InventoryFile.getConfig().getString("COSMETICS.TRAIL.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("COSMETICS.TRAIL.ITEM.DATA")).setName(InventoryFile.getConfig().getString("COSMETICS.TRAIL.NAME")).setLore(InventoryFile.getConfig().getStringList("COSMETICS.TRAIL.LORE")).setGlow(InventoryFile.getConfig().getBoolean("COSMETICS.TRAIL.ITEM.GLOW")).get();
   }

   public static ItemStack getGadget() {
      return (new ItemCreator(InventoryFile.getConfig().getString("COSMETICS.GADGET.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("COSMETICS.GADGET.ITEM.DATA")).setName(InventoryFile.getConfig().getString("COSMETICS.GADGET.NAME")).setLore(InventoryFile.getConfig().getStringList("COSMETICS.GADGET.LORE")).setGlow(InventoryFile.getConfig().getBoolean("COSMETICS.GADGET.ITEM.GLOW")).get();
   }
}
