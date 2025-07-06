package fr.GameOfFamous.hellstyliaAPI.Gestion;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RedisWrapper {

    private final RedisClient client;
    private final StatefulRedisConnection<String, String> connection;
    private final RedisCommands<String, String> sync;

    private final StatefulRedisPubSubConnection<String, String> pubSubConnection;
    private final RedisPubSubCommands<String, String> pubSub;

    private final Map<String, Consumer<String>> listeners = new HashMap<>();

    public RedisWrapper(String redisUrl) {
        this.client = RedisClient.create(redisUrl);
        this.connection = client.connect();
        this.sync = connection.sync();

        this.pubSubConnection = client.connectPubSub();
        this.pubSub = pubSubConnection.sync();

        // Gérer les messages entrants
        pubSubConnection.addListener(new RedisPubSubAdapter<>() {
            @Override
            public void message(String channel, String message) {
                Consumer<String> consumer = listeners.get(channel);
                if (consumer != null) {
                    consumer.accept(message);
                }
            }
        });
    }

    public void set(String key, String value) {
        sync.set(key, value);
    }

    public String get(String key) {
        return sync.get(key);
    }

    public void delete(String key) {
        sync.del(key);
    }

    public void publish(String channel, String message) {
        sync.publish(channel, message);
    }

    public void subscribe(String channel, Consumer<String> callback) {
        listeners.put(channel, callback);
        pubSub.subscribe(channel);
    }

    public void close() {
        pubSubConnection.close();
        connection.close();
        client.shutdown();
    }

}
