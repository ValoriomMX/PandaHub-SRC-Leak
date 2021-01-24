package dev.panda.hub.manager;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.utilities.Bungee;
import dev.panda.hub.utilities.item.ItemCreator;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackManager {
   public static ItemStack getServerSelector(String input) {
      return (new ItemCreator(ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(input).append(".ITEM.MATERIAL"))))).setDurability(ConfigFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(input).append(".ITEM.DATA")))).setName(ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(input).append(".NAME"))).replace(String.valueOf((new StringBuilder()).append("{count_").append(Bungee.getServer(input)).append("}")), Bungee.getPlayerCountString(Bungee.getServer(input)))).setLore((List)ConfigFile.getConfig().getStringList(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(input).append(".LORE"))).stream().map((s) -> {
         return s.replace(String.valueOf((new StringBuilder()).append("{count_").append(Bungee.getServer(input)).append("}")), Bungee.getPlayerCountString(Bungee.getServer(input)));
      }).collect(Collectors.toList())).setGlow(ConfigFile.getConfig().getBoolean(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(input).append(".ITEM.GLOW")))).get();
   }

   public static ItemStack getFill() {
      return (new ItemCreator(ConfigFile.getConfig().getString("FILL-ITEM.MATERIAL"))).setDurability(ConfigFile.getConfig().getInt("FILL-ITEM.DATA")).setName(ConfigFile.getConfig().getString("FILL-ITEM.NAME")).get();
   }

   public static ItemStack getLobbySelector(String input) {
      return (new ItemCreator(ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(input).append(".ITEM.MATERIAL"))))).setDurability(ConfigFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(input).append(".ITEM.DATA")))).setName(ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(input).append(".NAME"))).replace(String.valueOf((new StringBuilder()).append("{count_").append(Bungee.getLobby(input)).append("}")), Bungee.getPlayerCountString(Bungee.getLobby(input)))).setLore((List)ConfigFile.getConfig().getStringList(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(input).append(".LORE"))).stream().map((s) -> {
         return s.replace(String.valueOf((new StringBuilder()).append("{count_").append(Bungee.getLobby(input)).append("}")), Bungee.getPlayerCountString(Bungee.getLobby(input)));
      }).collect(Collectors.toList())).setGlow(ConfigFile.getConfig().getBoolean(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(input).append(".ITEM.GLOW")))).get();
   }

   public static ItemStack getSkull(String input) {
      return (new ItemCreator(Material.SKULL_ITEM)).setDurability((int)3).setOwner(input).setName(String.valueOf((new StringBuilder()).append("&6Skull of ").append(input))).get();
   }

   public static ItemStack getBack() {
      return (new ItemCreator(Material.ARROW)).setName("&7Back").get();
   }
}
