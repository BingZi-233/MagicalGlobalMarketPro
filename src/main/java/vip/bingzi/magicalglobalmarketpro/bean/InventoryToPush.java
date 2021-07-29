package vip.bingzi.magicalglobalmarketpro.bean;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class InventoryToPush implements Serializable {
    private ItemStack itemStack;
    private int inventorySlot;

    public InventoryToPush() {
        this.itemStack = new ItemStack(Material.AIR);
        this.inventorySlot = -1;
    }

    public InventoryToPush(ItemStack itemStack, int inventorySlot) {
        this.itemStack = itemStack;
        this.inventorySlot = inventorySlot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public int getInventorySlot() {
        return inventorySlot;
    }

    public void setInventorySlot(int inventorySlot) {
        this.inventorySlot = inventorySlot;
    }
}
