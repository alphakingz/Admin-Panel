package de.happybavarian07.events.server;

import de.happybavarian07.events.AdminPanelEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class MuteChatEvent extends AdminPanelEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private final Player player;
    private final boolean chatMuted;

    public MuteChatEvent(Player player, boolean chatMuted) {
        this.player = player;
        this.chatMuted = chatMuted;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isChatMuted() {
        return chatMuted;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;

    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
