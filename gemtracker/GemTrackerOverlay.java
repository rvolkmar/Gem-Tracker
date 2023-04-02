package net.runelite.client.plugins.gemtracker;

import com.google.common.collect.ImmutableSet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.GraphicID;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY;
import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import net.runelite.api.Skill;
import net.runelite.client.plugins.xptracker.XpTrackerService;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

class GemTrackerOverlay extends OverlayPanel
{
    private static final String MINING_SPOT = "Mining spot";
    private static final String GEMTRACKER_RESET = "Reset";

    private static final Set<Integer> MINING_ANIMATION = ImmutableSet.of(
            AnimationID.MINING_BRONZE_PICKAXE,
            AnimationID.MINING_IRON_PICKAXE,
            AnimationID.MINING_STEEL_PICKAXE,
            AnimationID.MINING_BLACK_PICKAXE,
            AnimationID.MINING_MITHRIL_PICKAXE,
            AnimationID.MINING_ADAMANT_PICKAXE,
            AnimationID.MINING_RUNE_PICKAXE,
            AnimationID.MINING_GILDED_PICKAXE,
            AnimationID.MINING_DRAGON_PICKAXE,
            AnimationID.MINING_3A_PICKAXE,
            AnimationID.MINING_INFERNAL_PICKAXE,
            AnimationID.MINING_CRYSTAL_PICKAXE);

    private final Client client;
    private GemTrackerPlugin plugin;
    private final GemTrackerConfig config;
    private final XpTrackerService xpTrackerService;

    @Inject
    public GemTrackerOverlay(Client client, GemTrackerPlugin plugin, GemTrackerConfig config, XpTrackerService xpTrackerService)
    {
        super(plugin);
        setPosition(OverlayPosition.TOP_LEFT);
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.xpTrackerService = xpTrackerService;
        addMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Gem Tracker overlay");
        addMenuEntry(RUNELITE_OVERLAY, GEMTRACKER_RESET, "Gem Tracker overlay", e -> plugin.reset());
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!config.trackGemMiningSessionStats() || plugin.getSession().setLastMinedGem() == null)
        {
            return null;
        }

        if (client.getLocalPlayer().getInteracting() != null
                && client.getLocalPlayer().getInteracting().getName().contains(MINING_SPOT))
        {
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("Mining")
                    .color(Color.GREEN)
                    .build());
        }
        else
        {
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("NOT Mining")
                    .color(Color.RED)
                    .build());
        }

        int actions = xpTrackerService.getActions(Skill.MINING);
        if (actions > 0)
        {
            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Gems mined:")
                    .right(Integer.toString(actions))
                    .build());

            if (actions > 2)
            {
                panelComponent.getChildren().add(LineComponent.builder()
                        .left("Gems/hr:")
                        .right(Integer.toString(xpTrackerService.getActionsHr(Skill.MINING)))
                        .build());
            }
        }

        return super.render(graphics);
    }
}