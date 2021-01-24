package dev.panda.hub.utilities;

import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.ItemStackManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Utilities {
   public static int getPlayerPing(Player player) {
      try {
         String name = Bukkit.getServer().getClass().getPackage().getName().substring(23);
         Class<?> clazz = Class.forName(String.valueOf((new StringBuilder()).append("org.bukkit.craftbukkit.").append(name).append(".entity.CraftPlayer")));
         Object object = clazz.getMethod("getHandle").invoke(player);
         return (Integer)object.getClass().getDeclaredField("ping").get(object);
      } catch (Exception e) {
         return 0;
      }
   }

   public static String getJoinMessage(String input, Player player) {
      return input.replace("{name}", player.getName()).replace("{discord}", ConfigFile.getConfig().getString("DISCORD")).replace("{store}", ConfigFile.getConfig().getString("STORE")).replace("{teamspeak}", ConfigFile.getConfig().getString("TEAMSPEAK")).replace("{twitter}", ConfigFile.getConfig().getString("TWITTER")).replace("{website}", ConfigFile.getConfig().getString("WEBSITE")).replace("{rank}", Hub.getInstance().getRankManager().getRankName(player)).replace("{rank-prefix}", Hub.getInstance().getRankManager().getRankPrefix(player)).replace("{rank-suffix}", Hub.getInstance().getRankManager().getRankSuffix(player));
   }

   private static long convert(int input, char value) {
      switch(value) {
      case 'M':
         return (long)input * TimeUnit.DAYS.toMillis(30L);
      case 'd':
         return (long)input * TimeUnit.DAYS.toMillis(1L);
      case 'h':
         return (long)input * TimeUnit.HOURS.toMillis(1L);
      case 'm':
         return (long)input * TimeUnit.MINUTES.toMillis(1L);
      case 's':
         return (long)input * TimeUnit.SECONDS.toMillis(1L);
      case 'y':
         return (long)input * TimeUnit.DAYS.toMillis(365L);
      default:
         return -1L;
      }
   }

   public static long parse(String input) {
      if (input != null && !input.isEmpty()) {
         long toReturn = 0L;
         StringBuilder builder = new StringBuilder();

         for(int i = 0; i < input.length(); ++i) {
            char chr = input.charAt(i);
            if (Character.isDigit(chr)) {
               builder.append(chr);
            } else {
               String line;
               if (Character.isLetter(chr) && !(line = String.valueOf(builder)).isEmpty()) {
                  toReturn += convert(Integer.parseInt(line), chr);
                  builder = new StringBuilder();
               }
            }
         }

         return toReturn;
      } else {
         return -1L;
      }
   }

   public static void getFill(Inventory inventory) {
      for(int i = 0; i < inventory.getSize(); ++i) {
         if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
            inventory.setItem(i, ItemStackManager.getFill());
         }
      }

   }

   public static String getIP() {
      String toReturn = "";

      try {
         URL url = new URL("http://bot.whatismyipaddress.com");
         BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
         toReturn = reader.readLine().trim();
         if (toReturn.length() <= 0) {
            try {
               InetAddress address = InetAddress.getLocalHost();
               System.out.println(address.getHostAddress().trim());
               toReturn = address.getHostAddress().trim();
            } catch (Exception e) {
               toReturn = "ERROR";
            }
         }
      } catch (Exception e) {
         Hub.getInstance().getLogger().info("[License] Error in check your host ip!");
         e.printStackTrace();
      }

      return toReturn;
   }
}
