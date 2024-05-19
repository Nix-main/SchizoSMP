package org.nixfrost.schizo.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.nixfrost.schizo.util.Util;

import static org.nixfrost.schizo.util.Util.version;
import static org.nixfrost.schizo.util.Util.getInstance;

public class maincmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length > 0){
            switch (args[0]){
                case "info":{
                    Util.sendColMsg(sender, "&c-------SchizoSMP Plugin-------\n&cAuthors: Nix, Frosty\n&cVersion: %s", version);
                    break;
                }
                case "reload":{
                    Util.sendColMsg(sender, "&eConfig reloaded");
                    getInstance().reloadConfig();
                }
                case "disable":{
                    Util.sendColMsg(sender, "&eDisabling...restart server to re-enable");
                    Bukkit.getPluginManager().disablePlugin(getInstance());
                }
            }
        }
        return true;
    }
}
