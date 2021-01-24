package dev.panda.hub.listeners.cosmetic;

import dev.panda.hub.Hub;
import dev.panda.hub.files.InventoryFile;
import dev.panda.hub.files.ItemFile;
import dev.panda.hub.manager.menu.cosmetic.buttons.CosmeticButton;
import dev.panda.hub.manager.menu.gadget.GadgetMenu;
import dev.panda.hub.manager.menu.outfit.OutfitMenu;
import dev.panda.hub.manager.menu.trail.TrailMenu;
import dev.panda.hub.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CosmeticListener implements Listener {
   public CosmeticListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }

   @EventHandler
   public void onCosmeticClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         if (event.getInventory().getTitle().equals(CC.set(InventoryFile.getConfig().getString("COSMETICS.TITLE")))) {
            event.setCancelled(true);
            ItemStack stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            if (CosmeticButton.getOutfit().isSimilar(stack)) {
               OutfitMenu.getOutfitEditor(player);
            }

            if (CosmeticButton.getTrail().isSimilar(stack)) {
               TrailMenu.getTrail(player);
            }

            if (CosmeticButton.getGadget().isSimilar(stack)) {
               if (!ItemFile.getConfig().getBoolean("GADGET.ENABLE")) {
                  player.sendMessage(CC.set("&cGadgets is currently disabled."));
                  return;
               }

               GadgetMenu.getGadget(player);
            }

            player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
         }

      }
   }
}
