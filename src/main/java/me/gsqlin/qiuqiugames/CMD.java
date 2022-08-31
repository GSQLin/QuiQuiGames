package me.gsqlin.qiuqiugames;

import me.gsqlin.qiuqiugames.GuiHub.Game1;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD implements CommandExecutor {
    String[] help = new String[]{
            "=======HELP========"
    };


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1){
            sender.sendMessage("help代写");
        }else{
            if (args[0].equalsIgnoreCase("game1")){
                if (!(sender instanceof Player)) return false;
                Player player = (Player) sender;
                Game1 game1 = new Game1(Bukkit.createInventory(player,3*9,"0"));
                player.openInventory(game1.getInventory());
            }
        }
        return false;
    }
}
