package me.aj.uhc.arena;

import com.google.common.collect.Lists;
import me.aj.gm.util.LocationUtils;
import me.aj.uhc.SpeedUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class Arena {

    private List<Location> playerSpawns = Lists.newArrayList();

    private World world;

    private Location center;//TODO config

    public Arena() {
        //Load all Spawns from Config
        List<String> locations = SpeedUHC.get().getConfig().getStringList("playerSpawns");
        playerSpawns.addAll(locations.stream().map(LocationUtils::stringToLocation).collect(Collectors.toList()));
        world = playerSpawns.get(0).getWorld();
        center = LocationUtils.stringToLocation(SpeedUHC.get().getConfig().getString("center"));
    }

    public Location getCenter() {
        return center;
    }

    public World getWorld() {
        return world;
    }

    public void teleportAll() {
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        for (int x = 0; x < players.size(); x++) {
            players.get(x).teleport(playerSpawns.get(x));
        }
    }

    public void deleteCages(Material m) {
        for (Location l : playerSpawns) {
            LocationUtils.getBlocks(l, 5).stream().filter(bloc ->
                    bloc.getBlock().getType() == m)
                    .forEach(bloc -> bloc.getBlock().setType(Material.AIR));
        }
    }
}
