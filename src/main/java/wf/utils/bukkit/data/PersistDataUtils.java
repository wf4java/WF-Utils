package wf.utils.bukkit.data;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class PersistDataUtils {

    private Plugin plugin;

    public PersistDataUtils(Plugin plugin) {
        this.plugin = plugin;
    }




    public static void set(Plugin plugin, String key, ItemStack item, boolean value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, int value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, double value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, byte value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, long value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, String value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, short value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, float value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, byte[] value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, long[] value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, int[] value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public static void set(Plugin plugin, String key, ItemStack item, PersistentDataContainer value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }




    public boolean getBoolean(String key, ItemStack item){
        return getBoolean(plugin, key, item.getItemMeta());
    }
    public int getInt(String key, ItemStack item){
        return getInt(plugin, key, item.getItemMeta());
    }

    public double getDouble(String key, ItemStack item){
        return getDouble(plugin, key, item.getItemMeta());
    }

    public byte getByte(String key, ItemStack item){
        return getByte(plugin, key, item.getItemMeta());
    }

    public long getLong(String key, ItemStack item){
        return getLong(plugin, key, item.getItemMeta());
    }

    public String getString(String key, ItemStack item){
        return getString(plugin, key, item.getItemMeta());
    }

    public short getShort(String key, ItemStack item){
        return getShort(plugin, key, item.getItemMeta());
    }

    public float getFloat(String key, ItemStack item){
        return getFloat(plugin, key, item.getItemMeta());
    }

    public byte[] getByteArray(String key, ItemStack item){
        return getByteArray(plugin, key, item.getItemMeta());
    }

    public long[] getLongArray(String key, ItemStack item){
        return getLongArray(plugin, key, item.getItemMeta());
    }

    public int[] getIntArray(String key, ItemStack item){
        return getIntArray(plugin, key, item.getItemMeta());
    }

    public PersistentDataContainer getDataContainer(String key, ItemStack item){
        return getDataContainer(plugin, key, item.getItemMeta());
    }






    public static boolean getBoolean(Plugin plugin, String key, ItemStack item){
        return getBoolean(plugin, key, item.getItemMeta());
    }
    public static int getInt(Plugin plugin, String key, ItemStack item){
        return getInt(plugin, key, item.getItemMeta());
    }

    public static double getDouble(Plugin plugin, String key, ItemStack item){
        return getDouble(plugin, key, item.getItemMeta());
    }

    public static byte getByte(Plugin plugin, String key, ItemStack item){
        return getByte(plugin, key, item.getItemMeta());
    }

    public static long getLong(Plugin plugin, String key, ItemStack item){
        return getLong(plugin, key, item.getItemMeta());
    }

    public static String getString(Plugin plugin, String key, ItemStack item){
        return getString(plugin, key, item.getItemMeta());
    }

    public static short getShort(Plugin plugin, String key, ItemStack item){
        return getShort(plugin, key, item.getItemMeta());
    }

    public static float getFloat(Plugin plugin, String key, ItemStack item){
        return getFloat(plugin, key, item.getItemMeta());
    }

    public static byte[] getByteArray(Plugin plugin, String key, ItemStack item){
        return getByteArray(plugin, key, item.getItemMeta());
    }

    public static long[] getLongArray(Plugin plugin, String key, ItemStack item){
        return getLongArray(plugin, key, item.getItemMeta());
    }

    public static int[] getIntArray(Plugin plugin, String key, ItemStack item){
        return getIntArray(plugin, key, item.getItemMeta());
    }

    public static PersistentDataContainer getDataContainer(Plugin plugin, String key, ItemStack item){
        return getDataContainer(plugin, key, item.getItemMeta());
    }




    public void set(String key, ItemStack item, boolean value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, int value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, double value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, byte value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, long value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, String value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, short value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, float value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, byte[] value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, long[] value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, int[] value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }

    public void set(String key, ItemStack item, PersistentDataContainer value){
        ItemMeta meta = item.getItemMeta();
        set(plugin, key, meta, value);
        item.setItemMeta(meta);
    }




    public void set(String key, PersistentDataHolder item, boolean value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, int value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, double value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, byte value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, long value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, String value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, short value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, float value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, byte[] value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, long[] value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, int[] value){
        set(plugin, key, item, value);
    }

    public void set(String key, PersistentDataHolder item, PersistentDataContainer value){
        set(plugin, key, item, value);
    }







    public boolean getBoolean(String key, PersistentDataHolder item){
        return getBoolean(plugin, key, item);
    }
    public int getInt(String key, PersistentDataHolder item){
        return getInt(plugin, key, item);
    }

    public double getDouble(String key, PersistentDataHolder item){
        return getDouble(plugin, key, item);
    }

    public byte getByte(String key, PersistentDataHolder item){
        return getByte(plugin, key, item);
    }

    public long getLong(String key, PersistentDataHolder item){
        return getLong(plugin, key, item);
    }

    public String getString(String key, PersistentDataHolder item){
        return getString(plugin, key, item);
    }

    public short getShort(String key, PersistentDataHolder item){
        return getShort(plugin, key, item);
    }

    public float getFloat(String key, PersistentDataHolder item){
        return getFloat(plugin, key, item);
    }

    public byte[] getByteArray(String key, PersistentDataHolder item){
        return getByteArray(plugin, key, item);
    }

    public long[] getLongArray(String key, PersistentDataHolder item){
        return getLongArray(plugin, key, item);
    }

    public int[] getIntArray(String key, PersistentDataHolder item){
        return getIntArray(plugin, key, item);
    }

    public PersistentDataContainer getDataContainer(String key, PersistentDataHolder item){
        return getDataContainer(plugin, key, item);
    }










    public static void set(Plugin plugin, String key, PersistentDataHolder item, boolean value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.BYTE, (byte) (value ? 1 : 0));
    }
    public static void set(Plugin plugin, String key, PersistentDataHolder item, int value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, double value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.DOUBLE, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, byte value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.BYTE, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, long value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.LONG, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, String value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, short value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.SHORT, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, float value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.FLOAT, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, byte[] value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, long[] value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.LONG_ARRAY, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, int[] value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.INTEGER_ARRAY, value);
    }

    public static void set(Plugin plugin, String key, PersistentDataHolder item, PersistentDataContainer value){
        item.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.TAG_CONTAINER, value);
    }







    public static boolean getBoolean(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.BYTE) >= 1 ? true : false;
    }
    public static int getInt(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER);
    }

    public static double getDouble(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.DOUBLE);
    }

    public static byte getByte(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.BYTE);
    }

    public static long getLong(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.LONG);
    }

    public static String getString(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    public static short getShort(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.SHORT);
    }

    public static float getFloat(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.FLOAT);
    }

    public static byte[] getByteArray(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.BYTE_ARRAY);
    }

    public static long[] getLongArray(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.LONG_ARRAY);
    }

    public static int[] getIntArray(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.INTEGER_ARRAY);
    }

    public static PersistentDataContainer getDataContainer(Plugin plugin, String key, PersistentDataHolder item){
        return item.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.TAG_CONTAINER);
    }


    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

}
