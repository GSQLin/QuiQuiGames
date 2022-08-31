package me.gsqlin.qiuqiugames;

import me.gsqlin.qiuqiugames.Listener.ClickGame1;
import org.bukkit.plugin.java.JavaPlugin;

public final class QiuQiuGames extends JavaPlugin {
    String[] logo = new String[]{
            //logo
            "§3 _____              §b ____                                          " ,
            "§3/\\  __`\\          __§b/\\  _`\\                                        " ,
            "§3\\ \\ \\/\\ \\  __  __/\\_\\§b \\ \\L\\_\\     __      ___ ___ §r     __    ____  " ,
            "§3 \\ \\ \\ \\ \\/\\ \\/\\ \\/\\ \\§b \\ \\L_L   /'__`\\  /' __` __`\\§r  /'__`\\ /',__\\ ",
            "§3  \\ \\ \\\\'\\\\ \\ \\_\\ \\ \\ \\§b \\ \\/, \\/\\ \\L\\.\\_/\\ \\/\\ \\/\\ \\§r/\\  __//\\__, `\\",
            "§3   \\ \\___\\_\\ \\____/\\ \\_\\§b \\____/\\ \\__/.\\_\\ \\_\\ \\_\\ \\_\\§r \\____\\/\\____/",
            "§3    \\/__//_/\\/___/  \\/_/§b\\/___/  \\/__/\\/_/\\/_/\\/_/\\/_/§r\\/____/\\/___/ ",
    };

    @Override
    public void onLoad() {
        // Plugin load logic
        getServer().getConsoleSender().sendMessage(logo);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("quiquigames").setExecutor(new CMD());
        getServer().getPluginManager().registerEvents(new ClickGame1(), this);
        getLogger().info("§3插件已载入~");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
