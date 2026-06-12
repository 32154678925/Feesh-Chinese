package com.github.sleepypanda.feesh.settings.categories

import com.github.sleepypanda.feesh.FeeshMod
import com.github.sleepypanda.feesh.settings.models.AlertableSeaCreatureTypes
import com.github.sleepypanda.feesh.settings.models.RareSeaCreatureTypesAllChat
import com.github.sleepypanda.feesh.constants.RareDropTypes
import com.github.sleepypanda.feesh.utils.enums.ColorCodes.*
import com.github.sleepypanda.feesh.utils.enums.FormattingCodes.*
import com.github.sleepypanda.feesh.features.chat.CompactCatchMessages
import com.teamresourceful.resourcefulconfigkt.api.CategoryKt
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen
import net.minecraft.Util

enum class HotspotChatSource(val displayName: String) {
    PARTY_CHAT("队伍聊天"),
    ALL_CHAT("全部聊天");

    override fun toString(): String = displayName
}

object Chat : CategoryKt("聊天") {
    override val description: TranslatableValue
        get() = Literal(
            "通过队伍/全部聊天分享信息，或修改本地玩家聊天的功能。"
        )

    init {
        separator {
            this.title = "${AQUA}${BOLD}压缩捕获消息"
        }
    }

    var compactSeaCreaturesMessages by boolean(false) {
        this.name = Translated("压缩海洋生物捕获消息")
        this.description = Translated("缩短双钩消息和捕获消息，告诉你捕获了什么海洋生物。因此，你将看到类似 '双重钩！一只雪人已生成！' 的消息，而不是 '这是一个双重钩！太棒了！这是什么生物？！'。")
    }

    var compactDoubleHookMessageTemplate by strings(CompactCatchMessages.DEFAULT_DOUBLE_HOOK_TEMPLATE) {
        this.name = Translated("双钩消息模板")
        this.description = Translated("${GRAY}自定义双钩时显示的文本。留空以使用默认文本。")
    }

    var compactCatchMessageTemplate by strings(CompactCatchMessages.DEFAULT_CATCH_TEMPLATE) {
        this.name = Translated("海洋生物捕获消息模板")
        this.description = Translated("${GRAY}自定义海洋生物捕获消息。留空以使用默认文本。占位符：${WHITE}{article}${GRAY} — a/an（小写）；${WHITE}{Article}${GRAY} — A/An（大写）；${WHITE}{sc}${GRAY} — 海洋生物名称（默认带颜色）。")
    }

    init {
        button {
            title = "颜色与格式指南"
            description = "使用上述自定义文本模板前，请阅读解释颜色代码和格式代码的指南。"
            text = "点击打开"
            onClick {
                Util.getPlatform().openUri("https://github.com/Sleepy-Panda/Feesh/blob/develop/docs/Colors%20and%20formatting%20guide.md")
            }
        }
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}海洋生物"
        }
    }

    var shareRareSeaCreatures by boolean(true) {
        this.name = Translated("将海洋生物分享到队伍聊天")
        this.description = Translated("当你捕获海洋生物时，向队伍聊天发送消息。请启用 ${YELLOW}Skyblock 设置 -> 个人 -> 钓鱼设置 -> 海洋生物聊天")
    }

    var shareSeaCreaturesList by select(
        *AlertableSeaCreatureTypes.values().filter { it.isEnabledByDefault }.toTypedArray(),
    ) {
        this.name = Translated("选择要分享到队伍聊天的海洋生物")
        this.searchTerms = AlertableSeaCreatureTypes.values().map { it.displayName }.toList()
    }

    var shareSeaCreaturesIncludeCocooned by boolean(true) {
        this.name = Translated("分享茧化海洋生物")
        this.description = Translated("当选定的海洋生物被你茧化时，也向队伍聊天发送消息。")
    }

    var shareRareSeaCreaturesAllChat by boolean(false) {
        this.name = Translated("将稀有海洋生物位置分享到全部聊天")
        this.description = Translated("当你捕获稀有海洋生物时，向全部聊天发送带有坐标的消息。请启用 ${YELLOW}Skyblock 设置 -> 个人 -> 钓鱼设置 -> 海洋生物聊天")
    }

    var shareRareSeaCreaturesTypesAllChat by select(RareSeaCreatureTypesAllChat.THUNDER, *RareSeaCreatureTypesAllChat.values()) {
        this.name = Translated("选择要分享位置到全部聊天的海洋生物")
        this.searchTerms = RareSeaCreatureTypesAllChat.values().map { it.displayName }.toList()
    }

    var messageOnPlayerDeath by boolean(true) {
        this.name = Translated("被钓鱼Boss击杀时发送队伍聊天消息")
        this.description = Translated("当你被雷霆/贾巴斯领主/拉格纳罗克/威基蒂基/泰坦巨蚺/尼斯湖水怪击杀时，向队伍聊天发送消息。这样你的队伍成员可以等你或嘲笑你 😈")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}稀有掉落"
        }
    }

    var messageOnRareDrops by boolean(true) {
        this.name = Translated("将稀有掉落分享到队伍聊天")
        this.description = Translated("当稀有物品掉落时，向队伍聊天发送消息。")
    }

    var messageOnRareDropTypes by select(RareDropTypes.LUCKY_CLOVER_CORE, *RareDropTypes.values()) {
        this.name = Translated("选择要分享到队伍聊天的稀有掉落")
        this.searchTerms = RareDropTypes.values().map { it.displayName }.toList()
    }

    var includeDropNumberIntoDropMessage by boolean(true) {
        this.name = Translated("包含掉落编号")
        this.description = Translated("${GRAY}在队伍聊天消息中包含当前会话中掉落的序号。\n${RED}需要启用钓鱼收益追踪器！${GRAY}掉落编号在钓鱼收益追踪器重置时重置。")
    }

    var includeMagicFindIntoRareDropMessage by boolean(true) {
        this.name = Translated("包含魔法寻宝")
        this.description = Translated("将 ✯ 魔法寻宝值作为队伍聊天消息的一部分发送。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}热点"
        }
    }

    var messageOnHotspotFound by boolean(true) {
        this.name = Translated("点击时提供分享发现的热点")
        this.description = Translated("显示可点击的聊天消息，提供将热点位置及其加成分享到全部聊天或队伍聊天。你需要靠近热点才能触发。")
    }

    init {
        button {
            title = "分享热点按钮"
            description = "在 Minecraft 的控制菜单中设置按键绑定，按下时可将最近的热点分享到队伍聊天或全部聊天。按下按钮时需要靠近热点。"
            text = "点击打开"
            onClick {
                val mc = FeeshMod.mc
                mc.schedule {
                    val currentScreen = mc.screen ?: return@schedule
                    mc.setScreen(KeyBindsScreen(currentScreen, mc.options))
                }
            }
        }
    }

    var autoMessageOnHotspotFound by boolean(false) {
        this.name = Translated("自动分享发现的热点")
        this.description = Translated("将热点位置及其加成发送到选定的聊天。你需要靠近热点才能触发。")
    }

    var autoMessageOnHotspotFoundSource by enum(HotspotChatSource.PARTY_CHAT) {
        this.name = Translated("自动分享到")
        this.description = Translated("自动分享发现的热点的聊天类型（如果启用了自动分享）。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}奖杯"
        }
    }

    var shareTrophyFrogDiscovered by boolean(true) {
        this.name = Translated("将奖杯蛙发现分享到队伍聊天")
        this.description = Translated("当你发现新的奖杯蛙时，向队伍聊天发送消息。")
    }

    var shareTrophyFishDiscovered by boolean(true) {
        this.name = Translated("将奖杯鱼发现分享到队伍聊天")
        this.description = Translated("当你发现新的奖杯鱼时，向队伍聊天发送消息。")
    }
}
