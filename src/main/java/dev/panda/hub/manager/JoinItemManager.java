package dev.panda.hub.manager;

import dev.panda.hub.files.ItemFile;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JoinItemManager {
   public static void setJoinItems(Player player) {
      if (ItemFile.getConfig().getBoolean("SERVER-SELECTOR.ENABLE")) {
         player.getInventory().setItem(ItemFile.getConfig().getInt("SERVER-SELECTOR.SLOT") - 1, getServerSelector());
      }

      if (ItemFile.getConfig().getBoolean("LOBBY-SELECTOR.ENABLE")) {
         player.getInventory().setItem(ItemFile.getConfig().getInt("LOBBY-SELECTOR.SLOT") - 1, getLobbySelector());
      }

      if (ItemFile.getConfig().getBoolean("HIDE-PLAYER.ENABLE")) {
         player.getInventory().setItem(ItemFile.getConfig().getInt("HIDE-PLAYER.SLOT") - 1, getHidePlayer());
      }

      if (ItemFile.getConfig().getBoolean("COSMETICS.ENABLE")) {
         player.getInventory().setItem(ItemFile.getConfig().getInt("COSMETICS.SLOT") - 1, getCosmetics());
      }

      if (ItemFile.getConfig().getBoolean("ENDER-BUTT.ENABLE")) {
         player.getInventory().setItem(ItemFile.getConfig().getInt("ENDER-BUTT.SLOT") - 1, getEnderButt());
      }

      if (ItemFile.getConfig().getBoolean("INFORMATION.ENABLE")) {
         player.getInventory().setItem(ItemFile.getConfig().getInt("INFORMATION.SLOT") - 1, getInformation());
      }

      if (ItemFile.getConfig().getBoolean("PVP-MODE.ENABLE")) {
         player.getInventory().setItem(ItemFile.getConfig().getInt("PVP-MODE.ENTER.SLOT") - 1, getEnterPvPMode());
      }

   }

   public static ItemStack getLobbySelector() {
      return (new ItemCreator(ItemFile.getConfig().getString("LOBBY-SELECTOR.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("LOBBY-SELECTOR.ITEM.DATA")).setName(ItemFile.getConfig().getString("LOBBY-SELECTOR.NAME")).setLore(ItemFile.getConfig().getStringList("LOBBY-SELECTOR.LORE")).setGlow(ItemFile.getConfig().getBoolean("LOBBY-SELECTOR.ITEM.GLOW")).get();
   }

   public static ItemStack getServerSelector() {
      return (new ItemCreator(ItemFile.getConfig().getString("SERVER-SELECTOR.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("SERVER-SELECTOR.ITEM.DATA")).setName(ItemFile.getConfig().getString("SERVER-SELECTOR.NAME")).setLore(ItemFile.getConfig().getStringList("SERVER-SELECTOR.LORE")).setGlow(ItemFile.getConfig().getBoolean("SERVER-SELECTOR.ITEM.GLOW")).get();
   }

   public static void clearJoinItems(Player player) {
      player.getInventory().clear();
   }

   public static ItemStack getHidePlayer() {
      return (new ItemCreator(ItemFile.getConfig().getString("HIDE-PLAYER.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("HIDE-PLAYER.ITEM.DATA")).setName(ItemFile.getConfig().getString("HIDE-PLAYER.NAME")).setLore(ItemFile.getConfig().getStringList("HIDE-PLAYER.LORE")).setGlow(ItemFile.getConfig().getBoolean("HIDE-PLAYER.ITEM.GLOW")).get();
   }

   public static void refreshJoinItems(Player player) {
      clearJoinItems(player);
      setJoinItems(player);
   }

   public static ItemStack getShowPlayer() {
      return (new ItemCreator(ItemFile.getConfig().getString("SHOW-PLAYER.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("SHOW-PLAYER.ITEM.DATA")).setName(ItemFile.getConfig().getString("SHOW-PLAYER.NAME")).setLore(ItemFile.getConfig().getStringList("SHOW-PLAYER.LORE")).setGlow(ItemFile.getConfig().getBoolean("SHOW-PLAYER.ITEM.GLOW")).get();
   }

   public static ItemStack getLeavePvPMode() {
      return (new ItemCreator(ItemFile.getConfig().getString("PVP-MODE.LEAVE.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("PVP-MODE.LEAVE.ITEM.DATA")).setName(ItemFile.getConfig().getString("PVP-MODE.LEAVE.NAME")).setLore(ItemFile.getConfig().getStringList("PVP-MODE.LEAVE.LORE")).setGlow(ItemFile.getConfig().getBoolean("PVP-MODE.LEAVE.ITEM.GLOW")).get();
   }

   public static ItemStack getEnterPvPMode() {
      return (new ItemCreator(ItemFile.getConfig().getString("PVP-MODE.ENTER.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("PVP-MODE.ENTER.ITEM.DATA")).setName(ItemFile.getConfig().getString("PVP-MODE.ENTER.NAME")).setLore(ItemFile.getConfig().getStringList("PVP-MODE.ENTER.LORE")).setGlow(ItemFile.getConfig().getBoolean("PVP-MODE.ENTER.ITEM.GLOW")).get();
   }

   public static ItemStack getInformation() {
      return (new ItemCreator(ItemFile.getConfig().getString("INFORMATION.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("INFORMATION.ITEM.DATA")).setName(ItemFile.getConfig().getString("INFORMATION.NAME")).setLore(ItemFile.getConfig().getStringList("INFORMATION.LORE")).setGlow(ItemFile.getConfig().getBoolean("INFORMATION.ITEM.GLOW")).get();
   }

   public static ItemStack getEnderButt() {
      return (new ItemCreator(ItemFile.getConfig().getString("ENDER-BUTT.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("ENDER-BUTT.ITEM.DATA")).setName(ItemFile.getConfig().getString("ENDER-BUTT.NAME")).setLore(ItemFile.getConfig().getStringList("ENDER-BUTT.LORE")).setGlow(ItemFile.getConfig().getBoolean("ENDER-BUTT.ITEM.GLOW")).get();
   }

   public static ItemStack getCosmetics() {
      return (new ItemCreator(ItemFile.getConfig().getString("COSMETICS.ITEM.MATERIAL"))).setDurability(ItemFile.getConfig().getInt("COSMETICS.ITEM.DATA")).setName(ItemFile.getConfig().getString("COSMETICS.NAME")).setLore(ItemFile.getConfig().getStringList("COSMETICS.LORE")).setGlow(ItemFile.getConfig().getBoolean("COSMETICS.ITEM.GLOW")).get();
   }
}
