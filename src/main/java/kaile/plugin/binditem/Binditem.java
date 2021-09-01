package kaile.plugin.binditem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public final class Binditem extends JavaPlugin implements Listener {

    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginCommand("bind").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(this,this);
        this.getLogger().info("加载完毕！");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("卸载完毕！");
    }
    @EventHandler
    //丢物品事件
    public void DropItem(PlayerDropItemEvent e)
    {
        List<String> itemlore=new ArrayList();
        ItemStack item=e.getItemDrop().getItemStack();
        if(!item.hasItemMeta())
        {
            return;
        }
        ItemMeta id=item.getItemMeta();
        if(id==null){
            return;
    }
        if(!id.hasLore())
        {
            return;
        }
        itemlore.addAll(id.getLore());
        String Itemlorestring=String.join(",",itemlore);
        if(id.hasLore()){
            if(Function.IsBind(Itemlorestring)){
                if(!e.getPlayer().isOp()){
                    if(Function.GetBindName(Itemlorestring).equals(e.getPlayer().getName())){
                        e.getPlayer().sendMessage("绑定物品请勿随意丢弃！");
                        e.setCancelled(true);
                    }else{
                        e.getPlayer().sendMessage("请将物品归还于:"+Function.GetBindName(Itemlorestring));
                    }
                }else{
                    e.getPlayer().sendMessage("op忽略限制");
                }
            }
        }
    }
    //end
    @EventHandler
    //格子事件
    public void BoxItem(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        List<String>itemlore=new ArrayList<>();
        //防止乱点
        if (item == null){

            return;
    }
        if(!item.hasItemMeta())
        {
            return;
        }
        ItemMeta id=item.getItemMeta();
        if(id==null)
        {
            return;
        }
        if(!id.hasLore())
        {
          return;
        }
        if(id.getLore()==null){
            return;
        }
        itemlore.addAll(id.getLore());
        String ItemString=String.join(",",itemlore);
        if(Function.IsBind(ItemString))
        {
            if(!Function.GetBindName(ItemString).equals(player.getName())){
                if(player.isOp())
                {
                    e.getWhoClicked().sendMessage("op忽略限制");
                    return;
                }
                e.setCancelled(true);
                player.closeInventory();
                e.getWhoClicked().sendMessage("这不是你的物品，请勿操作");
        }
        }

    }
    //end
    @EventHandler
    //捡起物品事件
    public void Pickup(PlayerPickupItemEvent e) {

        ItemStack item =e.getItem().getItemStack();
        List<String> itemlore = new ArrayList<>();
        if (item == null){

            return;
        }
        if (!item.hasItemMeta()) {
            return;
        }
        ItemMeta id=item.getItemMeta();
        if(id==null){
            return;
        }
        if(!id.hasLore())
        {
           return;
        }
        if(id.getLore()==null)
        {
            return;
        }
        itemlore.addAll(id.getLore());
        String ItemString=String.join(",",itemlore);
        if(Function.IsBind(ItemString)){
            if(!Function.GetBindName(ItemString).equals(e.getPlayer().getName())) {
                if (e.getPlayer().isOp()) {
                    e.getPlayer().sendMessage("OP忽略限制！");
                    return;
                }
                e.setCancelled(true);
                e.getPlayer().sendMessage("这不是你的物品，请勿捡起！");
            }
        }

    }
        //end

    @EventHandler
    //左右键事件
    public void Interactive (PlayerInteractEvent e){
        Player player=e.getPlayer();
        if(player.isOp()){
            return;
        }
        ItemStack item =e.getItem();
        if (item == null){

            return;
        }
        List<String> itemlore = new ArrayList<>();
        if (!item.hasItemMeta()) {
            return;
        }
        ItemMeta id=item.getItemMeta();
        if(id==null){
            return;
        }
        if(!id.hasLore())
        {
            return;
        }
        if(id.getLore()==null)
        {
            return;
        }
        itemlore.addAll(id.getLore());
        String ItemString=String.join(",",itemlore);
        if(Function.IsBind(ItemString)){
            if(!Function.GetBindName(ItemString).equals(player.getName()))
            {
                e.setCancelled(true);
                player.sendMessage("这不是你的物品！");
                return;
            }
        }
        if(Function.IsWillBind(ItemString)){
            itemlore.remove("待绑定");
            itemlore.add("【"+player.getName()+"】"+" 专属");
            id.setLore(itemlore);
            item.setItemMeta(id);
            player.sendMessage("绑定成功");
            return;
        }
    }
        //end

    //盔甲架交互
    @EventHandler
    public void armor(PlayerArmorStandManipulateEvent e){
        Player player=e.getPlayer();
        if(player.isOp()){
            return;
        }
        ItemStack item =e.getPlayerItem();

        List<String> itemlore = new ArrayList<>();
        if (!item.hasItemMeta()) {
            return;
        }
        ItemMeta id=item.getItemMeta();
        if(id==null){
            return;
        }
        if(!id.hasLore())
        {
            return;
        }
        if(id.getLore()==null)
        {
            return;
        }
        itemlore.addAll(id.getLore());
        String ItemString=String.join(",",itemlore);
        if(Function.IsBind(ItemString)){
            if(!Function.GetBindName(ItemString).equals(player.getName()))
            {
                e.setCancelled(true);
                player.sendMessage("此物品禁止与盔甲架交互");
                return;
            }
        }
        if(Function.IsWillBind(ItemString)){
            e.setCancelled(true);
            player.sendMessage("此物品禁止与盔甲架交互");
            return;
        }
    }


}
