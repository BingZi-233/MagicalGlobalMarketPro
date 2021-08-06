package vip.bingzi.magicalglobalmarketpro.money

import io.izzel.taboolib.TabooLibAPI
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import vip.bingzi.magicalglobalmarketpro.MagicalGlobalMarketPro
import vip.bingzi.magicalglobalmarketpro.util.logger

class EconomyTaboolibAPI : Economy() {
    private var info = true

    init {
        info = TabooLibAPI.getPluginBridge().economyHooked()
        if (info) {
            logger.info("Taboolib经济桥已建立链接，相关功能已启用。")
        } else {
            logger.warn("Taboolib经济桥失去链接，相关功能已自动禁用。")
            object : BukkitRunnable() {
                override fun run() {
                    logger.error("正在对MagicalGlobalMarketPro执行关闭程序...")
                    Bukkit.getServer().pluginManager.disablePlugin(MagicalGlobalMarketPro.plugin)
                }
            }.runTask(MagicalGlobalMarketPro.plugin)
        }
    }

    /**
     * 增加点数
     * @param player 玩家
     * @param value 点数
     */
    override fun add(player: String, value: Double): Boolean {
        if (value < 0 || TabooLibAPI.getPluginBridge().economyHooked()) return false
        TabooLibAPI.getPluginBridge().economyGive(Bukkit.getOfflinePlayer(player), value)
        return true
    }

    /**
     * 减少点数
     * @param player 玩家
     * @param value 点数
     */
    override fun take(player: String, value: Double): Boolean {
        if (value > TabooLibAPI.getPluginBridge().economyLook(Bukkit.getOfflinePlayer(player)) && value < 0.0) {
            logger.fine("对于玩家 ${player} 的扣款(${value})失败! 玩家剩余金额:${get(player)}")
            return false
        }
        TabooLibAPI.getPluginBridge().economyTake(Bukkit.getOfflinePlayer(player), value)
        logger.finest("对于玩家 ${player} 的扣款(${value})成功.玩家剩余金额:${get(player)}")
        return true
    }

    override fun getHooked(): Boolean {
        return info
    }

    override fun get(player: String): Double {
        return TabooLibAPI.getPluginBridge().economyLook(Bukkit.getOfflinePlayer(player))
    }
}