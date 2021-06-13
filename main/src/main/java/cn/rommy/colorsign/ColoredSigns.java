package cn.rommy.colorsign;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import cn.rommy.colorsign.command.CommandEditSign;
import cn.rommy.colorsign.listener.ListenerHexColors;
import cn.rommy.colorsign.listener.ListenerLegacyColors;
import cn.rommy.colorsign.utility.LegacyColorUtility;
import cn.rommy.colorsign.utility.VersionUtility;

public class ColoredSigns extends JavaPlugin {
    @Override
    public void onLoad() {
        saveDefaultConfig();
    }
    
    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();
        ListenerLegacyColors listenerLegacyColors = new ListenerLegacyColors(this);
        manager.registerEvents(listenerLegacyColors, this);
        
        int minorVersion = VersionUtility.getMinorVersion();
        if(minorVersion >= 16) {
            ListenerHexColors listenerHexColors = new ListenerHexColors(this);
            manager.registerEvents(listenerHexColors, this);
        }
    
        CommandEditSign commandEditSign = new CommandEditSign(this);
        PluginCommand pluginCommand = getCommand("edit-sign");
        pluginCommand.setExecutor(commandEditSign);
        pluginCommand.setTabCompleter(commandEditSign);
    
        FileConfiguration config = getConfig();
        if(config.getBoolean("options.broadcast startup")) {
            String message = LegacyColorUtility.replaceAll('&', "&2Colored Signs are now enabled!");
            Bukkit.broadcastMessage(message);
        }
    }
    
    @Override
    public void onDisable() {
        FileConfiguration config = getConfig();
        if(config.getBoolean("options.broadcast startup")) {
            String message = LegacyColorUtility.replaceAll('&', "&4Colored Signs are now disabled!");
            Bukkit.broadcastMessage(message);
        }
    }

    public void debug(String message) {
        FileConfiguration config = getConfig();
        if(!config.getBoolean("options.debug")) return;

        Logger logger = getLogger();
        logger.info("[Debug] " + message);
    }
}