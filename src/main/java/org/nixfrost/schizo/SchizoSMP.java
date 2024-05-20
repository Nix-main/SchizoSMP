package org.nixfrost.schizo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.nixfrost.schizo.commands.maincmd;
import org.nixfrost.schizo.listeners.SchizoPower;
import org.nixfrost.schizo.util.Util;

import static org.nixfrost.schizo.util.Util.version;
import static org.nixfrost.schizo.SchizoPluginManager.canEnable;


@SuppressWarnings("deprecation")
public final class SchizoSMP extends JavaPlugin {

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        if (!canEnable()){
            Bukkit.getPluginManager().disablePlugin(this);
        }
        getCommand("schizo").setExecutor(new maincmd());
        Bukkit.getPluginManager().registerEvents(new SchizoPower(), this);
        saveDefaultConfig();
        Util.log("&aSuccessfully enabled v%s &7in&b %.2f seconds", version, (float) (System.currentTimeMillis() - start) / 1000);
    }

    @Override
    public void onDisable() {
        long start = System.currentTimeMillis();
        saveConfig();
        Util.log("&aSuccessfully disabled v%s &7in&b %.2f seconds", version, (float) (System.currentTimeMillis() - start) / 1000);
    }
}
