package com.gmail.markushygedombrowski.scoreBoard;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DamageIndi implements Listener {




    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("HLStaff"), new Runnable() {
            @Override
            public void run() {
                setDamageindi();

            }
        }, 10L);




    }

    private void setDamageindi() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("showhealth", "health");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName("/ 20§4❤");
        for(Player online : Bukkit.getOnlinePlayers()) {
            if(!online.hasMetadata("NPC")) {
                objective.getScore(online.getName()).setScore((int) online.getHealth());
                online.setScoreboard(objective.getScoreboard());
                online.setHealth(online.getHealth());
            }

        }
    }


}
