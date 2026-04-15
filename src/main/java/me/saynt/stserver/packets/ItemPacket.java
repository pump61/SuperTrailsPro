package me.saynt.stserver.packets;

import java.io.Serializable;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class ItemPacket implements Serializable {
   private static final long serialVersionUID = 6006559345544043658L;
   public String ItemID;
   public String NewItemID;
   public int amount;
   public int byt;
   public String displayname;
   public List<String> lore;
   public String headimage;
   public boolean head = false;
   public int slot;
   public String command;

   public ItemPacket(ItemStack var1) {
   }

   public ItemPacket(String var1) {
   }

   public ItemPacket() {
   }

   public void createHead(String var1) {
      this.head = true;
   }
}
