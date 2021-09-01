package kaile.plugin.binditem;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] arg) {
        Player playername=(Player) commandSender;
        if(playername.getInventory().getItemInMainHand().getType()== Material.AIR)
        {
            commandSender.sendMessage("请勿空手使用此命令");
            return true;
        }
        List<String> itemlore=new ArrayList();
        ItemStack itemStack=playername.getItemInHand();
        ItemMeta id=itemStack.getItemMeta();
        String itemstringlore="";
        String bind_name="【"+playername.getName()+"】"+" 专属";
        if(arg.length>=1){
            switch (arg[0]) {
                case "add":
                    if (playername.getInventory().getItemInMainHand().getType() != Material.AIR) {
                        if (id.hasLore()) {
                            itemlore.addAll(id.getLore());
                            itemstringlore = String.join(",", itemlore);
                        }
                        if (Function.IsBind(itemstringlore)) {
                            commandSender.sendMessage("该物品已经被绑定，你无法再次绑定");
                            return true;
                        }
                        if (Function.IsWillBind(itemstringlore)) {
                            itemlore.remove("待绑定");
                            itemlore.add(bind_name);
                            id.setLore(itemlore);
                            itemStack.setItemMeta(id);
                            commandSender.sendMessage("绑定成功");
                            return true;
                        }
                        if (commandSender.isOp()) {
                            //判断是否有待绑定


                            if (Function.IsWillBind(itemstringlore)) {
                                itemlore.remove("待绑定");
                                id.setLore(itemlore);
                                itemStack.setItemMeta(id);
                            }
                            itemlore.add(bind_name);
                            id.setLore(itemlore);
                            itemStack.setItemMeta(id);
                            commandSender.sendMessage("绑定成功");
                            return true;
                        }
                        commandSender.sendMessage("物品暂不支持绑定！");
                        return true;
                    }
                    commandSender.sendMessage("请勿空手使用此命令");
                    return true;


                case "unbind":
                    if (commandSender.isOp()) {
                        if (playername.getInventory().getItemInMainHand().getType() != Material.AIR) {
                            if (id.hasLore()) {
                                itemlore.addAll(id.getLore());
                                itemstringlore = String.join(",", itemlore);
                            }
                            if (Function.IsBind(itemstringlore)) {
                                itemlore.remove(bind_name);
                                id.setLore(itemlore);
                                itemStack.setItemMeta(id);
                                commandSender.sendMessage("物品解除绑定成功！");
                                return true;
                            }
                            if (Function.IsWillBind(itemstringlore)) {
                                itemlore.remove("待绑定");
                                id.setLore(itemlore);
                                itemStack.setItemMeta(id);
                                commandSender.sendMessage("物品解除绑定成功！");
                                return true;
                            }
                            commandSender.sendMessage("物品暂不支持绑定！");
                            return true;
                        }
                        commandSender.sendMessage("请勿空手使用此命令");
                        return true;
                    }
                    commandSender.sendMessage("你没有权限这么做！");
                    return true;

                case "to":

                    if (commandSender.isOp()) {
                        if (arg.length == 2) {
                            String WillBindname = "【" + arg[1] + "】" + " 专属";
                            if (playername.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                if (id.hasLore()) {
                                    itemlore.addAll(id.getLore());
                                    itemstringlore = String.join(",", itemlore);
                                }
                                if (Function.IsBind(itemstringlore)) {
                                    commandSender.sendMessage("该物品已经被绑定，你无法再次绑定");
                                    return true;
                                }
                                itemlore.add(WillBindname);
                                id.setLore(itemlore);
                                itemStack.setItemMeta(id);
                                commandSender.sendMessage("绑定成功");
                                return true;
                            }
                            commandSender.sendMessage("请勿空手使用此命令");
                            return true;
                        }
                    }
                    commandSender.sendMessage("你没有权限这么做！");
                    return true;
                case "wb":
                    if (playername.getInventory().getItemInMainHand().getType() != Material.AIR) {
                        if (id.hasLore()) {
                            itemlore.addAll(id.getLore());
                            itemstringlore = String.join(",", itemlore);
                        }
                        if (commandSender.isOp()) {
                            if (Function.IsBind(itemstringlore)) {
                                commandSender.sendMessage("物品已经被绑定，你可以先解除绑定！");
                                return true;
                            }
                            if (Function.IsWillBind(itemstringlore)) {
                                commandSender.sendMessage("物品已经被绑定，你可以先解除绑定！");
                                return true;
                            }
                            itemlore.add("待绑定");
                            id.setLore(itemlore);
                            itemStack.setItemMeta(id);
                            commandSender.sendMessage("物品待绑定成功");
                            return true;
                        }
                        commandSender.sendMessage("你没有权限这么做！");
                    }
            }
        }
        return true;
    }
}

