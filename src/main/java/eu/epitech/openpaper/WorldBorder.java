package eu.epitech.openpaper;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;

public class WorldBorder {
    static public ArrayList<Advancement> trophies;

    static public void compute() {
        double expantion = OpenPaper.plugin.getConfig().getDouble("worldborder.base")
                + trophies.size() * OpenPaper.plugin.getConfig().getDouble("worldborder.onAchievment");

        for (World w : Bukkit.getWorlds())
            w.getWorldBorder().setSize(expantion);
    }

    WorldBorder() {
        getTrophies();

        compute();
    }

    private void getTrophies() {
        Iterator<Advancement> advancement = Bukkit.advancementIterator();
        trophies = new ArrayList<>();

        while (advancement.hasNext()) {
            Advancement trophy = advancement.next();

            if (trophy.getKey().asString().startsWith("minecraft:recipes"))
                continue;

            for (OfflinePlayer p : Bukkit.getOfflinePlayers())
                if (p.getPlayer().getAdvancementProgress(trophy).isDone()) {
                    trophies.add(trophy);
                    break;
                }
        }
    }
}
