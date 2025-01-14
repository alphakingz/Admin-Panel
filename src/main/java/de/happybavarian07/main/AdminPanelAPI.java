package de.happybavarian07.main;

import de.happybavarian07.events.AdminPanelEvent;
import de.happybavarian07.events.NotAPanelEventException;
import de.happybavarian07.menusystem.Menu;
import de.happybavarian07.menusystem.PlayerMenuUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AdminPanelAPI {
    // Basic

    /**
     * Offnet das angegebene Menü für den in der PlayerMenuUtility angegebenen Spieler
     * Und um von dem Menu eine Instance zu bilden braucht man die {@code getPlayerMenuUtility(Player player)} Methode
     *
     * @param menu Ist das Menü das man öffnen will
     */
    void openPanel(Menu menu);

    /**
     * Gibt die PlayerMenuUtility Map zurück
     * Sie wird dafür benutzt um Informationen über die Spieler,
     * die das Panel offen haben überall nutzen zu können!
     *
     * @return Die Map
     */
    Map<Player, PlayerMenuUtility> getPlayerMenuUtilityMap();

    /**
     * Diese Methode sucht nach der PlayerMenuUtility von dem Spieler
     * und gibt sie zurück falls gefunden sonst erstellt sie eine neue!
     *
     * @param player der Spieler
     * @return Gibt die PlayerMenuUtility zurück
     */
    PlayerMenuUtility getPlayerMenuUtility(Player player);

    /**
     * Diese Methode nimmt ein Head Texture String entgegen und setzt diesen als Texture ein
     * und baut davon einen ItemStack!
     * Köpfe können auf https://minecraft-heads.com/ gefunden werden,
     * Dort muss man sich das Value kopieren und als der headTexture Parameter einfügen!
     * Dieser ist mit Base64 Encoded und muss nicht mehr Decoded werden
     * Da er so von minecraft verwendet wird!
     *
     * @param headTexture Das Value der Texture
     * @param name        Der Name des Items
     * @return Gibt einen Kopf zurück
     */
    ItemStack createSkull(String headTexture, String name);

    /**
     * Diese Methode nimmt ein Head Objekt entgegen nimmt sich daraus die Full Texture
     * und baut davon einen ItemStack mit der Kopf Texture
     *
     * @param headTexture Der Kopf den man möchte
     * @param name        Der Name des Items
     * @return Gibt einen Kopf zurück
     */
    ItemStack createSkull(Head headTexture, String name);

    // Plugin Manager

    /**
     * Lädt ein Plugin (wenn gefunden)
     * Der Parameter pluginName ist der Name des Plugins
     * Und dieses Plugin wird dann geladen!
     * Aber Nicht aktiviert!!!
     *
     * @param pluginName der Plugin Name
     * @throws InvalidPluginException      Wenn das Plugin kein Plugin ist
     * @throws InvalidDescriptionException Wenn das Plugin keine plugin.yml hat
     */
    void loadPlugin(File pluginName) throws InvalidPluginException, InvalidDescriptionException;

    /**
     * Reloaded ein Plugin (wenn gefunden)
     * Der Parameter plugin ist das Plugin
     * Und dieses Plugin wird dann geladen!
     *
     * @param plugin das Plugin
     */
    void reloadPlugin(Plugin plugin);


    /**
     * Reloaded ein Plugin (wenn gefunden)
     * Der Parameter pluginName ist der Name des Plugins
     * Und dieses Plugin wird dann geladen!
     *
     * @param pluginName der Plugin Name
     */
    void reloadPlugin(String pluginName);

    /**
     * Entlädt ein Plugin komplett
     * Alle Commands werden entladen,
     * Alle Listener werden entladen,
     * Und es wird komplett aus der Liste der Bukkit Plugins gelöscht
     * Und wird erst wieder da sein nach einem Reload/Restart
     *
     * @param plugin Das Plugin
     */
    void unloadPlugin(Plugin plugin);

    /**
     * Entlädt ein Plugin komplett
     * Alle Commands werden entladen,
     * Alle Listener werden entladen,
     * Und es wird komplett aus der Liste der Bukkit Plugins gelöscht
     * Und wird erst wieder da sein nach einem Reload/Restart
     *
     * @param pluginName Der Plugin Name
     */
    void unloadPlugin(String pluginName);

    /**
     * Gibt eine Liste aller Plugin Name zurück
     * (Nur wenn sie geladen sind)
     *
     * @param fullName Gibt ob es der vollständige Name sein soll oder nicht
     * @return die Plugin Namen
     */
    List<String> getPluginNames(boolean fullName);

    /**
     * Gibt eine Liste aller Plugins zurück
     * (Nur wenn sie geladen sind)
     *
     * @return Eine Liste mit Plugins
     */
    List<Plugin> getAllPlugins();

    /**
     * Gibt ein Plugin vom Namen zurück
     *
     * @param pluginName Der Name
     * @return das Plugin
     * @throws NullPointerException wenn das Plugin null ist
     */
    Plugin getPluginByName(String pluginName) throws NullPointerException;

    /**
     * Diese Methode downloaded ein Plugin von Spigot/Spiget!
     * <p>
     * Infos:
     * Die {@code resourceID} bekommt man dadurch das man auf Spigot
     * in der URL hinter dem Namen des Plugins die Zahl kopiert!
     * Der {@code fileName} ist der Name mit die Datei erstellt werden soll!
     * Und {@code enableAfterStart} macht, dass das Plugin entweder
     * Automatisch aktiviert werden soll oder nicht!
     *
     * @param resourceID         Resource Id des Plugins
     * @param fileName           Datei Name
     * @param enableAfterInstall Automatic Start
     * @return Das heruntergeladene Plugin
     * @throws IOException                 Wenn irgendwas mit der Website nicht geht
     * @throws InvalidPluginException      Wenn das Plugin kein Plugin ist
     * @throws InvalidDescriptionException Wenn das Plugin keine plugin.yml hat
     * @throws UnknownDependencyException  Wenn die Dependencies des Plugins fehlen
     */
    Plugin downloadPluginFromSpiget(int resourceID, String fileName, Boolean enableAfterInstall) throws IOException, InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;

    // Utils

    /**
     * Cleart den Chat
     *
     * @param lines              Linien die gecleart werden sollen
     * @param broadcastChatClear Ob geBroadcastet werden soll bei Chat Clear
     * @param player             Der Spieler der dann genannt wird wenn {@code broadcastChatClear}
     */
    void clearChat(int lines, boolean broadcastChatClear, Player player);

    /**
     * Restartet den Server
     *
     * @param time Zeit zwischen den Aktionen und Nachrichten
     * @param time Zeit zwischen dem Restart und Player Kick
     * @throws InterruptedException Wenn der Restart unterbrochen wird
     */
    void restartServer(int time, int time2) throws InterruptedException;

    /**
     * Stoppt den Server
     *
     * @param time  Zeit zwischen den Aktionen und Nachrichten
     * @param time2 Zeit bevor Spieler gekickt werden und der Server gestoppt wird
     * @throws InterruptedException Wenn es unterbrochen wird
     */
    void stopServer(int time, int time2) throws InterruptedException;

    // Language System

    /**
     * Registriert eine Sprache im Plugin
     *
     * @param languageFile Der Language File mit den Einträgen drinnen
     * @param languageName Der Sprachname unter der die Sprache registriert werden soll
     */
    void addLanguage(LanguageFile languageFile, String languageName);

    /**
     * Gibt eine Registrierte Sprache zurück
     * wenn gefunden
     *
     * @param name Der Name Der Sprache
     * @return Die Sprache wenn vorhanden
     * @throws NullPointerException Wenn die Sprache nicht gefunden wurde
     */
    LanguageFile getLanguage(String name) throws NullPointerException;

    /**
     * Gibt die Liste aller registrierten Sprachen zurück
     *
     * @return Liste aller registrierten Sprachen
     */
    Map<String, LanguageFile> getRegisteredLanguages();

    /**
     * Entfernt eine Registrierte Sprache
     * aus der Liste
     *
     * @param languageFile der Name der Sprache
     */
    void removeLanguage(String languageFile);

    /**
     * Setzt die Sprache die von dem System benutzt wird!
     *
     * @param languageFile Der Language File
     * @throws NullPointerException Wenn die Sprache {@code null} ist
     */
    void setCurrentLanguage(LanguageFile languageFile) throws NullPointerException;

    /**
     * Gibt dir eine Nachricht mit Placeholders zurück,
     * wenn gefunden aus der Sprache die gerade eingestellt ist!
     *
     * @param path   Der Pfad zu der Nachricht in der Config
     * @param player Der Spieler für die Placeholders
     * @return Die Nachricht (wenn gefunden) mit Placeholders
     */
    String getMessage(String path, Player player);

    /**
     * Gibt dir eine Nachricht mit Placeholders zurück,
     * wenn gefunden aus der Sprache die angegeben ist (sie muss im System vorhanden sein, also erst {@code addLanguage()})!
     *
     * @param path     Der Pfad zu der Nachricht in der Config
     * @param player   Der Spieler für die Placeholders
     * @param langName Der Sprachen Name falls gewollt!
     * @return Die Nachricht (wenn gefunden) mit Placeholders
     */
    String getMessage(String path, Player player, String langName);

    /**
     * Gibt dir ein Item mit Placeholders im Namen und Lore zurück,
     * wenn gefunden aus der Sprache die gerade eingestellt ist!
     *
     * @param path   Der Pfad zum Item in der Config
     * @param player Der Spieler für die Placeholders
     * @return Das Item (wenn gefunden) mit Placeholders im Namen und Lore
     */
    ItemStack getItem(String path, Player player);

    /**
     * Gibt dir ein Item mit Placeholders im Namen und Lore zurück,
     * wenn gefunden aus der Sprache die angegeben ist (sie muss im System vorhanden sein, also erst {@code addLanguage()})!
     *
     * @param path     Der Pfad zum Item in der Config
     * @param player   Der Spieler für die Placeholders
     * @param langName Der Sprachen Name falls gewollt!
     * @return Das Item (wenn gefunden) mit Placeholders im Namen und Lore
     */
    ItemStack getItem(String path, Player player, String langName);

    /**
     * Gibt dir ein Item mit Placeholders im Namen und Lore zurück,
     * wenn gefunden aus der Sprache die gerade eingestellt ist!
     *
     * @param path   Der Pfad zum Item in der Config
     * @param player Der Spieler für die Placeholders
     * @return Den Menu Title mit Placeholders
     */
    String getMenuTitle(String path, Player player);

    /**
     * Gibt dir einen Menu Title mit Placeholders zurück,
     * wenn gefunden aus der Sprache die angegeben ist (sie muss im System vorhanden sein, also erst {@code addLanguage()})!
     *
     * @param path     Der Pfad zum Item in der Config
     * @param player   Der Spieler für die Placeholders
     * @param langName Der Sprachen Name falls gewollt!
     * @return Den Menu Title mit Placeholders
     */
    String getMenuTitle(String path, Player player, String langName);

    /**
     * Reloaded die Config
     * (Sprach Files, Config)
     *
     * @param messageReceiver Der Spieler der Nachrichten erhält
     *                        Der Speiler darf nicht null sein!
     */
    void reloadConfigurationFiles(Player messageReceiver);

    // Events

    /**
     * Ein Admin Panel Event aufrufen!
     *
     * @param event Das Event das aufgerufen werden soll
     * @return Das Event
     * @throws NotAPanelEventException Wenn das Event nicht AdminPanelEvent extended
     */
    AdminPanelEvent callAdminPanelEvent(Event event) throws NotAPanelEventException;
}
