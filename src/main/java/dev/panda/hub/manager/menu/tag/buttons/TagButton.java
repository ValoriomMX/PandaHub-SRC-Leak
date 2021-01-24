package dev.panda.hub.manager.menu.tag.buttons;

import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TagButton {
   public static ItemStack getRemove(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      return (new ItemCreator(Material.RECORD_4)).setName("&c&lRemove Tag").setLore("", String.valueOf((new StringBuilder()).append("&cTag&7: &r").append(data.getTag() == null ? "None" : data.getTag())), "", "&7Click to remove your currently tag.").get();
   }

   public static String getTag(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      if (data.getTag() == null) {
         return "";
      } else {
         String tag = data.getTag();
         int i = -1;
         switch(tag.hashCode()) {
         case -391235369:
            if (tag.equals("Snowman")) {
               i = 5;
            }
            break;
         case 2587250:
            if (tag.equals("Star")) {
               i = 2;
            }
            break;
         case 69599270:
            if (tag.equals("Heart")) {
               i = 0;
            }
            break;
         case 74710533:
            if (tag.equals("Music")) {
               i = 1;
            }
            break;
         case 81001239:
            if (tag.equals("Toxic")) {
               i = 6;
            }
            break;
         case 2107205243:
            if (tag.equals("Flower")) {
               i = 4;
            }
            break;
         case 2112749248:
            if (tag.equals("Frozen")) {
               i = 3;
            }
         }

         switch(i) {
         case 0:
            return "&c❤";
         case 1:
            return "&c♪&e♫&a♬";
         case 2:
            return "&6✪";
         case 3:
            return "&b❆";
         case 4:
            return "&d✿";
         case 5:
            return "&f☃";
         case 6:
            return "&2☣";
         default:
            return "";
         }
      }
   }
}
