package org.yauhyeah.fnsshards.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.yauhyeah.fnsshards.FNSShards;

public class StringUtils {
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String getPrefix() {
        return colorize(FNSShards.getMain().getConfig().getString("PluginPrefix"));
    }
    public static void sendActionText(Player p, String msg) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(colorize(msg)));
    } public static String generatePercentageBar(char barChar, double current, double maximum, String complete, String incomplete) {
        StringBuilder bar = new StringBuilder();
        if (maximum == 0.0D)
            maximum = current;
        double percentage = current / maximum * 100.0D;
        long percentageRounded = Math.round(percentage) / 2L;
        complete = ChatColor.translateAlternateColorCodes('&', complete);
        incomplete = ChatColor.translateAlternateColorCodes('&', incomplete);
        for (int i = 0; i < 50; i++) {
            bar.append((percentageRounded > 0L) ? complete : incomplete).append(barChar);
            if (percentageRounded > 0L)
                percentageRounded--;
        }
        return bar.toString();
    }
}
