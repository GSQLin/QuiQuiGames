package me.gsqlin.qiuqiugames.Listener;

import me.gsqlin.qiuqiugames.GuiHub.Game1;
import me.gsqlin.qiuqiugames.InvHub;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ClickGame1 implements Listener {
    //实际上多个事件可以写一起的 列如
    /*
    @EventHandler
    public void on第二个事件(PlayerMoveEvent e){
        e.getPlayer().sendMessage("你移动了");
    }
    */

    @EventHandler
    public void onClickGame1(InventoryClickEvent e){
        if (e.getInventory().getHolder() instanceof InvHub){
            InvHub invHub = (InvHub) e.getInventory().getHolder();
            if (invHub.getClassHolder().equals(me.gsqlin.qiuqiugames.GuiHub.Game1.class)){
                if (e.getWhoClicked() instanceof Player){
                e.setCancelled(true);
                Game1 game1 = (Game1) invHub;
                Player p = (Player) e.getWhoClicked();
                    if (game1.canClickItem().equals(e.getCurrentItem())){
                        //是不是玩家点的
                        int x = Game1.randomHaded3x3Int(game1.getInventory());
                        game1.getInventory().setItem(e.getSlot(),null);
                        game1.getInventory().setItem(x,game1.canClickItem());

                        //给玩家发送音效
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0F,1.0F);

                        //点对加1分
                        invHub.renameInv(String.valueOf((Integer.parseInt(game1.getInventory().getTitle()) + 1)));

                        p.openInventory(invHub.getInventory());
                        return;
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK,1.0F,1.0F);
                    //点错扣2分
                    invHub.renameInv(String.valueOf((Integer.parseInt(game1.getInventory().getTitle()) - 2)));

                    p.openInventory(invHub.getInventory());
                }
            }
        }
    }
}
