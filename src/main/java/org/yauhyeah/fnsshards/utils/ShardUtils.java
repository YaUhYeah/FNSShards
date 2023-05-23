package org.yauhyeah.fnsshards.utils;

import org.yauhyeah.fnsshards.FNSShards;
import org.yauhyeah.fnsshards.shards.obj.DivineShard;

public class ShardUtils {

    public static DivineShard getShardByName(String name) {
        for (DivineShard shard : FNSShards.getMain().divineShards) {
            if (shard.getShardName().equals(name)) {
                return shard;
            }
        }
        return null;
    }

}
