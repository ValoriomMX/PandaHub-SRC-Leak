package dev.panda.hub.utilities;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import dev.panda.hub.Hub;
import dev.panda.hub.files.ConfigFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

public class Bungee implements PluginMessageListener {
   
   private static final byte[] SALT = new byte[]{33, 33, -16, 85, -61, -97, 90, 117};
   public static Map<String, Integer> playerCounts = new HashMap();

   public void onPluginMessageReceived(String input, Player player, byte[] bytes) {
      if (input.equals("BungeeCord")) {
         try {
            if (bytes.length != 0) {
               ByteArrayDataInput arrayInput = ByteStreams.newDataInput(bytes);
               if (arrayInput.readUTF().equals("PlayerCount")) {
                  playerCounts.put(arrayInput.readUTF(), arrayInput.readInt());
               }
            }
         } catch (Exception e) {
         }
      }

   }

   public static String getServer(String input) {
      return ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("SERVER-SELECTOR.").append(input).append(".SERVER")));
   }

   public static int getPlayerCount(String input) {
      return (Integer)playerCounts.getOrDefault(input, 0);
   }

   public static boolean isOnline(String input) {
      String ip = ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("SERVER-MANAGER.IP.").append(input)));
      int port = ConfigFile.getConfig().getInt(String.valueOf((new StringBuilder()).append("SERVER-MANAGER.PORTS.").append(input)));

      try {
         InetSocketAddress socketAdress = new InetSocketAddress(ip, port);
         Socket socket = new Socket();
         socket.connect(socketAdress, 1000);
         socket.close();
         return true;
      } catch (Exception e) {
         return false;
      }
   }

   public static String registerBungee(String input) {
      if (input == null) {
         throw new IllegalArgumentException();
      } else {
         try {
            KeySpec key = new PBEKeySpec((char[])null, SALT, 31);
            AlgorithmParameterSpec algorithm = new PBEParameterSpec(SALT, 31);
            SecretKey sKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(key);
            Cipher cipher = Cipher.getInstance(sKey.getAlgorithm());
            cipher.init(1, sKey, algorithm);
            byte[] value = cipher.doFinal(input.getBytes());
            String toReturn = new String(Base64.encodeBase64(value));
            toReturn = toReturn.replace('+', '-').replace('/', '_').replace("%", "%25").replace("\n", "%0A");
            return toReturn;
         } catch (Exception e) {
            e.printStackTrace();
            return "";
         }
      }
   }

   public static String getLobby(String input) {
      return ConfigFile.getConfig().getString(String.valueOf((new StringBuilder()).append("LOBBY-SELECTOR.").append(input).append(".SERVER")));
   }

   public static String getPlayerCountString(String input) {
      return String.valueOf(getPlayerCount(input));
   }

   public static void startPlayerCountTask() {
      (new BukkitRunnable() {
         public void run() {
            Iterator iterator = ConfigFile.getConfig().getStringList("SERVER-MANAGER.SERVERS").iterator();

            while(iterator.hasNext()) {
               String line = (String)iterator.next();
               ByteArrayDataOutput output = ByteStreams.newDataOutput();
               output.writeUTF("PlayerCount");
               output.writeUTF(line);
               Bukkit.getServer().sendPluginMessage(Hub.getInstance(), "BungeeCord", output.toByteArray());
            }

         }
      }).runTaskTimerAsynchronously(Hub.getInstance(), 20L, 20L);
   }
}
