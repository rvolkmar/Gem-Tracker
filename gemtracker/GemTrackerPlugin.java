package net.runelite.client.plugins.gemtracker;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.fishing.FishingConfig;
import net.runelite.client.plugins.xptracker.XpTrackerPlugin;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@PluginDescriptor(
        name = "Gem Rock Tracker",
        description = "A plugin that tracks gems from gem rocks that you mine in the Shilo Village underground mine.",
        tags = {"gem", "mining"}
)

public class GemTrackerPlugin extends Plugin
{
    @Getter(AccessLevel.PACKAGE)
    private final GemTrackerSession session = new GemTrackerSession();

    @Inject
    private Client client;

    @Inject
    private Notifier notifier;
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private GemTrackerConfig config;

    @Inject
    private GemTrackerOverlay overlay;

    @Provides
    GemTrackerConfig provideConfig(ConfigManager configManager) {return configManager.getConfig(GemTrackerConfig.class);}

    void reset() {session.setLastMinedGem(null); }

    @Override
    protected void startUp() throws Exception{
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }

}