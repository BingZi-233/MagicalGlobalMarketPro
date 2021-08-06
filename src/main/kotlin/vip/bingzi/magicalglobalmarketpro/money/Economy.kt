package vip.bingzi.magicalglobalmarketpro.money

/**
 * 经济
 * @author 冰子
 * @since 2021/5/14 19:46
 */
abstract class Economy {
    abstract fun add(player: String, value: Double): Boolean
    abstract fun take(player: String, value: Double): Boolean
    abstract fun getHooked(): Boolean
    abstract fun get(player: String): Double
}