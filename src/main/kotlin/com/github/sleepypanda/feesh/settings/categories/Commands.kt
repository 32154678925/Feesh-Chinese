package com.github.sleepypanda.feesh.settings.categories

import com.github.sleepypanda.feesh.features.commands.FearMongererShopPricesCommand
import com.github.sleepypanda.feesh.features.commands.GearCraftPricesCommand
import com.github.sleepypanda.feesh.features.commands.JunkerJoelShopPricesCommand
import com.github.sleepypanda.feesh.features.commands.PersonalBestCommand
import com.github.sleepypanda.feesh.features.commands.PetLevelUpPricesCommand
import com.github.sleepypanda.feesh.features.commands.SpiderDenRainScheduleCommand
import com.github.sleepypanda.feesh.features.commands.TerryShopPricesCommand
import com.github.sleepypanda.feesh.utils.ChatUtils
import com.github.sleepypanda.feesh.utils.enums.ColorCodes.*
import com.github.sleepypanda.feesh.utils.enums.FormattingCodes.*
import com.github.sleepypanda.feesh.utils.enums.PricingMode
import com.teamresourceful.resourcefulconfigkt.api.CategoryKt

object Commands : CategoryKt("命令") {
    init {
        separator {
            this.title = "${AQUA}${BOLD}宠物升级价格"
        }

        button {
            title = "宠物升级价格"
            description = "计算将钓鱼宠物从1级升级到100级的收益，并在聊天中显示结果。执行 ${WHITE}/${PetLevelUpPricesCommand.COMMAND_NAME}"
            text = "点击执行"
            onClick {
                ChatUtils.command(PetLevelUpPricesCommand.COMMAND_NAME)
            }
        }

        separator {
            this.title = "${AQUA}${BOLD}装备制作价格"
        }

        button {
            title = "装备制作价格"
            description = "计算用钓鱼掉落物制作不同装备部件的收益，并在聊天中显示结果。执行 ${WHITE}/${GearCraftPricesCommand.COMMAND_NAME}"
            text = "点击执行"
            onClick {
                ChatUtils.command(GearCraftPricesCommand.COMMAND_NAME)
            }
        }
    }

    var gearCraftPricesPriceMode by enum(PricingMode.SELL_OFFER) {
        this.name = Translated("装备制作价格模式")
        this.description = Translated("定义如何计算基础钓鱼掉落物的价格，这些物品可以出售给集市或用于制作装备。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}恐惧商贩商店价格"
        }

        button {
            title = "恐惧商贩商店价格"
            description = "计算从恐惧商贩商店出售物品与出售绿色/紫色糖果的收益比较，并在聊天中显示结果。执行 ${WHITE}/${FearMongererShopPricesCommand.COMMAND_NAME}"
            text = "点击执行"
            onClick {
                ChatUtils.command(FearMongererShopPricesCommand.COMMAND_NAME)
            }
        }
    }

    var fearMongererShopPricesPriceMode by enum(PricingMode.SELL_OFFER) {
        this.name = Translated("恐惧商贩商店价格模式")
        this.description = Translated("定义如何计算糖果和商店物品的价格，这些物品可以出售给集市。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Junker Joel 商店价格"
        }

        button {
            title = "Junker Joel 商店价格"
            description = "计算从 Junker Joel 商店出售物品与出售锈蚀硬币、破损皮带扣和旧皮靴的收益比较，并在聊天中显示结果。执行 ${WHITE}/${JunkerJoelShopPricesCommand.COMMAND_NAME}"
            text = "点击执行"
            onClick {
                ChatUtils.command(JunkerJoelShopPricesCommand.COMMAND_NAME)
            }
        }
    }

    var junkerJoelShopPricesPriceMode by enum(PricingMode.SELL_OFFER) {
        this.name = Translated("Junker Joel 商店价格模式")
        this.description = Translated("定义如何计算基础物品和商店物品的价格（集市出售报价与即时出售）。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Terry 商店价格"
        }

        button {
            title = "Terry 商店价格"
            description = "计算从 Terry 商店出售物品与出售冰块/蓝冰块碎块的收益比较，并在聊天中显示结果。执行 ${WHITE}/${TerryShopPricesCommand.COMMAND_NAME}"
            text = "点击执行"
            onClick {
                ChatUtils.command(TerryShopPricesCommand.COMMAND_NAME)
            }
        }
    }

    var terryShopPricesPriceMode by enum(PricingMode.SELL_OFFER) {
        this.name = Translated("Terry 商店价格模式")
        this.description = Translated("定义如何计算冰块、蓝冰块碎块和 Terry 商店物品的价格（集市出售报价与即时出售）。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}蜘蛛巢穴降雨时间表"
        }

        button {
            title = "蜘蛛巢穴降雨时间表"
            description = "在聊天中显示最近的蜘蛛巢穴降雨/雷暴事件。执行 ${WHITE}/${SpiderDenRainScheduleCommand.COMMAND_NAME}"
            text = "点击执行"
            onClick {
                ChatUtils.command(SpiderDenRainScheduleCommand.COMMAND_NAME)
            }
        }

        separator {
            this.title = "${AQUA}${BOLD}个人最佳记录"
        }

        button {
            title = "个人最佳记录"
            description = "在聊天中显示你的个人最佳记录。执行 ${WHITE}/${PersonalBestCommand.COMMAND_NAME}"
            text = "点击执行"
            onClick {
                ChatUtils.command(PersonalBestCommand.COMMAND_NAME)
            }
        }
    }
}
