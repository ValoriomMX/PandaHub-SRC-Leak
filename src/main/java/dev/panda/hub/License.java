package dev.panda.hub;

import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.Description;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class License {
   
   private String securityKey = "DFiu3qRBERHJEDFkibjhjwkerh34rHRHBRKa";
   private Plugin plugin;
   private String validationServer;
   private String licenseKey;
   private boolean debug = false;

   public License(String licenseKey, String validationServer, Plugin plugin) {
      this.licenseKey = licenseKey;
      this.validationServer = validationServer;
      this.plugin = plugin;
   }

   public void registerMain() {
      try {
         Bukkit.getConsoleSender().sendMessage(CC.set("&7&m==================================="));
         Bukkit.getConsoleSender().sendMessage(CC.set("&cConnecting to License..."));
         String uuid = this.toBinary(UUID.randomUUID().toString());
         String sKey = this.toBinary(this.securityKey);
         String lKey = this.toBinary(this.licenseKey);
         String response = this.requestServer(this.xor(uuid, sKey), this.xor(uuid, lKey));
         if (response.equalsIgnoreCase("PAGE_ERROR")) {
            Bukkit.getConsoleSender().sendMessage(CC.set(" "));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append("     &4❤ &c&l").append(Description.getName()).append(" &4❤"))));
            Bukkit.getConsoleSender().sendMessage(CC.set(""));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cAuthor&7: &f").append(Description.getAuthor()))).replace("[", "").replace("]", ""));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cVersion&7: &f").append(Description.getVersion()))));
            Bukkit.getConsoleSender().sendMessage(CC.set(" &7➥ &cLicense&7: &4✖"));
            Bukkit.getConsoleSender().sendMessage(CC.set(" "));
            Bukkit.getConsoleSender().sendMessage(CC.set("&7&m==================================="));
            Bukkit.getScheduler().cancelTasks(this.plugin);
            Bukkit.getPluginManager().disablePlugin(this.plugin);
         } else if (response.equalsIgnoreCase("KEY_NOT_FOUND")) {
            Bukkit.getConsoleSender().sendMessage(CC.set(" "));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append("     &4❤ &c&l").append(Description.getName()).append(" &4❤"))));
            Bukkit.getConsoleSender().sendMessage(CC.set(""));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cAuthor&7: &f").append(Description.getAuthor()))).replace("[", "").replace("]", ""));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cVersion&7: &f").append(Description.getVersion()))));
            Bukkit.getConsoleSender().sendMessage(CC.set(" &7➥ &cLicense&7: &4✖"));
            Bukkit.getConsoleSender().sendMessage(CC.set(" "));
            Bukkit.getConsoleSender().sendMessage(CC.set("&7&m==================================="));
            Bukkit.getScheduler().cancelTasks(this.plugin);
            Bukkit.getPluginManager().disablePlugin(this.plugin);
         } else {
            String value = this.xor(this.xor(response, lKey), sKey);
            if (uuid.substring(0, value.length()).equals(value)) {
               Bukkit.getConsoleSender().sendMessage(CC.set(" "));
               Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append("     &4❤ &c&l").append(Description.getName()).append(" &4❤"))));
               Bukkit.getConsoleSender().sendMessage(CC.set(""));
               Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cAuthor&7: &f").append(Description.getAuthor()))).replace("[", "").replace("]", ""));
               Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cVersion&7: &f").append(Description.getVersion()))));
               Bukkit.getConsoleSender().sendMessage(CC.set(" &7➥ &cLicense&7: &4✖"));
               Bukkit.getConsoleSender().sendMessage(CC.set(" "));
               Bukkit.getConsoleSender().sendMessage(CC.set("&7&m==================================="));
               Bukkit.getScheduler().cancelTasks(this.plugin);
               Bukkit.getPluginManager().disablePlugin(this.plugin);
            } else {
               Bukkit.getConsoleSender().sendMessage(CC.set(" "));
               Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append("     &4❤ &c&l").append(Description.getName()).append(" &4❤"))));
               Bukkit.getConsoleSender().sendMessage(CC.set(""));
               Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cAuthor&7: &f").append(Description.getAuthor()))).replace("[", "").replace("]", ""));
               Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &cVersion&7: &f").append(Description.getVersion()))));
               Bukkit.getConsoleSender().sendMessage(CC.set(" &7➥ &cLicense&7: &4✖"));
               Bukkit.getConsoleSender().sendMessage(CC.set(" "));
               Bukkit.getConsoleSender().sendMessage(CC.set("&7&m==================================="));
               Bukkit.getScheduler().cancelTasks(this.plugin);
               Bukkit.getPluginManager().disablePlugin(this.plugin);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   private String xor(String v1, String v2) {
      String toReturntoReturn = "";

      for(int i = 0; i < (v1.length() < v2.length() ? v1.length() : v2.length()); ++i) {
         toReturntoReturn = String.valueOf((new StringBuilder()).append(toReturntoReturn).append(Byte.valueOf(String.valueOf((new StringBuilder()).append("").append(v1.charAt(i)))) ^ Byte.valueOf(String.valueOf((new StringBuilder()).append("").append(v2.charAt(i))))));
      }

      return toReturntoReturn;
   }

   private String toBinary(String input) {
      byte[] sBytes = input.getBytes();
      StringBuilder builder = new StringBuilder();
      byte[] bytes = sBytes;
      int length = sBytes.length;

      for(int i = 0; i < length; ++i) {
         byte b = bytes[i];
         byte b2 = b;

         for(int t = 0; t < 8; ++t) {
            builder.append((b2 & 128) == 0 ? 0 : 1);
            b2 <<= 1;
         }
      }

      return String.valueOf(builder);
   }

   private String requestServer(String v1, String v2) throws IOException {
      URL url = new URL(String.valueOf((new StringBuilder()).append(this.validationServer).append("?v1=").append(v1).append("&v2=").append(v2).append("&pl=").append(this.plugin.getName())));
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("User-Agent", "Mozilla/5.0");
      int responseCode = connection.getResponseCode();
      if (this.debug) {
         System.out.println(String.valueOf((new StringBuilder()).append("\nSending 'GET' request to URL : ").append(url)));
         System.out.println(String.valueOf((new StringBuilder()).append("Response Code : ").append(responseCode)));
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      Throwable throwable = null;

      try {
         StringBuilder builder = new StringBuilder();

         String line;
         while((line = reader.readLine()) != null) {
            builder.append(line);
         }

         String toReturn = String.valueOf(builder);
         return toReturn;
      } catch (Throwable t) {
         throwable = t;
         throw t;
      } finally {
         if (reader != null) {
            if (throwable != null) {
               try {
                  reader.close();
               } catch (Throwable t) {
                  throwable.addSuppressed(t);
               }
            } else {
               reader.close();
            }
         }

      }
   }
}
