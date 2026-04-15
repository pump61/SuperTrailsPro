package me.saynt.supertrailspro;

import org.bukkit.Bukkit;

public class ServerVersion {

   public static ServerVersionsEnum getCurrent() {
      String version = getVersionString();
      // Novo esquema de versão (26.x.x+) — qualquer versão >= 26 é tratada como S261
      try {
         String[] parts = version.split("[^0-9]+");
         if (parts.length > 0 && !parts[0].isEmpty()) {
            int major = Integer.parseInt(parts[0]);
            if (major >= 26) return ServerVersionsEnum.S261;
         }
      } catch (Exception ignored) {}
      // Esquema legado 1.x.x
      if (version.contains("1.21"))  return ServerVersionsEnum.S121;
      if (version.contains("1.20"))  return ServerVersionsEnum.S120;
      if (version.contains("1.19"))  return ServerVersionsEnum.S119;
      if (version.contains("1.18"))  return ServerVersionsEnum.S118;
      if (version.contains("1.17"))  return ServerVersionsEnum.S117;
      if (version.contains("1.16"))  return ServerVersionsEnum.S116;
      if (version.contains("1.15"))  return ServerVersionsEnum.S115;
      if (version.contains("1.14"))  return ServerVersionsEnum.S114;
      if (version.contains("1.13"))  return ServerVersionsEnum.S113;
      if (version.contains("1.12"))  return ServerVersionsEnum.S112;
      if (version.contains("1.11"))  return ServerVersionsEnum.S111;
      if (version.contains("1.10"))  return ServerVersionsEnum.S110;
      if (version.contains("1.9"))   return ServerVersionsEnum.S19;
      return ServerVersionsEnum.S18;
   }

   public static boolean higherThanOrEqual(ServerVersionsEnum target) {
      if (target == null) return true;
      return SuperTrails.currentversion.getVersionID() >= target.getVersionID();
   }

   // Usa Bukkit.getBukkitVersion() — funciona em todas as versões inclusive 1.21
   public static String getVersionString() {
      return Bukkit.getBukkitVersion(); // ex: "1.21.4-R0.1-SNAPSHOT"
   }

   // Mantido para compatibilidade com código legado que chama getVersion()
   public static String getVersion() {
      return getVersionString();
   }
}