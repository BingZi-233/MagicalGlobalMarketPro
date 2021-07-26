package vip.bingzi.util

import io.izzel.taboolib.loader.PluginBoot
import io.izzel.taboolib.module.locale.logger.TLogger
import io.izzel.taboolib.module.locale.logger.TLoggerManager
import vip.bingzi.MagicalGlobalMarketPro

// 方便全局访问
val plugin: PluginBoot = MagicalGlobalMarketPro.plugin
val logger: TLogger = TLoggerManager.getLogger(plugin)