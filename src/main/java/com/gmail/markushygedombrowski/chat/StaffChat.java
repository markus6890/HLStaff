package com.gmail.markushygedombrowski.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!sender.hasPermission("staff.chat")) {
            sender.sendMessage("§cDet har du ikke permission til!");
            return true;
        }

        Player p = (Player) sender;
        if(args.length == 0) {
            p.sendMessage("§7§l----------§c§lSTAFF§7§l----------");
            p.sendMessage("§c /sc §7<text>");
            p.sendMessage("§7§l----------§c§lSTAFF§7§l----------");
            return true;
        }
        StringBuilder message = new StringBuilder("§4[Staff Chat]: §r" + p.getDisplayName());
        for(String text : args) {
            message.append(" §7").append(text);
        }
        Bukkit.broadcast(message.toString(),"staff.chat");
        return true;
    }
}
