package de.happybavarian07.menusystem.menu.worldmanager.weather;

import de.happybavarian07.main.LanguageManager;
import de.happybavarian07.main.Main;
import de.happybavarian07.menusystem.Menu;
import de.happybavarian07.menusystem.PlayerMenuUtility;
import de.happybavarian07.menusystem.menu.worldmanager.WorldSettingsMenu;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WeatherChangeMenu extends Menu {
    private final Main plugin = Main.getPlugin();
    private final LanguageManager lgm = plugin.getLanguageManager();
    private final World world;

    public WeatherChangeMenu(PlayerMenuUtility playerMenuUtility, World world) {
        super(playerMenuUtility);
        this.world = world;
    }

    @Override
    public String getMenuName() {
        return lgm.getMenuTitle("WorldManager.WeatherMenu", null);
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        String itemPath = "WorldManager.Time.";

        String noPerms = lgm.getMessage("Player.General.NoPermissions", player);

        if (item == null || !item.hasItemMeta()) return;
        if (item.equals(lgm.getItem(itemPath + Weather.CLEAR, player))) {
            world.setStorm(false); world.setThundering(false);
        } else if (item.equals(lgm.getItem(itemPath + Weather.RAINING, player))) {
            world.setStorm(true); world.setThundering(false);
        } else if (item.equals(lgm.getItem(itemPath + Weather.THUNDERING, player))) {
            world.setStorm(true); world.setThundering(true);
        } else if (item.equals(lgm.getItem("General.Close", null))) {
            if(!player.hasPermission("AdminPanel.Button.Close")) {
                player.sendMessage(noPerms);
                return;
            }
            new WorldSettingsMenu(playerMenuUtility, world).open();
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        Player player = playerMenuUtility.getOwner();
        String itemPath = "WorldManager.Weather.";

        inventory.setItem(0, lgm.getItem(itemPath + Weather.CLEAR, player));
        inventory.setItem(1, lgm.getItem(itemPath + Weather.RAINING, player));
        inventory.setItem(2, lgm.getItem(itemPath + Weather.THUNDERING, player));
        inventory.setItem(8, lgm.getItem("General.Close", player));
    }
}