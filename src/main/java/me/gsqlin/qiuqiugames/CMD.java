package me.gsqlin.qiuqiugames;

import me.gsqlin.qiuqiugames.GuiHub.Game1;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CMD implements CommandExecutor {
    Plugin plugin = me.gsqlin.qiuqiugames.QiuQiuGames.getPlugin(me.gsqlin.qiuqiugames.QiuQiuGames.class);
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
                Game1 game1 = new Game1(Bukkit.createInventory(player,3*9,"0"),player);
                player.openInventory(game1.getInventory());
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,()->{
                    Inventory pinv = Bukkit.getPlayer(player.getUniqueId()).getOpenInventory().getTopInventory();
                    //如果玩家没有界面就不做别的操作了,没有界面就证明是玩家自己已经手动退出
                    if (pinv == null) return;
                    Game1 NowGame1 = (Game1) pinv.getHolder();
                    NowGame1.EndGame(true,new BukkitRunnable() {
                        @Override
                        public void run() {
                            //结束时候要运行的任务
                            player.sendMessage(NowGame1.getPoint()+"");
                        }
                    });
                },60*20);
                //20tick = 1秒
                //这里就是60秒
            }
        }
        return false;
    }
}
