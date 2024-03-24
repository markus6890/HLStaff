package com.gmail.markushygedombrowski.utils;

import com.gmail.markushygedombrowski.HLStaff;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

public class NameTagEditor {
    private TabAPI tabAPI;
    private HLStaff plugin;

    public NameTagEditor(TabAPI tabAPI, HLStaff plugin) {
        this.tabAPI = tabAPI;
        this.plugin = plugin;
    }

    public void changeTapName(Player player) {

        TabPlayer tabPlayer = tabAPI.getPlayer(player.getUniqueId());
        assert tabPlayer != null;
        Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

            @Override
            public void run() {
                setTabName(player, tabPlayer);
            }
        }, 10L);



    }

    private void setTabName(Player player, TabPlayer tabPlayer) {
        String vanish = "§7[§4Vanish§7] ";
        if (procent(1)) {
            vanish = "§7[§dVanish§7] ";
        }
        tabAPI.getTabListFormatManager().setPrefix(tabPlayer, vanish);
        player.setMetadata("vanished", new org.bukkit.metadata.FixedMetadataValue(plugin, true));
    }

    public void reloadTap(Player player) {
        TabPlayer tabPlayer = tabAPI.getPlayer(player.getUniqueId());
        assert tabPlayer != null;
        tabAPI.getTabListFormatManager().setPrefix(tabPlayer, tabAPI.getTabListFormatManager().getOriginalPrefix(tabPlayer));
        player.removeMetadata("vanished", plugin);

    }


    public boolean procent(double pro) {
        Random r = new Random();
        double num = r.nextInt(100);
        return num < pro;

    }
}
