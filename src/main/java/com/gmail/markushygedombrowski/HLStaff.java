package com.gmail.markushygedombrowski;

import com.gmail.markushygedombrowski.chat.StaffChat;
import com.gmail.markushygedombrowski.listener.BreakListener;
import com.gmail.markushygedombrowski.listener.ChatListener;
import com.gmail.markushygedombrowski.listener.JoinListener;
import com.gmail.markushygedombrowski.staffprofile.StaffProfiles;
import com.gmail.markushygedombrowski.utils.HotBarMessage;
import com.gmail.markushygedombrowski.utils.NameTagEditor;
import com.gmail.markushygedombrowski.utils.Sql;
import com.gmail.markushygedombrowski.utils.SqlSettings;
import com.gmail.markushygedombrowski.vanish.BuildProtectCommands;
import com.gmail.markushygedombrowski.vanish.VanishCommand;
import me.neznamy.tab.api.TabAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class HLStaff extends JavaPlugin {
    private StaffProfiles staffProfiles;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        SqlSettings sqlSettings = new SqlSettings();
        sqlSettings.load(config);
        Sql sql = new Sql(sqlSettings);
        TabAPI tabAPI = TabAPI.getInstance();
        staffProfiles = new StaffProfiles(sql);
        try {
            staffProfiles.loadStaffProfiles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        NameTagEditor nameTagEditor = new NameTagEditor(tabAPI,this);

        HotBarMessage hotBarMessage = new HotBarMessage();
        VanishCommand vanishCommand = new VanishCommand(hotBarMessage, this, staffProfiles, nameTagEditor);
        getCommand("vanish").setExecutor(vanishCommand);
        BuildProtectCommands buildProtectCommands = new BuildProtectCommands(staffProfiles);
        getCommand("buildprotect").setExecutor(buildProtectCommands);

        BreakListener breakListener = new BreakListener(staffProfiles);
        Bukkit.getPluginManager().registerEvents(breakListener, this);


        JoinListener joinListener = new JoinListener(vanishCommand, staffProfiles, nameTagEditor);
        Bukkit.getPluginManager().registerEvents(joinListener, this);
        StaffChat staffChat = new StaffChat();
        getCommand("sc").setExecutor(staffChat);
        ChatListener chatListener = new ChatListener();
        Bukkit.getPluginManager().registerEvents(chatListener, this);

        getLogger().info("----------------------------");
        getLogger().info("HLStaff has been enabled!");
        getLogger().info("----------------------------");





        CompletableFuture<Void> countDown = vanishCommand.vanishMessageLoop();
        try {
            countDown.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        try {
            staffProfiles.saveStaffProfiles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getLogger().info("HLStaff has been disabled!");

    }
}
