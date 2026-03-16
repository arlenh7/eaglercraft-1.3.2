package net.minecraft.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lax1dude.eaglercraft.EagRuntime;

public class AchievementMap
{
    /** Holds the singleton instance of AchievementMap. */
    public static AchievementMap instance = new AchievementMap();

    /** Maps a achievement id with it's unique GUID. */
    private Map guidMap = new HashMap();

    private AchievementMap()
    {
        loadLines("achievement/map.txt");
        loadLines("/achievement/map.txt");
        loadLines("assets/minecraft/achievement/map.txt");
    }

    private void loadLines(String path)
    {
        List<String> lines = EagRuntime.getResourceLines(path);
        if (lines == null)
        {
            return;
        }

        for (int i = 0, l = lines.size(); i < l; ++i)
        {
            String line = lines.get(i);
            if (line == null)
            {
                continue;
            }
            line = line.trim();
            if (line.length() == 0)
            {
                continue;
            }

            try
            {
                String[] parts = line.split(",");
                if (parts.length >= 2)
                {
                    int id = Integer.parseInt(parts[0]);
                    this.guidMap.put(Integer.valueOf(id), parts[1]);
                }
            }
            catch (Throwable t)
            {
                // ignore malformed entries
            }
        }
    }

    /**
     * Returns the unique GUID of a achievement id.
     */
    public static String getGuid(int par0)
    {
        return (String)instance.guidMap.get(Integer.valueOf(par0));
    }
}
