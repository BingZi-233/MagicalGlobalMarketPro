package vip.bingzi.magicalglobalmarketpro.money

import org.black_ixx.playerpoints.PlayerPoints
import org.black_ixx.playerpoints.PlayerPointsAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import vip.bingzi.magicalglobalmarketpro.util.economy
import vip.bingzi.magicalglobalmarketpro.util.logger

/**
 * 经济-点券
 * @author 冰子
 * @since 2021/5/14 19:46
 */
class EconomyPlayerPoints : Economy() {
    private var playerPoints: PlayerPoints? = null
    private var info = false

    init {
        info = !hookPlayerPoints()
        if (!info) {
            logger.fine("PlayerPoints已正确载入，相关功能已启用。")
        } else {
            logger.warn("PlayerPoints异常，相关功能已自动禁用。")
            economy = EconomyTaboolibAPI()
        }
    }

    /**
     * 增加点数
     * @param player 玩家
     * @param value 点数
     */
    override fun add(player: Player, value: Double): Boolean {
        if (info) return false
        playerPointsAPI.give(player.uniqueId, value.toInt())
        return true
    }

    /**
     * 减少点数
     * @param player 玩家
     * @param value 点数
     */
    override fun take(player: Player, value: Double): Boolean {
        if (info) return false
        logger.finest("玩家 ${player.name} 尝试消费 $value 点数, 持有点数为: ${playerPointsAPI.look(player.uniqueId)}")
        return playerPointsAPI.take(player.uniqueId, value.toInt())
    }

    /**
     * 注册
     */
    private fun hookPlayerPoints(): Boolean {
        val plugin = Bukkit.getPluginManager().getPlugin("PlayerPoints")
        playerPoints = plugin as PlayerPoints?
        return playerPoints != null
    }

    private val playerPointsAPI: PlayerPointsAPI
        get() = playerPoints!!.api

    override fun getHooked(): Boolean {
        return !info
    }

    override fun get(player: Player): Double {
        return playerPointsAPI.look(player.name).toDouble()
    }
}