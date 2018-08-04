package com.shadowmc.plugin.handlers.objects;

import com.shadowmc.plugin.handlers.files.Config;

public class Rewards {

    private Config rf;
    private String command;
    private int amount;

    public Rewards(Config rf, String command, int amount) {
        this.rf = rf;
        this.command = command;
        this.amount = amount;
    }


    public String getCommand() {
        return command;
    }

    public int getAmount() {
        return amount;
    }
}
