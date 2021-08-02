package vip.bingzi.magicalglobalmarketpro

import io.izzel.taboolib.loader.Plugin
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.inject.TInject
import vip.bingzi.magicalglobalmarketpro.money.EconomyTaboolibAPI
import vip.bingzi.magicalglobalmarketpro.util.economy


object MagicalGlobalMarketPro : Plugin() {
    // 配置文件
    @TInject(value = ["setting.yml"], locale = "LOCALE-PRIORITY")
    lateinit var setting: TConfig
        private set

    // 视图文件
    @TInject(value = ["view.yml"], locale = "LOCALE-PRIORITY")
    lateinit var view: TConfig
        private set

    // 数据文件
    @TInject(value = ["data.yml"], locale = "LOCALE-PRIORITY")
    lateinit var data: TConfig
        private set

    override fun onLoad() {

    }

    override fun onEnable() {
        economy = EconomyTaboolibAPI()
    }

    override fun onDisable() {
    }
}