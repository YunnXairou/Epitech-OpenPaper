package eu.epitech.openpaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

public class WorldBorder {
    static public FileConfiguration config;

    static public Map<String, ArrayList<Advancement>> trophies;
    static public ArrayList<OfflinePlayer> witherKiller;
    static public ArrayList<OfflinePlayer> dragonKiller;

    static public void compute() {
        double expantion = config.getDouble("worldborder.base")
                + witherKiller.size() * config.getDouble("worldborder.witherKiller")
                + dragonKiller.size() * config.getDouble("worldborder.dragonKiller");

        for (String type : trophies.keySet())
            expantion += trophies.get(type).size()
                    * OpenPaper.plugin.getConfig().getDouble("worldborder.advancement." + type);

        for (World w : Bukkit.getWorlds())
            w.getWorldBorder().setSize(expantion);
    }

    WorldBorder() {
        config = OpenPaper.plugin.getConfig();

        getTrophies();
        getStats();

        compute();
    }

    private void getTrophies() {
        trophies = new HashMap<>();
        Iterator<Advancement> advancement = Bukkit.advancementIterator();

        while (advancement.hasNext()) {
            Advancement trophy = advancement.next();
            String type = trophy.getKey().asString().split("[:/]")[1];

            if (!config.isInt("worldborder.advancement." + type))
                continue;

            trophies.putIfAbsent(type, new ArrayList<Advancement>());
            for (OfflinePlayer p : Bukkit.getOfflinePlayers())
                if (p.getPlayer().getAdvancementProgress(trophy).isDone()) {
                    trophies.get(type).add(trophy);
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
