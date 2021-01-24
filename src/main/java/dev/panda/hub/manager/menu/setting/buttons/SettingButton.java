package dev.panda.hub.manager.menu.setting.buttons;

import dev.panda.hub.commands.toggle.PrivateMessageCommand;
import dev.panda.hub.commands.toggle.ScoreboardCommand;
import dev.panda.hub.commands.toggle.SoundCommand;
import dev.panda.hub.files.InventoryFile;
import dev.panda.hub.utilities.item.ItemCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SettingButton {
   public static ItemStack getSoundMessage(Player player) {
      return (new ItemCreator(InventoryFile.getConfig().getString("SETTINGS.SOUND-MESSAGE.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("SETTINGS.SOUND-MESSAGE.ITEM.DATA")).setName(InventoryFile.getConfig().getString("SETTINGS.SOUND-MESSAGE.NAME")).setLore(SoundCommand.getInstance().getPrivateSound().contains(player.getUniqueId()) ? InventoryFile.getConfig().getStringList("SETTINGS.SOUND-MESSAGE.DISABLE-LORE") : InventoryFile.getConfig().getStringList("SETTINGS.SOUND-MESSAGE.ENABLE-LORE")).setGlow(InventoryFile.getConfig().getBoolean("SETTINGS.SOUND-MESSAGE.ITEM.GLOW")).get();
   }

   public static ItemStack getTag() {
      return (new ItemCreator(InventoryFile.getConfig().getString("SETTINGS.TAG.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("SETTINGS.TAG.ITEM.DATA")).setName(InventoryFile.getConfig().getString("SETTINGS.TAG.NAME")).setLore(InventoryFile.getConfig().getStringList("SETTINGS.TAG.LORE")).setGlow(InventoryFile.getConfig().getBoolean("SETTINGS.TAG.ITEM.GLOW")).get();
   }

   public static ItemStack getPrivateMessage(Player player) {
      return (new ItemCreator(InventoryFile.getConfig().getString("SETTINGS.PRIVATE-MESSAGE.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("SETTINGS.PRIVATE-MESSAGE.ITEM.DATA")).setName(InventoryFile.getConfig().getString("SETTINGS.PRIVATE-MESSAGE.NAME")).setLore(PrivateMessageCommand.getInstance().getPrivateMessage().contains(player.getUniqueId()) ? InventoryFile.getConfig().getStringList("SETTINGS.PRIVATE-MESSAGE.DISABLE-LORE") : InventoryFile.getConfig().getStringList("SETTINGS.PRIVATE-MESSAGE.ENABLE-LORE")).setGlow(InventoryFile.getConfig().getBoolean("SETTINGS.PRIVATE-MESSAGE.ITEM.GLOW")).get();
   }

   public static ItemStack getScoreboard(Player player) {
      return (new ItemCreator(InventoryFile.getConfig().getString("SETTINGS.SCOREBOARD.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("SETTINGS.SCOREBOARD.ITEM.DATA")).setName(InventoryFile.getConfig().getString("SETTINGS.SCOREBOARD.NAME")).setLore(ScoreboardCommand.getInstance().getScoreboard().contains(player.getUniqueId()) ? InventoryFile.getConfig().getStringList("SETTINGS.SCOREBOARD.DISABLE-LORE") : InventoryFile.getConfig().getStringList("SETTINGS.SCOREBOARD.ENABLE-LORE")).setGlow(InventoryFile.getConfig().getBoolean("SETTINGS.SCOREBOARD.ITEM.GLOW")).get();
   }

   public static ItemStack getColorChat() {
      return (new ItemCreator(InventoryFile.getConfig().getString("SETTINGS.COLOR-CHAT.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("SETTINGS.COLOR-CHAT.ITEM.DATA")).setName(InventoryFile.getConfig().getString("SETTINGS.COLOR-CHAT.NAME")).setLore(InventoryFile.getConfig().getStringList("SETTINGS.COLOR-CHAT.LORE")).setGlow(InventoryFile.getConfig().getBoolean("SETTINGS.COLOR-CHAT.ITEM.GLOW")).get();
   }

   public static ItemStack getColorMessage() {
      return (new ItemCreator(InventoryFile.getConfig().getString("SETTINGS.COLOR-MESSAGE.ITEM.MATERIAL"))).setDurability(InventoryFile.getConfig().getInt("SETTINGS.COLOR-MESSAGE.ITEM.DATA")).setName(InventoryFile.getConfig().getString("SETTINGS.COLOR-MESSAGE.NAME")).setLore(InventoryFile.getConfig().getStringList("SETTINGS.COLOR-MESSAGE.LORE")).setGlow(InventoryFile.getConfig().getBoolean("SETTINGS.COLOR-MESSAGE.ITEM.GLOW")).get();
   }
}
