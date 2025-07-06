package fr.GameOfFamous.hellstyliaAPI.redis;

import com.google.gson.JsonObject;
import fr.GameOfFamous.hellstyliaAPI.Gestion.RedisWrapper;

import java.util.UUID;

public class TabPublisher {

    private final RedisWrapper redis;

    public TabPublisher(RedisWrapper redis) {
        this.redis = redis;
    }

    public void updatePlayerTab(UUID uuid, String name, String rankId) {
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", uuid.toString());
        obj.addProperty("name", name);
        obj.addProperty("rank", rankId);
        redis.publish("global_tab", obj.toString());
    }

}
