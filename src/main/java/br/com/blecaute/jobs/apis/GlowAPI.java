package br.com.blecaute.jobs.apis;

import br.com.blecaute.jobs.utils.ReflectionUtils;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public class GlowAPI {

    private static Class<?> NBTTagCompoundClass;
    private static Class<?> NBTTagListClass;

    private static Method asNMSCopy;
    private static Method asCraftMirror;

    private static Method hasNBTTagCompound;
    private static Method getNBTTagCompound;
    private static Method setNBTTagCompound;

    private static Method setNBTBaseCompound;

    public static ItemStack addGlow(ItemStack item) {
        try {
            Object NBTTagCompound = null;
            Object CraftItemStack = asNMSCopy.invoke(null, item);
            boolean hasNBTTag = (boolean) hasNBTTagCompound.invoke(CraftItemStack);
            if (!hasNBTTag) {
                NBTTagCompound = NBTTagCompoundClass.newInstance();
                setNBTBaseCompound.invoke(CraftItemStack, NBTTagCompound);
            }

            if (NBTTagCompound == null) NBTTagCompound = getNBTTagCompound.invoke(CraftItemStack);
            setNBTBaseCompound.invoke(NBTTagCompound, "ench", NBTTagListClass.newInstance());
            setNBTTagCompound.invoke(CraftItemStack, NBTTagCompound);
            return (ItemStack) asCraftMirror.invoke(null, CraftItemStack);

        } catch (Throwable e) {
            return item;
        }
    }

    public static void load() {

        try {
            Class<?> itemStackClass = ReflectionUtils.getNMSClass("ItemStack");
            Class<?> craftItemStackClass = ReflectionUtils.getOBClass("inventory.CraftItemStack");

            NBTTagListClass = ReflectionUtils.getNMSClass("NBTTagList");
            NBTTagCompoundClass = ReflectionUtils.getNMSClass("NBTTagCompound");

            asNMSCopy = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
            asCraftMirror = craftItemStackClass.getDeclaredMethod("asCraftMirror", itemStackClass);

            hasNBTTagCompound = itemStackClass.getDeclaredMethod("hasTag");
            getNBTTagCompound = itemStackClass.getDeclaredMethod("getTag");
            setNBTTagCompound = itemStackClass.getDeclaredMethod("setTag", NBTTagCompoundClass);

            Class<?> NBTBaseClass = ReflectionUtils.getNMSClass("NBTBase");
            setNBTBaseCompound = NBTTagCompoundClass.getDeclaredMethod("set", String.class, NBTBaseClass);

        } catch (Throwable ignored) { }
    }
}