package vip.bingzi.magicalglobalmarketpro.util

import io.izzel.taboolib.loader.PluginBoot
import io.izzel.taboolib.module.config.TConfig
import io.izzel.taboolib.module.locale.TLocale
import io.izzel.taboolib.module.locale.logger.TLogger
import io.izzel.taboolib.module.locale.logger.TLoggerManager
import org.bukkit.entity.Player
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import vip.bingzi.magicalglobalmarketpro.MagicalGlobalMarketPro
import vip.bingzi.magicalglobalmarketpro.bean.Email
import vip.bingzi.magicalglobalmarketpro.bean.Shop
import vip.bingzi.magicalglobalmarketpro.money.Economy
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

// 方便全局访问
val plugin: PluginBoot = MagicalGlobalMarketPro.plugin
val logger: TLogger = TLoggerManager.getLogger(plugin)
val setting: TConfig = MagicalGlobalMarketPro.setting
val view: TConfig = MagicalGlobalMarketPro.view
val data: TConfig = MagicalGlobalMarketPro.data
var economy: Economy? = null
val email: MutableSet<Email> = mutableSetOf()

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

fun toEmail(data: String): Email {
    ByteArrayInputStream(Base64.getDecoder().decode(data)).use { byteArrayInputStream ->
        BukkitObjectInputStream(byteArrayInputStream).use { bukkitObjectInputStream ->
            return bukkitObjectInputStream.readObject() as Email
        }
    }
}

fun fromEmail(email: Email): String {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        BukkitObjectOutputStream(byteArrayOutputStream).use { bukkitObjectOutputStream ->
            bukkitObjectOutputStream.writeObject(email)
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
        }
    }
}

fun asStringColored(string: String): String {
    return TLocale.asString(string).replace("&", "§")
}

fun getPlayerEmail(player: Player): Email {
    email.forEach { email ->
        if (email.player == player.name) {
            return email
        }
    }
    email.add(Email(player))
    return getPlayerEmail(player)
}

object Tools {
    fun loadEmail() {
        data.getStringList("Email").forEach {
            email.add(toEmail(it))
        }
    }

    fun saveEmail() {
        val mutableListOf = mutableListOf<String>()
        email.forEach {
            mutableListOf.add(fromEmail(it))
        }
        data.set("Email", mutableListOf)
        data.saveToFile()
    }
}