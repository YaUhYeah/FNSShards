package org.yauhyeah.fnsshards.shards.obj;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yauhyeah.fnsshards.utils.StringUtils;

import java.util.List;

public class ShardReward {

    private final String rewardName;
    private final List<String> rewardCmds;
    private final Material rewardMaterial;
    private final ItemStack rewardDummyItem;
    private boolean isWinReward;

    public ShardReward(String rewardName, List<String> rewardCmds, Material rewardMaterial, boolean isWinReward) {
        this.rewardName = rewardName;
        this.rewardCmds = rewardCmds;
        this.rewardMaterial = rewardMaterial;
        this.rewardDummyItem = new ItemStack(rewardMaterial);
        this.isWinReward = isWinReward;
        ItemMeta meta = this.rewardDummyItem.getItemMeta();
        assert meta != null;
        meta.setDisplayName(StringUtils.colorize("&e" + rewardName));
        this.rewardDummyItem.setItemMeta(meta);
    }

    public String getRewardName() {
        return rewardName;
    }

    public List<String> getRewardCmds() {
        return rewardCmds;
    }

    public ItemStack getRewardDummyItem() {
        return rewardDummyItem;
    }

    public Material getRewardMaterial() {
        return rewardMaterial;
    }

    public boolean isWinReward() {
        return isWinReward;
    }

    public void setWinReward(boolean winReward) {
        isWinReward = winReward;
    }
}
