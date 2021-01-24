package dev.panda.hub.utilities.tablist.v1_7.entry;

import dev.panda.hub.utilities.tablist.v1_7.skin.Skin;
import java.beans.ConstructorProperties;

public class TabEntry {
   
   private int column;
   private int ping = 0;
   private int row;
   private Skin skin;
   private String text;

   @ConstructorProperties({"column", "row", "text", "ping", "skin"})
   public TabEntry(int column, int row, String text, int ping, Skin skin) {
      this.skin = Skin.DEFAULT_SKIN;
      this.column = column;
      this.row = row;
      this.text = text;
      this.ping = ping;
      this.skin = skin;
   }

   public Skin getSkin() {
      return this.skin;
   }

   public TabEntry setSkin(Skin input) {
      this.skin = input;
      return this;
   }

   public int getColumn() {
      return this.column;
   }

   public int getPing() {
      return this.ping;
   }

   public int getRow() {
      return this.row;
   }

   public String getText() {
      return this.text;
   }

   public TabEntry setPing(int input) {
      this.ping = input;
      return this;
   }

   @ConstructorProperties({"column", "row", "text"})
   public TabEntry(int culumn, int row, String text) {
      this.skin = Skin.DEFAULT_SKIN;
      this.column = column;
      this.row = row;
      this.text = text;
   }
}
