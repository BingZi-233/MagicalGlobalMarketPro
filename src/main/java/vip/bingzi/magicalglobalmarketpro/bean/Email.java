package vip.bingzi.magicalglobalmarketpro.bean;

import io.izzel.taboolib.kotlin.Serializer;
import io.izzel.taboolib.module.config.TConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import vip.bingzi.magicalglobalmarketpro.MagicalGlobalMarketPro;
import vip.bingzi.magicalglobalmarketpro.util.ToolsKt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件构建
 */
public class Email implements Serializable {
    private TConfig config = MagicalGlobalMarketPro.INSTANCE.getSetting();
    private Player player;
    private ArrayList<ItemStack> arrayList;

    {
        List<String> stringList = config.getStringList(player.getName() + ".Email");
        for (String s : stringList) {
            arrayList.add(Serializer.INSTANCE.toItemStack(s));
        }
        ToolsKt.getLogger().verbose("已经导入玩家" + player.getName() + "的邮件，共计" + arrayList.size() + "个");
    }

    public Email() {
        this(null);
    }

    public Email(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "玩家: " + player.getName() + " 邮件数量:" + arrayList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (!getPlayer().equals(email.getPlayer())) return false;
        return getArrayList() != null ? getArrayList().equals(email.getArrayList()) : email.getArrayList() == null;
    }

    @Override
    public int hashCode() {
        int result = getPlayer().hashCode();
        result = 31 * result + (getArrayList() != null ? getArrayList().hashCode() : 0);
        return result;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<ItemStack> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ItemStack> arrayList) {
        this.arrayList = arrayList;
    }

    /**
     * 获取邮件数量
     *
     * @return 邮件数量
     */
    public int getSize() {
        return arrayList.size();
    }

    /**
     * 添加物品
     *
     * @param itemStack 要添加的物品
     */
    public void addItemStack(ItemStack itemStack) {
        arrayList.add(itemStack);
        ToolsKt.getLogger().verbose("玩家" + player.getName() + "的邮件仓库已放入:" + itemStack.getType() + " | " + itemStack.getAmount());
    }

    /**
     * 添加一堆物品
     *
     * @param itemStackArrayList 物品集合
     */
    public void addItemStack(ArrayList<ItemStack> itemStackArrayList) {
        arrayList.addAll(itemStackArrayList);
        ToolsKt.getLogger().verbose("玩家" + player.getName() + "的邮件仓库已放入 " + itemStackArrayList.size() + " 个物品");
    }

    /**
     * 获取指定序号的物品
     *
     * @param i 序号
     * @return 物品
     */
    public ItemStack getItemStack(int i) {
        return arrayList.get(i);
    }

    /**
     * 删除指定序号的物品
     *
     * @param i 序号
     */
    public void removeItemStack(int i) {
        ItemStack itemStack = getItemStack(i);
        arrayList.remove(i);
        ToolsKt.getLogger().verbose("玩家" + player.getName() + "的邮件仓库已删除:" + itemStack.getType() + " | " + itemStack.getAmount());
    }
}
