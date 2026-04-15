package me.saynt.supertrailspro.eventtrails;

public enum Rarity {
   COMMON("Common", new int[]{401, 402, 403, 404, 411, 412, 413, 414, 421, 422, 423, 431}),
   RARE("Rare", new int[]{405, 406, 415, 416, 424, 425, 432, 433}),
   EPIC("Epic", new int[]{407, 417, 426, 427, 434, 435, 436, 437});

   String s;
   int[] i;

   private Rarity(String var3, int[] var4) {
      this.s = var3;
      this.i = var4;
   }

   public String getName() {
      return this.s;
   }

   public int[] getInts() {
      return this.i;
   }
}
