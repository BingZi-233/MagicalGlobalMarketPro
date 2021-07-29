package vip.bingzi.magicalglobalmarketpro.util

import io.izzel.taboolib.loader.PluginBoot
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.module.locale.logger.TLogger
import io.izzel.taboolib.module.locale.logger.TLoggerManager
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import vip.bingzi.magicalglobalmarketpro.MagicalGlobalMarketPro
import vip.bingzi.magicalglobalmarketpro.bean.Shop
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

// 方便全局访问
val plugin: PluginBoot = MagicalGlobalMarketPro.plugin
val logger: TLogger = TLoggerManager.getLogger(plugin)
val setting: TConfig = MagicalGlobalMarketPro.setting
val view: TConfig = MagicalGlobalMarketPro.view
val data: TConfig = MagicalGlobalMarketPro.data

fun toShop(data: String): Shop {
    ByteArrayInputStream(Base64.getDecoder().decode(data)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as Shop
        }
    }
}

fun fromShop(shop: Shop): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(shop)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

fun asStringColored(string: String): String {
    return TLocale.asString(string).replace("&", "§")
}