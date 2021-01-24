package dev.panda.hub.listeners.cosmetic;

import com.google.common.collect.Sets;
import dev.panda.hub.Hub;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.RainbowManager;
import dev.panda.hub.manager.menu.cosmetic.CosmeticMenu;
import dev.panda.hub.manager.menu.outfit.OutfitMenu;
import dev.panda.hub.manager.menu.outfit.buttons.OutfitButton;
import dev.panda.hub.utilities.CC;
import java.util.Set;
import java.util.UUID;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class OutfitListener implements Listener {

   private static OutfitListener instance;
   private final Set<UUID> rainbow = Sets.newHashSet();

   @EventHandler
   public void onOutfitClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         ItemStack stack;
         if (event.getInventory().getTitle().equals("Outfit Editor")) {
            event.setCancelled(true);
            stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            if (OutfitButton.getOutfits().isSimilar(stack)) {
               OutfitMenu.getOutfits(player);
            }

            if (OutfitButton.getEquipHelmet(player).isSimilar(stack)) {
               if (event.isLeftClick()) {
                  OutfitMenu.getOutfitHelmet(player);
               }

               if (event.isRightClick()) {
                  if (player.getInventory().getHelmet() == null) {
                     return;
                  }

                  player.getInventory().setHelmet((ItemStack)null);
                  player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                  OutfitMenu.getOutfitEditor(player);
               }
            }

            if (OutfitButton.getEquipChestplate(player).isSimilar(stack)) {
               if (event.isLeftClick()) {
                  OutfitMenu.getOutfitChestplate(player);
               }

               if (event.isRightClick()) {
                  if (player.getInventory().getChestplate() == null) {
                     return;
                  }

                  player.getInventory().setChestplate((ItemStack)null);
                  player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                  OutfitMenu.getOutfitEditor(player);
               }
            }

            if (OutfitButton.getEquipLeggings(player).isSimilar(stack)) {
               if (event.isLeftClick()) {
                  OutfitMenu.getOutfitLeggings(player);
               }

               if (event.isRightClick()) {
                  if (player.getInventory().getLeggings() == null) {
                     return;
                  }

                  player.getInventory().setLeggings((ItemStack)null);
                  player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                  OutfitMenu.getOutfitEditor(player);
               }
            }

            if (OutfitButton.getEquipBoots(player).isSimilar(stack)) {
               if (event.isLeftClick()) {
                  OutfitMenu.getOutfitBoots(player);
               }

               if (event.isRightClick()) {
                  if (player.getInventory().getBoots() == null) {
                     return;
                  }

                  player.getInventory().setBoots((ItemStack)null);
                  player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
                  OutfitMenu.getOutfitEditor(player);
               }
            }

            if (OutfitButton.getRemove().isSimilar(stack)) {
               if (this.rainbow.contains(player.getUniqueId())) {
                  this.rainbow.remove(player.getUniqueId());
               }

               player.getInventory().setArmorContents((ItemStack[])null);
               player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);
               OutfitMenu.getOutfitEditor(player);
            }

            if (ItemStackManager.getBack().isSimilar(stack)) {
               CosmeticMenu.getCosmetic(player);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
            }
         }

         String s1;
         String s2;
         if (event.getInventory().getTitle().equals("Select Outfit")) {
            event.setCancelled(true);
            stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            s1 = ChatColor.stripColor(stack.getItemMeta().getDisplayName().replace(" ", ""));
            s2 = s1.replace("Outfit", "").toLowerCase();
            if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2))) || !player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2)))) {
               player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
               player.sendMessage(CC.set("&cYou don't have permissions to this outfit."));
               return;
            }

            if (stack.getItemMeta().getDisplayName().contains(CC.set("&cR&6a&ei&2n&9b&5o&dw"))) {
               if (!this.rainbow.contains(player.getUniqueId())) {
                  this.rainbow.add(player.getUniqueId());
                  player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                  RainbowManager manager = new RainbowManager(this.rainbow, player, 0, 0, 255, 0, 0, 0, 0, 0, 0);
                  manager.runTaskTimerAsynchronously(Hub.getInstance(), 0L, 1L);
                  OutfitMenu.getOutfitEditor(player);
               }

               return;
            }

            if (this.rainbow.contains(player.getUniqueId())) {
               this.rainbow.remove(player.getUniqueId());
            }

            LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
            String name = meta.getDisplayName();
            Color color = meta.getColor();
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            player.getInventory().setArmorContents((ItemStack[])null);
            OutfitButton.getEquipOutfit(player, name, color);
            OutfitMenu.getOutfitEditor(player);
         }

         if (event.getInventory().getTitle().equals("Select Helmet")) {
            event.setCancelled(true);
            stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            s1 = ChatColor.stripColor(stack.getItemMeta().getDisplayName().replace(" ", ""));
            s2 = s1.replace("Helmet", "").toLowerCase();
            if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2))) || !player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2)))) {
               player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
               player.sendMessage(CC.set("&cYou don't have permissions to this outfit."));
               return;
            }

            player.getInventory().setHelmet(event.getCurrentItem());
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            OutfitMenu.getOutfitEditor(player);
         }

         if (event.getInventory().getTitle().equals("Select Chestplate")) {
            event.setCancelled(true);
            stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            s1 = ChatColor.stripColor(stack.getItemMeta().getDisplayName().replace(" ", ""));
            s2 = s1.replace("Chestplate", "").toLowerCase();
            if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2))) || !player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2)))) {
               player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
               player.sendMessage(CC.set("&cYou don't have permissions to this outfit."));
               return;
            }

            player.getInventory().setChestplate(event.getCurrentItem());
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            OutfitMenu.getOutfitEditor(player);
         }

         if (event.getInventory().getTitle().equals("Select Leggings")) {
            event.setCancelled(true);
            stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            s1 = ChatColor.stripColor(stack.getItemMeta().getDisplayName().replace(" ", ""));
            s2 = s1.replace("Leggings", "").toLowerCase();
            if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2))) || !player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2)))) {
               player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
               player.sendMessage(CC.set("&cYou don't have permissions to this outfit."));
               return;
            }

            player.getInventory().setLeggings(event.getCurrentItem());
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            OutfitMenu.getOutfitEditor(player);
         }

         if (event.getInventory().getTitle().equals("Select Boots")) {
            event.setCancelled(true);
            stack = event.getCurrentItem();
            if (stack != null && !stack.getType().equals(Material.AIR) && !stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               s1 = ChatColor.stripColor(stack.getItemMeta().getDisplayName().replace(" ", ""));
               s2 = s1.replace("Boots", "").toLowerCase();
               if (player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2))) && player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.outfit.").append(s2)))) {
                  player.getInventory().setBoots(event.getCurrentItem());
                  player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                  OutfitMenu.getOutfitEditor(player);
               } else {
                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have permissions to this outfit."));
               }
            }
         }
      }
   }

   public static OutfitListener getInstance() {
      return instance;
   }

   public Set<UUID> getRainbow() {
      return this.rainbow;
   }

   public OutfitListener() {
      instance = this;
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }
}
