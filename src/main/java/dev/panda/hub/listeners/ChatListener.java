package dev.panda.hub.listeners;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.manager.menu.setting.buttons.ColorChatButton;
import dev.panda.hub.manager.menu.tag.buttons.TagButton;
import dev.panda.hub.utilities.CC;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
   @EventHandler
   private void onChat(AsyncPlayerChatEvent event) {
      Player player = event.getPlayer();
      if (ConfigFile.getConfig().getBoolean("DISABLE-CHAT") && !player.hasPermission("pandahub.chat.bypass")) {
         event.setCancelled(true);
         if (ConfigFile.getConfig().getBoolean("ENABLE-CHAT-MESSAGE")) {
            player.sendMessage(CC.set(MessageFile.getConfig().getString("DISABLE-CHAT")));
         }

      } else {
         if (ConfigFile.getConfig().getBoolean("ENABLE-CUSTOM-CHAT")) {
            String s = CC.set(ConfigFile.getConfig().getString("CUSTOM-CHAT").replace("{name}", player.getName()).replace("{tag}", TagButton.getTag(player)).replace("{color_chat}", ColorChatButton.getColorChat(player)).replace("{rank}", Hub.getInstance().getRankManager().getRankName(player)).replace("{rank-prefix}", Hub.getInstance().getRankManager().getRankPrefix(player)).replace("{rank-suffix}", Hub.getInstance().getRankManager().getRankSuffix(player))).replace("{message}", player.hasPermission("pandahub.chat.color") ? CC.set(event.getMessage()) : event.getMessage());
            if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
               event.setFormat(PlaceholderAPI.setPlaceholders(player, s));
            } else {
               event.setFormat(s);
            }
         }

      }
   }

   public ChatListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }
}
