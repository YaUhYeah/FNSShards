package org.yauhyeah.fnsshards.shards.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.yauhyeah.fnsshards.FNSShards;
import org.yauhyeah.fnsshards.shards.obj.DivineShard;
import org.yauhyeah.fnsshards.utils.NumberUtils;
import org.yauhyeah.fnsshards.utils.ShardUtils;
import org.yauhyeah.fnsshards.utils.StringUtils;

import java.util.*;

public class ShardListener implements Listener {

    public static List<Player> playersOpeningShard = new ArrayList<>();
    public static Inventory getShardOpeningInventory() {
        Inventory inv = Bukkit.createInventory(null, 27, StringUtils.colorize("&bShard Opening"));
        inv.setItem(4, getTorchWinningItem());
        inv.setItem(22, getTorchWinningItem());
        inv.setItem(0, blackGlass());
        inv.setItem(1, blackGlass());
        inv.setItem(2, blackGlass());
        inv.setItem(3, blackGlass());
        inv.setItem(5, blackGlass());
        inv.setItem(6, blackGlass());
        inv.setItem(7, blackGlass());
        inv.setItem(8, blackGlass());
        inv.setItem(18, blackGlass());
        inv.setItem(19, blackGlass());
        inv.setItem(20, blackGlass());
        inv.setItem(21, blackGlass());
        inv.setItem(23, blackGlass());
        inv.setItem(24, blackGlass());
        inv.setItem(25, blackGlass());
        inv.setItem(26, blackGlass());
        return inv;
    }   @Deprecated
    public static ItemStack blackGlass() {
        ItemStack i = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        i.setDurability((short) 15);
        ItemMeta meta = i.getItemMeta();
        assert meta != null;
        meta.setDisplayName(StringUtils.colorize("&7"));
        i.setItemMeta(meta);
        return i;
    }    public static ItemStack getTorchWinningItem() {
        ItemStack item = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(StringUtils.colorize("&e&lPRIZE!"));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStackInHand = player.getInventory().getItemInMainHand();
        if (!itemStackInHand.getType().equals(Material.AIR)) {
            NBTItem nbtItem = new NBTItem(itemStackInHand);
            if (!playersOpeningShard.contains(player)) {
                if (nbtItem.hasNBTData()) {
                    if (nbtItem.hasKey("Shard")) {
                        DivineShard shard = ShardUtils.getShardByName(nbtItem.getString("Shard"));
                        if (shard != null) {
                            e.setCancelled(true);
                            pop(player, itemStackInHand, 1);
                            boolean didPlayerWin = new Random().nextInt(101) <= shard.getChance();
                            playAnimation(shard, 4, player, getShardOpeningInventory(), didPlayerWin);
                            playersOpeningShard.add(player);
                        }
                    }
                }
            }
        }
    }      public static ItemStack createLosingShardRewardGUIItem(DivineShard shard) {
        ItemStack newItem = shard.getLoseReward().getRewardDummyItem();
        ItemMeta meta = newItem.getItemMeta();
        assert meta != null;
        int amt = NumberUtils.random(shard.getLowerRangePrize(), shard.getUpperRangePrize());
        meta.setDisplayName(Objects.requireNonNull(shard.getLoseReward().getRewardDummyItem().getItemMeta()).getDisplayName().replace("%amount%", NumberUtils.withLargeIntegers(amt)));
        newItem.setItemMeta(meta);
        return newItem;
    }  public static List<ItemStack> possibleWins(DivineShard shard) {
        List<ItemStack> items = new ArrayList<>();
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(shard.getWinReward().getRewardDummyItem());
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(shard.getWinReward().getRewardDummyItem());
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(createLosingShardRewardGUIItem(shard));
        items.add(shard.getWinReward().getRewardDummyItem());
        items.add(createLosingShardRewardGUIItem(shard));
        return items;
    }
    public static void playAnimation(final DivineShard shard, final double seconds, final Player reciever, Inventory inv, boolean didPlayerWin) {
        ItemStack winningItem;
        List<ItemStack> items = possibleWins(shard);
        Collections.shuffle(items);
        int amt = NumberUtils.random(shard.getLowerRangePrize(), shard.getUpperRangePrize());
        if (didPlayerWin) {
            winningItem = shard.getWinReward().getRewardDummyItem();
        } else {
            ItemStack newItem = shard.getLoseReward().getRewardDummyItem();
            ItemMeta meta = newItem.getItemMeta();
            assert meta != null;
            meta.setDisplayName(Objects.requireNonNull(shard.getLoseReward().getRewardDummyItem().getItemMeta()).getDisplayName().replace("%amount%", NumberUtils.withLargeIntegers(amt)));
            newItem.setItemMeta(meta);
            NBTItem nbtItem = new NBTItem(newItem);
            nbtItem.setInteger("Worth", amt);
            winningItem = nbtItem.getItem();
        }
        items.add(winningItem);
        reciever.openInventory(inv);
        new BukkitRunnable() {
            int frame = 0;
            double delay = 0;
            int ticks = 0;
            boolean done = false;

            public void run() {
                if (done) return;
                ticks++;
                delay += 1 / (20 * seconds);
                if (ticks > delay * 10) {
                    ticks = 0;
                    for (int i = 17; i >= 9; i--) {
                        reciever.playSound(reciever.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.1F, 1.0F);
                        inv.setItem(i, items.get((i + frame) % items.size()));
                        if (frame >= items.size()) {
                            frame = 0;
                            break;
                        }

                    }
                    frame++;

                    if (delay >= 1) {

                        inv.setItem(13, winningItem);
                        done = true;
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (didPlayerWin) {
                                    reciever.playSound(reciever.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 1.0F);
                                    reciever.sendMessage(StringUtils.getPrefix() + " Success, shard prize has been won!");
                                    for (String cmd : shard.getWinReward().getRewardCmds()) {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", reciever.getName()));
                                    }
                                } else {
                                    reciever.playSound(reciever.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.5F, 1.0F);
                                    reciever.sendMessage(StringUtils.getPrefix() + " Failure, shard lost, but instead, you won: " + NumberUtils.withLargeIntegers(amt) + " points!");
                                    for (String cmd : shard.getLoseReward().getRewardCmds()) {
                                        String cmd1 = cmd.replace("%amount%", amt + "");
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd1.replace("%player%", reciever.getName()));
                                    }
                                }
                                reciever.closeInventory();
                                cancel();
                            }
                        }.runTaskLater(FNSShards.getMain(), 50);
                        ShardListener.playersOpeningShard.remove(reciever);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(FNSShards.getMain(), 0, 1);
    }
    public static void pop(Player p, ItemStack i, int amount) {
        p.getInventory().remove(i);
        i.setAmount(i.getAmount() - amount);
        if (i.getAmount() > 0)
            p.getInventory().addItem(i);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(StringUtils.colorize("&bShard Opening"))) {
            e.setCancelled(true);
        }
    }

}
