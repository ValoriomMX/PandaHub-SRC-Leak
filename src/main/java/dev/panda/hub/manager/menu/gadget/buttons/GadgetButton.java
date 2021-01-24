package dev.panda.hub.manager.menu.gadget.buttons;

import dev.panda.hub.files.ItemFile;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.manager.types.GadgetType;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GadgetButton {
   public static ItemStack getRemove(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      return (new ItemCreator(Material.RECORD_4)).setName("&c&lRemove Gadget").setLore("", String.valueOf((new StringBuilder()).append("&cGadget&7: &r").append(data.getGadget() == null ? "None" : data.getGadget())), "", "&7Click to remove your currently gadget.").get();
   }

   public static void checkGadget(Player player, PlayerData data) {
      String dadget = data.getGadget();
      int i = -1;
      switch(dadget.hashCode()) {
      case -391235369:
         if (dadget.equals("Snowman")) {
            i = 1;
         }
         break;
      case 1416557825:
         if (dadget.equals("Bow Teleport")) {
            i = 0;
         }
      }

      switch(i) {
      case 0:
         getEquipGadget(player, GadgetType.BOW_TELEPORT);
         break;
      case 1:
         getEquipGadget(player, GadgetType.SNOWMAN);
      }

   }

   public static void getRemoveGadget(Player player) {
      player.getInventory().setItem(ItemFile.getConfig().getInt("GADGET.SLOT") - 1, (ItemStack)null);
      player.getInventory().setItem(27, (ItemStack)null);
      player.updateInventory();
   }

   public static ItemStack getGadget(GadgetType type) {
      return (new ItemCreator(type.getMaterial())).setName(type.getDisplayName()).get();
   }

   public static void getEquipGadget(Player player, GadgetType type) {
      player.getInventory().setItem(ItemFile.getConfig().getInt("GADGET.SLOT") - 1, getGadget(type));
      if (type.getName().equals("Bow Teleport")) {
         player.getInventory().setItem(27, new ItemStack(Material.ARROW));
      }

      player.updateInventory();
   }
}
