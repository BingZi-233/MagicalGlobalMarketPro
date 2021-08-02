package vip.bingzi.magicalglobalmarketpro.view.viewimpt

import io.izzel.taboolib.util.item.inventory.ClickEvent
import io.izzel.taboolib.util.item.inventory.linked.MenuLinked
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import vip.bingzi.magicalglobalmarketpro.bean.Email
import vip.bingzi.magicalglobalmarketpro.bean.Shop
import vip.bingzi.magicalglobalmarketpro.util.*
import vip.bingzi.magicalglobalmarketpro.view.View
import java.text.SimpleDateFormat
import java.util.*

class OpenView : View {
    override fun startView(player: Player) {
        val decoration: MutableList<Int> = mutableListOf(45, 46, 47, 49, 51, 52, 53)
        object : MenuLinked<Shop>(player) {
            init {
                // 上一页按钮
                addButtonPreviousPage(48)
                // 下一页按钮
                addButtonNextPage(50)
            }

            // 所有元素
            override fun getElements(): MutableList<Shop> {
                val mutableList = mutableListOf<Shop>()
                for (s in data.getStringList("Shop")) {
                    mutableList.add(toShop(s))
                }
                return mutableList
            }

            // 标题
            override fun getTitle(): String {
                return view.getStringColored("Open.Title")
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
                    36, 37, 38, 39, 40, 41, 42, 43, 44
                )
            }

            // 构建结束时(异步)
            override fun onBuildAsync(p0: Inventory) {
                if (hasPreviousPage()) {
                    p0.setItem(48, ItemStack(Material.CHEST))
                } else {
                    p0.setItem(48, ItemStack(Material.CHEST))
                }
                if (hasNextPage()) {
                    p0.setItem(50, ItemStack(Material.CHEST))
                } else {
                    p0.setItem(50, ItemStack(Material.CHEST))
                }
                decoration.forEach {
                    p0.setItem(it, ItemStack(Material.WHITE_STAINED_GLASS_PANE))
                }
            }

            // 点击时
            override fun onClick(p0: ClickEvent, p1: Shop) {
                p0.isCancelled = true
                val buyPlayer = p0.clicker
                val fromShop = fromShop(p1)
                val stringList = data.getStringList("Shop")
                // 检测当前配置文件中是否还有此物品
                if (stringList.contains(fromShop)) {
                    // 扣款是否成功
                    if (economy!!.take(buyPlayer, p1.price)) {
                        // 移除该物品
                        stringList.remove(fromShop)
                        data.set("Shop", stringList)
                        data.saveToFile()
                        val playerEmail = getPlayerEmail(buyPlayer)
                        if (playerEmail != null) {
                            playerEmail.addShop(p1)
                            economy!!.add(Bukkit.getOfflinePlayer(p1.player) as Player, p1.price)
                        } else {
                            val email1 = Email(player)
                            email1.addShop(p1)
                            email.add(email1)
                        }
                    }
                } else {
                    logger.fine("玩家 ${buyPlayer.name} 正在尝试购买一个已经被购买的商品,该操作已被取消!")
                    buyPlayer.sendMessage(asStringColored("Shop.NotShop"))
                }
            }

            // 生成元素所对应的物品
            override fun generateItem(p0: Player, p1: Shop, p2: Int, p3: Int): ItemStack {
                try {
                    val clone = p1.itemStack.clone()
                    val itemMeta = clone.itemMeta
                    val mutableListTemp = view.getStringListColored("Open.ItemStack.Lore")
                    val mutableList = mutableListOf<String>()
                    if (itemMeta != null) {
                        mutableListTemp.forEach {
                            val replace = it.replace("{0}", p1.price.toString())
                                .replace("{1}", SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date(p1.time)))
                                .replace("{2}", p1.player)
                            mutableList.add(replace)
                        }
                        if (itemMeta.lore == null || itemMeta.lore!!.size == 0) {
                            itemMeta.lore = mutableList
                        } else {
                            val lore = itemMeta.lore
                            lore?.addAll(mutableList)
                            itemMeta.lore = lore
                        }
                    }
                    clone.itemMeta = itemMeta
                    return clone
                } catch (e: Exception) {
                    return ItemStack(Material.AIR)
                }
            }

            // 构建结束时
            override fun onBuild(p0: Inventory) {
                logger.finest(player.name + "的购买页面已经打开")
            }
        }.open()
    }
}