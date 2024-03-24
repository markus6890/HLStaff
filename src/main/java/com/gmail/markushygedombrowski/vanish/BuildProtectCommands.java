package com.gmail.markushygedombrowski.vanish;

import com.gmail.markushygedombrowski.staffprofile.StaffProfile;
import com.gmail.markushygedombrowski.staffprofile.StaffProfiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildProtectCommands implements CommandExecutor {
    private StaffProfiles staffProfiles;

    public BuildProtectCommands(StaffProfiles staffProfiles) {
        this.staffProfiles = staffProfiles;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))
            return true;
        Player player = (Player) commandSender;
        if(!player.hasPermission("buildprotect")) {
            player.sendMessage("§cDu har ikke permission til at bruge denne kommando!");
            return true;
        }
        StaffProfile staffProfile = staffProfiles.getStaffProfile(player.getUniqueId());
        staffProfile.setBuildprotect(!staffProfile.isBuildprotect());
        player.sendMessage("§7Buildprotect er nu " + (staffProfile.isBuildprotect() ? "§aaktiveret" : "§cdeaktiveret"));

        return true;
    }
}
