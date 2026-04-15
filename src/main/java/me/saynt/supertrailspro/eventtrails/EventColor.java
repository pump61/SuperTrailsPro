package me.saynt.supertrailspro.eventtrails;

import me.saynt.supertrailspro.trails.TrailType;

public class EventColor extends TrailEvent {
   public HeadColor color;

   public EventColor(int var1, String var2, HeadColor var3, Rarity var4) {
      super(var1, var2, var3.getItemStack(), var4);
      this.color = var3;
   }

   public HeadColor getColor() {
      return this.color;
   }

   public TrailType getType() {
      return TrailType.Event_Color;
   }

   public int getDataValue() {
      return this.id - 410;
   }
}
