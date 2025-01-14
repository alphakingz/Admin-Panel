package de.happybavarian07.menusystem.menu.servermanager;

/**
 * @Author HappyBavarian07
 * @Date 02.09.2021
 */

import de.happybavarian07.events.NotAPanelEventException;
import de.happybavarian07.events.server.ClearChatEvent;
import de.happybavarian07.events.server.MuteChatEvent;
import de.happybavarian07.main.AdminPanelMain;
import de.happybavarian07.main.LanguageManager;
import de.happybavarian07.menusystem.Menu;
import de.happybavarian07.menusystem.PlayerMenuUtility;
import de.happybavarian07.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class ChatManagerMenu extends Menu implements Listener {
    private final AdminPanelMain plugin = AdminPanelMain.getPlugin();

    public ChatManagerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        setOpeningPermission("AdminPanel.ServerManagment.ChatManager.Open");
    }

    @Override
    public String getMenuName() {
        return lgm.getMenuTitle("ServerManager.ChatManagerMenu", null);
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        String path = "ChatManager.";

        String noPerms = lgm.getMessage("Player.General.NoPermissions", player);

        if (item == null || !item.hasItemMeta()) return;
        if (item.equals(lgm.getItem(path + "ClearChat", player))) {
            if (!player.hasPermission("AdminPanel.ServerManagment.ChatManager.Clear")) {
                player.sendMessage(noPerms);
                return;
            }
            ClearChatEvent clearChatEvent = new ClearChatEvent(player, 100);
            try {
                AdminPanelMain.getAPI().callAdminPanelEvent(clearChatEvent);
                if (!clearChatEvent.isCancelled()) {
                    Utils.clearChat(clearChatEvent.getLines(), false, player);
                    Bukkit.broadcastMessage(lgm.getMessage("Player.Chat.Header", player));
                    Bukkit.broadcastMessage(lgm.getMessage("Player.Chat.Message", player));
                    Bukkit.broadcastMessage(lgm.getMessage("Player.Chat.Footer", player));
                }
            } catch (NotAPanelEventException notAPanelEventException) {
                notAPanelEventException.printStackTrace();
            }
        } else if (item.equals(lgm.getItem(path + "UnMuteChat", player))) {
            if (!player.hasPermission("AdminPanel.ServerManagment.ChatManager.Mute")) {
                player.sendMessage(noPerms);
                return;
            }
            MuteChatEvent muteChatEvent = new MuteChatEvent(player, false);
            try {
                AdminPanelMain.getAPI().callAdminPanelEvent(muteChatEvent);
                if (!muteChatEvent.isCancelled()) {
                    plugin.setChatMuted(false);
                    super.open();
                    Bukkit.broadcastMessage(lgm.getMessage("Player.ChatUnMute.Header", player));
                    Bukkit.broadcastMessage(lgm.getMessage("Player.ChatUnMute.Message", player));
                    Bukkit.broadcastMessage(lgm.getMessage("Player.ChatUnMute.Footer", player));
                }
            } catch (NotAPanelEventException notAPanelEventException) {
                notAPanelEventException.printStackTrace();
            }
        } else if (item.equals(lgm.getItem(path + "MuteChat", player))) {
            if (!player.hasPermission("AdminPanel.ServerManagment.ChatManager.Mute")) {
                player.sendMessage(noPerms);
                return;
            }
            MuteChatEvent muteChatEvent = new MuteChatEvent(player, true);
            try {
                AdminPanelMain.getAPI().callAdminPanelEvent(muteChatEvent);
                if (!muteChatEvent.isCancelled()) {
                    plugin.setChatMuted(true);
                    super.open();
                    Bukkit.broadcastMessage(lgm.getMessage("Player.ChatMute.Header", player));
                    Bukkit.broadcastMessage(lgm.getMessage("Player.ChatMute.Message", player));
                    Bukkit.broadcastMessage(lgm.getMessage("Player.ChatMute.Footer", player));
                }
            } catch (NotAPanelEventException notAPanelEventException) {
                notAPanelEventException.printStackTrace();
            }
        } else if (item.equals(lgm.getItem("General.Close", player))) {
            if (!player.hasPermission("AdminPanel.Button.Close")) {
                player.sendMessage(noPerms);
                return;
            }
            new ServerManagerMenu(AdminPanelMain.getAPI().getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        Player player = playerMenuUtility.getOwner();
        String path = "ChatManager.";

        inventory.setItem(getSlot(path + "ClearChat", 12), lgm.getItem(path + "ClearChat", player));
        if (plugin.isChatMuted()) {
            inventory.setItem(getSlot(path + "UnMuteChat", 14), lgm.getItem(path + "UnMuteChat", player));
        } else {
            inventory.setItem(getSlot(path + "MuteChat", 14), lgm.getItem(path + "MuteChat", player));
        }
        inventory.setItem(getSlot("General.Close", 22), lgm.getItem("General.Close", player));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.isChatMuted()) {
            event.setCancelled(true);
            player.sendMessage(lgm.getMessage("Player.ChatMute.PlayerMessage", player));
        }
    }
}
