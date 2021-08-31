package de.happybavarian07.menusystem.menu.playermanager;

import de.happybavarian07.main.LanguageManager;
import de.happybavarian07.main.Main;
import de.happybavarian07.menusystem.Menu;
import de.happybavarian07.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ArmorMenu extends Menu {
    private final Main plugin = Main.getPlugin();
    private final LanguageManager lgm = plugin.getLanguageManager();
    private final UUID targetUUID;

    public ArmorMenu(PlayerMenuUtility playerMenuUtility, UUID targetUUID) {
        super(playerMenuUtility);
        this.targetUUID = targetUUID;
        setOpeningPermission("AdminPanel.PlayerManager.PlayerSettings.Actions.Inventoryview");
    }

    @Override
    public String getMenuName() {
        return lgm.getMenuTitle("PlayerManager.ArmorView", Bukkit.getPlayer(targetUUID));
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Player target = Bukkit.getPlayer(targetUUID);
        ItemStack item = e.getCurrentItem();

        String noPerms = lgm.getMessage("Player.General.NoPermissions", player);

        if (item == null || !item.hasItemMeta() || target == null || !target.isOnline()) return;
        if(e.getSlot() == 0) {
            target.getInventory().getArmorContents()[0] = null;
            target.updateInventory();
            inventory.setItem(0, lgm.getItem("General.EmptySlot", target));
        } else if (e.getSlot() == 1) {
            target.getInventory().getArmorContents()[1] = null;
            target.updateInventory();
            inventory.setItem(1, lgm.getItem("General.EmptySlot", target));
        } else if (e.getSlot() == 2) {
            target.getInventory().getArmorContents()[2] = null;
            target.updateInventory();
            inventory.setItem(2, lgm.getItem("General.EmptySlot", target));
        } else if (e.getSlot() == 3) {
            target.getInventory().getArmorContents()[3] = null;
            target.updateInventory();
            inventory.setItem(3, lgm.getItem("General.EmptySlot", target));
        } else if (item.equals(lgm.getItem("General.Refresh", null))) {
            open();
        }
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, super.FILLER);
        }
        Player target = Bukkit.getPlayer(targetUUID);
        if(target.getInventory().getArmorContents()[0] != null) {
            inventory.setItem(0, target.getInventory().getArmorContents()[0]);
        } else {
            inventory.setItem(0, lgm.getItem("General.EmptySlot", target));
        }
        if(target.getInventory().getArmorContents()[1] != null) {
            inventory.setItem(1, target.getInventory().getArmorContents()[1]);
        } else {
            inventory.setItem(1, lgm.getItem("General.EmptySlot", target));
        }
        if(target.getInventory().getArmorContents()[2] != null) {
            inventory.setItem(2, target.getInventory().getArmorContents()[2]);
        } else {
            inventory.setItem(2, lgm.getItem("General.EmptySlot", target));
        }
        if(target.getInventory().getArmorContents()[3] != null) {
            inventory.setItem(3, target.getInventory().getArmorContents()[3]);
        } else {
            inventory.setItem(3, lgm.getItem("General.EmptySlot", target));
        }
        inventory.setItem(7, lgm.getItem("General.Refresh", target));
        inventory.setItem(8, lgm.getItem("General.Close", target));
    }
}