package dev.panda.hub.manager.types;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.item.ItemCreator;
import java.beans.ConstructorProperties;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum ColorMessageType {
   
   AQUA("Aqua", "&bAqua", 3),
   PINK("Pink", "&dPink", 6),
   WHITE("White", "&fWhite", 0),
   DARK_AQUA("Dark Aqua", "&3Dark Aqua", 9),
   DARK_RED("Dark Red", "&4Dark Red", 14),
   ORANGE("Orange", "&6Orange", 1),
   DARK_BLUE("Dark Blue", "&1Dark Blue", 11),
   GRAY("Gray", "&7Gray", 8),
   PURPLE("Purple", "&5Purple", 10),
   BLUE("Blue", "&9Blue", 9),
   DARK_GREEN("Dark Green", "&2Dark Green", 13),
   BLACK("Black", "&0Black", 15),
   YELLOW("Yellow", "&eYellow", 4),
   RED("Red", "&cRed", 14),
   GREEN("Green", "&aGreen", 5),
   DARK_GRAY("Dark Gray", "&8Dark Gray", 7);

   private String name;
   private String displayName;
   private int data;

   public String getName() {
      return this.name;
   }

   public ItemStack getItem(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      ItemCreator creator = new ItemCreator(Material.WOOL);
      creator.setDurability(this.getData());
      creator.setName(this.getDisplayName());
      creator.setLore(Arrays.asList("", "&aClick to equip this color."));
      if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.settings.color_message.").append(this.getName().toLowerCase().replace(" ", "_"))))) {
         creator.setLore(Arrays.asList("", "&cYou don't have permission to this color.", String.valueOf((new StringBuilder()).append("&cPurchase at ").append(ConfigFile.getConfig().getString("STORE")))));
      }

      if (this.getName().equals(data.getColorMessage())) {
         creator.setLore(Arrays.asList("", "&aEquipped."));
      }

      return creator.get();
   }

   public int getData() {
      return this.data;
   }

   @ConstructorProperties({"name", "displayName", "data"})
   private ColorMessageType(String name, String displayName, int data) {
      this.name = name;
      this.displayName = displayName;
      this.data = data;
   }

   public String getDisplayName() {
      return CC.set(this.displayName);
   }
}
