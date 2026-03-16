package net.minecraft.src;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class StatFileWriter
{
    private Map field_77457_a = new HashMap();
    private Map field_77455_b = new HashMap();
    private boolean field_77456_c = false;
    private StatsSyncher statsSyncher;

    public StatFileWriter(Session par1Session, File par2File)
    {
        this.statsSyncher = new StatsSyncher(par1Session, this, null);
    }

    public void readStat(StatBase par1StatBase, int par2)
    {
        this.writeStatToMap(this.field_77455_b, par1StatBase, par2);
        this.writeStatToMap(this.field_77457_a, par1StatBase, par2);
        this.field_77456_c = true;
    }

    private void writeStatToMap(Map par1Map, StatBase par2StatBase, int par3)
    {
        Integer var4 = (Integer)par1Map.get(par2StatBase);
        int var5 = var4 == null ? 0 : var4.intValue();
        par1Map.put(par2StatBase, Integer.valueOf(var5 + par3));
    }

    public Map func_77445_b()
    {
        return new HashMap(this.field_77455_b);
    }

    /**
     * write a whole Map of stats to the statmap
     */
    public void writeStats(Map par1Map)
    {
        if (par1Map != null)
        {
            this.field_77456_c = true;
            Iterator var2 = par1Map.keySet().iterator();

            while (var2.hasNext())
            {
                StatBase var3 = (StatBase)var2.next();
                this.writeStatToMap(this.field_77455_b, var3, ((Integer)par1Map.get(var3)).intValue());
                this.writeStatToMap(this.field_77457_a, var3, ((Integer)par1Map.get(var3)).intValue());
            }
        }
    }

    public void func_77452_b(Map par1Map)
    {
        if (par1Map != null)
        {
            Iterator var2 = par1Map.keySet().iterator();

            while (var2.hasNext())
            {
                StatBase var3 = (StatBase)var2.next();
                Integer var4 = (Integer)this.field_77455_b.get(var3);
                int var5 = var4 == null ? 0 : var4.intValue();
                this.field_77457_a.put(var3, Integer.valueOf(((Integer)par1Map.get(var3)).intValue() + var5));
            }
        }
    }

    public void func_77448_c(Map par1Map)
    {
        if (par1Map != null)
        {
            this.field_77456_c = true;
            Iterator var2 = par1Map.keySet().iterator();

            while (var2.hasNext())
            {
                StatBase var3 = (StatBase)var2.next();
                this.writeStatToMap(this.field_77455_b, var3, ((Integer)par1Map.get(var3)).intValue());
            }
        }
    }

    public static Map func_77453_b(String par0Str)
    {
        HashMap var1 = new HashMap();

        try
        {
            String var2 = "local";
            StringBuilder var3 = new StringBuilder();
            JSONObject var4 = new JSONObject(par0Str);
            JSONArray var5 = var4.optJSONArray("stats-change");

            if (var5 != null)
            {
                for (int i = 0; i < var5.length(); ++i)
                {
                    Object entryObj = var5.get(i);

                    if (entryObj instanceof JSONObject)
                    {
                        JSONObject var7 = (JSONObject)entryObj;
                        Iterator var8 = var7.keys();

                        if (var8.hasNext())
                        {
                            String key = (String)var8.next();
                            int var10 = Integer.parseInt(key);
                            int var11 = Integer.parseInt(var7.get(key).toString());
                            StatBase var12 = StatList.getOneShotStat(var10);

                            if (var12 == null)
                            {
                                System.out.println(var10 + " is not a valid stat");
                            }
                            else
                            {
                                var3.append(var12.statGuid).append(",");
                                var3.append(var11).append(",");
                                var1.put(var12, Integer.valueOf(var11));
                            }
                        }
                    }
                }
            }

            MD5String var14 = new MD5String(var2);
            String var15 = var14.getMD5String(var3.toString());
            String checksum = var4.optString("checksum", null);

            if (checksum == null || !var15.equals(checksum))
            {
                System.out.println("CHECKSUM MISMATCH");
                return null;
            }
        }
        catch (Exception var13)
        {
            var13.printStackTrace();
        }

        return var1;
    }

    public static String func_77441_a(String par0Str, String par1Str, Map par2Map)
    {
        JSONObject root = new JSONObject();
        StringBuilder var4 = new StringBuilder();

        if (par0Str != null && par1Str != null)
        {
            JSONObject user = new JSONObject();
            user.put("name", par0Str);
            user.put("sessionid", par1Str);
            root.put("user", user);
        }

        JSONArray stats = new JSONArray();
        Iterator var6 = par2Map.keySet().iterator();

        while (var6.hasNext())
        {
            StatBase var7 = (StatBase)var6.next();
            Object val = par2Map.get(var7);
            JSONObject entry = new JSONObject();
            entry.put(Integer.toString(var7.statId), val);
            stats.put(entry);
            var4.append(var7.statGuid).append(",");
            var4.append(val).append(",");
        }

        root.put("stats-change", stats);
        MD5String var8 = new MD5String(par1Str);
        root.put("checksum", var8.getMD5String(var4.toString()));
        return root.toString();
    }

    /**
     * Returns true if the achievement has been unlocked.
     */
    public boolean hasAchievementUnlocked(Achievement par1Achievement)
    {
        return this.field_77457_a.containsKey(par1Achievement);
    }

    /**
     * Returns true if the parent has been unlocked, or there is no parent
     */
    public boolean canUnlockAchievement(Achievement par1Achievement)
    {
        return par1Achievement.parentAchievement == null || this.hasAchievementUnlocked(par1Achievement.parentAchievement);
    }

    public int writeStat(StatBase par1StatBase)
    {
        Integer var2 = (Integer)this.field_77457_a.get(par1StatBase);
        return var2 == null ? 0 : var2.intValue();
    }

    public void syncStats()
    {
        this.statsSyncher.syncStatsFileWithMap(this.func_77445_b());
    }

    public void func_77449_e()
    {
        if (this.field_77456_c && this.statsSyncher.func_77425_c())
        {
            this.statsSyncher.beginSendStats(this.func_77445_b());
        }

        this.statsSyncher.func_77422_e();
    }
}
