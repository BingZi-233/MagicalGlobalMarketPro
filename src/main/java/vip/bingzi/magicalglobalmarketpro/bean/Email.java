package vip.bingzi.magicalglobalmarketpro.bean;

import org.bukkit.entity.Player;
import vip.bingzi.magicalglobalmarketpro.MagicalGlobalMarketPro;
import vip.bingzi.magicalglobalmarketpro.util.ToolsKt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件构建
 */
public class Email implements Serializable {
    private String player;
    private String uuid;
    private double price;
    private ArrayList<Shop> arrayList = new ArrayList<>();

    {
        List<String> stringList = MagicalGlobalMarketPro.INSTANCE.getSetting().getStringList(player + ".Email");
        for (String shop : stringList) {
            arrayList.add(ToolsKt.toShop(shop));
        }
        ToolsKt.getLogger().verbose("已经导入玩家" + player + "的邮件，共计" + arrayList.size() + "个");
    }

    public Email(Player player) {
        this.player = player.getName();
        this.uuid = player.getUniqueId().toString();
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(Player player) {
        this.uuid = player.getUniqueId().toString();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public ArrayList<Shop> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Shop> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public String toString() {
        return "玩家: " + player + " 邮件数量:" + arrayList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return getPlayer().equals(email.getPlayer());
    }

    @Override
    public int hashCode() {
        int result = getPlayer().hashCode();
        result = 31 * result + getArrayList().hashCode();
        return result;
    }

    /**
     * 在玩家邮件里面添加金额
     *
     * @param price 待添加的数值
     */
    public void addPrice(double price) {
        this.price += price;
        ToolsKt.getLogger().verbose("已经为 " + player + " 添加金额(" + price + ") 待提取:" + this.price);
    }

    /**
     * 添加一个商品到玩家的邮箱中
     *
     * @param shop 商品
     */
    public void addShop(Shop shop) {
        arrayList.add(shop);
        ToolsKt.getLogger().verbose("玩家 " + player + " 已添加商品! 邮箱内存储物品数量为:" + getSize());
    }

    /**
     * 从玩家的邮箱中，删除一个商品
     *
     * @param shop 商品
     * @return 是否成功
     */
    public boolean removeShop(Shop shop) {
        return arrayList.remove(shop);
    }

    /**
     * 返回玩家邮箱中商品的个数
     *
     * @return 商品个数
     */
    public int getSize() {
        return arrayList.size();
    }
}
