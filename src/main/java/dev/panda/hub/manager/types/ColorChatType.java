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

public enum ColorChatType {
   
   DARK_GREEN("Dark Green", "&2Dark Green", 13),
   GREEN("Green", "&aGreen", 5),
   AQUA("Aqua", "&bAqua", 3),
   WHITE("White", "&fWhite", 0),
   DARK_AQUA("Dark Aqua", "&3Dark Aqua", 9),
   GRAY("Gray", "&7Gray", 8),
   RED("Red", "&cRed", 14),
   DARK_RED("Dark Red", "&4Dark Red", 14),
   DARK_BLUE("Dark Blue", "&1Dark Blue", 11),
   PINK("Pink", "&dPink", 6),
   BLACK("Black", "&0Black", 15),
   DARK_GRAY("Dark Gray", "&8Dark Gray", 7),
   ORANGE("Orange", "&6Orange", 1),
   YELLOW("Yellow", "&eYellow", 4),
   BLUE("Blue", "&9Blue", 9),
   PURPLE("Purple", "&5Purple", 10);

   private int data;
   private String displayName;
   private String name;

   public int getData() {
      return this.data;
   }

   public ItemStack getItem(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      ItemCreator creator = new ItemCreator(Material.WOOL);
      creator.setDurability(this.getData());
      creator.setName(this.getDisplayName());
      creator.setLore(Arrays.asList("", "&aClick to equip this color."));
      if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.settings.color_chat.").append(this.getName().toLowerCase().replace(" ", "_"))))) {
         creator.setLore(Arrays.asList("", "&cYou don't have permission to this color.", String.valueOf((new StringBuilder()).append("&cPurchase at ").append(ConfigFile.getConfig().getString("STORE")))));
      }

      if (this.getName().equals(data.getColorChat())) {
         creator.setLore(Arrays.asList("", "&aEquipped."));
      }

      return creator.get();
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return CC.set(this.displayName);
   }

   @ConstructorProperties({"name", "displayName", "data"})
   private ColorChatType(String name, String displayName, int data) {
      this.name = name;
      this.displayName = displayName;
      this.data = data;
   }
}
