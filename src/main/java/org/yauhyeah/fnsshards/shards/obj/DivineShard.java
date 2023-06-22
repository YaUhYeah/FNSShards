package org.yauhyeah.fnsshards.shards.obj;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yauhyeah.fnsshards.utils.NumberUtils;
import org.yauhyeah.fnsshards.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DivineShard {

    private final String shardName;
    private final ShardReward loseReward;
    private final ShardReward winReward;
    private final int lowerRangePrize;
    private final int upperRangePrize;
    private final int chance;
    private ItemStack itemShard = new ItemStack(Material.PRISMARINE_SHARD);

    public DivineShard(String shardName, ShardReward loseReward, ShardReward winReward, int lowerRangePrize, int upperRangePrize, int chance) {
        this.shardName = shardName;
        this.loseReward = loseReward;
        this.winReward = winReward;
        this.lowerRangePrize = lowerRangePrize;
        this.upperRangePrize = upperRangePrize;
        this.chance = chance;
        ItemMeta meta = this.itemShard.getItemMeta();
        assert meta != null;
        meta.setDisplayName(StringUtils.colorize("&b&lDivine Shard &r&7(Right Click)"));
        List<String> lore = new ArrayList<>();
        lore.add(StringUtils.colorize("&7A chance to win the &e" + shardName + "&7!"));
        lore.add("");
        lore.add(StringUtils.colorize("&aWinning: &7Grants access to the prize."));
        lore.add(StringUtils.colorize("&cLosing: &7Rewards points from &e" + NumberUtils.withLargeIntegers(lowerRangePrize) + " - " + NumberUtils.withLargeIntegers(upperRangePrize) + ""));
        meta.setLore(lore);
        this.itemShard.setItemMeta(meta);
        NBTItem item = new NBTItem(this.itemShard);
        item.setString("Shard", shardName);
        this.itemShard = item.getItem();
    }

    public ShardReward getLoseReward() {
        return loseReward;
    }

    public int getChance() {
        return chance;
    }

    public ShardReward getWinReward() {
        return winReward;
    }

    public ItemStack getItemShard() {
        return this.itemShard;
    }

    public String getShardName() {
        return shardName;
    }


    public int getLowerRangePrize() {
        return lowerRangePrize;
    }

    public int getUpperRangePrize() {
        return upperRangePrize;
    }
}
