package dev.panda.hub.utilities.tablist.v1_7.util;

import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelDuplexHandler;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.ChannelHandler;
import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
import net.minecraft.util.io.netty.channel.ChannelInitializer;
import net.minecraft.util.io.netty.channel.ChannelPipeline;
import net.minecraft.util.io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class TinyProtocol {
   
   private ChannelInitializer<Channel> beginInitProtocol;
   private static AtomicInteger ID = new AtomicInteger(0);
   private static Reflection.FieldAccessor<Object> getManager = Reflection.getField("{nms}.PlayerConnection", "networkManager", Object.class);
   private ChannelInitializer<Channel> endInitProtocol;
   private static Reflection.FieldAccessor<GameProfile> getGameProfile;
   protected volatile boolean closed;
   private Listener listener;
   private String handlerName;
   private static Reflection.MethodInvoker getPlayerHandle = Reflection.getMethod("{obc}.entity.CraftPlayer", "getHandle");
   private static Reflection.FieldAccessor<Object> getConnection = Reflection.getField("{nms}.EntityPlayer", "playerConnection", Object.class);
   private static Reflection.FieldAccessor<Object> getServerConnection;
   private List<Object> networkManagers;
   private static Reflection.FieldAccessor<Object> getMinecraftServer;
   protected Plugin plugin;
   private List<Channel> serverChannels = Lists.newArrayList();
   private static Class<Object> minecraftServerClass = Reflection.getUntypedClass("{nms}.MinecraftServer");
   private static Class<?> PACKET_LOGIN_IN_START;
   private static Reflection.MethodInvoker getNetworkMarkers;
   private Set<Channel> uninjectedChannels = Collections.newSetFromMap((new MapMaker()).weakKeys().makeMap());
   private static Class<Object> serverConnectionClass = Reflection.getUntypedClass("{nms}.ServerConnection");
   private ChannelInboundHandlerAdapter serverChannelHandler;
   private Map<String, Channel> channelLookup = (new MapMaker()).weakValues().makeMap();
   private static Reflection.FieldAccessor<Channel> getChannel = Reflection.getField((String)"{nms}.NetworkManager", Channel.class, 0);

   private TinyProtocol.PacketInterceptor injectChannelInternal(Channel channel) {
      try {
         TinyProtocol.PacketInterceptor interceptor = (TinyProtocol.PacketInterceptor)channel.pipeline().get(this.handlerName);
         if (interceptor == null) {
            interceptor = new TinyProtocol.PacketInterceptor();
            channel.pipeline().addBefore("packet_handler", this.handlerName, interceptor);
            this.uninjectedChannels.remove(channel);
         }

         return interceptor;
      } catch (IllegalArgumentException var3) {
         return (TinyProtocol.PacketInterceptor)channel.pipeline().get(this.handlerName);
      }
   }

   public void uninjectChannel(Channel channel) {
      if (!this.closed) {
         this.uninjectedChannels.add(channel);
      }

      channel.eventLoop().execute(new Runnable() {
         public void run() {
            channel.pipeline().remove(handlerName);
         }
      });
   }

   public void injectChannel(Channel channel) {
      this.injectChannelInternal(channel);
   }

   private void registerChannelHandler() {
      Object o1 = getMinecraftServer.get(Bukkit.getServer());
      Object o2 = getServerConnection.get(o1);
      boolean b1 = true;
      this.networkManagers = (List)getNetworkMarkers.invoke((Object)null, o2);
      this.createServerChannelHandler();

      for(int i = 0; b1; ++i) {
         List<Object> list = (List)Reflection.getField(o2.getClass(), List.class, i).get(o2);

         for(Iterator iterator = list.iterator(); iterator.hasNext(); b1 = false) {
            Object o3 = iterator.next();
            if (!ChannelFuture.class.isInstance(o3)) {
               break;
            }

            Channel channel = ((ChannelFuture)o3).channel();
            this.serverChannels.add(channel);
            channel.pipeline().addFirst(new ChannelHandler[]{this.serverChannelHandler});
         }
      }

   }

   protected String getHandlerName() {
      return String.valueOf((new StringBuilder()).append("tiny-").append(this.plugin.getName()).append("-").append(ID.incrementAndGet()));
   }

   public void sendPacket(Channel channel, Object object) {
      channel.pipeline().writeAndFlush(object);
   }

   public Channel getChannel(Player player) {
      Channel channel = (Channel)this.channelLookup.get(player.getName());
      if (channel == null) {
         Object o1 = getConnection.get(getPlayerHandle.invoke(player));
         Object o2 = getManager.get(o1);
         this.channelLookup.put(player.getName(), channel = (Channel)getChannel.get(o2));
      }

      return channel;
   }

   public void receivePacket(Channel channel, Object object) {
      channel.pipeline().context("encoder").fireChannelRead(object);
   }

   public Object onPacketInAsync(Player player, Channel channel, Object object) {
      return object;
   }

   public TinyProtocol(Plugin plugin) {
      this.plugin = plugin;
      this.handlerName = this.getHandlerName();
      this.registerBukkitEvents();

      try {
         this.registerChannelHandler();
         this.registerPlayers(plugin);
      } catch (IllegalArgumentException var3) {
         plugin.getLogger().info("Delaying server channel injection due to late bind.");
         (new BukkitRunnable() {
            public void run() {
               registerChannelHandler();
               registerPlayers(plugin);
               plugin.getLogger().info("Late bind injection successful.");
            }
         }).runTask(plugin);
      }

   }

   private void createServerChannelHandler() {
      this.endInitProtocol = new ChannelInitializer<Channel>() {
         protected void initChannel(Channel channel) throws Exception {
            try {
               synchronized(networkManagers) {
                  if (!closed) {
                     injectChannelInternal(channel);
                  }
               }
            } catch (Exception var5) {
               plugin.getLogger().log(Level.SEVERE, String.valueOf((new StringBuilder()).append("Cannot inject incomming channel ").append(channel)), var5);
            }

         }
      };
      this.beginInitProtocol = new ChannelInitializer<Channel>() {
         protected void initChannel(Channel channel) throws Exception {
            channel.pipeline().addLast(new ChannelHandler[]{endInitProtocol});
         }
      };
      this.serverChannelHandler = new ChannelInboundHandlerAdapter() {
         public void channelRead(ChannelHandlerContext handlerContext, Object o1) throws Exception {
            Channel channel = (Channel)o1;
            channel.pipeline().addFirst(new ChannelHandler[]{beginInitProtocol});
            handlerContext.fireChannelRead(o1);
         }
      };
   }

   public void sendPacket(Player player, Object object) {
      this.sendPacket(this.getChannel(player), object);
   }

   public void receivePacket(Player player, Object object) {
      this.receivePacket(this.getChannel(player), object);
   }

   public void uninjectPlayer(Player player) {
      this.uninjectChannel(this.getChannel(player));
   }

   public Object onPacketOutAsync(Player player, Channel channel, Object object) {
      return object;
   }

   public void injectPlayer(Player player) {
      this.injectChannelInternal(this.getChannel(player)).player = player;
   }

   public boolean hasInjected(Channel channel) {
      return channel.pipeline().get(this.handlerName) != null;
   }

   static {
      getMinecraftServer = Reflection.getField((String)"{obc}.CraftServer", minecraftServerClass, 0);
      getServerConnection = Reflection.getField((Class)minecraftServerClass, serverConnectionClass, 0);
      getNetworkMarkers = Reflection.getTypedMethod(serverConnectionClass, (String)null, List.class, serverConnectionClass);
      PACKET_LOGIN_IN_START = Reflection.getMinecraftClass("PacketLoginInStart");
      getGameProfile = Reflection.getField((Class)PACKET_LOGIN_IN_START, GameProfile.class, 0);
   }

   private void registerPlayers(Plugin plugin) {
      Iterator iterator = plugin.getServer().getOnlinePlayers().iterator();

      while(iterator.hasNext()) {
         Player player = (Player)iterator.next();
         this.injectPlayer(player);
      }

   }

   private void unregisterChannelHandler() {
      if (this.serverChannelHandler != null) {
         Iterator iterator = this.serverChannels.iterator();

         while(iterator.hasNext()) {
            Channel channel = (Channel)iterator.next();
            ChannelPipeline pipeLine = channel.pipeline();
            channel.eventLoop().execute(new Runnable() {
               public void run() {
                  try {
                     pipeLine.remove(serverChannelHandler);
                  } catch (NoSuchElementException var2) {
                  }

               }
            });
         }

      }
   }

   public boolean hasInjected(Player player) {
      return this.hasInjected(this.getChannel(player));
   }

   public void close() {
      if (!this.closed) {
         this.closed = true;
         Iterator iterator = this.plugin.getServer().getOnlinePlayers().iterator();

         while(iterator.hasNext()) {
            Player player = (Player)iterator.next();
            this.uninjectPlayer(player);
         }

         HandlerList.unregisterAll(this.listener);
         this.unregisterChannelHandler();
      }

   }

   private void registerBukkitEvents() {
      this.listener = new Listener() {
         @EventHandler(
            priority = EventPriority.LOWEST
         )
         public void onPlayerLogin(PlayerLoginEvent event) {
            if (!closed) {
               Channel channel = getChannel(event.getPlayer());
               if (!uninjectedChannels.contains(channel)) {
                  injectPlayer(event.getPlayer());
               }

            }
         }

         @EventHandler
         public void onPluginDisable(PluginDisableEvent event) {
            if (event.getPlugin().equals(plugin)) {
               close();
            }

         }
      };
      this.plugin.getServer().getPluginManager().registerEvents(this.listener, this.plugin);
   }

   private class PacketInterceptor extends ChannelDuplexHandler {
      
      public volatile Player player;

      
      PacketInterceptor(Object object) {
         this();
      }

      private void handleLoginStart(Channel channel, Object object) {
         if (TinyProtocol.PACKET_LOGIN_IN_START.isInstance(object)) {
            GameProfile profile = (GameProfile)TinyProtocol.getGameProfile.get(object);
            TinyProtocol.this.channelLookup.put(profile.getName(), channel);
         }

      }

      public void channelRead(ChannelHandlerContext handlerContext, Object object) throws Exception {
         Channel channel = handlerContext.channel();
         this.handleLoginStart(channel, object);

         try {
            object = TinyProtocol.this.onPacketInAsync(this.player, channel, object);
         } catch (Exception var5) {
            TinyProtocol.this.plugin.getLogger().log(Level.SEVERE, "Error in onPacketInAsync().", var5);
         }

         if (object != null) {
            super.channelRead(handlerContext, object);
         }

      }

      private PacketInterceptor() {
      }

      public void write(ChannelHandlerContext handlerContext, Object object, ChannelPromise channelPromise) throws Exception {
         try {
            object = TinyProtocol.this.onPacketOutAsync(this.player, handlerContext.channel(), object);
         } catch (Exception var5) {
            TinyProtocol.this.plugin.getLogger().log(Level.SEVERE, "Error in onPacketOutAsync().", var5);
         }

         if (object != null) {
            super.write(handlerContext, object, channelPromise);
         }

      }
   }
}
