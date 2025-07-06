package fr.GameOfFamous.hellstyliaAPI.DataManager;

import java.util.UUID;

public class PlayerData {

    private final String name;
    private final UUID uuid;
    private final String rank;

    public PlayerData(String name, UUID uuid, String rank) {
        this.name = name;
        this.uuid = uuid;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getRank() {
        return rank;
    }

}
