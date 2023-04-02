package net.runelite.client.plugins.gemtracker;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

class GemTrackerSession
{
    @Getter
    @Setter
    private Instant lastMinedGem;
}
