package eu.epitech.openpaper;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.EntityType;

public class WorldBorder {
    static public ArrayList<Advancement> trophies;
    static public ArrayList<OfflinePlayer> witherKiller;
    static public ArrayList<OfflinePlayer> dragonKiller;

    static public void compute() {
        double expantion = OpenPaper.plugin.getConfig().getDouble("worldborder.base")
                + trophies.size() * OpenPaper.plugin.getConfig().getDouble("worldborder.onAchievment")
                + witherKiller.size() * OpenPaper.plugin.getConfig().getDouble("worldborder.witherKiller")
                + dragonKiller.size() * OpenPaper.plugin.getConfig().getDouble("worldborder.dragonKiller");

        for (World w : Bukkit.getWorlds())
            w.getWorldBorder().setSize(expantion);
    }

    WorldBorder() {
        getTrophies();
        getStats();

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

    private void getStats() {
        witherKiller = new ArrayList<>();
        dragonKiller = new ArrayList<>();

        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getStatistic(Statistic.KILL_ENTITY, EntityType.WITHER) > 0)
                witherKiller.add(p);
            if (p.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDER_DRAGON) > 0)
                dragonKiller.add(p);
        }

    }
}
