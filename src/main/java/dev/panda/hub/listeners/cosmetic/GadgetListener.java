package dev.panda.hub.listeners.cosmetic;

import dev.panda.hub.Hub;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.manager.menu.cosmetic.CosmeticMenu;
import dev.panda.hub.manager.menu.gadget.buttons.GadgetButton;
import dev.panda.hub.manager.types.GadgetType;
import dev.panda.hub.utilities.CC;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GadgetListener implements Listener {
   
   private final List<Location> blocksMap = new ArrayList();

   public List<Location> getBlocksMap() {
      return this.blocksMap;
   }

   @EventHandler
   private void onGadgetClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         if (event.getInventory().getTitle().equals("Gadget")) {
            event.setCancelled(true);
            ItemStack stacl = event.getCurrentItem();
            if (stacl == null || stacl.getType().equals(Material.AIR) || stacl.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            if (GadgetButton.getRemove(player).isSimilar(stacl)) {
               PlayerData data = new PlayerData(player.getUniqueId());
               if (data.getGadget() == null) {
                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have any gadget equipped."));
                  return;
               }

               data.setGadget((String)null);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               player.closeInventory();
               GadgetButton.getRemoveGadget(player);
               player.sendMessage(CC.set("&aYour gadget has been remove."));
               return;
            }

            if (ItemStackManager.getBack().isSimilar(stacl)) {
               CosmeticMenu.getCosmetic(player);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               return;
            }

            GadgetType[] types = GadgetType.values();
            int length = types.length;

            for(int i = 0; i < length; ++i) {
               GadgetType type = types[i];
               if (type.getItem(player).isSimilar(stacl)) {
                  PlayerData data = new PlayerData(player.getUniqueId());
                  if (player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.gadget.").append(type.getName().toLowerCase().replace(" ", "_")))) && player.hasPermission("pandahub.cosmetics.gadget.*")) {
                     if (type.getName().equals(data.getGadget())) {
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                        player.sendMessage(CC.set("&cThis gadget is already equipped."));
                        return;
                     }

                     data.setGadget(type.getName());
                     player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                     player.closeInventory();
                     GadgetButton.getRemoveGadget(player);
                     GadgetButton.getEquipGadget(player, type);
                     player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&aSuccessfully equipped ").append(type.getDisplayName()).append("&a."))));
                     return;
                  }

                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have permissions to this gadget."));
                  return;
               }
            }
         }

      }
   }

   @EventHandler
   private void onBowTeleport(ProjectileHitEvent event) {
      if (event.getEntity() instanceof Arrow && event.getEntity().getShooter() instanceof Player) {
         Player player = (Player)event.getEntity().getShooter();
         player.teleport(event.getEntity().getLocation());
         player.playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
         player.getInventory().setItem(27, new ItemStack(Material.ARROW));
      }

   }

   @EventHandler
   private void onSnowman(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      if (player.getItemInHand().isSimilar(GadgetButton.getGadget(GadgetType.SNOWMAN)) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
         if (player.getVehicle() != null) {
            player.getVehicle().remove();
            player.eject();
         }

         Snowball snoeball = (Snowball)player.launchProjectile(Snowball.class);
         event.setCancelled(true);
         snoeball.setVelocity(player.getLocation().getDirection());
         snoeball.setPassenger(player);
         player.spigot().setCollidesWithEntities(false);
         player.updateInventory();
      }

   }

   public GadgetListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }
}
