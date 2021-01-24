package dev.panda.hub.manager;

import java.util.Set;
import java.util.UUID;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class RainbowManager extends BukkitRunnable {
   
   private int g;
   private int sixth;
   private int fourth;
   private int fifth;
   private int first;
   private int second;
   private int r;
   private int third;
   private Player player;
   private Set<UUID> players;
   private int b;

   public ItemStack helmet(int v1, int v2, int v3) {
      ItemStack stack = new ItemStack(Material.LEATHER_HELMET, 1);
      LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
      meta.setColor(Color.fromBGR(v1, v2, v3));
      stack.setItemMeta(meta);
      return stack;
   }

   public RainbowManager(Set<UUID> players, Player player, int b, int g, int r, int first, int second, int third, int fourth, int fifth, int sixth) {
      this.player = player;
      this.b = b;
      this.g = g;
      this.r = r;
      this.first = first;
      this.second = second;
      this.third = third;
      this.fourth = fourth;
      this.fifth = fifth;
      this.sixth = sixth;
      this.players = players;
   }

   public ItemStack chestplate(int v1, int v2, int v3) {
      ItemStack stack = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
      LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
      meta.setColor(Color.fromBGR(v1, v2, v3));
      stack.setItemMeta(meta);
      return stack;
   }

   public void run() {
      if (!this.players.contains(this.player.getUniqueId())) {
         this.cancel();
      } else {
         if (this.first <= 17) {
            ++this.first;
            this.g = (this.first - 1) * 15;
            this.player.getInventory().setHelmet(this.helmet(this.b, this.g, this.r));
            this.player.getInventory().setChestplate(this.chestplate(this.b, this.g, this.r));
            this.player.getInventory().setLeggings(this.leggings(this.b, this.g, this.r));
            this.player.getInventory().setBoots(this.boots(this.b, this.g, this.r));
         } else if (this.second <= 17) {
            ++this.second;
            this.r = 255 - 15 * (this.second - 1);
            this.player.getInventory().setHelmet(this.helmet(this.b, this.g, this.r));
            this.player.getInventory().setChestplate(this.chestplate(this.b, this.g, this.r));
            this.player.getInventory().setLeggings(this.leggings(this.b, this.g, this.r));
            this.player.getInventory().setBoots(this.boots(this.b, this.g, this.r));
         } else if (this.third <= 17) {
            ++this.third;
            this.b = (this.third - 1) * 15;
            this.player.getInventory().setHelmet(this.helmet(this.b, this.g, this.r));
            this.player.getInventory().setChestplate(this.chestplate(this.b, this.g, this.r));
            this.player.getInventory().setLeggings(this.leggings(this.b, this.g, this.r));
            this.player.getInventory().setBoots(this.boots(this.b, this.g, this.r));
         } else if (this.fourth <= 17) {
            ++this.fourth;
            this.g = 255 - 15 * (this.fourth - 1);
            this.player.getInventory().setHelmet(this.helmet(this.b, this.g, this.r));
            this.player.getInventory().setChestplate(this.chestplate(this.b, this.g, this.r));
            this.player.getInventory().setLeggings(this.leggings(this.b, this.g, this.r));
            this.player.getInventory().setBoots(this.boots(this.b, this.g, this.r));
         } else if (this.fifth <= 17) {
            ++this.fifth;
            this.r = (this.fifth - 1) * 15;
            this.player.getInventory().setHelmet(this.helmet(this.b, this.g, this.r));
            this.player.getInventory().setChestplate(this.chestplate(this.b, this.g, this.r));
            this.player.getInventory().setLeggings(this.leggings(this.b, this.g, this.r));
            this.player.getInventory().setBoots(this.boots(this.b, this.g, this.r));
         } else if (this.sixth <= 17) {
            ++this.sixth;
            this.b = 255 - 15 * (this.sixth - 1);
            this.player.getInventory().setHelmet(this.helmet(this.b, this.g, this.r));
            this.player.getInventory().setChestplate(this.chestplate(this.b, this.g, this.r));
            this.player.getInventory().setLeggings(this.leggings(this.b, this.g, this.r));
            this.player.getInventory().setBoots(this.boots(this.b, this.g, this.r));
         } else {
            this.first = 0;
            this.second = 0;
            this.third = 0;
            this.fourth = 0;
            this.fifth = 0;
            this.sixth = 0;
         }

      }
   }

   public ItemStack boots(int v1, int v2, int v3) {
      ItemStack stack = new ItemStack(Material.LEATHER_BOOTS, 1);
      LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
      meta.setColor(Color.fromBGR(v1, v2, v3));
      stack.setItemMeta(meta);
      return stack;
   }

   public ItemStack leggings(int v1, int v2, int v3) {
      ItemStack stack = new ItemStack(Material.LEATHER_LEGGINGS, 1);
      LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
      meta.setColor(Color.fromBGR(v1, v2, v3));
      stack.setItemMeta(meta);
      return stack;
   }
}
