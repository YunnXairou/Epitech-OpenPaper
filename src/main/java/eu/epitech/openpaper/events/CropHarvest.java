package eu.epitech.openpaper.events;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import eu.epitech.openpaper.OpenPaper;

/**
 * Copied from pablo3x6/Grains CropHarvest
 */
public class CropHarvest implements Listener {
    private static final Map<Material, Material> crops = new HashMap(5);
    static {
        crops.put(Material.WHEAT, Material.WHEAT_SEEDS);
        crops.put(Material.POTATOES, Material.POTATOES);
        crops.put(Material.CARROTS, Material.CARROTS);
        crops.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
        crops.put(Material.NETHER_WART, Material.NETHER_WART);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (!OpenPaper.plugin.getConfig().getBoolean("events.harvest-crop")) {
            return;
        }

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Block block = e.getClickedBlock();

        if (!crops.containsKey(block.getType()) || !(block.getBlockData() instanceof Ageable)) {
            return;
        }

        harvestCrop(block.getType(), e);
    }

    void sendServerPacket(ProtocolManager pm, Player p, PacketContainer pc) {
        try {
            pm.sendServerPacket(p, pc);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    void harvestCrop(Material m, PlayerInteractEvent e) {
        Block block = e.getClickedBlock();

        Ageable crop = (Ageable) block.getBlockData();
        if (crop.getAge() != crop.getMaximumAge())
            return;

        Material seed = crops.get(m);
        boolean SeedInDrop = false;
        for (ItemStack is : block.getDrops())
            if (is.getType() == seed) {
                SeedInDrop = true;
            }

        // http://wiki.vg/Protocol#Animation
        ProtocolManager pm = ProtocolLibrary.getProtocolManager();

        PacketContainer handAnimation = pm.createPacket(PacketType.Play.Server.ANIMATION, false);
        handAnimation.getEntityModifier(e.getPlayer().getWorld()).write(0,
                e.getPlayer());
        handAnimation.getIntegers().write(1, 0);

        if (SeedInDrop) {
            block.getDrops().remove(new ItemStack(seed, 1));
        } else if (e.getPlayer().getInventory().containsAtLeast(new ItemStack(seed), 1)) {
            e.getPlayer().getInventory().remove(new ItemStack(seed, 1));
        }

        sendServerPacket(pm, e.getPlayer(), handAnimation);

        block.breakNaturally();
        block.setType(m);
    }
}
