package vip.bingzi.magicalglobalmarketpro.money

import io.izzel.taboolib.TabooLibAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import vip.bingzi.magicalglobalmarketpro.MagicalGlobalMarketPro
import vip.bingzi.magicalglobalmarketpro.util.logger

class EconomyTaboolibAPI : Economy() {
    private var info = false

    init {
        info = !TabooLibAPI.getPluginBridge().economyHooked()
        if (!info) {
            logger.info("Taboolib经济桥已建立链接，相关功能已启用。")
        } else {
            logger.warn("Taboolib经济桥失去链接，相关功能已自动禁用。")
            Bukkit.getServer().pluginManager.disablePlugin(MagicalGlobalMarketPro.plugin)
        }
    }

    /**
     * 增加点数
     * @param player 玩家
     * @param value 点数
     */
    override fun add(player: Player, value: Double): Boolean {
        if (value < 0 || TabooLibAPI.getPluginBridge().economyHooked()) return false
        TabooLibAPI.getPluginBridge().economyGive(player, value)
        return true
    }

    /**
     * 减少点数
     * @param player 玩家
     * @param value 点数
     */
    override fun take(player: Player, value: Double): Boolean {
        if (value > TabooLibAPI.getPluginBridge().economyLook(player) ||
            value < 0 || TabooLibAPI.getPluginBridge()
                .economyHooked()
        ) {
            return false
        }
        TabooLibAPI.getPluginBridge().economyTake(player, value)
        return true
    }

    override fun getHooked(): Boolean {
        return !info
    }

    override fun get(player: Player): Double {
        return TabooLibAPI.getPluginBridge().economyLook(player)
    }
}