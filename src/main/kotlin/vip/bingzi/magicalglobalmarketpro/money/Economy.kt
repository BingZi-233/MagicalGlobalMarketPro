package vip.bingzi.magicalglobalmarketpro.money

import org.bukkit.entity.Player

/**
 * 经济
 * @author 冰子
 * @since 2021/5/14 19:46
 */
abstract class Economy {
    abstract fun add(player: Player, value: Double): Boolean
    abstract fun take(player: Player, value: Double): Boolean
    abstract fun getHooked(): Boolean
    abstract fun get(player: Player): Double
}