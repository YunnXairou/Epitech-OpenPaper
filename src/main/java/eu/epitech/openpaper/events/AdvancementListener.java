package eu.epitech.openpaper.events;

import org.bukkit.advancement.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import eu.epitech.openpaper.OpenPaper;
import eu.epitech.openpaper.WorldBorder;

public class AdvancementListener implements Listener {

    @EventHandler
    public void OnGetAdvancement(PlayerAdvancementDoneEvent adv) {
        Advancement trophy = adv.getAdvancement();
        String type = trophy.getKey().asString().split("[:/]")[1];

        if (!WorldBorder.config.isInt("worldborder.advancement." + type)
                || WorldBorder.trophies.get(type).indexOf(trophy) >= 0)
            return;

        OpenPaper.plugin.getLogger().info("ahaha " + WorldBorder.trophies.size());
        OpenPaper.plugin.getLogger().info("ahaha " + WorldBorder.trophies.get(type).size());

        WorldBorder.trophies.get(type).add(trophy);
        WorldBorder.compute();
    }
}
