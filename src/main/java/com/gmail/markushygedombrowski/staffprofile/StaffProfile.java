package com.gmail.markushygedombrowski.staffprofile;

import java.util.UUID;

public class StaffProfile {
    private String name;
    private final UUID uuid;
    private boolean buildprotect;
    private boolean commandSpy;


    private boolean vanished;

    public StaffProfile(String name, UUID uuid, boolean buildprotect, boolean commandSpy, boolean vanished) {
        this.name = name;
        this.uuid = uuid;
        this.buildprotect = buildprotect;
        this.commandSpy = commandSpy;
        this.vanished = vanished;
    }


    public boolean isCommandSpy() {
        return commandSpy;
    }

    public void setCommandSpy(boolean commandSpy) {
        this.commandSpy = commandSpy;
    }
    public boolean isBuildprotect() {
        return buildprotect;
    }
    public void setBuildprotect(boolean buildprotect) {
        this.buildprotect = buildprotect;
    }
    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }
    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }
}
