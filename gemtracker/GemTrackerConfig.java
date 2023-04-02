package net.runelite.client.plugins.gemtracker;

// Mandatory imports
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("gemtracker")
public interface GemTrackerConfig extends Config
        {
            @ConfigItem(
                position = 1,
                keyName = "trackGemMiningSessionStats",
                name = "Track mining stats?",
                description = "Keeps track of overall session while your mining."
            )
            default boolean trackGemMiningSessionStats() { return true; }

            @ConfigItem(
                    position = 2,
                    keyName = "trackGemTypes",
                    name = "Track gems types?",
                    description = "Keeps track of gem types while your mining."
            )
            default boolean trackGemTypes() { return true; }
}