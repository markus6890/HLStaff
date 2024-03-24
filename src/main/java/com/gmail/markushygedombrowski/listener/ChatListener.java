package com.gmail.markushygedombrowski.listener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener{

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if(player.hasPermission("staff.color")) {
            return;
        }
        if(message.contains("&")) {
            event.setCancelled(true);
            player.sendMessage("§cDu må ikke bruge farver i chatten");
            return;
        }
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        StringBuilder sb = new StringBuilder("§7[§d§lCMD§7] ");
        sb.append(event.getPlayer().getName()).append(": ").append(event.getMessage());
        Bukkit.broadcast(sb.toString(), "cmd.spy");

    }
}
