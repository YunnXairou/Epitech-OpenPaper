package eu.epitech.openpaper.events;

import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import eu.epitech.openpaper.WorldBorder;

public class AdvancementListener implements Listener {

    @EventHandler
    public void OnGetAdvancement(PlayerAdvancementDoneEvent adv) {
        Advancement trophy = adv.getAdvancement();

        if (trophy.getKey().asString().startsWith("minecraft:recipes")
                || WorldBorder.trophies.indexOf(trophy) >= 0)
            return;

        WorldBorder.trophies.add(trophy);
        WorldBorder.compute();
    }
}
