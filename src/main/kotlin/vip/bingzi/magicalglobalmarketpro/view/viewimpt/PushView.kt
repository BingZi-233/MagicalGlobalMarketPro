package vip.bingzi.magicalglobalmarketpro.view.viewimpt

import io.izzel.taboolib.util.Features
import io.izzel.taboolib.util.item.inventory.ClickEvent
import io.izzel.taboolib.util.item.inventory.linked.MenuLinked
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import vip.bingzi.magicalglobalmarketpro.bean.InventoryToPush
import vip.bingzi.magicalglobalmarketpro.bean.Shop
import vip.bingzi.magicalglobalmarketpro.bean.ShopType
import vip.bingzi.magicalglobalmarketpro.util.*
import vip.bingzi.magicalglobalmarketpro.view.View

class PushView : View {
    override fun startView(player: Player) {
        val decoration: MutableList<Int> = mutableListOf(46, 47, 49, 51)
        object : MenuLinked<InventoryToPush>(player) {
            init {
                // 上一页按钮
                addButtonPreviousPage(48)
                // 下一页按钮
                addButtonNextPage(50)
                addButton(45) {
                    MyView().startView(player)
                }
                addButton(52) {
                    EmailView().startView(player)
                }
                addButton(53) {
                    OpenView().startView(player)
                }
            }

            // 所有元素
            override fun getElements(): MutableList<InventoryToPush> {
                val mutableList = mutableListOf<InventoryToPush>()
                val inventory = player.inventory
                for (i in 0..inventory.size) {
                    val item = inventory.getItem(i)
                    if (item != null && item.type != Material.AIR) {
                        mutableList.add(InventoryToPush(item, i))
                    }
                }
                return mutableList
            }

            // 标题
            override fun getTitle(): String {
                return view.getStringColored("Push.Title")
            }

            // 行数
            override fun getRows(): Int {
                return 6
            }

            // 可存放物品的位置
            override fun getSlots(): MutableList<Int> {
                return mutableListOf(
                    0, 1, 2, 3, 4, 5, 6, 7, 8,
                    9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 28, 29, 30, 31, 32, 33, 34, 35,
                )
            }

            // 构建结束时(异步)
            override fun onBuildAsync(p0: Inventory) {
                if (hasPreviousPage()) {
                    p0.setItem(48, getViewItemStack("BottomColumn.Up.Start"))
                } else {
                    p0.setItem(48, getViewItemStack("BottomColumn.Up.End"))
                }
                if (hasNextPage()) {
                    p0.setItem(50, getViewItemStack("BottomColumn.Down.Start"))
                } else {
                    p0.setItem(50, getViewItemStack("BottomColumn.Down.End"))
                }
                p0.setItem(45, getViewItemStack("BottomColumn.My"))
                p0.setItem(52, getViewItemStack("BottomColumn.Email"))
                p0.setItem(53, getViewItemStack("BottomColumn.Open"))
                decoration.forEach {
                    p0.setItem(it, ItemStack(Material.WHITE_STAINED_GLASS_PANE))
                }
            }

            // 点击时
            override fun onClick(p0: ClickEvent, p1: InventoryToPush) {
                p0.isCancelled = true
                Features.inputSign(p0.clicker, arrayOf("", "请输入出售的价格")) {
                    val toDoubleOrNull = it[0].toDoubleOrNull()
                    if (toDoubleOrNull != null) {
                        p0.clicker.inventory.setItem(p1.inventorySlot, ItemStack(Material.AIR))
                        val shop =
                            Shop(ShopType.Sell,
                                System.currentTimeMillis(),
                                p1.itemStack,
                                toDoubleOrNull,
                                p0.clicker.name)
                        val stringList = data.getStringList("Shop")
                        stringList.add(fromShop(shop))
                        data.set("Shop", stringList)
                        data.saveToFile()
                        p0.clicker.sendMessage(asStringColored("Prompt.SellSuccessfully"))
                        startView(player)
                        return@inputSign
                    }
                    p0.clicker.sendMessage(asStringColored("Prompt.TheSaleFailed"))
                }
            }

            // 生成元素所对应的物品
            override fun generateItem(p0: Player, p1: InventoryToPush, p2: Int, p3: Int): ItemStack {
                val clone = p1.itemStack.clone()
                val itemMeta = clone.itemMeta
                val mutableList = view.getStringListColored("Push.ItemStack.Lore")
                return if (itemMeta != null) {
                    if (itemMeta.lore == null || itemMeta.lore!!.size == 0) {
                        itemMeta.lore = mutableList
                    } else {
                        val lore = itemMeta.lore
                        lore?.addAll(mutableList)
                        itemMeta.lore = lore
                    }
                    clone.itemMeta = itemMeta
                    clone
                } else {
                    clone
                }
            }

            // 构建结束时
            override fun onBuild(p0: Inventory) {
            }
        }.open()
    }
}