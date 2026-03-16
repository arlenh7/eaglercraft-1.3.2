package net.minecraft.src;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.peyton.eagler.minecraft.suppliers.EntitySupplier;

public class EntityList
{
    /** Provides a mapping between entity classes and a string */
    private static Map<String, Class<? extends Entity>> stringToClassMapping = new HashMap();

    /** Provides a mapping between a string and an entity classes */
    private static Map<Class<? extends Entity>, String> classToStringMapping = new HashMap();

    /** provides a mapping between an entityID and an Entity Class */
    private static Map<Integer, Class<? extends Entity>> IDtoClassMapping = new HashMap();

    /** provides a mapping between an Entity Class and an entity ID */
    private static Map<Class<? extends Entity>, Integer> classToIDMapping = new HashMap();

    /** Maps entity names to their numeric identifiers */
    private static Map<String, Integer> stringToIDMapping = new HashMap();

    /** Provides a mapping between names/IDs and suppliers to avoid reflection */
    private static Map<String, EntitySupplier<? extends Entity>> stringToSupplierMapping = new HashMap();
    private static Map<Integer, EntitySupplier<? extends Entity>> IDtoSupplierMapping = new HashMap();
    private static Map<Class<? extends Entity>, EntitySupplier<? extends Entity>> classToSupplierMapping = new HashMap();

    /** This is a HashMap of the Creative Entity Eggs/Spawners. */
    public static HashMap<Integer, EntityEggInfo> entityEggs = new LinkedHashMap();

    /**
     * adds a mapping between Entity classes and both a string representation and an ID
     */
    private static void addMapping(Class<? extends Entity> par0Class, EntitySupplier<? extends Entity> supplier, String par1Str, int par2)
    {
        stringToClassMapping.put(par1Str, par0Class);
        classToStringMapping.put(par0Class, par1Str);
        IDtoClassMapping.put(Integer.valueOf(par2), par0Class);
        classToIDMapping.put(par0Class, Integer.valueOf(par2));
        stringToIDMapping.put(par1Str, Integer.valueOf(par2));
        stringToSupplierMapping.put(par1Str, supplier);
        IDtoSupplierMapping.put(Integer.valueOf(par2), supplier);
        classToSupplierMapping.put(par0Class, supplier);
    }

    /**
     * Adds a entity mapping with egg info.
     */
    private static void addMapping(Class<? extends Entity> par0Class, EntitySupplier<? extends Entity> supplier, String par1Str, int par2, int par3, int par4)
    {
        addMapping(par0Class, supplier, par1Str, par2);
        entityEggs.put(Integer.valueOf(par2), new EntityEggInfo(par2, par3, par4));
    }

    /**
     * Create a new instance of an entity in the world by using the entity name.
     */
    public static Entity createEntityByName(String par0Str, World par1World)
    {
        Entity var2 = null;

        try
        {
            EntitySupplier<? extends Entity> var3 = stringToSupplierMapping.get(par0Str);

            if (var3 != null)
            {
                var2 = var3.createEntity(par1World);
            }
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
        }

        return var2;
    }

    /**
     * create a new instance of an entity from NBT store
     */
    public static Entity createEntityFromNBT(NBTTagCompound par0NBTTagCompound, World par1World)
    {
        Entity var2 = null;

        try
        {
            EntitySupplier<? extends Entity> var3 = stringToSupplierMapping.get(par0NBTTagCompound.getString("id"));

            if (var3 != null)
            {
                var2 = var3.createEntity(par1World);
            }
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
        }

        if (var2 != null)
        {
            var2.readFromNBT(par0NBTTagCompound);
        }
        else
        {
            System.out.println("Skipping Entity with id " + par0NBTTagCompound.getString("id"));
        }

        return var2;
    }

    /**
     * Create a new instance of an entity in the world by using an entity ID.
     */
    public static Entity createEntityByID(int par0, World par1World)
    {
        Entity var2 = null;

        try
        {
            EntitySupplier<? extends Entity> var3 = IDtoSupplierMapping.get(Integer.valueOf(par0));

            if (var3 != null)
            {
                var2 = var3.createEntity(par1World);
            }
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
        }

        if (var2 == null)
        {
            System.out.println("Skipping Entity with id " + par0);
        }

        return var2;
    }

    /**
     * gets the entityID of a specific entity
     */
    public static int getEntityID(Entity par0Entity)
    {
        Class var1 = par0Entity.getClass();
        return classToIDMapping.containsKey(var1) ? ((Integer)classToIDMapping.get(var1)).intValue() : 0;
    }

    /**
     * Create a new instance of an entity in the world by using its class.
     */
    public static Entity createEntityByClass(Class<? extends Entity> par0Class, World par1World)
    {
        Entity var2 = null;

        try
        {
            EntitySupplier<? extends Entity> var3 = classToSupplierMapping.get(par0Class);

            if (var3 != null)
            {
                var2 = var3.createEntity(par1World);
            }
        }
        catch (Exception var4)
        {
            var4.printStackTrace();
        }

        return var2;
    }

    /**
     * Gets the string representation of a specific entity.
     */
    public static String getEntityString(Entity par0Entity)
    {
        return (String)classToStringMapping.get(par0Entity.getClass());
    }

    /**
     * Finds the class using IDtoClassMapping and classToStringMapping
     */
    public static String getStringFromID(int par0)
    {
        Class var1 = (Class)IDtoClassMapping.get(Integer.valueOf(par0));
        return var1 != null ? (String)classToStringMapping.get(var1) : null;
    }

    static
    {
        addMapping(EntityItem.class, EntityItem::new, "Item", 1);
        addMapping(EntityXPOrb.class, EntityXPOrb::new, "XPOrb", 2);
        addMapping(EntityPainting.class, EntityPainting::new, "Painting", 9);
        addMapping(EntityArrow.class, EntityArrow::new, "Arrow", 10);
        addMapping(EntitySnowball.class, EntitySnowball::new, "Snowball", 11);
        addMapping(EntityFireball.class, EntityFireball::new, "Fireball", 12);
        addMapping(EntitySmallFireball.class, EntitySmallFireball::new, "SmallFireball", 13);
        addMapping(EntityEnderPearl.class, EntityEnderPearl::new, "ThrownEnderpearl", 14);
        addMapping(EntityEnderEye.class, EntityEnderEye::new, "EyeOfEnderSignal", 15);
        addMapping(EntityPotion.class, EntityPotion::new, "ThrownPotion", 16);
        addMapping(EntityExpBottle.class, EntityExpBottle::new, "ThrownExpBottle", 17);
        addMapping(EntityTNTPrimed.class, EntityTNTPrimed::new, "PrimedTnt", 20);
        addMapping(EntityFallingSand.class, EntityFallingSand::new, "FallingSand", 21);
        addMapping(EntityMinecart.class, EntityMinecart::new, "Minecart", 40);
        addMapping(EntityBoat.class, EntityBoat::new, "Boat", 41);
        addMapping(EntityLiving.class, null, "Mob", 48);
        addMapping(EntityMob.class, null, "Monster", 49);
        addMapping(EntityCreeper.class, EntityCreeper::new, "Creeper", 50, 894731, 0);
        addMapping(EntitySkeleton.class, EntitySkeleton::new, "Skeleton", 51, 12698049, 4802889);
        addMapping(EntitySpider.class, EntitySpider::new, "Spider", 52, 3419431, 11013646);
        addMapping(EntityGiantZombie.class, EntityGiantZombie::new, "Giant", 53);
        addMapping(EntityZombie.class, EntityZombie::new, "Zombie", 54, 44975, 7969893);
        addMapping(EntitySlime.class, EntitySlime::new, "Slime", 55, 5349438, 8306542);
        addMapping(EntityGhast.class, EntityGhast::new, "Ghast", 56, 16382457, 12369084);
        addMapping(EntityPigZombie.class, EntityPigZombie::new, "PigZombie", 57, 15373203, 5009705);
        addMapping(EntityEnderman.class, EntityEnderman::new, "Enderman", 58, 1447446, 0);
        addMapping(EntityCaveSpider.class, EntityCaveSpider::new, "CaveSpider", 59, 803406, 11013646);
        addMapping(EntitySilverfish.class, EntitySilverfish::new, "Silverfish", 60, 7237230, 3158064);
        addMapping(EntityBlaze.class, EntityBlaze::new, "Blaze", 61, 16167425, 16775294);
        addMapping(EntityMagmaCube.class, EntityMagmaCube::new, "LavaSlime", 62, 3407872, 16579584);
        addMapping(EntityDragon.class, EntityDragon::new, "EnderDragon", 63);
        addMapping(EntityPig.class, EntityPig::new, "Pig", 90, 15771042, 14377823);
        addMapping(EntitySheep.class, EntitySheep::new, "Sheep", 91, 15198183, 16758197);
        addMapping(EntityCow.class, EntityCow::new, "Cow", 92, 4470310, 10592673);
        addMapping(EntityChicken.class, EntityChicken::new, "Chicken", 93, 10592673, 16711680);
        addMapping(EntitySquid.class, EntitySquid::new, "Squid", 94, 2243405, 7375001);
        addMapping(EntityWolf.class, EntityWolf::new, "Wolf", 95, 14144467, 13545366);
        addMapping(EntityMooshroom.class, EntityMooshroom::new, "MushroomCow", 96, 10489616, 12040119);
        addMapping(EntitySnowman.class, EntitySnowman::new, "SnowMan", 97);
        addMapping(EntityOcelot.class, EntityOcelot::new, "Ozelot", 98, 15720061, 5653556);
        addMapping(EntityIronGolem.class, EntityIronGolem::new, "VillagerGolem", 99);
        addMapping(EntityVillager.class, EntityVillager::new, "Villager", 120, 5651507, 12422002);
        addMapping(EntityEnderCrystal.class, EntityEnderCrystal::new, "EnderCrystal", 200);
    }
}
