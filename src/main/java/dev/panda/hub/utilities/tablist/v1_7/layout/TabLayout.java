package dev.panda.hub.utilities.tablist.v1_7.layout;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.panda.hub.utilities.tablist.v1_7.Tablist_v1_7;
import dev.panda.hub.utilities.tablist.v1_7.entry.TabEntry;
import dev.panda.hub.utilities.tablist.v1_7.skin.Skin;
import dev.panda.hub.utilities.tablist.v1_7.util.Reflection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector.PacketTabHeader;

public class TabLayout {

   private Tablist_v1_7 instance;
   private WorldServer worldServer;
   private static Map<UUID, TabLayout> layoutMapping = Maps.newHashMap();
   private PlayerInteractManager playerInteractManager;
   private Map<Integer, GameProfile> profileMapping;
   private Map<Integer, Skin> skinMapping;
   private MinecraftServer minecraftServer = MinecraftServer.getServer();
   private Player player;

   private Map<Integer, Integer> pingMapping;

   public TabLayout(Tablist_v1_7 instnace, Player player) {
      this.worldServer = this.minecraftServer.getWorldServer(0);
      this.playerInteractManager = new PlayerInteractManager(this.worldServer);
      this.pingMapping = Maps.newHashMap();
      this.profileMapping = Maps.newHashMap();
      this.skinMapping = Maps.newHashMap();
      this.instance = instnace;
      this.player = player;
   }

   public void update(int v1, int v2, String s1, int v3, Skin skin) {
      if (v2 > 19) {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append("Row is above 19 ").append(v2)));
      } else if (v1 > 4) {
         throw new RuntimeException(String.valueOf((new StringBuilder()).append("Column is above 4 ").append(v1)));
      } else {
         s1 = ChatColor.translateAlternateColorCodes('&', s1);
         String s2 = s1;
         String s3 = "";
         if (s1.length() > 16) {
            s2 = s1.substring(0, 16);
            if (s2.charAt(15) == 167) {
               s2 = s2.substring(0, 15);
               s3 = s1.substring(15, s1.length());
            } else if (s2.charAt(14) == 167) {
               s2 = s2.substring(0, 14);
               s3 = s1.substring(14, s1.length());
            } else {
               s3 = String.valueOf((new StringBuilder()).append(ChatColor.getLastColors(s2)).append(s1.substring(16, s1.length())));
            }
         }

         if (s3.length() > 16) {
            s3 = s3.substring(0, 16);
         }

         String s4 = String.valueOf((new StringBuilder()).append("$").append(this.getTeamAt(v2, v1)));
         PacketPlayOutScoreboardTeam scoreboardTeam = new PacketPlayOutScoreboardTeam();
         Reflection.getField(scoreboardTeam.getClass(), "a", String.class).set(scoreboardTeam, s4);
         Reflection.getField(scoreboardTeam.getClass(), "b", String.class).set(scoreboardTeam, s4);
         Reflection.getField(scoreboardTeam.getClass(), "c", String.class).set(scoreboardTeam, s2);
         Reflection.getField(scoreboardTeam.getClass(), "d", String.class).set(scoreboardTeam, s3);
         Reflection.getField(scoreboardTeam.getClass(), "f", Integer.TYPE).set(scoreboardTeam, 2);
         Reflection.getField(scoreboardTeam.getClass(), "g", Integer.TYPE).set(scoreboardTeam, -1);
         this.sendPacket(scoreboardTeam);
         this.fetchPing(v2 + v1 * 20, v3);
         this.fetchSkin(v2 + v1 * 20, skin);
      }
   }

   private String getTeamAt(int v1, int v2) {
      return this.getTeamAt(v1 + v2 * 20);
   }

   private void fetchSkin(int v1, Skin skin) {
      GameProfile profile = (GameProfile)this.profileMapping.get(v1);
      long l1 = ((CraftPlayer)this.player).getHandle().playerConnection.networkManager.getVersion();
      if (l1 >= 47) {
         if (skin == null) {
            skin = Skin.DEFAULT_SKIN;
         }

         Skin skin1 = (Skin)this.skinMapping.get(v1);
         if (!Objects.equals(skin, skin1)) {
            GameProfile profile1 = new GameProfile(profile.getId(), profile.getName());
            profile1.getProperties().put("textures", skin.getProperty());
            EntityPlayer ePlayer = new EntityPlayer(this.minecraftServer, this.worldServer, profile1, this.playerInteractManager);
            PacketPlayOutPlayerInfo playerInfo = PacketPlayOutPlayerInfo.removePlayer(ePlayer);
            this.sendPacket(playerInfo);
            PacketPlayOutPlayerInfo playerInfo1 = PacketPlayOutPlayerInfo.addPlayer(ePlayer);
            this.sendPacket(playerInfo1);
            this.profileMapping.put(v1, profile1);
            this.skinMapping.put(v1, skin);
         }
      }
   }

   public void setHeaderAndFooter() {
      int length = ((CraftPlayer)this.player).getHandle().playerConnection.networkManager.getVersion();
      if (length >= 47) {
         String s1 = ChatColor.translateAlternateColorCodes('&', this.instance.getAdapter().getHeader(this.player));
         String s2 = ChatColor.translateAlternateColorCodes('&', this.instance.getAdapter().getFooter(this.player));
         IChatBaseComponent baseComponent = ChatSerializer.a(String.valueOf((new StringBuilder()).append("{text:\"").append(StringEscapeUtils.escapeJava(s1)).append("\"}")));
         IChatBaseComponent baseComponent1 = ChatSerializer.a(String.valueOf((new StringBuilder()).append("{text:\"").append(StringEscapeUtils.escapeJava(s2)).append("\"}")));
         PacketTabHeader tabHeader = new PacketTabHeader(baseComponent, baseComponent1);
         this.sendPacket(tabHeader);
      }
   }

   private String getTeamAt(int v1) {
      return String.valueOf((new StringBuilder()).append(ChatColor.BOLD.toString()).append(ChatColor.GREEN.toString()).append(ChatColor.UNDERLINE.toString()).append(ChatColor.YELLOW.toString()).append(v1 >= 10 ? String.valueOf((new StringBuilder()).append('§').append(String.valueOf(v1 / 10)).append('§').append(String.valueOf(v1 % 10))) : String.valueOf((new StringBuilder()).append(ChatColor.BLACK.toString()).append('§').append(String.valueOf(v1)))).append(ChatColor.RESET));
   }

   private List<String> getAllPlayerNames() {
      List<String> list = Lists.newArrayList();
      Iterator iterator = Bukkit.getServer().getOnlinePlayers().iterator();

      while(iterator.hasNext()) {
         Player player = (Player)iterator.next();
         list.add(player.getName());
      }

      return list;
   }

   public TabEntry getByLocation(List<TabEntry> entries, int v1, int v2) {
      Iterator iterator = entries.iterator();

      TabEntry entry;
      do {
         if (!iterator.hasNext()) {
            return null;
         }

         entry = (TabEntry)iterator.next();
      } while(entry.getColumn() != v1 || entry.getRow() != v2);

      return entry;
   }

   public void sendPacket(Packet packet) {
      ((CraftPlayer)this.player).getHandle().playerConnection.sendPacket(packet);
   }

   public static Map<UUID, TabLayout> getLayoutMapping() {
      return layoutMapping;
   }

   private void fetchPing(int v1, int v2) {
      GameProfile profile1 = (GameProfile)this.profileMapping.get(v1);
      int length = (Integer)this.pingMapping.get(v1);
      if (!Objects.equals(length, v2)) {
         EntityPlayer ePlayer = new EntityPlayer(this.minecraftServer, this.worldServer, profile1, this.playerInteractManager);
         ePlayer.ping = v2;
         this.pingMapping.put(v1, v2);
         PacketPlayOutPlayerInfo playerInfo = PacketPlayOutPlayerInfo.updatePing(ePlayer);
         this.sendPacket(playerInfo);
      }
   }

   public void create() {
      int v1;
      int v2;
      int v3;
      for(v1 = 0; v1 < 20; ++v1) {
         for(v2 = 0; (long)v2 < 3L; ++v2) {
            v3 = v1 + v2 * 20;
            Skin skin = Skin.DEFAULT_SKIN;
            this.skinMapping.put(v3, skin);
            Property property = ((Skin)this.skinMapping.get(v3)).getProperty();
            GameProfile profile = new GameProfile(UUID.randomUUID(), this.getTeamAt(v1, v2));
            profile.getProperties().put("textures", property);
            EntityPlayer ePlayer = new EntityPlayer(this.minecraftServer, this.worldServer, profile, this.playerInteractManager);
            ePlayer.ping = 0;
            this.pingMapping.put(v3, 0);
            this.profileMapping.put(v3, profile);
            PacketPlayOutPlayerInfo playerInfo1 = PacketPlayOutPlayerInfo.addPlayer(ePlayer);
            this.sendPacket(playerInfo1);
         }
      }

      EntityPlayer ePlayer1;
      for(v1 = 60; v1 < 80; ++v1) {
         Skin skin1 = Skin.DEFAULT_SKIN;
         this.skinMapping.put(v1, skin1);
         Property v4 = ((Skin)this.skinMapping.get(v1)).getProperty();
         GameProfile profile2 = new GameProfile(UUID.randomUUID(), this.getTeamAt(v1));
         profile2.getProperties().put("textures", v4);
         ePlayer1 = new EntityPlayer(this.minecraftServer, this.worldServer, profile2, this.playerInteractManager);
         ePlayer1.ping = 0;
         this.pingMapping.put(v1, 0);
         this.profileMapping.put(v1, profile2);
         PacketPlayOutPlayerInfo playerInfo2 = PacketPlayOutPlayerInfo.addPlayer(ePlayer1);
         this.sendPacket(playerInfo2);
      }

      PacketPlayOutScoreboardTeam v7 = new PacketPlayOutScoreboardTeam();
      Reflection.getField(v7.getClass(), "a", String.class).set(v7, "tab");
      Reflection.getField(v7.getClass(), "b", String.class).set(v7, "tab");
      Reflection.getField(v7.getClass(), "f", Integer.TYPE).set(v7, 0);
      Reflection.getField(v7.getClass(), "g", Integer.TYPE).set(v7, -1);
      Reflection.getField(v7.getClass(), "e", Object.class).set(v7, this.getAllPlayerNames());
      this.sendPacket(v7);

      for(v2 = 0; v2 < 20; ++v2) {
         for(v3 = 0; v3 < 4; ++v3) {
            String s7 = String.valueOf((new StringBuilder()).append("$").append(this.getTeamAt(v2, v3)));
            v7 = new PacketPlayOutScoreboardTeam();
            Reflection.getField(v7.getClass(), "a", String.class).set(v7, s7);
            Reflection.getField(v7.getClass(), "b", String.class).set(v7, s7);
            Reflection.getField(v7.getClass(), "f", Integer.TYPE).set(v7, 0);
            Reflection.getField(v7.getClass(), "g", Integer.TYPE).set(v7, -1);
            Reflection.getField(v7.getClass(), "e", Object.class).set(v7, Arrays.asList(this.getTeamAt(v2, v3)));
            this.sendPacket(v7);
         }
      }

      PacketPlayOutScoreboardTeam scoreboardTeam1 = new PacketPlayOutScoreboardTeam();
      Reflection.getField(scoreboardTeam1.getClass(), "a", String.class).set(scoreboardTeam1, "tab");
      Reflection.getField(scoreboardTeam1.getClass(), "b", String.class).set(scoreboardTeam1, "tab");
      Reflection.getField(scoreboardTeam1.getClass(), "f", Integer.TYPE).set(scoreboardTeam1, 3);
      Reflection.getField(scoreboardTeam1.getClass(), "g", Integer.TYPE).set(scoreboardTeam1, -1);
      Reflection.getField(scoreboardTeam1.getClass(), "e", Object.class).set(scoreboardTeam1, Arrays.asList(this.player.getName()));
      Iterator iterator2 = Bukkit.getServer().getOnlinePlayers().iterator();

      while(iterator2.hasNext()) {
         Player player4 = (Player)iterator2.next();
         ePlayer1 = ((CraftPlayer)player4).getHandle();
         ePlayer1.playerConnection.sendPacket(scoreboardTeam1);
      }

   }
}
