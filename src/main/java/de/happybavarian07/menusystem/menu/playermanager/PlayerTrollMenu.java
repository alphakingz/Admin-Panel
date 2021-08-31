package de.happybavarian07.menusystem.menu.playermanager;

import de.happybavarian07.main.LanguageManager;
import de.happybavarian07.main.Main;
import de.happybavarian07.menusystem.Menu;
import de.happybavarian07.menusystem.PlayerMenuUtility;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerTrollMenu extends Menu implements Listener {
    private final Main plugin = Main.getPlugin();
    private final LanguageManager lgm = plugin.getLanguageManager();
    private final UUID targetUUID;

    public PlayerTrollMenu(PlayerMenuUtility playerMenuUtility, UUID targetUUID) {
        super(playerMenuUtility);
        this.targetUUID = targetUUID;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        setOpeningPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.Open");
    }

    @Override
    public String getMenuName() {
        return lgm.getMenuTitle("PlayerManager.TrollMenu", Bukkit.getPlayer(targetUUID));
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Player target = Bukkit.getPlayer(targetUUID);
        ItemStack item = e.getCurrentItem();

        if(target == null || !target.isOnline()) return;

        String noPerms = lgm.getMessage("Player.General.NoPermissions", player);
        /*
        Troll Permissions:
                  AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.*:
                    default: op
                    children:
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.Open: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.FakeOp: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.FakeDeop: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.FakeTNT: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.BuildPrevent: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.DropPlayersInv: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.VillagerSounds: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.MuteChat: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.HurtingWater: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.DupeMobs: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForWhitelist: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForServerstop: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForError: true
                      AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForConnectionReset: true
         */
        // Items
        if(item.equals(lgm.getItem("PlayerManager.TrollMenu.Kick.Serverstop", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForServerstop")) {
                player.sendMessage(noPerms);
                return;
            }
            target.kickPlayer("Server closed");
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.Kick.ConnectionReset", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForConnectionReset")) {
                player.sendMessage(noPerms);
                return;
            }
            target.kickPlayer("Internal exception: java.net.SocketException: Connection reset.");
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.Kick.Whitelist", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForWhitelist")) {
                player.sendMessage(noPerms);
                return;
            }
            target.kickPlayer("You are not whitelisted on this server!");
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.Kick.ServerStoppedError", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.KickForError")) {
                player.sendMessage(noPerms);
                return;
            }
            target.kickPlayer("io.netty.channel.AbstractChannel$AnnotatedConnectException: Connection refused: no further informations:");
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.DropPlayerInv", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.DropPlayersInv")) {
                player.sendMessage(noPerms);
                return;
            }
            List<ItemStack> items = new ArrayList<>();

            for(int i = 0; i < target.getInventory().getSize(); i++) {
                if(target.getInventory().getItem(i) != null) {
                    items.add(target.getInventory().getItem(i));
                }
            }

            target.getInventory().clear();

            for(ItemStack itemDrop : items) {
                target.getWorld().dropItem(target.getLocation(), itemDrop);
            }

            items.clear();
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.FakeOp", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.FakeOp")) {
                player.sendMessage(noPerms);
                return;
            }
            target.sendMessage(ChatColor.GRAY + "[Server: Made " + target.getName() + " a server operator]");
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.FakeTNT", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.FakeTNT")) {
                player.sendMessage(noPerms);
                return;
            }
            Location loc = target.getLocation();
            TNTPrimed tnt = target.getWorld().spawn(loc, TNTPrimed.class);
            tnt.setFuseTicks(20);
            tnt.setYield(0);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.FakeDeop", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.FakeDeop")) {
                player.sendMessage(noPerms);
                return;
            }
            target.sendMessage(ChatColor.GRAY + "[Server: Made " + target.getName() + " no longer a server operator]");
        } else if (item.equals(lgm.getItem("General.Close", target))) {
            if(!player.hasPermission("AdminPanel.Button.Close")) {
                player.sendMessage(noPerms);
                return;
            }
            new PlayerActionsMenu(playerMenuUtility, targetUUID).open();
        }
        // Enable/Disable Option
        else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.HurtingWater.false", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.HurtingWater")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.HurtingWater.true", target));
            if(!plugin.hurtingwater.containsKey(target))
                plugin.hurtingwater.put(target, true);
        }else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.HurtingWater.true", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.HurtingWater")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.HurtingWater.false", target));
            plugin.hurtingwater.remove(target);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.BreakPlacePrevent.false", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.BlockPrevent")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.BreakPlacePrevent.true", target));
            if(!plugin.blockBreakPrevent.containsKey(target))
                plugin.blockBreakPrevent.put(target, true);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.BreakPlacePrevent.true", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.BlockPrevent")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.BreakPlacePrevent.false", target));
            plugin.blockBreakPrevent.remove(target);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.VillagerSounds.false", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.VillagerSounds")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.VillagerSounds.true", target));
            if(!plugin.villagerSounds.containsKey(target))
                plugin.villagerSounds.put(target, true);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                if(plugin.villagerSounds.containsKey(target)) {
                    target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 50, (float) 1.0);
                }
            }, 0L, 10L);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.VillagerSounds.true", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.VillagerSounds")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.VillagerSounds.false", target));
            plugin.villagerSounds.remove(target);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.DupeMobsOnKill.false", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.DupeMobs")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.DupeMobsOnKill.true", target));
            if(!plugin.dupeMobsOnKill.containsKey(target))
                plugin.dupeMobsOnKill.put(target, true);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.DupeMobsOnKill.true", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.DupeMobs")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.DupeMobsOnKill.false", target));
            plugin.dupeMobsOnKill.remove(target);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.ChatMute.false", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.MuteChat")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.ChatMute.true", target));
            if(!plugin.chatmute.containsKey(target))
                plugin.chatmute.put(target, true);
        } else if (item.equals(lgm.getItem("PlayerManager.TrollMenu.ChatMute.true", target))) {
            if(!player.hasPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Troll.MuteChat")) {
                player.sendMessage(noPerms);
                return;
            }
            inventory.setItem(e.getSlot(), lgm.getItem("PlayerManager.TrollMenu.ChatMute.false", target));
            plugin.chatmute.remove(target);
        }
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, super.FILLER);
        }
        Player target = Bukkit.getPlayer(targetUUID);

        // Items
        if(plugin.hurtingwater.containsKey(target)) {
            inventory.setItem(4, lgm.getItem("PlayerManager.TrollMenu.HurtingWater.true", target));
        } else {
            inventory.setItem(4, lgm.getItem("PlayerManager.TrollMenu.HurtingWater.false", target));
        }

        if(plugin.blockBreakPrevent.containsKey(target)) {
            inventory.setItem(12, lgm.getItem("PlayerManager.TrollMenu.BreakPlacePrevent.true", target));
        } else {
            inventory.setItem(12, lgm.getItem("PlayerManager.TrollMenu.BreakPlacePrevent.false", target));
        }

        if(plugin.villagerSounds.containsKey(target)) {
            inventory.setItem(13, lgm.getItem("PlayerManager.TrollMenu.VillagerSounds.true", target));
        } else {
            inventory.setItem(13, lgm.getItem("PlayerManager.TrollMenu.VillagerSounds.false", target));
        }

        if(plugin.dupeMobsOnKill.containsKey(target)) {
            inventory.setItem(20, lgm.getItem("PlayerManager.TrollMenu.DupeMobsOnKill.true", target));
        } else {
            inventory.setItem(20, lgm.getItem("PlayerManager.TrollMenu.DupeMobsOnKill.false", target));
        }

        if(plugin.chatmute.containsKey(target)) {
            inventory.setItem(24, lgm.getItem("PlayerManager.TrollMenu.ChatMute.true", target));
        } else {
            inventory.setItem(24, lgm.getItem("PlayerManager.TrollMenu.ChatMute.false", target));
        }

        // Kicks
        inventory.setItem(29, lgm.getItem("PlayerManager.TrollMenu.Kick.Serverstop", target));
        inventory.setItem(30, lgm.getItem("PlayerManager.TrollMenu.Kick.ConnectionReset", target));
        inventory.setItem(32, lgm.getItem("PlayerManager.TrollMenu.Kick.Whitelist", target));
        inventory.setItem(33, lgm.getItem("PlayerManager.TrollMenu.Kick.ServerStoppedError", target));
        // Items
        inventory.setItem(14, lgm.getItem("PlayerManager.TrollMenu.DropPlayerInv", target));
        inventory.setItem(21, lgm.getItem("PlayerManager.TrollMenu.FakeOp", target));
        inventory.setItem(22, lgm.getItem("PlayerManager.TrollMenu.FakeTNT", target));
        inventory.setItem(23, lgm.getItem("PlayerManager.TrollMenu.FakeDeop", target));

        // Close Item
        inventory.setItem(53, lgm.getItem("General.Close", target));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(plugin.blockBreakPrevent.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(plugin.chatmute.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(plugin.blockBreakPrevent.containsKey(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    BukkitRunnable hurtingWaterRunnable;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(plugin.hurtingwater.containsKey(e.getPlayer())) {
            if(hurtingWaterRunnable == null || hurtingWaterRunnable.isCancelled() || !Bukkit.getScheduler().isCurrentlyRunning(hurtingWaterRunnable.getTaskId())) {
                hurtingWaterRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(!plugin.hurtingwater.containsKey(e.getPlayer())) {
                            cancel();
                            hurtingWaterRunnable = null;
                            return;
                        }
                        if (e.getPlayer().getLocation().getBlock().getType() == Material.WATER) {
                            if (e.getPlayer().getHealth() > 0.2) {
                                e.getPlayer().setHealth(e.getPlayer().getHealth() - 0.09);
                                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_GENERIC_HURT, 100, 1.0f);
                            }
                        }
                    }
                };
                hurtingWaterRunnable.runTaskTimer(plugin, 20L, 100L);
            }
        } else {
            if(hurtingWaterRunnable != null || !hurtingWaterRunnable.isCancelled() || Bukkit.getScheduler().isCurrentlyRunning(hurtingWaterRunnable.getTaskId())) {
                hurtingWaterRunnable.cancel();
            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if(e.getEntity().getKiller() != null) {
            e.getEntity();
            if (plugin.dupeMobsOnKill.containsKey(e.getEntity().getKiller())) {
                e.getDrops().clear();
                e.setDroppedExp(0);
                for (int i = 0; i < plugin.getConfig().getInt("Pman.Troll.MobDupe"); i++) {
                    e.getEntity().getKiller().getWorld().spawnEntity(e.getEntity().getLocation(), e.getEntityType());
                }
                e.getEntity().getKiller().getWorld().spawnEntity(e.getEntity().getLocation(), e.getEntityType());
            }
        }
    }
}