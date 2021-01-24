package dev.panda.hub.listeners;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.ItemFile;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.manager.JoinItemManager;
import dev.panda.hub.manager.menu.MenuManager;
import dev.panda.hub.manager.menu.cosmetic.CosmeticMenu;
import dev.panda.hub.utilities.CC;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class JoinItemListener implements Listener {
   @EventHandler
   public void onServerSelector(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = player.getItemInHand();
      if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && stack != null && stack.isSimilar(JoinItemManager.getServerSelector())) {
         event.setCancelled(true);
         MenuManager.getServerSelector(player);
         player.updateInventory();
         player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
      }

   }

   @EventHandler
   public void onInformation(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = player.getItemInHand();
      if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && stack != null && stack.isSimilar(JoinItemManager.getInformation())) {
         event.setCancelled(true);
         ItemFile.getConfig().getStringList("INFORMATION.ON-INTERACT").forEach((s) -> {
            player.sendMessage(CC.set(s.replace("{name}", player.getName()).replace("{discord}", ConfigFile.getConfig().getString("DISCORD")).replace("{store}", ConfigFile.getConfig().getString("STORE")).replace("{teamspeak}", ConfigFile.getConfig().getString("TEAMSPEAK")).replace("{twitter}", ConfigFile.getConfig().getString("TWITTER")).replace("{website}", ConfigFile.getConfig().getString("WEBSITE")).replace("{rank}", Hub.getInstance().getRankManager().getRankName(player)).replace("{rank-prefix}", Hub.getInstance().getRankManager().getRankPrefix(player)).replace("{rank-suffix}", Hub.getInstance().getRankManager().getRankSuffix(player))));
         });
      }

   }

   @EventHandler
   public void onEnderButt(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = player.getItemInHand();
      if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && stack != null && stack.isSimilar(JoinItemManager.getEnderButt())) {
         event.setCancelled(true);
         player.setVelocity(player.getLocation().getDirection().normalize().setY(2.0F));
         player.setVelocity(player.getLocation().getDirection().normalize().multiply(2.0F));
         player.updateInventory();
         player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
      }

   }

   public JoinItemListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }

   @EventHandler
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      JoinItemManager.clearJoinItems(player);
      JoinItemManager.setJoinItems(player);
   }

   @EventHandler
   public void onHideShow(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = player.getItemInHand();
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
         Iterator iterator;
         Player player1;
         if (stack != null && stack.isSimilar(JoinItemManager.getHidePlayer())) {
            event.setCancelled(true);
            iterator = Bukkit.getServer().getOnlinePlayers().iterator();

            while(iterator.hasNext()) {
               player1 = (Player)iterator.next();
               player.hidePlayer(player1);
            }

            player.setItemInHand(JoinItemManager.getShowPlayer());
            player.updateInventory();
            player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
            player.sendMessage(CC.set(MessageFile.getConfig().getString("HIDE-PLAYER")));
         }

         if (stack != null && stack.isSimilar(JoinItemManager.getShowPlayer())) {
            event.setCancelled(true);
            iterator = Bukkit.getServer().getOnlinePlayers().iterator();

            while(iterator.hasNext()) {
               player1 = (Player)iterator.next();
               player.showPlayer(player1);
            }

            player.setItemInHand(JoinItemManager.getHidePlayer());
            player.updateInventory();
            player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
            player.sendMessage(CC.set(MessageFile.getConfig().getString("SHOW-PLAYER")));
         }
      }

   }

   @EventHandler
   public void onLobbySelector(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = player.getItemInHand();
      if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && stack != null && stack.isSimilar(JoinItemManager.getLobbySelector())) {
         event.setCancelled(true);
         MenuManager.getLobbySelector(player);
         player.updateInventory();
         player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
      }

   }

   @EventHandler
   public void onCosmetics(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = player.getItemInHand();
      if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && stack != null && stack.isSimilar(JoinItemManager.getCosmetics())) {
         event.setCancelled(true);
         CosmeticMenu.getCosmetic(player);
         player.updateInventory();
         player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
      }

   }

   @EventHandler
   public void onPvPMode(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = player.getItemInHand();
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
         if (stack != null && stack.isSimilar(JoinItemManager.getEnterPvPMode())) {
            event.setCancelled(true);
            Hub.getInstance().getPvpManager().enterPvPMode(player);
         }

         if (stack != null && stack.isSimilar(JoinItemManager.getLeavePvPMode())) {
            event.setCancelled(true);
            Hub.getInstance().getPvpManager().leavePvPMode(player);
         }
      }

   }
}
