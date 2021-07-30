package de.happybavarian07.placeholders;

import de.happybavarian07.main.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PanelExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "appanel";
    }

    @Override
    public @NotNull String getAuthor() {
        return "HappyBavarian07";
    }

    @Override
    public @NotNull String getVersion() {
        return "3.2";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return null;
        }
        if (params.equals("openingsound")) {
            return Main.getPlugin().getConfig().getString("Panel.SoundWhenOpened");
        }
        if (params.equals("effectwhileopen")) {
            return Main.getPlugin().getConfig().getString("Panel.EffectWhenOpened");
        }
        return null;
    }
}