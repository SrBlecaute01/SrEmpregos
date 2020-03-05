package br.com.blecaute.empregos.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

	private ItemStack is;

	public ItemBuilder(Material Material) {
		this(Material, 1);
	}

	public ItemBuilder(ItemStack ItemStack) {
		this.is = ItemStack;
	}

	public ItemBuilder(Material Material, int Int) {
		this.is = new ItemStack(Material, Int);
	}

	public ItemBuilder setName(String String) {
		ItemMeta localItemMeta = this.is.getItemMeta();
		localItemMeta.setDisplayName(String);
		this.is.setItemMeta(localItemMeta);
		return this;
	}

	public ItemBuilder setLore(List<String> List) {
		ItemMeta localItemMeta = this.is.getItemMeta();
		localItemMeta.setLore(List);
		this.is.setItemMeta(localItemMeta);
		return this;
	}

	public ItemStack toItemStack() {
		return this.is;
	}
}