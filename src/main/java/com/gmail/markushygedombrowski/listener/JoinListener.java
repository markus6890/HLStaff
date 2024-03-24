package com.gmail.markushygedombrowski.listener;
import com.gmail.markushygedombrowski.staffprofile.StaffProfile;
import com.gmail.markushygedombrowski.staffprofile.StaffProfiles;
import com.gmail.markushygedombrowski.utils.NameTagEditor;
import com.gmail.markushygedombrowski.vanish.VanishCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinListener implements Listener{
    private VanishCommand vanishCommand;
    private StaffProfiles staffProfiles;
    private NameTagEditor nameTagEditor;

    public JoinListener(VanishCommand vanishCommand, StaffProfiles staffProfiles, NameTagEditor nameTagEditor) {
        this.vanishCommand = vanishCommand;
        this.staffProfiles = staffProfiles;
        this.nameTagEditor = nameTagEditor;
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission("staff")) {
            StaffProfile staffProfile = staffProfiles.getStaffProfile(player.getUniqueId());
            if(staffProfile == null) {
                boolean commandSpy = false;
                boolean buildprotect = false;
                staffProfile = new StaffProfile(player.getName(), player.getUniqueId(), buildprotect, commandSpy, false);
                staffProfiles.addStaffProfile(staffProfile);
            }
        }

        if(!vanishCommand.getVanishedPlayers().isEmpty()) {
            if(!player.hasPermission("vanish")) {
                for(UUID uuid : vanishCommand.getVanishedPlayers()) {
                    player.hidePlayer(Bukkit.getPlayer(uuid));
                }
            }

        }
        if(setofflineStaffToVanish(player)) {
            event.setJoinMessage(null);
            return;
        }
        if(Bukkit.getOfflinePlayer(player.getUniqueId()).hasPlayedBefore()) {
            event.setJoinMessage("§8[§a+§8] §7" + player.getName());
            return;
        }
        event.setJoinMessage("§7Velkommen Til §9§lHub§2§lLolLand §7Prison §c" + player.getName());

    }

    private boolean setofflineStaffToVanish(Player player) {
        if(!player.hasPermission("staff")) {
            return false;
        }
        StaffProfile staffProfile = staffProfiles.getStaffProfile(player.getUniqueId());
        if(staffProfile.isVanished()) {
            vanishCommand.getVanishedPlayers().add(player.getUniqueId());
            for(Player p : player.getServer().getOnlinePlayers()) {
                if(!p.hasPermission("vanish")) {
                    p.hidePlayer(player);
                    continue;
                }
                if (player.hasPermission("vanish.super") && !p.hasPermission("vanish.super")) {
                    p.hidePlayer(player);
                    continue;
                }
                p.sendMessage("§4[Staff] §r" + player.getDisplayName() + "§7 joinede serveren i §6Vanish");

            }
            nameTagEditor.changeTapName(player);

            player.setFlying(true);
            return true;
        }
        return false;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        vanishCommand.getVanishedPlayers().remove(player.getUniqueId());
        if(player.hasPermission("staff")) {
            StaffProfile staffProfile = staffProfiles.getStaffProfile(player.getUniqueId());
            if(staffProfile.isVanished()) {
                event.setQuitMessage(null);
                return;
            }
        }
        event.setQuitMessage("§8[§c-§8] §7" + player.getName());

    }


}
