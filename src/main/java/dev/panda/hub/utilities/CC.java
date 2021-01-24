package dev.panda.hub.utilities;

import dev.panda.hub.files.MessageFile;
import dev.panda.hub.manager.rank.Rank;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CC {
   public static String NO_PERMISSION = set(MessageFile.getConfig().getString("NO-PERMISSION"));
   public static String LINE = set("&7&m-----------------------------");
   public static String PLAYER_NOT_FOUND = set(MessageFile.getConfig().getString("PLAYER-NOT-FOUND"));
   public static String NO_CONSOLE = set(MessageFile.getConfig().getString("NO-CONSOLE"));
   public static String capitalize(String input) {
      return WordUtils.capitalize(input);
   }

   public static List<String> lore(List<String> input) {
      return (List)input.stream().map(CC::set).collect(Collectors.toList());
   }

   public static String set(String input) {
      return ChatColor.translateAlternateColorCodes('&', input);
   }

   public static void translate() {
      Bukkit.getConsoleSender().sendMessage(LINE);
      Bukkit.getConsoleSender().sendMessage(set("&aChecking license..."));
      Bukkit.getConsoleSender().sendMessage(LINE);
      Bukkit.getConsoleSender().sendMessage(set(" "));
      Bukkit.getConsoleSender().sendMessage(set("&cInvalid License."));
      Bukkit.getConsoleSender().sendMessage(set(" "));
      Bukkit.getConsoleSender().sendMessage(set("&cJoin our discord server for support."));
      Bukkit.getConsoleSender().sendMessage(set("&chttps://discord.io/Panda-Community"));
      Bukkit.getConsoleSender().sendMessage(set(" "));
      Bukkit.getConsoleSender().sendMessage(LINE);
   }

   public static void getNoConsole(CommandSender sender) {
      sender.sendMessage(NO_CONSOLE);
   }

   public static void getNoPermission(CommandSender sender) {
      sender.sendMessage(NO_PERMISSION);
   }

   public static void translate(Rank rank) {
      Bukkit.getConsoleSender().sendMessage(LINE);
      Bukkit.getConsoleSender().sendMessage(set("&aChecking license..."));
      Bukkit.getConsoleSender().sendMessage(LINE);
      Bukkit.getConsoleSender().sendMessage(set(" "));
      Bukkit.getConsoleSender().sendMessage(set("&aSuccessfully loaded license."));
      Bukkit.getConsoleSender().sendMessage(set(" "));
      Bukkit.getConsoleSender().sendMessage(set(String.valueOf((new StringBuilder()).append("&eBuyer&7: &f").append(rank.getB()))));
      Bukkit.getConsoleSender().sendMessage(set(String.valueOf((new StringBuilder()).append("&eGenerated&7: &f").append(rank.getGd()))));
      Bukkit.getConsoleSender().sendMessage(set(String.valueOf((new StringBuilder()).append("&eLicense&7: &f").append(rank.getL()))));
      Bukkit.getConsoleSender().sendMessage(set(" "));
      Bukkit.getConsoleSender().sendMessage(set("&aThanks for purchase in Panda Community."));
      Bukkit.getConsoleSender().sendMessage(set("&ahttps://discord.io/Panda-Community"));
      Bukkit.getConsoleSender().sendMessage(LINE);
   }

   public static void getPlayerNotFound(CommandSender sender, String playerName) {
      sender.sendMessage(PLAYER_NOT_FOUND.replace("{target}", playerName));
   }
}
