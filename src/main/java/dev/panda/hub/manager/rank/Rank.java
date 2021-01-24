package dev.panda.hub.manager.rank;

import dev.panda.hub.Hub;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.manager.rank.impl.Default;
import dev.panda.hub.manager.rank.impl.Vault;
import dev.panda.hub.utilities.Bungee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Rank {
   
   private boolean debug = false;
   private String gd;
   private String l;
   private boolean validRank = false;
   private String i;
   private Plugin p;
   private PlayerData.ErrorType et;
   private String b;
   private String s;
   private String ak;

   public void setB(String input) {
      this.b = input;
   }

   public void setGd(String input) {
      this.gd = input;
   }

   public String getAk() {
      return this.ak;
   }

   public String getS() {
      return this.s;
   }

   public boolean isDebug() {
      return this.debug;
   }

   public String getGd() {
      return this.gd;
   }

   public String getI() {
      return this.i;
   }

   public void setAk(String input) {
      this.ak = input;
   }

   public void setI(String input) {
      this.i = input;
   }

   public Rank(String server, String license, String ip, Plugin plugin, String apiKey) {
      this.s = server;
      this.l = license;
      this.i = ip;
      this.p = plugin;
      this.ak = apiKey;
   }

   public boolean isValidRank() {
      return this.validRank;
   }

   public Plugin getP() {
      return this.p;
   }

   public String getB() {
      return this.b;
   }

   public void setDebug(boolean type) {
      this.debug = type;
   }

   public void createRank() {
      try {
         String pluginName = this.p.getDescription().getName();

         URL url;
         try {
            url = new URL(String.valueOf((new StringBuilder()).append(this.s).append("/api/check/request/licenses?keyAPI=").append(Bungee.registerBungee(this.ak)).append("&license=").append(Bungee.registerBungee(this.l)).append("&plugin=").append(Bungee.registerBungee(pluginName)).append("&ip=").append(this.i)));
         } catch (MalformedURLException var9) {
            var9.printStackTrace();
            this.validRank = false;
            return;
         }

         URLConnection connection = url.openConnection();
         BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         StringBuilder builder = new StringBuilder();

         String line;
         while((line = reader.readLine()) != null) {
            builder.append(line);
         }

         String response = String.valueOf(builder);
         if (response.equalsIgnoreCase("API_KEY_NOT_VALID")) {
            this.et = PlayerData.ErrorType.API_KEY_NOT_VALID;
         } else if (response.equalsIgnoreCase("INVALID_LICENSE")) {
            this.et = PlayerData.ErrorType.INVALID_LICENSE;
         } else if (response.equalsIgnoreCase("INVALID_PLUGIN_NAME")) {
            this.et = PlayerData.ErrorType.INVALID_PLUGIN_NAME;
         } else if (response.equalsIgnoreCase("INVALID_IP")) {
            this.et = PlayerData.ErrorType.INVALID_IP;
         } else if (response.equalsIgnoreCase("EXPIRED")) {
            this.et = PlayerData.ErrorType.EXPIRED;
         } else if (response.startsWith("VALID")) {
            this.et = PlayerData.ErrorType.VALID;
            this.validRank = true;
            String[] validResponse = response.split(";");
            this.b = validResponse[1];
            this.gd = validResponse[3];
         } else {
            this.et = PlayerData.ErrorType.PAGE_ERROR;
         }
      } catch (IOException var10) {
         if (this.debug) {
            var10.printStackTrace();
         }

         this.validRank = false;
         this.et = PlayerData.ErrorType.PAGE_ERROR;
      }

   }

   public void setEt(PlayerData.ErrorType type) {
      this.et = type;
   }

   public void registerRankIntegration() {
      if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
         Hub.getInstance().loadVault();
         Hub.getInstance().setRankManager(new Vault());
         if (Hub.getInstance().getChat().getName().contains("PermissionsEx")) {
            Hub.getInstance().setRankSystem("PermissionsEx");
         } else if (Hub.getInstance().getChat().getName().contains("LuckPerms")) {
            Hub.getInstance().setRankSystem("LuckPerms");
         } else {
            Hub.getInstance().setRankManager(new Default());
            Hub.getInstance().setRankSystem("Unknown");
         }
      } else {
         Hub.getInstance().setRankManager(new Default());
         Hub.getInstance().setRankSystem("None");
      }

   }

   public void setL(String input) {
      this.l = input;
   }

   public void setValidRank(boolean type) {
      this.validRank = type;
   }

   public String getL() {
      return this.l;
   }

   public PlayerData.ErrorType getEt() {
      return this.et;
   }

   public void setS(String input) {
      this.s = input;
   }

   public void setP(Plugin plugin) {
      this.p = plugin;
   }
}
