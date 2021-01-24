package dev.panda.hub.manager.menu.trail.buttons;

import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrailButton {
   public static ItemStack getRemove(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      return (new ItemCreator(Material.RECORD_4)).setName("&c&lRemove Trail").setLore("", String.valueOf((new StringBuilder()).append("&cTrail&7: &r").append(data.getTrail() == null ? "None" : data.getTrail())), "", "&7Click to remove your currently trail.").get();
   }
}
