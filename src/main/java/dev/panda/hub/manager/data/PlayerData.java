package dev.panda.hub.manager.data;

import dev.panda.hub.files.DataFile;
import java.util.UUID;

public class PlayerData {
   
   private final UUID uuid;

   public void setColorChat(String input) {
      DataFile.getConfig().set(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".COLOR-CHAT")), input);
      DataFile.getConfig().saveAll();
   }

   public void setGadget(String input) {
      DataFile.getConfig().set(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".GADGET")), input);
      DataFile.getConfig().saveAll();
   }

   public void setTrail(String input) {
      DataFile.getConfig().set(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".TRAIL")), input);
      DataFile.getConfig().saveAll();
   }

   public String getColorChat() {
      return DataFile.getConfig().getString(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".COLOR-CHAT")));
   }

   public void setColorMessage(String input) {
      DataFile.getConfig().set(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".COLOR-MESSAGE")), input);
      DataFile.getConfig().saveAll();
   }

   public String getGadget() {
      return DataFile.getConfig().getString(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".GADGET")));
   }

   public PlayerData(UUID uuid) {
      this.uuid = uuid;
   }

   public String getTrail() {
      return DataFile.getConfig().getString(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".TRAIL")));
   }

   public String getTag() {
      return DataFile.getConfig().getString(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".TAG")));
   }

   public String getColorMessage() {
      return DataFile.getConfig().getString(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".COLOR-MESSAGE")));
   }

   public void setTag(String input) {
      DataFile.getConfig().set(String.valueOf((new StringBuilder()).append("DATA.").append(this.uuid).append(".TAG")), input);
      DataFile.getConfig().saveAll();
   }

   public static enum ErrorType {
      
      INVALID_IP,
      
      API_KEY_NOT_VALID,
      
      VALID,
      
      INVALID_LICENSE,
      
      EXPIRED,
      
      PAGE_ERROR,
      
      INVALID_PLUGIN_NAME;
   }
}
