package dev.panda.hub.utilities.tablist.v1_7.packet;

import dev.panda.hub.utilities.tablist.v1_7.util.Reflection;
import dev.panda.hub.utilities.tablist.v1_7.util.TinyProtocol;
import net.minecraft.server.v1_7_R4.PacketPlayOutScoreboardTeam;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TabPacket extends TinyProtocol {
   public TabPacket(Plugin plugin) {
      super(plugin);
   }

   public Object onPacketOutAsync(Player player, Channel channel, Object o1) {
      if (o1 instanceof PacketPlayOutScoreboardTeam) {
         PacketPlayOutScoreboardTeam scoreboardTeam = (PacketPlayOutScoreboardTeam)o1;
         double d1 = (Integer)Reflection.getField(scoreboardTeam.getClass(), "f", Integer.TYPE).get(scoreboardTeam);
         String s1 = (String)Reflection.getField(scoreboardTeam.getClass(), "a", String.class).get(scoreboardTeam);
         if (d1 == 4 && !s1.equals("tab")) {
            Reflection.getField(scoreboardTeam.getClass(), "a", String.class).set(scoreboardTeam, "tab");
            Reflection.getField(scoreboardTeam.getClass(), "b", String.class).set(scoreboardTeam, "tab");
            Reflection.getField(scoreboardTeam.getClass(), "f", Integer.TYPE).set(scoreboardTeam, 3);
         }
      }

      return super.onPacketOutAsync(player, channel, o1);
   }
}
