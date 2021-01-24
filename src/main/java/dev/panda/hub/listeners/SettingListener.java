package dev.panda.hub.listeners;

import dev.panda.hub.Hub;
import dev.panda.hub.files.InventoryFile;
import dev.panda.hub.manager.ItemStackManager;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.manager.menu.setting.ColorChatMenu;
import dev.panda.hub.manager.menu.setting.ColorMessageMenu;
import dev.panda.hub.manager.menu.setting.SettingMenu;
import dev.panda.hub.manager.menu.setting.buttons.ColorChatButton;
import dev.panda.hub.manager.menu.setting.buttons.ColorMessageButton;
import dev.panda.hub.manager.menu.setting.buttons.SettingButton;
import dev.panda.hub.manager.menu.tag.TagMenu;
import dev.panda.hub.manager.menu.tag.buttons.TagButton;
import dev.panda.hub.manager.types.ColorChatType;
import dev.panda.hub.manager.types.ColorMessageType;
import dev.panda.hub.manager.types.TagType;
import dev.panda.hub.utilities.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SettingListener implements Listener {
   @EventHandler
   public void onSettingClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         if (event.getInventory().getTitle().equals(CC.set(InventoryFile.getConfig().getString("SETTINGS.TITLE")))) {
            event.setCancelled(true);
            ItemStack stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            if (SettingButton.getScoreboard(player).isSimilar(stack)) {
               player.performCommand("toggleboard");
               event.setCurrentItem(SettingButton.getScoreboard(player));
            }

            if (SettingButton.getPrivateMessage(player).isSimilar(stack)) {
               player.performCommand("togglepm");
               event.setCurrentItem(SettingButton.getPrivateMessage(player));
            }

            if (SettingButton.getSoundMessage(player).isSimilar(stack)) {
               player.performCommand("togglesound");
               event.setCurrentItem(SettingButton.getSoundMessage(player));
            }

            if (SettingButton.getColorMessage().isSimilar(stack)) {
               ColorMessageMenu.getColorMessage(player);
            }

            if (SettingButton.getColorChat().isSimilar(stack)) {
               ColorChatMenu.getColorChat(player);
            }

            if (SettingButton.getTag().isSimilar(stack)) {
               TagMenu.getTag(player);
            }

            player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
         }

      }
   }

   @EventHandler
   public void onColorChatClick(InventoryClickEvent evvent) {
      Player player = (Player)evvent.getWhoClicked();
      if (evvent.getClickedInventory() != null && evvent.getInventory() == evvent.getClickedInventory()) {
         if (evvent.getInventory().getTitle().equals(CC.set("Color Chat"))) {
            evvent.setCancelled(true);
            ItemStack stack = evvent.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            if (ColorChatButton.getRemove(player).isSimilar(stack)) {
               PlayerData data = new PlayerData(player.getUniqueId());
               if (data.getColorChat() == null) {
                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have any color chat equipped."));
                  return;
               }

               data.setColorChat((String)null);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               player.closeInventory();
               player.sendMessage(CC.set("&aYour color chat has been remove."));
               return;
            }

            if (ItemStackManager.getBack().isSimilar(stack)) {
               SettingMenu.getSetting(player);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               return;
            }

            ColorChatType[] types = ColorChatType.values();
            int length = types.length;

            for(int i = 0; i < length; ++i) {
               ColorChatType type = types[i];
               if (type.getItem(player).isSimilar(stack)) {
                  PlayerData data = new PlayerData(player.getUniqueId());
                  if (player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.settings.color_chat.").append(type.getName().toLowerCase().replace(" ", "_")))) && player.hasPermission("pandahub.settings.color_message.*")) {
                     if (type.getName().equals(data.getColorChat())) {
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                        player.sendMessage(CC.set("&cThis color is already equipped."));
                        return;
                     }

                     data.setColorChat(type.getName());
                     player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                     player.closeInventory();
                     player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&aSuccessfully equipped ").append(type.getDisplayName()).append("&a."))));
                     return;
                  }

                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have permissions to this color."));
                  return;
               }
            }
         }

      }
   }

   @EventHandler
   public void onTagClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         if (event.getInventory().getTitle().equals(CC.set("Tags"))) {
            event.setCancelled(true);
            ItemStack stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            if (TagButton.getRemove(player).isSimilar(stack)) {
               PlayerData data = new PlayerData(player.getUniqueId());
               if (data.getTag() == null) {
                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have any tag equipped."));
                  return;
               }

               data.setTag((String)null);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               player.closeInventory();
               player.sendMessage(CC.set("&aYour tag has been remove."));
               return;
            }

            if (ItemStackManager.getBack().isSimilar(stack)) {
               SettingMenu.getSetting(player);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               return;
            }

            TagType[] types = TagType.values();
            int length = types.length;

            for(int i = 0; i < length; ++i) {
               TagType type = types[i];
               if (type.getItem(player).isSimilar(stack)) {
                  PlayerData data = new PlayerData(player.getUniqueId());
                  if (player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.settings.tag.").append(type.getName().toLowerCase()))) && player.hasPermission("pandahub.settings.tag.*")) {
                     if (type.getName().equals(data.getTag())) {
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                        player.sendMessage(CC.set("&cThis tag is already equipped."));
                        return;
                     }

                     data.setTag(type.getName());
                     player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                     player.closeInventory();
                     player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&aSuccessfully equipped &f").append(type.getName()).append(" &aTag."))));
                     return;
                  }

                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have permissions to this tag."));
                  return;
               }
            }
         }

      }
   }

   public SettingListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }

   @EventHandler
   public void onColorMessageClick(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (event.getClickedInventory() != null && event.getInventory() == event.getClickedInventory()) {
         if (event.getInventory().getTitle().equals(CC.set("Color Message"))) {
            event.setCancelled(true);
            ItemStack stack = event.getCurrentItem();
            if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.STAINED_GLASS_PANE)) {
               return;
            }

            if (ColorMessageButton.getRemove(player).isSimilar(stack)) {
               PlayerData data = new PlayerData(player.getUniqueId());
               if (data.getColorMessage() == null) {
                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have any color message equipped."));
                  return;
               }

               data.setColorMessage((String)null);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               player.closeInventory();
               player.sendMessage(CC.set("&aYour color message has been remove."));
               return;
            }

            if (ItemStackManager.getBack().isSimilar(stack)) {
               SettingMenu.getSetting(player);
               player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
               return;
            }

            ColorMessageType[] types = ColorMessageType.values();
            int length = types.length;

            for(int i = 0; i < length; ++i) {
               ColorMessageType type = types[i];
               if (type.getItem(player).isSimilar(stack)) {
                  PlayerData data = new PlayerData(player.getUniqueId());
                  if (player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.settings.color_message.").append(type.getName().toLowerCase().replace(" ", "_")))) && player.hasPermission("pandahub.settings.color_message.*")) {
                     if (type.getName().equals(data.getColorMessage())) {
                        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                        player.sendMessage(CC.set("&cThis color is already equipped."));
                        return;
                     }

                     data.setColorMessage(type.getName());
                     player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                     player.closeInventory();
                     player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&aSuccessfully equipped ").append(type.getDisplayName()).append("&a."))));
                     return;
                  }

                  player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                  player.sendMessage(CC.set("&cYou don't have permissions to this color."));
                  return;
               }
            }
         }

      }
   }
}
