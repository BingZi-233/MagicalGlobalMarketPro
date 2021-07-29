package vip.bingzi.magicalglobalmarketpro.command

import io.izzel.taboolib.module.command.base.BaseCommand
import io.izzel.taboolib.module.command.base.BaseMainCommand
import io.izzel.taboolib.module.command.base.BaseSubCommand
import io.izzel.taboolib.module.command.base.SubCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import vip.bingzi.magicalglobalmarketpro.view.viewimpt.OpenView
import vip.bingzi.magicalglobalmarketpro.view.viewimpt.PushView

@BaseCommand(name = "MagicalGlobalMarketPro",
    aliases = ["magicalglobalmarketpro",
        "MAGICALGLOBALMARKETPRO",
        "MGMP",
        "MagicalGlobalMarket",
        "magicalglobalmarket",
        "MAGICALGLOBALMARKET",
        "mgmp",
        "GlobalShop",
        "globalshop",
        "GLOBALSHOP",
        "gs",
        "GS"],
    permission = "magicalglobalmarketpro.use")
class Command : BaseMainCommand() {
    @SubCommand
    val open: BaseSubCommand = object : BaseSubCommand() {
        override fun onCommand(p0: CommandSender, p1: org.bukkit.command.Command, p2: String, p3: Array<out String>) {
            if (p0 is Player) {
                OpenView().startView(p0)
            }
        }
    }

    @SubCommand
    val push: BaseSubCommand = object : BaseSubCommand() {
        override fun onCommand(p0: CommandSender, p1: org.bukkit.command.Command, p2: String, p3: Array<out String>) {
            if (p0 is Player) {
                PushView().startView(p0)
            }
        }
    }
}