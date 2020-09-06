package br.com.blecaute.jobs.model.inventory;

import br.com.blecaute.jobs.apis.GlowAPI;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Easily create itemstacks, without messing your hands.
 * <i>Note that if you do use this in one of your projects, leave this notice.</i>
 * <i>Please do credit me if you do use this in one of your projects.</i>
 * @author NonameSL
 */

public class ItemBuilder {

	private ItemStack itemStack;
	/**
	 * Create a new ItemBuilder from scratch.
	 * @param material The material to create the ItemBuilder with.
	 */
	public ItemBuilder(Material material){
		this(material, 1);
	}
	/**
	 * Create a new ItemBuilder over an existing itemstack.
	 * @param itemStack The itemstack to create the ItemBuilder over.
	 */
	public ItemBuilder(ItemStack itemStack){
		this.itemStack = itemStack;
	}
	/**
	 * Create a new ItemBuilder from scratch.
	 * @param material The material of the item.
	 * @param amount The amount of the item.
	 */
	public ItemBuilder(Material material, int amount){
		itemStack = new ItemStack(material, amount);
	}

	public ItemBuilder setAmount(int amount){
		itemStack.setAmount(amount);
		return this;
	}

	/**
	 * Create a new ItemBuilder from scratch.
	 * @param material The material of the item.
	 * @param amount The amount of the item.
	 * @param durability The durability of the item.
	 */
	public ItemBuilder(Material material, int amount, byte durability){
		itemStack = new ItemStack(material, amount, durability);
	}
	/**
	 * Adiciona uma ItemFlag
	 */

	public ItemBuilder addItemFlag(ItemFlag itemFlag){
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(itemFlag);
		itemStack.setItemMeta(itemMeta);
		return this;
	}

	public ItemBuilder addItemFlags(){
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.values());
		itemStack.setItemMeta(itemMeta);
		return this;
	}


	/**
	 * Clone the ItemBuilder into a new one.
	 * @return The cloned instance.
	 */
	public ItemBuilder clone(){
		return new ItemBuilder(itemStack);
	}
	/**
	 * Change the durability of the item.
	 * @param durability The durability to set it to.
	 */
	public ItemBuilder setDurability(short durability){
		itemStack.setDurability(durability);
		return this;
	}
	/**
	 * Set the displayname of the item.
	 * @param name The name to change it to.
	 */
	public ItemBuilder setName(String name){
		ItemMeta im = itemStack.getItemMeta();
		im.setDisplayName(name);
		itemStack.setItemMeta(im);
		return this;
	}

	/**
	 * get the displayname of the item
	 * @return the displayname of item.
	 */
	public String getName() {
		if(!itemStack.hasItemMeta()) return "";
		return itemStack.getItemMeta().hasDisplayName() ? itemStack.getItemMeta().getDisplayName() : "";
	}

	/**
	 * Add an unsafe enchantment.
	 * @param enchantment The enchantment to add.
	 * @param level The level to put the enchant on.
	 */
	public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level){
		itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}
	/**
	 * Remove a certain enchant from the item.
	 * @param enchantment The enchantment to remove
	 */
	public ItemBuilder removeEnchantment(Enchantment enchantment){
		itemStack.removeEnchantment(enchantment);
		return this;
	}
	/**
	 * Set the skull owner for the item. Works on skulls only.
	 * @param owner The name of the skull's owner.
	 */
	public ItemBuilder setSkullOwner(String owner){
		try{
			SkullMeta im = (SkullMeta) itemStack.getItemMeta();
			im.setOwner(owner);
			itemStack.setItemMeta(im);
		}catch(ClassCastException expected){}
		return this;
	}
	/**
	 * Add an enchant to the item.
	 * @param enchantment The enchant to add
	 * @param level The level
	 */
	public ItemBuilder addEnchant(Enchantment enchantment, int level){
		ItemMeta im = itemStack.getItemMeta();
		im.addEnchant(enchantment, level, true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemStack.setItemMeta(im);
		return this;
	}
	/**
	 * Add multiple enchants at once.
	 * @param enchantments The enchants to add.
	 */
	public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
		itemStack.addEnchantments(enchantments);
		return this;
	}
	/**
	 * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
	 */
	public ItemBuilder setInfinityDurability(){
		itemStack.setDurability(Short.MAX_VALUE);
		return this;
	}
	/**
	 * Re-sets the lore.
	 * @param lore The lore to set it to.
	 */
	public ItemBuilder setLore(String... lore){
		ItemMeta im = itemStack.getItemMeta();
		im.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(im);
		return this;
	}
	/**
	 * Re-sets the lore.
	 * @param lore The lore to set it to.
	 */
	public ItemBuilder setLore(List<String> lore) {
		ItemMeta im = itemStack.getItemMeta();
		im.setLore(lore);
		itemStack.setItemMeta(im);
		return this;
	}

	public List<String> getLore() {
		if(!itemStack.hasItemMeta()) return new ArrayList<>();
		return itemStack.getItemMeta().hasLore() ? itemStack.getItemMeta().getLore() : new ArrayList<>();
	}

	/**
	 * Remove a lore line.
	 * @param line The lore to remove.
	 */
	public ItemBuilder removeLoreLine(String line){
		ItemMeta im = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>(im.getLore());
		if(!lore.contains(line))return this;
		lore.remove(line);
		im.setLore(lore);
		itemStack.setItemMeta(im);
		return this;
	}
	/**
	 * Remove a lore line.
	 * @param index The index of the lore line to remove.
	 */
	public ItemBuilder removeLoreLine(int index){
		ItemMeta im = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>(im.getLore());
		if(index<0||index>lore.size())return this;
		lore.remove(index);
		im.setLore(lore);
		itemStack.setItemMeta(im);
		return this;
	}
	/**
	 * Add a lore line.
	 * @param line The lore line to add.
	 */
	public ItemBuilder addLoreLine(String line){
		ItemMeta im = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>();
		if(im.hasLore())lore = new ArrayList<>(im.getLore());
		lore.add(line);
		im.setLore(lore);
		itemStack.setItemMeta(im);
		return this;
	}
	/**
	 * Add a lore line.
	 * @param line The lore line to add.
	 * @param pos The index of where to put it.
	 */
	public ItemBuilder addLoreLine(String line, int pos){
		ItemMeta im = itemStack.getItemMeta();
		List<String> lore = new ArrayList<>(im.getLore());
		lore.set(pos, line);
		im.setLore(lore);
		itemStack.setItemMeta(im);
		return this;
	}

	/**
	 * add glow to item
	 */
	public ItemBuilder addGlow() {
		this.itemStack = GlowAPI.addGlow(this.itemStack.clone());
		return this;
	}

	/**
	 * Sets the dye color on an item.
	 * <b>* Notice that this doesn't check for item type, sets the literal data of the dyecolor as durability.</b>
	 * @param color The color to put.
	 */
	@SuppressWarnings("deprecation")
	public ItemBuilder setDyeColor(DyeColor color){
		this.itemStack.setDurability(color.getData());
		return this;
	}
	/**
	 * Sets the dye color of a wool item. Works only on wool.
	 * @deprecated As of version 1.2 changed to setDyeColor.
	 * @see ItemBuilder@setDyeColor(DyeColor)
	 * @param color The DyeColor to set the wool item to.
	 */
	@Deprecated
	public ItemBuilder setWoolColor(DyeColor color){
		if(!itemStack.getType().equals(Material.WOOL))return this;
		this.itemStack.setDurability(color.getData());
		return this;
	}
	/**
	 * Sets the armor color of a leather armor piece. Works only on leather armor pieces.
	 * @param color The color to set it to.
	 */
	public ItemBuilder setLeatherArmorColor(Color color){
		try{
			LeatherArmorMeta im = (LeatherArmorMeta) itemStack.getItemMeta();
			im.setColor(color);
			itemStack.setItemMeta(im);
		}catch(ClassCastException expected){}
		return this;
	}
	/**
	 * Retrieves the itemstack from the ItemBuilder.
	 * @return The itemstack created/modified by the ItemBuilder instance.
	 */
	public ItemStack toItemStack(){
		return itemStack;
	}
}