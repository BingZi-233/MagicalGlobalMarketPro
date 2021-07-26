package vip.bingzi.bean;

import io.izzel.taboolib.kotlin.blockdb.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

/**
 * 商品对象
 *
 * @author lhby2
 */
public class Shop implements Serializable {
    // 类型
    private ShopType shopType;
    // 时间
    private Long time;
    // 物品
    private ItemStack itemStack;
    // 价格
    private double price;
    // 玩家
    private Player player;

    public Shop() {
        this(ShopType.None, 0L, null, 0, null);
    }

    public Shop(ShopType shopType, Long time, ItemStack itemStack, double price, Player player) {
        this.shopType = shopType;
        this.time = time;
        this.itemStack = itemStack;
        this.price = price;
        this.player = player;
    }

    @Override
    public String toString() {
        return "类型:" +
                shopType +
                " 上架时间:" +
                new Data(time) +
                " 上架物品类型:" +
                itemStack.getType() +
                " 上架玩家:" +
                player.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (Double.compare(shop.getPrice(), getPrice()) != 0) return false;
        if (getShopType() != shop.getShopType()) return false;
        if (getTime() != null ? !getTime().equals(shop.getTime()) : shop.getTime() != null) return false;
        if (getItemStack() != null ? !getItemStack().equals(shop.getItemStack()) : shop.getItemStack() != null)
            return false;
        return getPlayer() != null ? getPlayer().equals(shop.getPlayer()) : shop.getPlayer() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getShopType() != null ? getShopType().hashCode() : 0;
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        result = 31 * result + (getItemStack() != null ? getItemStack().hashCode() : 0);
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getPlayer() != null ? getPlayer().hashCode() : 0);
        return result;
    }

    public ShopType getShopType() {
        return shopType;
    }

    public void setShopType(ShopType shopType) {
        this.shopType = shopType;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
