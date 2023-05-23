package org.yauhyeah.fnsshards.shards.cmd;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.yauhyeah.fnsshards.shards.obj.DivineShard;
import org.yauhyeah.fnsshards.utils.ShardUtils;
import org.yauhyeah.fnsshards.utils.StringUtils;

public class ShardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelpMessage(sender);
            return false;
        }
        if (args.length == 4) {
            if (sender.hasPermission("TPN.admin")) {
                if (args[0].equals("give")) {
                    DivineShard shard = ShardUtils.getShardByName(args[1]);
                    if (shard == null) {
                        sender.sendMessage(StringUtils.getPrefix() + "That shard does not exist.");
                        return false;
                    }
                    try {
                        Player target = Bukkit.getPlayer(args[3]);
                        if (target == null) {
                            sender.sendMessage(StringUtils.getPrefix() + "That player is not online.");
                            return false;
                        }
                        int amount = Integer.parseInt(args[2]);
                        ItemStack shardItem = shard.getItemShard();
                        shardItem.setAmount(amount);
                        target.getInventory().addItem(shardItem);
                        sender.sendMessage(StringUtils.getPrefix() + "Gave " + target.getName() + " a " + shard.getShardName() + " shard.");
                        target.sendMessage(StringUtils.getPrefix() + "Recieved a " + shard.getShardName() + " shard.");
                        return false;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public void sendHelpMessage(CommandSender p) {
        p.sendMessage(StringUtils.colorize("&8<&7-----&b&lShards Help&7-----&8>"));
        p.sendMessage(StringUtils
                .colorize("&7/shard give <shardName> <amount> <player> &8Gives shards to a player!"));
        p.sendMessage(StringUtils.colorize("&7More cmds to come."));
    }

}
