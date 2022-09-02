package me.gsqlin.qiuqiugames.GuiHub;

import me.gsqlin.qiuqiugames.InvHub;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game1 extends InvHub {
    Plugin plugin = me.gsqlin.qiuqiugames.QiuQiuGames.getPlugin(me.gsqlin.qiuqiugames.QiuQiuGames.class);
    public static List<Integer> allint = new ArrayList<>();
    public static Integer[] ClickSlot = new Integer[]{3,4,5,12,13,14,21,22,23};
    public Player player;

    //用于玩家关闭的那一瞬间看是不是时间到了(再计时器中写入修改这个参数为true)
    public boolean invalid = false;

    //用来整理是玩家关闭的容器还是插件给玩家换界面的时候的
    //默认true,如果是插件操作关闭的话就改成false执行后会重新打开,再事件里面弄一个打开后默认把这个调整会true
    public boolean isplayerclose = true;

    public Game1(Inventory inv,Player player) {
        super(inv,me.gsqlin.qiuqiugames.GuiHub.Game1.class);
        inv = this.getInventory();

        List<Integer> list = new ArrayList<>();
        for (int x = 0;x<3;x++){
            int i;
            i = list.size() > 0 ? random3x3Int(list):random3x3Int(null);
            inv.setItem(i,canClickItem());
            list.add(i);
        }
        for (int i = 0;i < inv.getSize();i++){
            if (!Arrays.asList(ClickSlot).contains(i)){
                inv.setItem(i,notCanClickItem());
            }
        }
        this.player = player;
    }

    public Integer random3x3Int(List<Integer> i) {
        List<Integer> xList = Arrays.asList(ClickSlot);
        List<Integer> mubiao = new ArrayList<>();
        if (i != null){
            for (int x:xList){
                if (!i.contains(x)){
                    mubiao.add(x);
                }
            }
        }else{
            mubiao = xList;
        }
        return mubiao.get((int)(Math.random()*mubiao.size()));
    }
    public ItemStack canClickItem(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1, (short) 3);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§3点我");
        item.setItemMeta(itemMeta);
        return item;
    }
    public ItemStack notCanClickItem(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1, (short) 15);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§7空白处");
        item.setItemMeta(itemMeta);
        return item;
    }
    //随机一个处一个没有物品的3x3的位置
    public static Integer randomHaded3x3Int(Inventory inv) {
        List<Integer> xList = Arrays.asList(ClickSlot);
        List<Integer> mubiao = new ArrayList<>();
        for (Integer i : xList){
            if (inv.getItem(i) == null){
                mubiao.add(i);
            }else if(inv.getItem(i).getType().equals(Material.AIR)){
                mubiao.add(i);
            }
        }
        return mubiao.get((int)(Math.random()*mubiao.size()));
    }

    //获取积分
    public Integer getPoint(){
        return Integer.valueOf(this.getInventory().getTitle());
    }

    public void EndGame(Boolean guo, BukkitRunnable... runnables){
        this.isplayerclose = false;
        if (runnables != null) {
            if (runnables.length > 0) {
                for (BukkitRunnable runnable : runnables) {
                    runnable.runTask(plugin);
                }
            }
        }
        //如果guo是true就执行这里面的一下操作,如果不就不执行
        if (!guo) return;
        this.player.closeInventory();
        this.player.sendMessage("§3游戏结束了");
    }
}
