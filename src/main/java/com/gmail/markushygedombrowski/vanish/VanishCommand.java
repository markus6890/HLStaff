package com.gmail.markushygedombrowski.vanish;

import com.gmail.markushygedombrowski.HLStaff;
import com.gmail.markushygedombrowski.staffprofile.StaffProfile;
import com.gmail.markushygedombrowski.staffprofile.StaffProfiles;
import com.gmail.markushygedombrowski.utils.HotBarMessage;
import com.gmail.markushygedombrowski.utils.NameTagEditor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class VanishCommand implements CommandExecutor {

    private List<UUID> vanishedPlayers = new ArrayList<>();
    private HotBarMessage hotBarMessage;
    private HLStaff plugin;
    private StaffProfiles staffProfiles;
    private NameTagEditor nameTagEditor;
    public VanishCommand(HotBarMessage hotBarMessage, HLStaff plugin, StaffProfiles staffProfiles, NameTagEditor nameTagEditor) {
        this.hotBarMessage = hotBarMessage;
        this.plugin = plugin;
        this.staffProfiles = staffProfiles;
        this.nameTagEditor = nameTagEditor;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("vanish")) {
            player.sendMessage("§cDet har du ikke permission til!");
            return true;

        }
        StaffProfile staffProfile = staffProfiles.getStaffProfile(player.getUniqueId());
        if(staffProfile.isVanished()) {
            unVanish(player, staffProfile);
            return true;
        }
        for(Player p : player.getServer().getOnlinePlayers()) {
            if(!p.hasPermission("vanish")) {
                p.hidePlayer(player);
                continue;
            }
            if (player.hasPermission("vanish.super") && !p.hasPermission("vanish.super")) {
                p.hidePlayer(player);
            }
        }
        staffProfile.setVanished(true);
        vanishedPlayers.add(player.getUniqueId());
        player.setAllowFlight(true);
        player.setFlying(true);
        player.sendMessage("§aDu nu er nu vanished!");
        Bukkit.broadcastMessage("§8[§c-§8] §7" + player.getName());
        nameTagEditor.changeTapName(player);



        return true;
    }

    private void unVanish(Player player, StaffProfile staffProfile) {
        for(Player p : player.getServer().getOnlinePlayers()) {
            p.showPlayer(player);
        }
        staffProfile.setVanished(false);

        vanishedPlayers.remove(player.getUniqueId());
        if(!player.hasPermission("fly.allow")) {
            player.setAllowFlight(false);
        }
        player.setFlying(false);
        player.sendMessage("§cDu er ikke længere i vanish!");
        Bukkit.broadcastMessage("§8[§a+§8] §7" + player.getName());
        nameTagEditor.reloadTap(player);
    }

    public CompletableFuture<Void> vanishMessageLoop() {
        return CompletableFuture.runAsync(() -> hotBarMessageLoop());
    }

    private void hotBarMessageLoop() {

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                vanishList();
            }
        }, 1L, 1L);


    }
    public List<UUID> getVanishedPlayers() {
        return vanishedPlayers;
    }

    private void vanishList() {
        if(vanishedPlayers.isEmpty()) {
            return;
        }

            for(UUID uuid : vanishedPlayers) {
                Player player = Bukkit.getPlayer(uuid);
                if(player == null) {
                    vanishedPlayers.remove(uuid);
                    return;
                }
                hotBarMessage.sendActionbar(player,"§6§lDu er i Vanish");
            }

    }
}
