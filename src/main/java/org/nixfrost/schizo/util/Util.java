package org.nixfrost.schizo.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.nixfrost.schizo.SchizoSMP;

import java.util.regex.Pattern;


@SuppressWarnings("deprecation")
public class Util {
    public static final String version = Util.getInstance().getDescription().getVersion();
    public static final String mcver = Bukkit.getServer().getMinecraftVersion();
    public static final String bukkitver = Bukkit.getBukkitVersion();

    public static SchizoSMP getInstance(){
        return SchizoSMP.getPlugin(SchizoSMP.class);
    }

    private static final String PREFIX = "&7[&bSchizo&3SMP&7] ";
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f\\d]){6}>");

    public static String getColString(String string) {
        string = HEX_PATTERN.matcher(string).replaceAll("");
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendColMsg(CommandSender receiver, String format, Object... objects) {
        receiver.sendMessage(getColString(String.format(format, objects)));
    }

    public static void log(String format, Object... objects) {
        String log = String.format(format, objects);
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX + log));
    }
}
