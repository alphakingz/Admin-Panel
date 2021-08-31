package de.happybavarian07.menusystem;

import de.happybavarian07.main.LanguageManager;
import de.happybavarian07.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;

    // 28 empty slots per page
    protected int maxItemsPerPage = 28;

    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    //Set the border and menu buttons for the menu
    public void addMenuBorder(){
        LanguageManager lgm = Main.getPlugin().getLanguageManager();
        inventory.setItem(48, lgm.getItem("General.Left", null));

        inventory.setItem(49, lgm.getItem("General.Close", null));

        inventory.setItem(50, lgm.getItem("General.Right", null));

        inventory.setItem(51, lgm.getItem("General.Refresh", null));

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER);
            }
        }

        inventory.setItem(17, super.FILLER);
        inventory.setItem(18, super.FILLER);
        inventory.setItem(26, super.FILLER);
        inventory.setItem(27, super.FILLER);
        inventory.setItem(35, super.FILLER);
        inventory.setItem(36, super.FILLER);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER);
            }
        }
    }

    @Override
    public void setMenuItems() {

    }
}