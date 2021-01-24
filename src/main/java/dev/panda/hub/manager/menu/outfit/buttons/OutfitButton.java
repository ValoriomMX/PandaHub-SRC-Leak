package dev.panda.hub.manager.menu.outfit.buttons;

import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class OutfitButton {
   public static ItemStack getLeggings(String input, Color color) {
      return (new ItemCreator(Material.LEATHER_LEGGINGS)).setName(String.valueOf((new StringBuilder()).append(input).append(" Leggings"))).setArmorColor(color).get();
   }

   public static ItemStack getEquipLeggings(Player player) {
      if (player.getInventory().getLeggings() == null) {
         return (new ItemCreator(Material.REDSTONE)).setName("&aClick to equip leggings").get();
      } else {
         LeatherArmorMeta meta = (LeatherArmorMeta)player.getInventory().getLeggings().getItemMeta();
         return (new ItemCreator(player.getInventory().getLeggings().getType())).setName(player.getInventory().getLeggings().getItemMeta().getDisplayName()).setLore("&a&lLEFT-CLICK &ato change leggings", "&c&lRIGHT-CLICK &cto remove leggings").setArmorColor(meta.getColor()).get();
      }
   }

   public static ItemStack getEquipChestplate(Player player) {
      if (player.getInventory().getChestplate() == null) {
         return (new ItemCreator(Material.REDSTONE)).setName("&aClick to equip chestplate").get();
      } else {
         LeatherArmorMeta meta = (LeatherArmorMeta)player.getInventory().getChestplate().getItemMeta();
         return (new ItemCreator(player.getInventory().getChestplate().getType())).setName(player.getInventory().getChestplate().getItemMeta().getDisplayName()).setLore("&a&lLEFT-CLICK &ato change chestplate", "&c&lRIGHT-CLICK &cto remove chestplate").setArmorColor(meta.getColor()).get();
      }
   }

   public static ItemStack getOutfits() {
      return (new ItemCreator(Material.CHEST)).setName("&c&lOutfits").get();
   }

   public static ItemStack getBoots(String input, Color color) {
      return (new ItemCreator(Material.LEATHER_BOOTS)).setName(String.valueOf((new StringBuilder()).append(input).append(" Boots"))).setArmorColor(color).get();
   }

   public static ItemStack getOutfit(String input, Color color) {
      return (new ItemCreator(Material.LEATHER_CHESTPLATE)).setName(String.valueOf((new StringBuilder()).append(input).append(" Outfit"))).setArmorColor(color).get();
   }

   public static ItemStack getEquipBoots(Player player) {
      if (player.getInventory().getBoots() == null) {
         return (new ItemCreator(Material.REDSTONE)).setName("&aClick to equip boots").get();
      } else {
         LeatherArmorMeta meta = (LeatherArmorMeta)player.getInventory().getBoots().getItemMeta();
         return (new ItemCreator(player.getInventory().getBoots().getType())).setName(player.getInventory().getBoots().getItemMeta().getDisplayName()).setLore("&a&lLEFT-CLICK &ato change boots", "&c&lRIGHT-CLICK &cto remove boots").setArmorColor(meta.getColor()).get();
      }
   }

   public static ItemStack getEquipHelmet(Player player) {
      if (player.getInventory().getHelmet() == null) {
         return (new ItemCreator(Material.REDSTONE)).setName("&aClick to equip helmet").get();
      } else {
         LeatherArmorMeta meta = (LeatherArmorMeta)player.getInventory().getHelmet().getItemMeta();
         return (new ItemCreator(player.getInventory().getHelmet().getType())).setName(player.getInventory().getHelmet().getItemMeta().getDisplayName()).setLore("&a&lLEFT-CLICK &ato change helmet", "&c&lRIGHT-CLICK &cto remove helmet").setArmorColor(meta.getColor()).get();
      }
   }

   public static ItemStack getRemove() {
      return (new ItemCreator(Material.RECORD_4)).setName("&c&lRemove Outfit").get();
   }

   public static ItemStack getHelmet(String input, Color color) {
      return (new ItemCreator(Material.LEATHER_HELMET)).setName(String.valueOf((new StringBuilder()).append(input).append(" Helmet"))).setArmorColor(color).get();
   }

   public static ItemStack getChestplate(String input, Color color) {
      return (new ItemCreator(Material.LEATHER_CHESTPLATE)).setName(String.valueOf((new StringBuilder()).append(input).append(" Chestplate"))).setArmorColor(color).get();
   }

   public static void getEquipOutfit(Player player, String input, Color color) {
      player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.LEATHER_BOOTS, 1), new ItemStack(Material.LEATHER_LEGGINGS, 1), new ItemStack(Material.LEATHER_CHESTPLATE, 1), new ItemStack(Material.LEATHER_HELMET, 1)});
      ItemStack[] stacks = player.getInventory().getArmorContents();
      int length = stacks.length;

      for(int i = 0; i < length; ++i) {
         ItemStack stack = stacks[i];
         LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
         meta.setDisplayName(CC.set(input));
         meta.setColor(color);
         stack.setItemMeta(meta);
      }

   }
}
