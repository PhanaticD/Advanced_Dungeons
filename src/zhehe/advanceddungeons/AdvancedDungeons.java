/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons;

import greymerk.roguelike.DungeonGenerator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Zhehe
 */
public class AdvancedDungeons extends JavaPlugin {
    public static String version = "0.1.0";
    public static String date = "09/13/2019";
    public static boolean enabled = false;
    public static final String configDirName = "plugins" + File.separator + "advanced_dungeons";
    private static WorldConfig wc;
    private static final String logfile = configDirName + File.separator + "log.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void logMessage(String message)
    {		
        try
        {
            FileWriter writer = new FileWriter(logfile, true);
            writer.write(AdvancedDungeons.dateFormat.format(new Date()) + " " + message);
            writer.write("\n");
            writer.close();
        }
        catch(IOException e)
        {
            Bukkit.getLogger().info("Failed to write to log file " + logfile);
        }
    }

    
    @Override
    public void onEnable() {
        wc = new WorldConfig();
        wc.init();
        enabled = true;
        getServer().getPluginManager().registerEvents(new DLDWorldListener(), this);
        Bukkit.getLogger().log(Level.INFO, "              _                               _ ");
        Bukkit.getLogger().log(Level.INFO, "     /\\      | |                             | |");
        Bukkit.getLogger().log(Level.INFO, "    /  \\   __| |_   ____ _ _ __   ___ ___  __| |");
        Bukkit.getLogger().log(Level.INFO, "   / /\\ \\ / _` \\ \\ / / _` | '_ \\ / __/ _ \\/ _` |");
        Bukkit.getLogger().log(Level.INFO, "  / ____ \\ (_| |\\ V / (_| | | | | (_|  __/ (_| |");
        Bukkit.getLogger().log(Level.INFO, " /_/    \\_\\__,_| \\_/ \\__,_|_| |_|\\___\\___|\\__,_|");
        Bukkit.getLogger().log(Level.INFO, "  _____                                         ");
        Bukkit.getLogger().log(Level.INFO, " |  __ \\                                        ");
        Bukkit.getLogger().log(Level.INFO, " | |  | |_   _ _ __   __ _  ___  ___  _ __  ___ ");
        Bukkit.getLogger().log(Level.INFO, " | |  | | | | | '_ \\ / _` |/ _ \\/ _ \\| '_ \\/ __|");
        Bukkit.getLogger().log(Level.INFO, " | |__| | |_| | | | | (_| |  __/ (_) | | | \\__ \\");
        Bukkit.getLogger().log(Level.INFO, " |_____/ \\__,_|_| |_|\\__, |\\___|\\___/|_| |_|___/");
        Bukkit.getLogger().log(Level.INFO, "                      __/ |                     ");
        Bukkit.getLogger().log(Level.INFO, "                     |___/                      ");
//        BukkitRunnable run = new BukkitRunnable() {
//            @Override
//            public void run() {
//                WorldCreator wc = new WorldCreator("testw");
//                World world = wc.createWorld();
//                DungeonGenerator gen = new DungeonGenerator();
//                gen.forcePopulate(world, new Random(), world.getChunkAt(0, 0));
//                this.cancel();
//            }
//        };
//        run.runTaskTimer(this, 1, 5);
    }
    
    private boolean senderHasOPPermission(final CommandSender sender) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (!player.hasPermission("advanceddungeons.op")) {
                player.sendMessage("You don't have the permission required to use this plugin");
                return false;
            }
        }
        return true;
    }
    
    private class DLDWorldListener implements Listener {
        @EventHandler(priority = EventPriority.LOW)
        public void onWorldInit(WorldInitEvent event) {
            if(!enabled) return;
            String world_name = event.getWorld().getName();
            if(wc.isDungeon(world_name)) {
                Bukkit.getLogger().log(Level.INFO, "Add AdvancedDungeons Populator to world: " + world_name);
                event.getWorld().getPopulators().add(new DungeonGenerator());
            } else {
                Bukkit.getLogger().log(Level.INFO, "AdvancedDungeons Populator is not used in " + world_name);
            }
        }
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
//        if (sender instanceof Player) {
//            final Player player = (Player) sender;
//            if (!player.hasPermission("advanceddungeons.op")) {
//                player.sendMessage("You don't have the permission required to use this plugin");
//                return true;
//            }
//        }
        
        if (command.getName().equalsIgnoreCase("advanceddungeons")) {

            switch (args.length) {
                default:  // /advanceddungeons help
                    Bukkit.getLogger().log(Level.INFO, "Advanced Dungeons Help");
//                    Bukkit.getLogger().log(Level.INFO, "[OP] /advanceddungeons create worldname");
                    Bukkit.getLogger().log(Level.INFO, "/advanceddungeons enter worldname");
                    Bukkit.getLogger().log(Level.INFO, "[OP] /advanceddungeons apply worldname");
                    Bukkit.getLogger().log(Level.INFO, "[OP] /advanceddungeons unapply worldname");
                    break;
                case 2: // /advanceddungeons options worldname
                    String option = args[0];
                    String worldName = args[1];
                    /*if(option.equals("create")) {
                        if(!senderHasOPPermission(sender)) return true;
                        wc.addWorld(worldName);
                        
                        WorldCreator wc = new WorldCreator(worldName);
                        wc.generateStructures(false);
                        wc.environment(World.Environment.NORMAL);
                        wc.seed(UUID.randomUUID().toString().hashCode());
                        
                        wc.createWorld();
                    } else*/ if(option.equals("enter")) {
                        if (!(sender instanceof Player)) {
                            sender.sendMessage("Player only command");
                            return true;
                        }
                        if(!wc.isDungeon(worldName)) {
                            sender.sendMessage(worldName + " is not a dungeon world");
                            return true;
                        }
                        Player player = (Player) sender;
                        World world = Bukkit.getWorld(worldName);
                        if(world == null) {
                            sender.sendMessage("Invalid world name");
                            return true;
                        }
                        player.teleport(world.getSpawnLocation());
                    } else if(option.equals("apply")) {
                        wc.addWorld(worldName);
                        sender.sendMessage("Done");
                        return true;
                    } else if(option.equals("unapply")) {
                        wc.removeWorld(worldName);
                        sender.sendMessage("Done");
                        return true;
                    } else {
                        sender.sendMessage("Invalid options...");
                        return true;
                    }
                    break;
            }
        }
        return false;
    }
}
