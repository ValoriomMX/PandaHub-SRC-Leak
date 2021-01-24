package dev.panda.hub.manager.menu.setting.buttons;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ColorChatButton {
   public static String getColorChat(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      if (data.getColorChat() == null) {
         return ConfigFile.getConfig().getString("DEFAULT-COLOR-CHAT");
      } else {
         String color = data.getColorChat();
         int f2 = -1;
         switch(color.hashCode()) {
         case -1924984242:
            if (color.equals("Orange")) {
               f2 = 2;
            }
            break;
         case -1893076004:
            if (color.equals("Purple")) {
               f2 = 10;
            }
            break;
         case -1650372460:
            if (color.equals("Yellow")) {
               f2 = 3;
            }
            break;
         case -1431712295:
            if (color.equals("Dark Green")) {
               f2 = 4;
            }
            break;
         case 82033:
            if (color.equals("Red")) {
               f2 = 1;
            }
            break;
         case 2048732:
            if (color.equals("Aqua")) {
               f2 = 9;
            }
            break;
         case 2073722:
            if (color.equals("Blue")) {
               f2 = 6;
            }
            break;
         case 2227843:
            if (color.equals("Gray")) {
               f2 = 13;
            }
            break;
         case 2487702:
            if (color.equals("Pink")) {
               f2 = 11;
            }
            break;
         case 64266207:
            if (color.equals("Black")) {
               f2 = 15;
            }
            break;
         case 69066467:
            if (color.equals("Green")) {
               f2 = 5;
            }
            break;
         case 83549193:
            if (color.equals("White")) {
               f2 = 12;
            }
            break;
         case 92183846:
            if (color.equals("Dark Aqua")) {
               f2 = 8;
            }
            break;
         case 92208836:
            if (color.equals("Dark Blue")) {
               f2 = 7;
            }
            break;
         case 92362957:
            if (color.equals("Dark Gray")) {
               f2 = 14;
            }
            break;
         case 1804104935:
            if (color.equals("Dark Red")) {
               f2 = 0;
            }
         }

         switch(f2) {
         case 0:
            return "&4";
         case 1:
            return "&c";
         case 2:
            return "&6";
         case 3:
            return "&e";
         case 4:
            return "&2";
         case 5:
            return "&a";
         case 6:
            return "&9";
         case 7:
            return "&1";
         case 8:
            return "&3";
         case 9:
            return "&b";
         case 10:
            return "&5";
         case 11:
            return "&d";
         case 12:
            return "&f";
         case 13:
            return "&7";
         case 14:
            return "&8";
         case 15:
            return "&0";
         default:
            return ConfigFile.getConfig().getString("DEFAULT-COLOR-CHAT");
         }
      }
   }

   public static ItemStack getRemove(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      return (new ItemCreator(Material.RECORD_4)).setName("&c&lRemove Color").setLore("", String.valueOf((new StringBuilder()).append("&cColor&7: &r").append(data.getColorChat() == null ? "None" : data.getColorChat())), "", "&7Click to remove your currently color.").get();
   }
}
