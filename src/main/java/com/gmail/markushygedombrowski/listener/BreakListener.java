package com.gmail.markushygedombrowski.listener;

import com.gmail.markushygedombrowski.staffprofile.StaffProfiles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {
    private StaffProfiles staffProfiles;

    public BreakListener(StaffProfiles staffProfiles) {
        this.staffProfiles = staffProfiles;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent blockBreakEvent) {
        Player player = blockBreakEvent.getPlayer();
        if(staffProfiles.getStaffProfile(player.getUniqueId()) == null) {
            return;
        }
        if (staffProfiles.getStaffProfile(player.getUniqueId()).isBuildprotect()) {
            player.sendMessage("§cBuildprotect er aktiveret!");
            blockBreakEvent.setCancelled(true);

        }
    }
}
