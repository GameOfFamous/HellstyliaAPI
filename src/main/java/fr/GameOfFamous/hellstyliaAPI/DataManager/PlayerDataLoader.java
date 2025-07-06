package fr.GameOfFamous.hellstyliaAPI.DataManager;

import com.google.gson.Gson;
import fr.GameOfFamous.hellstyliaAPI.Gestion.RedisWrapper;

import java.util.UUID;

public class PlayerDataLoader {

    private final RedisWrapper redis;
    private final Gson gson = new Gson();

    private static final String KEY_PREFIX = "player:";

    public PlayerDataLoader(RedisWrapper redis) {
        this.redis = redis;
    }

    public PlayerData getPlayerData(UUID uuid) {
        String json = redis.get(KEY_PREFIX + uuid);
        if (json == null) return null;
        return gson.fromJson(json, PlayerData.class);
    }

}
