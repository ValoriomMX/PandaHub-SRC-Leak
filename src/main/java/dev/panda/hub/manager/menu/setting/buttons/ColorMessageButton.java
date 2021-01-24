package dev.panda.hub.manager.menu.setting.buttons;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ColorMessageButton {
   public static ItemStack getRemove(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      return (new ItemCreator(Material.RECORD_4)).setName("&c&lRemove Color").setLore("", String.valueOf((new StringBuilder()).append("&cColor&7: &r").append(data.getColorMessage() == null ? "None" : data.getColorMessage())), "", "&7Click to remove your currently color.").get();
   }

   public static String getColorMessage(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      if (data.getColorMessage() == null) {
         return ConfigFile.getConfig().getString("DEFAULT-COLOR-MESSAGE");
      } else {
         String s1 = data.getColorMessage();
         int i2 = -1;
         switch(s1.hashCode()) {
         case -1924984242:
            if (s1.equals("Orange")) {
               i2 = 2;
            }
            break;
         case -1893076004:
            if (s1.equals("Purple")) {
               i2 = 10;
            }
            break;
         case -1650372460:
            if (s1.equals("Yellow")) {
               i2 = 3;
            }
            break;
         case -1431712295:
            if (s1.equals("Dark Green")) {
               i2 = 4;
            }
            break;
         case 82033:
            if (s1.equals("Red")) {
               i2 = 1;
            }
            break;
         case 2048732:
            if (s1.equals("Aqua")) {
               i2 = 9;
            }
            break;
         case 2073722:
            if (s1.equals("Blue")) {
               i2 = 6;
            }
            break;
         case 2227843:
            if (s1.equals("Gray")) {
               i2 = 13;
            }
            break;
         case 2487702:
            if (s1.equals("Pink")) {
               i2 = 11;
            }
            break;
         case 64266207:
            if (s1.equals("Black")) {
               i2 = 15;
            }
            break;
         case 69066467:
            if (s1.equals("Green")) {
               i2 = 5;
            }
            break;
         case 83549193:
            if (s1.equals("White")) {
               i2 = 12;
            }
            break;
         case 92183846:
            if (s1.equals("Dark Aqua")) {
               i2 = 8;
            }
            break;
         case 92208836:
            if (s1.equals("Dark Blue")) {
               i2 = 7;
            }
            break;
         case 92362957:
            if (s1.equals("Dark Gray")) {
               i2 = 14;
            }
            break;
         case 1804104935:
            if (s1.equals("Dark Red")) {
               i2 = 0;
            }
         }

         switch(i2) {
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
            return ConfigFile.getConfig().getString("DEFAULT-COLOR-MESSAGE");
         }
      }
   }
}
