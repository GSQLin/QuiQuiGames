package me.gsqlin.qiuqiugames.Listener;

import me.gsqlin.qiuqiugames.GuiHub.Game1;
import me.gsqlin.qiuqiugames.InvHub;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Game1Event implements Listener {
    Plugin plugin = me.gsqlin.qiuqiugames.QiuQiuGames.getPlugin(me.gsqlin.qiuqiugames.QiuQiuGames.class);

    /*
    实际上多个事件可以写一起的 列如

@EventHandler
public void on第二个事件(PlayerMoveEvent e){
    e.getPlayer().sendMessage("你移动了");
}
*/
    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        if (e.getPlayer() instanceof Player){
            Game1 game1 = getGame1(e.getInventory());
            if (game1==null)return;
            game1.isplayerclose = true;
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if (e.getPlayer() instanceof Player){
            Game1 game1 = getGame1(e.getInventory());
            if (game1==null)return;

            //是玩家自己手动关闭的?那就不要让他关掉,不是玩家自己关的是自己手动关闭的那就不鸟他
            if (game1.isplayerclose){
                //失效了就不管了
                if (!game1.invalid){
                    //没失效的时候
                    Bukkit.getScheduler().runTask(plugin, ()-> {
                        e.getPlayer().openInventory(e.getInventory());
                        e.getPlayer().sendMessage("以上是您的分数");
                    });
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (e.getWhoClicked() instanceof Player){
            Game1 game1 = getGame1(e.getInventory());
            if (game1==null)return;
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (game1.canClickItem().equals(e.getCurrentItem())){
                //是不是玩家点的
                int x = Game1.randomHaded3x3Int(game1.getInventory());
                game1.getInventory().setItem(e.getSlot(),null);
                game1.getInventory().setItem(x,game1.canClickItem());

                //给玩家发送音效
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0F,1.0F);

                //点对加1分
                game1.renameInv(String.valueOf((Integer.parseInt(game1.getInventory().getTitle()) + 1)));

                game1.isplayerclose = false;
/*
                下面如果写了关闭inventory的话就会导致鼠标重置到中心//为了玩的舒服所以不写关闭再打开的时候closeevent还是会触发的
                p.closeInventory();
*/
                p.openInventory(game1.getInventory());
                return;
            }else if (game1.quitButtonItem().equals(e.getCurrentItem())){
                game1.EndGame(true, new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.sendMessage("§3你手动退出了,分数不会结算");
                    }
                });
                return;
            }
            p.playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK,1.0F,1.0F);
            //点错扣2分
            game1.renameInv(String.valueOf((Integer.parseInt(game1.getInventory().getTitle()) - 2)));

            game1.isplayerclose = false;
            p.openInventory(game1.getInventory());
        }
    }

    //获取自己插件的Gui获取不到就证明这个gui不是这个插件创建的
    public static Game1 getGame1(Inventory inv) {
        if (inv.getHolder() instanceof InvHub) {
            InvHub invHub = (InvHub) inv.getHolder();
            if (invHub.getClassHolder().equals(me.gsqlin.qiuqiugames.GuiHub.Game1.class)){
                return (Game1) invHub;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
