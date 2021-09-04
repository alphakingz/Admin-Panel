package de.happybavarian07.menusystem.menu.pluginmanager;

/**
 * @Author HappyBavarian07
 * @Date 02.09.2021
 */

import de.happybavarian07.events.plugins.PluginInstallEvent;
import de.happybavarian07.main.Head;
import de.happybavarian07.main.LanguageManager;
import de.happybavarian07.main.Main;
import de.happybavarian07.menusystem.PaginatedMenu;
import de.happybavarian07.menusystem.PlayerMenuUtility;
import de.happybavarian07.menusystem.menu.AdminPanelStartMenu;
import de.happybavarian07.utils.PluginUtils;
import de.happybavarian07.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class PluginSelectMenu extends PaginatedMenu {
    private final Main plugin = Main.getPlugin();
    private final PluginUtils pluginUtils;
    private final LanguageManager lgm = plugin.getLanguageManager();

    public PluginSelectMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.pluginUtils = new PluginUtils();
        setOpeningPermission("AdminPanel.PluginManager.Open");
    }

    @Override
    public String getMenuName() {
        return lgm.getMenuTitle("PluginManager.Selector", null);
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        String path = "PluginManager.";
        List<Plugin> plugins = new ArrayList<>(pluginUtils.getAllPlugins());

        String noPerms = lgm.getMessage("Player.General.NoPermissions", player);

        if (item == null || !item.hasItemMeta()) return;
        if (item.getType().equals(Material.PLAYER_HEAD)) {
            if (!player.hasPermission("AdminPanel.PluginManager.PluginSettings.Open")) {
                player.sendMessage(noPerms);
                return;
            }
            new PluginSettingsMenu(Main.getAPI().getPlayerMenuUtility(player), Bukkit.getPluginManager().getPlugin(ChatColor.stripColor(item.getItemMeta().getDisplayName()))).open();
        } else if (item.equals(lgm.getItem(path + "Install", player))) {
            if (!player.hasPermission("AdminPanel.Button.Close")) {
                player.sendMessage(noPerms);
                return;
            }
            new PluginInstallMenu(playerMenuUtility).open();
        } else if (item.equals(lgm.getItem("General.Close", null))) {
            if (!player.hasPermission("AdminPanel.Button.Close")) {
                player.sendMessage(noPerms);
                return;
            }
            new AdminPanelStartMenu(Main.getAPI().getPlayerMenuUtility(player)).open();
        } else if (item.equals(lgm.getItem("General.Left", null))) {
            if (!player.hasPermission("AdminPanel.Button.pageleft")) {
                player.sendMessage(noPerms);
                return;
            }
            if (page == 0) {
                player.sendMessage(lgm.getMessage("Player.General.AlreadyOnFirstPage", player));
            } else {
                page = page - 1;
                super.open();
            }
        } else if (item.equals(lgm.getItem("General.Right", null))) {
            if (!player.hasPermission("AdminPanel.Button.pageright")) {
                player.sendMessage(noPerms);
                return;
            }
            if (!((index + 1) >= plugins.size())) {
                page = page + 1;
                super.open();
            } else {
                player.sendMessage(lgm.getMessage("Player.General.AlreadyOnLastPage", player));
            }
        } else if (item.equals(lgm.getItem("General.Refresh", player))) {
            if (!player.hasPermission("AdminPanel.Button.refresh")) {
                player.sendMessage(noPerms);
                return;
            }
            super.open();
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        inventory.setItem(47, lgm.getItem("PluginManager.Install", playerMenuUtility.getOwner()));
        List<Plugin> plugins = new ArrayList<>(pluginUtils.getAllPlugins());

        ///////////////////////////////////// Pagination loop template
        if (plugins != null && !plugins.isEmpty()) {
            for (int i = 0; i < super.maxItemsPerPage; i++) {
                index = super.maxItemsPerPage * page + i;
                if (index >= plugins.size()) break;
                if (plugins.get(index) != null) {
                    ///////////////////////////

                    Plugin currentPlugin = plugins.get(index);
                    boolean enabled = currentPlugin.isEnabled();
                    ItemStack item;
                    if (enabled) {
                        item = Head.BLANK_GREEN.getAsItem();
                    } else {
                        item = Head.BLANK_RED.getAsItem();
                    }
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(Utils.getInstance().chat("&a" + currentPlugin.getName()));
                    List<String> lore = new ArrayList<>();
                    Utils utils = Utils.getInstance();
                    lore.add(utils.chat("&6Enabled: &a" + enabled));
                    lore.add(utils.chat("&6Version: &a" + currentPlugin.getDescription().getVersion()));
                    if (currentPlugin.getDescription().getAuthors().size() == 1) {
                        lore.add(utils.chat("&6Author: &a" + currentPlugin.getDescription().getAuthors().get(0)));
                    } else {
                        lore.add(utils.chat("&6Authors: &a" + currentPlugin.getDescription().getAuthors()));
                    }
                    lore.add(utils.chat("&6Website: &a" + currentPlugin.getDescription().getWebsite()));
                    lore.add(utils.chat("&6API-Version: &a" + currentPlugin.getDescription().getAPIVersion()));
                    lore.add(utils.chat("&6Full-Name: &a" + currentPlugin.getDescription().getFullName()));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    inventory.addItem(item);

                    ////////////////////////
                }
            }
        }
        ////////////////////////
    }
}