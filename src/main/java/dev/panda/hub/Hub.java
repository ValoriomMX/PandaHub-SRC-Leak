package dev.panda.hub;

import dev.panda.hub.commands.PandaHubCommand;
import dev.panda.hub.commands.SettingsCommand;
import dev.panda.hub.commands.essentials.BuildCommand;
import dev.panda.hub.commands.essentials.FlyCommand;
import dev.panda.hub.commands.essentials.GamemodeCommand;
import dev.panda.hub.commands.essentials.MessageCommand;
import dev.panda.hub.commands.essentials.ReplyCommand;
import dev.panda.hub.commands.essentials.SkullCommand;
import dev.panda.hub.commands.essentials.TeleportAllCommand;
import dev.panda.hub.commands.essentials.TeleportCommand;
import dev.panda.hub.commands.essentials.TeleportHereCommand;
import dev.panda.hub.commands.network.DiscordCommand;
import dev.panda.hub.commands.network.StoreCommand;
import dev.panda.hub.commands.network.TeamspeakCommand;
import dev.panda.hub.commands.network.TwitterCommand;
import dev.panda.hub.commands.network.WebsiteCommand;
import dev.panda.hub.commands.queue.LeaveQueueCommand;
import dev.panda.hub.commands.queue.PauseQueueCommand;
import dev.panda.hub.commands.queue.PlayQueueCommand;
import dev.panda.hub.commands.spawn.SetSpawnCommand;
import dev.panda.hub.commands.spawn.SpawnCommand;
import dev.panda.hub.commands.toggle.PrivateMessageCommand;
import dev.panda.hub.commands.toggle.ScoreboardCommand;
import dev.panda.hub.commands.toggle.SoundCommand;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.TablistFile;
import dev.panda.hub.listeners.ChatListener;
import dev.panda.hub.listeners.JoinItemListener;
import dev.panda.hub.listeners.PlayerListener;
import dev.panda.hub.listeners.SettingListener;
import dev.panda.hub.listeners.cosmetic.CosmeticListener;
import dev.panda.hub.listeners.cosmetic.GadgetListener;
import dev.panda.hub.listeners.cosmetic.OutfitListener;
import dev.panda.hub.listeners.cosmetic.TrailListener;
import dev.panda.hub.listeners.selector.LobbySelectorListener;
import dev.panda.hub.listeners.selector.ServerSelectorListener;
import dev.panda.hub.manager.PvPManager;
import dev.panda.hub.manager.queue.QueueManager;
import dev.panda.hub.manager.rank.Rank;
import dev.panda.hub.manager.rank.RankManager;
import dev.panda.hub.provider.ScoreboardProvider;
import dev.panda.hub.provider.TablistProvider_v1_7;
import dev.panda.hub.utilities.Bungee;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.Description;
import dev.panda.hub.utilities.Utilities;
import dev.panda.hub.utilities.scoreboard.BoardManager;
import dev.panda.hub.utilities.tablist.v1_7.Tablist_v1_7;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Hub extends JavaPlugin {

   private RankManager rankManager;
   private PvPManager pvpManager;
   private String rankSystem;
   private BoardManager boardManager;
   private QueueManager queueManager;
   private static Hub instance;
   private Chat chat;

   public RankManager getRankManager() {
      return this.rankManager;
   }

   public void onEnable() {
      instance = this;
      if (this.getDescription().getAuthors().contains("Risas") && this.getDescription().getName().equals("PandaHub")) {
         Rank license = new Rank("http://ryzeon.me:9999", ConfigFile.getConfig().getString("LICENSE"), String.valueOf((new StringBuilder()).append(Utilities.getIP()).append(":").append(this.getServer().getPort())), this, "TOPssjRDkpFMyuHYBFCfYtOczILxDgWZIIUttDGCPphMPZqnoZ");
         /* License System:
          * license.createRank();
          */
         /* License System:
          * license.isValidRank()
          */
         if (true) {
            /*
             * License System:
             * CC.translate(license);
             */
            this.loadManagers();
            this.loadCommands();
            this.loadListeners();
            license.registerRankIntegration();
            Bukkit.getConsoleSender().sendMessage(CC.set("&aLoading plugin..."));
            Bukkit.getConsoleSender().sendMessage(CC.set(" "));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append("     &4❤ &6&l").append(Description.getName()).append(" &4❤"))));
            Bukkit.getConsoleSender().sendMessage(CC.set(""));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &eAuthor&7: &f").append(Description.getAuthor()))));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &eVersion&7: &f").append(Description.getVersion()))));
            Bukkit.getConsoleSender().sendMessage(CC.set(String.valueOf((new StringBuilder()).append(" &7➥ &eRank System&7: &f").append(getInstance().getRankSystem()))));
            Bukkit.getConsoleSender().sendMessage(CC.set(" "));
            Bukkit.getConsoleSender().sendMessage(CC.LINE);
            Bukkit.getMessenger().registerOutgoingPluginChannel(getInstance(), "BungeeCord");
            Bukkit.getMessenger().registerIncomingPluginChannel(getInstance(), "BungeeCord", new Bungee());
            Bungee.startPlayerCountTask();
         } else {
            CC.translate();
            Bukkit.shutdown();
            System.exit(0);
         }

      } else {
         for(int i = 0; i < 30; ++i) {
            Bukkit.getConsoleSender().sendMessage(CC.set("&cERROR | The plugin.yml has been change? :)"));
         }

         Bukkit.getPluginManager().disablePlugins();
         Bukkit.shutdown();
      }
   }

   public void setRankSystem(String input) {
      this.rankSystem = input;
   }

   public void loadVault() {
      RegisteredServiceProvider<Chat> provider = this.getServer().getServicesManager().getRegistration(Chat.class);
      if (provider != null) {
         this.chat = (Chat)provider.getProvider();
      }

   }

   public void setPvpManager(PvPManager input) {
      this.pvpManager = input;
   }

   public String getRankSystem() {
      return this.rankSystem;
   }

   public void setQueueManager(QueueManager input) {
      this.queueManager = input;
   }

   public QueueManager getQueueManager() {
      return this.queueManager;
   }

   public Chat getChat() {
      return this.chat;
   }

   public void setChat(Chat input) {
      this.chat = input;
   }

   public void onDisable() {
      instance = null;
      this.chat = null;
      Bukkit.getServer().getOnlinePlayers().forEach((player) -> {
         if (this.getPvpManager().hasPvPMode(player)) {
            this.getPvpManager().getPvpMode().remove(player.getUniqueId());
            player.getInventory().setArmorContents((ItemStack[])null);
         }

         if (OutfitListener.getInstance().getRainbow().contains(player.getUniqueId())) {
            player.getInventory().setArmorContents((ItemStack[])null);
         }

      });
   }

   public static Hub getInstance() {
      return instance;
   }

   private void loadCommands() {
      new PandaHubCommand();
      new DiscordCommand();
      new StoreCommand();
      new TeamspeakCommand();
      new TwitterCommand();
      new WebsiteCommand();
      new FlyCommand();
      new SetSpawnCommand();
      new SpawnCommand();
      new BuildCommand();
      new TeleportCommand();
      new TeleportHereCommand();
      new TeleportAllCommand();
      new GamemodeCommand();
      new MessageCommand();
      new ReplyCommand();
      new SkullCommand();
      new ScoreboardCommand();
      new SoundCommand();
      new PrivateMessageCommand();
      new SettingsCommand();
      new PlayQueueCommand();
      new LeaveQueueCommand();
      new PauseQueueCommand();
   }

   private void loadListeners() {
      if (TablistFile.getConfig().getBoolean("ENABLE")) {
         if (Bukkit.getVersion().contains("1.7")) {
            new Tablist_v1_7(new TablistProvider_v1_7(), this);
         } else {
            Bukkit.getConsoleSender().sendMessage(CC.set("&cNo version of tablist has been loaded as there is only support for 1.7 and 1.8."));
         }
      }

      new ServerSelectorListener();
      new LobbySelectorListener();
      new PlayerListener();
      new ChatListener();
      new JoinItemListener();
      new SettingListener();
      new CosmeticListener();
      new OutfitListener();
      new TrailListener();
      new GadgetListener();
   }

   private void loadManagers() {
      this.boardManager = new BoardManager(new ScoreboardProvider(), 2);
      this.queueManager = new QueueManager();
      this.pvpManager = new PvPManager();
   }

   public void setRankManager(RankManager input) {
      this.rankManager = input;
   }

   public PvPManager getPvpManager() {
      return this.pvpManager;
   }

   public void setBoardManager(BoardManager input) {
      this.boardManager = input;
   }

   public BoardManager getBoardManager() {
      return this.boardManager;
   }
}
