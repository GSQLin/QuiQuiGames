package me.gsqlin.qiuqiugames;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InvHub implements InventoryHolder {
    Plugin plugin = me.gsqlin.qiuqiugames.QiuQiuGames.getPlugin(me.gsqlin.qiuqiugames.QiuQiuGames.class);
    private Inventory inv;
    private Class<?> classHolder;
    public InvHub(Inventory inv,Class<?> objects) {
        if (objects!=null) classHolder = objects;
        Inventory xinv = Bukkit.createInventory(this,inv.getType(),inv.getTitle());
        Bukkit.getScheduler().runTask(plugin, () ->{
           for (int i = 0; i < xinv.getSize(); i++){
               ItemStack item = inv.getItem(i);
               if (item != null){
                   if (item.getType() != Material.AIR){
                       xinv.setItem(i,item);
                   }
               }
           }
        });
        this.inv = xinv;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
    public Class<?> getClassHolder() {
        return classHolder;
    }
    public void renameInv(String title){
        Inventory xinv = Bukkit.createInventory(this.inv.getHolder(),this.inv.getType(),title);
        for (int i = 0; i < xinv.getSize(); i++) {
            ItemStack item = this.inv.getItem(i);
            if (item != null) {
                if (item.getType() != Material.AIR) {
                    xinv.setItem(i, item);
                }
            }
        }
        this.inv = xinv;
    }
}
