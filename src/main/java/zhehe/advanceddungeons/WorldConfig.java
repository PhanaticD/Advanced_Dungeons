/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zhehe.advanceddungeons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import static zhehe.advanceddungeons.AdvancedDungeons.configDirName;

/**
 *
 * @author Zhehe
 */
public class WorldConfig {
    public static final transient String configFileName = "world.json";
    
    public Set<String> world = new HashSet<>();
    public void addWorld(String name) {
        world.add(name);
        save();
    }
    public void removeWorld(String name) {
        world.remove(name);
        save();
    }
    public String listWorld() {
        StringBuilder sb = new StringBuilder();
        for(String item : world) {
            sb.append('#');
            sb.append(item);
            sb.append(',');
        }
        if(sb.length() > 0) sb.setLength(sb.length() - 1);
        
        return sb.toString();
    }
    public boolean isDungeon(String world_name) {
        return world.contains(world_name);
    }
    public boolean isDungeon(World w) {
        return world.contains(w.getName());
    }
    public void init() {
        File directory = new File(configDirName);
        if(!directory.exists()) {
            directory.mkdir();
        }
        String file_path = configDirName + File.separator + configFileName;
        File file = new File(file_path);
        if(file.exists()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_path), "UTF8"))) {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                WorldConfig wc = (new Gson()).fromJson(sb.toString(), WorldConfig.class);
                this.world = wc.world;
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                Bukkit.getLogger().log(Level.SEVERE, sw.toString());
            }
        }
    }
    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        String file_path = configDirName + File.separator + configFileName;
        File file = new File(file_path);
        try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while saving world config file.");
        }
    }
}
