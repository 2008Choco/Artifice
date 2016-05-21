package me.choco.relics.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.choco.relics.utils.general.ItemBuilder;

public enum Relic {
	RELIC1(new ItemBuilder(Material.AIR).build()),
	RELIC2(new ItemBuilder(Material.AIR).build()),
	RELIC3(new ItemBuilder(Material.AIR).build());
	
	private ItemStack item;
	Relic(ItemStack item){
		this.item = item;
	}
	
	public ItemStack getItem(){
		return item;
	}
}