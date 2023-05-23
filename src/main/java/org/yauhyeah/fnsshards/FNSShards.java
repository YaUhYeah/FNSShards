package org.yauhyeah.fnsshards;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yauhyeah.fnsshards.shards.cmd.ShardCommand;
import org.yauhyeah.fnsshards.shards.listeners.ShardListener;
import org.yauhyeah.fnsshards.shards.obj.DivineShard;
import org.yauhyeah.fnsshards.shards.obj.ShardReward;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FNSShards extends JavaPlugin {
    public List<DivineShard> divineShards;
    private final File shardsConfigFile = new File(getDataFolder() + "/shards.yml");
    private final FileConfiguration shardsConfig = YamlConfiguration
            .loadConfiguration(this.shardsConfigFile);
    public static FNSShards main;

    @Override
    public void onEnable() {
        divineShards = getDivineShards();
        saveCustomYml(shardsConfig,shardsConfigFile);
        Objects.requireNonNull(getCommand("shard")).setExecutor(new ShardCommand());

        Bukkit.getPluginManager().registerEvents(new ShardListener(), this);

    }

    @Override
    public void onLoad() {
        main = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FNSShards getMain() {
        return main;
    }

    public List<DivineShard> getDivineShards() {
        List<DivineShard> divineShards = new ArrayList<>();
        if (getShardsConfig().isConfigurationSection("Shards")) {
            for (String key : Objects.requireNonNull(getShardsConfig().getConfigurationSection("Shards")).getKeys(false)) {
                String name = getShardsConfig().getString("Shards." + key + ".name");
                int chance = getShardsConfig().getInt("Shards." + key + ".chance");
                int lower = getShardsConfig().getInt("Shards." + key + ".lowerRewardPrizeMoney");
                int upper = getShardsConfig().getInt("Shards." + key + ".upperRewardPrizeMoney");
                String loseRewardName = getShardsConfig().getString("Shards." + key + ".loseReward.rewardName");
                List<String> loseCmds = getShardsConfig().getStringList("Shards." + key + ".loseReward.rewardCommands");
                String loseMaterial = getShardsConfig().getString("Shards." + key + ".loseReward.rewardMaterialName");
                assert loseMaterial != null;
                Material realLoseMaterial = Material.getMaterial(loseMaterial);
                ShardReward rewLose = new ShardReward(loseRewardName, loseCmds, realLoseMaterial, false);
                String winRewardName = getShardsConfig().getString("Shards." + key + ".winReward.rewardName");
                List<String> winCmds = getShardsConfig().getStringList("Shards." + key + ".winReward.rewardCommands");
                String winMaterial = getShardsConfig().getString("Shards." + key + ".winReward.rewardMaterialName");
                assert winMaterial != null;
                Material realWinMaterial = Material.getMaterial(winMaterial);
                ShardReward rewWin = new ShardReward(winRewardName, winCmds, realWinMaterial, false);
                DivineShard shard = new DivineShard(name, rewLose, rewWin, lower, upper, chance);
                divineShards.add(shard);
            }
        }
        return divineShards;
    }

    public File getShardsConfigFile() {
        return shardsConfigFile;
    }

    public FileConfiguration getShardsConfig() {
        return shardsConfig;
    }
}
