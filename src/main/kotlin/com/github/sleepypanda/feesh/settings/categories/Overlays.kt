package com.github.sleepypanda.feesh.settings.categories

import com.github.sleepypanda.feesh.FeeshMod
import com.github.sleepypanda.feesh.utils.enums.ColorCodes.*
import com.github.sleepypanda.feesh.utils.enums.FormattingCodes.*
import com.github.sleepypanda.feesh.utils.enums.DeployableTypes
import com.github.sleepypanda.feesh.utils.enums.PricingModeWithNpc
import com.github.sleepypanda.feesh.utils.ChatUtils
import com.teamresourceful.resourcefulconfigkt.api.ObservableEntry
import com.teamresourceful.resourcefulconfigkt.api.CategoryKt
import com.github.sleepypanda.feesh.features.commands.PauseAllTrackersCommand
import com.github.sleepypanda.feesh.features.commands.SetTrackerDropsCommand
import com.github.sleepypanda.feesh.features.overlays.ArchfiendDiceProfitTracker
import com.github.sleepypanda.feesh.features.overlays.BarnFishingTimer
import com.github.sleepypanda.feesh.features.overlays.FishingProfitTracker
import com.github.sleepypanda.feesh.features.overlays.CrimsonIsleTracker
import com.github.sleepypanda.feesh.features.overlays.FishingFestivalTracker
import com.github.sleepypanda.feesh.features.overlays.JerryWorkshopTracker
import com.github.sleepypanda.feesh.features.overlays.MagmaCoreFishingTracker
import com.github.sleepypanda.feesh.features.overlays.SeaCreaturesPerHourTracker
import com.github.sleepypanda.feesh.features.overlays.SeaCreaturesTracker
import com.github.sleepypanda.feesh.features.overlays.TreasureFishingTracker
import com.github.sleepypanda.feesh.features.overlays.BayouTracker
import com.github.sleepypanda.feesh.features.overlays.WaterHotspotsTracker
import com.github.sleepypanda.feesh.features.overlays.GalateaWaterTracker
import com.github.sleepypanda.feesh.features.overlays.LotusAtollTracker
import com.github.sleepypanda.feesh.features.overlays.SeaCreatureHpTracker
import com.github.sleepypanda.feesh.settings.models.HpTrackableSeaCreatureTypes
import com.github.sleepypanda.feesh.utils.gui.MoveGuis
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen
import net.minecraft.Util
import java.awt.Color

enum class SeaCreaturesTrackerDisplayMode(val displayName: String) {
    ONLY_RARE("仅稀有"),
    ALL("全部");

    override fun toString(): String = displayName
}

enum class SeaCreaturesTrackerSorting(val displayName: String) {
    CATCHES_COUNT_DESC("捕获次数（降序）"),
    CATCHES_COUNT_ASC("捕获次数（升序）"),
    RARITY_DESC("稀有度（降序）"),
    RARITY_ASC("稀有度（升序）");

    override fun toString(): String = displayName
}

enum class FishingHookTimerMode(val displayName: String) {
    UNTIL_REEL_IN("直到收线"),
    SINCE_CASTED("自抛出起");

    override fun toString(): String = displayName
}

enum class NearbyEntitiesCounterTypes(val displayName: String) {
    LEGION("军团"),
    BOBBING_TIME("弹动时间"),
    CHUMCAP_BUCKETS("Chumcap 桶");

    override fun toString(): String = displayName
}

enum class CrimsonIsleTrashGearDropsPriceMode(val displayName: String) {
    NORMAL("普通拍卖行价格"),
    ESSENCE("深红精华"),
    NPC_PRICE("NPC价格");

    override fun toString(): String = displayName
}

object Overlays : CategoryKt("叠加层") {
    private fun getCustomStyleDescription(overlayName: String): String {
        return "是否将\"自定义叠加层样式\"类别中的自定义样式应用于 $overlayName。禁用时，叠加层将以纯文本形式绘制，没有这些装饰。"
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}通用"
        }

        button {
            title = "移动 GUI"
            description = "允许移动和调整\"叠加层\"设置部分中启用的所有 GUI。执行 ${WHITE}/${MoveGuis.COMMAND_NAME}"
            text = "点击移动"
            onClick {
                MoveGuis.moveAllGuis()
            }
        }
    }

    var overlayButtonsRequireCtrlClick by boolean(false) {
        this.name = Translated("叠加层按钮需要 Ctrl+点击")
        this.description = Translated("启用后，叠加层按钮（重置、暂停、切换视图模式和行内 +/-/× 按钮）仅在 Ctrl+点击时激活，以避免意外交互。")
    }
    
    init {
        button {
            title = "暂停所有追踪器按键绑定"
            description = "在 Minecraft 的控制菜单中设置按键绑定，按下时暂停所有活跃追踪器（计时器停止）。默认为 PAUSE。\n执行 ${WHITE}/${PauseAllTrackersCommand.COMMAND_NAME}"
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

    var trackersAutoPauseSeconds by int(180) {
        this.name = Translated("空闲秒数后自动暂停所有追踪器")
        this.description = Translated("当你停止钓鱼这么长时间后（以秒为单位），暂停各种小部件上的计时器。\n${YELLOW}请确保选择足够的时间来击杀钓鱼生物并从中获取战利品，以便在暂停前被追踪器计数！")
        this.range = 10..300
        this.slider = true
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}自定义叠加层样式"
            this.description = "自定义叠加层的样式。你可以为每个叠加层单独启用/禁用此样式的应用。"
        }
    }

    var overlaysBackground by boolean(false) {
        this.name = Translated("叠加层背景")
        this.description = Translated("在叠加层后面绘制背景（渐变或纯色）以获得更好的可读性。")
    }

    var overlaysBackgroundColor1 by color(Color(0, 0, 0, 70).rgb) {
        this.name = Translated("叠加层背景颜色 #1")
        this.description = Translated("选择带有透明度的背景颜色。用作垂直渐变的顶部颜色。")
        this.allowAlpha = true
    }

    var overlaysBackgroundColor2 by color(Color(0, 0, 0, 70).rgb) {
        this.name = Translated("叠加层背景颜色 #2")
        this.description = Translated("选择带有透明度的背景颜色。用作垂直渐变的底部颜色。使用与上方相同的颜色以纯色填充背景。")
        this.allowAlpha = true
    }

    var overlaysBorder by boolean(false) {
        this.name = Translated("叠加层边框")
        this.description = Translated("在叠加层周围绘制边框。")
    }

    var overlaysBorderColor by color(Color(255, 255, 255, 255).rgb) {
        this.name = Translated("叠加层边框颜色")
        this.description = Translated("选择带有透明度的边框颜色。")
        this.allowAlpha = true
    }

    var overlaysBorderWidth by int(1) {
        this.name = Translated("叠加层边框宽度")
        this.description = Translated("选择边框宽度。")
        this.range = 1..5
        this.slider = true
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}海洋生物"
        }
    }

    var seaCreaturesTrackerOverlay by boolean(false) {
        this.name = Translated("海洋生物追踪器")
        this.description = Translated("""
${GRAY}显示一个叠加层，概述捕获的海洋生物及其相关统计数据。此叠加层有[会话]和[总计]视图模式。
${GRAY}查看详情：在物品栏屏幕中悬停在一行上。
${GRAY}重置[会话]：${WHITE}/${SeaCreaturesTracker.RESET_SESSION}
${GRAY}重置[总计]：${WHITE}/${SeaCreaturesTracker.RESET_TOTAL}
""".trimIndent())
    }

    var seaCreaturesTrackerMode by enum(SeaCreaturesTrackerDisplayMode.ALL) {
        this.name = Translated("海洋生物追踪器显示模式")
        this.description = Translated("设置是否在叠加层中隐藏普通海洋生物，仅显示稀有海洋生物。无论此设置如何，所有海洋生物都会被追踪。")
    }

    var countCocoonedSeaCreatures by boolean(true) {
        this.name = Translated("计数茧化海洋生物")
        this.description = Translated("将在海洋生物追踪器中包含由你的 Bloodshot 重铸茧化的海洋生物，因为 SB 将其视为你自己的。")
    }

    var showSeaCreaturesPercentage by boolean(true) {
        this.name = Translated("显示百分比")
        this.description = Translated("显示每种海洋生物占海洋生物总数的百分比。如果禁用，统计数据仍可在工具提示中查看。")
    }

    var showSeaCreaturesDoubleHookStatistics by boolean(true) {
        this.name = Translated("显示双钩统计")
        this.description = Translated("显示每种海洋生物被双钩的频率统计（在叠加层中显示为'DH'）。如果禁用，统计数据仍可在工具提示中查看。")
    }

    var showCocoonedStatistics by boolean(false) {
        this.name = Translated("显示茧化统计")
        this.description = Translated("显示每种海洋生物被你的 Bloodshot 重铸茧化的频率统计（在叠加层中显示为'BS'）。如果禁用，统计数据仍可在工具提示中查看。")
    }

    var seaCreaturesTrackerSorting by enum(SeaCreaturesTrackerSorting.RARITY_DESC) {
        this.name = Translated("海洋生物排序")
        this.description = Translated("设置海洋生物列表的排序顺序。")
    }

    var seaCreaturesTrackerShowTop by int(50) {
        this.name = Translated("最大行数")
        this.description = Translated("在会话/总计视图中显示前 N 行海洋生物。剩余条目将分组在\"其他海洋生物\"下。")
        this.range = 1..100
        this.slider = true
    }

    var resetSeaCreaturesTrackerSessionOnGameClosed by boolean(true) {
        this.name = Translated("关闭游戏时自动重置[会话]")
        this.description = Translated("关闭 Minecraft 时自动重置海洋生物追踪器[会话]。")
    }

    init {
        button {
            title = "编辑海洋生物追踪器指南"
            description = "打开关于如何在海洋生物追踪器[会话]和[总计]中调整海洋生物数量和统计数据的指南。"
            text = "点击打开"
            onClick {
                Util.getPlatform().openUri("https://github.com/Sleepy-Panda/Feesh/blob/develop/docs/Editing%20sea%20creatures%20tracker.md")
            }
        }
    }

    var seaCreaturesTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("海洋生物追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}钓鱼收益"
        }
    }

    var fishingProfitTrackerOverlay by boolean(false) {
        this.name = Translated("钓鱼收益追踪器")
        this.description = Translated("""
${GRAY}显示一个叠加层，展示你钓鱼时获得的收益。此叠加层有[会话]和[总计]视图模式。
${GRAY}要计算添加到背包的物品，请确保启用 ${YELLOW}Skyblock 设置 -> 个人 -> 聊天反馈 -> 背包通知
${GRAY}重置[会话]：${WHITE}/${FishingProfitTracker.RESET_COMMAND}
${GRAY}重置[总计]：${WHITE}/${FishingProfitTracker.RESET_TOTAL_COMMAND}
${GRAY}暂停：${WHITE}/${FishingProfitTracker.PAUSE_COMMAND}
        """.trimIndent())
    }

    var fishingProfitTrackerPriceMode by ObservableEntry(
        enum(PricingModeWithNpc.SELL_OFFER) {
            this.name = Translated("价格模式")
            this.description = Translated("如何计算钓鱼收益追踪器中掉落物品的价格。")
        }
    ) { prev, new ->
        if (prev != new) {
            FishingProfitTracker.refreshTotalItemsProfits()
        }
    }
    
    var priceModeForCrimsonIsleTrashGearDrops by ObservableEntry(
        enum(CrimsonIsleTrashGearDropsPriceMode.NPC_PRICE) {
            this.name = Translated("深红之岛垃圾装备掉落价格模式")
            this.description = Translated("如何计算深红之岛钓鱼掉落物的价格：蜗牛靴、Moogma 护腿、烈焰胸甲、火山之刃、火山之杖。")
        }
    ) { prev, new ->
        if (prev != new) {
            FishingProfitTracker.refreshTotalItemsProfits()
        }
    }

    var fishingProfitTrackerHideCheaperThan by int(1_000_000) {
        this.name = Translated("隐藏廉价物品[会话]")
        this.description = Translated("比指定阈值（以硬币计）更便宜的物品将在钓鱼收益追踪器[会话]中隐藏。它们将分组在\"廉价物品\"部分下。设为0以显示所有物品。")
    }

    var fishingProfitTrackerHideCheaperThanTotal by int(1_000_000) {
        this.name = Translated("隐藏廉价物品[总计]")
        this.description = Translated("比指定阈值（以硬币计）更便宜的物品将在钓鱼收益追踪器[总计]中隐藏。它们将分组在\"廉价物品\"部分下。设为0以显示所有物品。")
    }

    var fishingProfitTrackerShowTop by int(15) {
        this.name = Translated("最大行数")
        this.description = Translated("显示最昂贵物品的前 N 行。其他更便宜的物品将分组在\"廉价物品\"部分下。此设置基于\"隐藏廉价物品\"设置之上。")
        this.range = 1..50
        this.slider = true
    }

    var shouldAnnounceRareDropsWhenPickup by boolean(true) {
        this.name = Translated("宣布稀有掉落")
        this.description = Translated("当稀有物品添加到钓鱼收益追踪器时，向玩家聊天发送\"稀有掉落！\"消息（针对相对稀有但 Hypixel 默认没有\"稀有掉落！\"消息的物品）。")
    }

    var shouldHideTimerInTotal by boolean(false) {
        this.name = Translated("在[总计]视图中隐藏计时器和 coins/h")
        this.description = Translated("在钓鱼收益追踪器[总计]视图中隐藏计时器和 coins/h。如果你想将过去的掉落添加到追踪器但不知道经过的时间，这很有用。")
    }

    var resetFishingProfitTrackerOnGameClosed by boolean(true) {
        this.name = Translated("关闭游戏时自动重置[会话]")
        this.description = Translated("关闭 Minecraft 时自动重置钓鱼收益追踪器[会话]。")
    }
 
    init {
        button {
            title = "编辑钓鱼收益追踪器指南"
            description = "打开关于如何在钓鱼收益追踪器[会话]和[总计]中调整物品数量和经过时间的指南。"
            text = "点击打开"
            onClick {
                Util.getPlatform().openUri("https://github.com/Sleepy-Panda/Feesh/blob/develop/docs/Editing%20profit%20tracker.md")
            }
        }
    }

    var fishingProfitTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("钓鱼收益追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}附近实体"
        }
    }

    var nearbyEntitiesCounterOverlay by boolean(false) {
        this.name = Translated("附近实体计数器")
        this.description = Translated("""
显示一个带有各种附近实体计数器的叠加层，这些实体为你提供钓鱼增益：
- 军团 - 30格内的玩家数量（不包括你）。
- 弹动时间 - 30格内的鱼钩数量（包括你自己的鱼钩）。
- Chumcap 桶 - 30格内的 Chumcap 桶数量（包括你自己的桶）。Chum 桶不计入。

如果你的快捷栏中没有钓鱼竿则隐藏！""".trimIndent())
    }

    var nearbyEntitiesCounterTypes by select(NearbyEntitiesCounterTypes.LEGION, NearbyEntitiesCounterTypes.BOBBING_TIME) {
        this.name = Translated("要显示的附近实体计数器类型")
        this.searchTerms = NearbyEntitiesCounterTypes.values().map { it.displayName }.toList()
    }

    var nearbyEntitiesCounterCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("附近实体计数器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Barn 钓鱼计时器"
        }
    }

    var barnFishingTimerOverlay by boolean(false) {
        this.name = Translated("Barn 钓鱼计时器")
        this.description = Translated("显示一个叠加层，展示附近的海洋生物数量及其存活时间。主要用于 barn 钓鱼。如果你的快捷栏中没有钓鱼竿或穿着 Hunter 盔甲则隐藏！\n重置：${WHITE}/${BarnFishingTimer.RESET_COMMAND}")
    }

    init {
        button {
            title = "重置 barn 钓鱼计时器按键绑定"
            description = "在 Minecraft 的控制菜单中设置按键绑定以重置 barn 钓鱼计时器。"
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

    var barnFishingTimerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("Barn 钓鱼计时器"))
    }
      
    init {
        separator {
            this.title = "${AQUA}${BOLD}可部署物品"
        }
    }

    var deployablesTimerOverlay by boolean(false) {
        this.name = Translated("可部署物品计时器")
        this.description = Translated("显示一个叠加层，展示附近可部署物品的剩余时间。")
    }

    var deployablesOverlayTypes by select(DeployableTypes.TOTEM_OF_CORRUPTION, *DeployableTypes.values()) {
        this.name = Translated("选择要在叠加层中显示的可部署物品")
        this.searchTerms = DeployableTypes.values().map { it.displayName }.toList()
    }

    var deployablesTimerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("可部署物品计时器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}消耗品"
        }
    }

    var consumablesTimerOverlay by boolean(false) {
        this.name = Translated("消耗品计时器")
        this.description = Translated("显示一个叠加层，展示活跃 Moby-Duck 的剩余时间。")
    }

    var consumablesTimerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("消耗品计时器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}海洋生物 HP"
        }
    }

    var seaCreaturesHpOverlay by boolean(false) {
        this.name = Translated("海洋生物 HP")
        this.description = Translated("显示一个叠加层，展示 lootshare 范围内附近海洋生物的 HP。\n显示约5秒免疫指示器，表示某些海洋生物类型的伤害减免期。并非100%精确！")
    }

    var seaCreaturesHpTrackedList by ObservableEntry(select(
            *HpTrackableSeaCreatureTypes.values().filter { it.isEnabledByDefault }.toTypedArray(),
        ) {
            this.name = Translated("选择海洋生物")
            this.description = Translated("哪些海洋生物要追踪并显示在 HP 叠加层中。")
            this.searchTerms = HpTrackableSeaCreatureTypes.values().map { it.displayName }.toList()
    }) { prev, new ->
        if (!prev.contentEquals(new)) {
            SeaCreatureHpTracker.updateEnabledMobTypes()
        }
    }

    var seaCreaturesHpOverlayMaxCount by int(7) {
        this.name = Translated("最大条目数")
        this.description = Translated("最多显示 N 个附近的海洋生物（以限制叠加层大小）。HP 较低的海洋生物优先显示。")
        this.range = 1..20
        this.slider = true
    }

    var seaCreaturesHpCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("海洋生物 HP"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}鱼钩"
        }
    }

    var fishingHookTimerOverlay by boolean(false) {
        this.name = Translated("鱼钩计时器")
        this.description = Translated("显示你的鱼钩计时器，以及鱼已到达可以收线的提示。为此，请启用 ${YELLOW}Skyblock 设置 -> 个人 -> 钓鱼设置 -> 钓鱼计时器")
    }

    var fishingHookTimerMode by enum(FishingHookTimerMode.UNTIL_REEL_IN) {
        this.name = Translated("鱼钩计时器模式")
        this.description = Translated("'直到收线'在鱼游向鱼钩时显示倒计时。'自抛出起'在鱼钩抛出后显示计时器。")
    }

    var fishingHookFishArrivedTemplate by string("§c§l!!!") {
        this.name = Translated("自定义鱼到达模板")
        this.description = Translated("当鱼到达你的鱼钩时，用自定义文本替换默认的 !!!。留空以使用默认文本。")
    }

    var fishingHookFishTimerTemplate by string("§e§l{timer}") {
        this.name = Translated("自定义计时器格式")
        this.description = Translated("用自定义计时器文本替换默认格式。使用 {timer} 将计时器秒数插入模板。留空以使用默认文本。")
    }

    init {
        button {
            title = "颜色与格式指南"
            description = "对于上方带有自定义文本模板的设置，请查看颜色代码和格式代码。"
            text = "点击打开"
            onClick {
                Util.getPlatform().openUri("https://github.com/Sleepy-Panda/Feesh/blob/develop/docs/Colors%20and%20formatting%20guide.md")
            }
        }
    }

    var fishingHookTimerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("鱼钩计时器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}鱼饵"
        }
    }

    var baitTrackerOverlay by boolean(false) {
        this.name = Translated("鱼饵追踪器")
        this.description = Translated("${GRAY}从你的钓鱼袋预览（快捷栏第9格）显示剩余鱼饵数量。")
    }

    var baitTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("鱼饵追踪器"))
    }
    
    init {
        separator {
            this.title = "${AQUA}${BOLD}每小时海洋生物数"
        }
    }

    var seaCreaturesPerHourTrackerOverlay by boolean(false) {
        this.name = Translated("每小时海洋生物追踪器")
        this.description = Translated("""
${GRAY}显示一个叠加层，展示每小时捕获的海洋生物数量以及每会话捕获的海洋生物总数。非持久性 - 重启 MC 后重置。
${GRAY}重置：${WHITE}/${SeaCreaturesPerHourTracker.RESET_COMMAND}
${GRAY}暂停：${WHITE}/${SeaCreaturesPerHourTracker.PAUSE_COMMAND}
""".trimIndent())
    }

    var seaCreaturesPerHourCountDoubleHookAsTwo by boolean(true) {
        this.name = Translated("将双钩计为2个")
        this.description = Translated("启用时，双钩捕获计为2个海洋生物。禁用时计为1个。")
    }

    var seaCreaturesPerHourTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("每小时海洋生物追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}雨、雷、暴风雪"
        }
    }

    var rainTimerOverlay by boolean(false) {
        this.name = Translated("雨/雷/暴风雪计时器")
        this.description = Translated("${GRAY}显示一个叠加层，展示公园、蜘蛛巢穴、Lotus Atoll、Backwater Bayou 和 Jerry's Workshop 中活跃/即将到来的雨/雷/暴风雪计时器。请启用 ${YELLOW}TabList 设置 -> 通用信息小部件 -> 显示雨/显示暴风雪")
    }

    var rainTimerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("雨/雷/暴风雪计时器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}钓鱼节"
        }
    }

    var fishingFestivalTrackerOverlay by boolean(false) {
        this.name = Translated("钓鱼节追踪器")
        this.description = Translated("""
${GRAY}显示一个叠加层，展示钓鱼节期间捕获的鲨鱼数量。非持久性 - 重启 MC 后重置。
${GRAY}重置：${WHITE}/${FishingFestivalTracker.RESET_COMMAND}
""".trimIndent())
    }

    var fishingFestivalTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("钓鱼节追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Jerry's Workshop"
        }
    }

    var jerryWorkshopTrackerOverlay by boolean(false) {
        this.name = Translated("Jerry's Workshop 追踪器")
        this.description = Translated("""
${GRAY}显示一个叠加层，展示在 Jerry Workshop 中雪人/雷因德拉肯的捕获统计。
${GRAY}重置：${WHITE}/${JerryWorkshopTracker.RESET_COMMAND}
""".trimIndent())
    }

    var resetJerryWorkshopTrackerOnGameClosed by boolean(false) {
        this.name = Translated("关闭游戏时自动重置")
        this.description = Translated("关闭 Minecraft 时自动重置 Jerry Workshop 追踪器。")
    }

    var jerryWorkshopTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("Jerry's Workshop 追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Bayou 追踪器"
        }
    }

    var bayouTrackerOverlay by boolean(false) {
        this.name = Translated("Bayou 追踪器")
        this.description = Translated("""
${GRAY}在 Backwater Bayou 钓鱼时显示泰坦巨蚺捕获统计和泰坦巨蚺蜕皮掉落统计。
${GRAY}重置：${WHITE}/${BayouTracker.RESET_COMMAND}
        """.trimIndent())
    }

    var resetBayouTrackerOnGameClosed by boolean(false) {
        this.name = Translated("关闭游戏时自动重置")
        this.description = Translated("关闭 Minecraft 时自动重置 Bayou 追踪器。")
    }

    init {
        button {
            title = "设置泰坦巨蚺蜕皮数量"
            description = "在你的聊天中解释如何为 Bayou 追踪器初始化泰坦巨蚺蜕皮数量和最后掉落日期。"
            text = "点击获取帮助"
            onClick {
                ChatUtils.sendLocalChat("${AQUA}${BOLD}泰坦巨蚺蜕皮设置${RESET}", true)
                ChatUtils.sendLocalChat("${YELLOW}命令：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME}${GOLD} <ITEM_ID> <COUNT> [LAST_ON_DATE]")
                ChatUtils.sendLocalChat("${DARK_AQUA}<ITEM_ID>: ${GRAY}必须是 TITANOBOA_SHED")
                ChatUtils.sendLocalChat("${DARK_AQUA}<COUNT>: ${GRAY}你获得了多少个掉落")
                ChatUtils.sendLocalChat("${DARK_AQUA}[LAST_ON_DATE]: ${GRAY}可选，YYYY-MM-DD hh:mm:ss，不能是未来时间")
                ChatUtils.sendLocalChat("${GREEN}示例：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME} TITANOBOA_SHED 5 2025-05-30 23:59:00")
            }
        }
    }

    var bayouTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("Bayou 追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}水域热点追踪器"
        }
    }

    var waterHotspotsTrackerOverlay by boolean(false) {
        this.name = Translated("水域热点追踪器")
        this.description = Translated("""
${GRAY}在水域热点钓鱼时显示威基蒂基捕获统计和提基面具掉落统计。
${GRAY}重置：${WHITE}/${WaterHotspotsTracker.RESET_COMMAND}
        """.trimIndent())
    }

    var resetWaterHotspotsTrackerOnGameClosed by boolean(false) {
        this.name = Translated("关闭游戏时自动重置")
        this.description = Translated("关闭 Minecraft 时自动重置水域热点追踪器。")
    }

    init {
        button {
            title = "设置提基面具数量"
            description = "在你的聊天中解释如何为水域热点追踪器初始化提基面具数量和最后掉落日期。"
            text = "点击获取帮助"
            onClick {
                ChatUtils.sendLocalChat("${AQUA}${BOLD}提基面具设置${RESET}", true)
                ChatUtils.sendLocalChat("${YELLOW}命令：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME}${GOLD} <ITEM_ID> <COUNT> [LAST_ON_DATE]")
                ChatUtils.sendLocalChat("${DARK_AQUA}<ITEM_ID>: ${GRAY}必须是 TIKI_MASK")
                ChatUtils.sendLocalChat("${DARK_AQUA}<COUNT>: ${GRAY}你获得了多少个掉落")
                ChatUtils.sendLocalChat("${DARK_AQUA}[LAST_ON_DATE]: ${GRAY}可选，YYYY-MM-DD hh:mm:ss，不能是未来时间")
                ChatUtils.sendLocalChat("${GREEN}示例：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME} TIKI_MASK 5 2025-05-30 23:59:00")
            }
        }
    }

    var waterHotspotsTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("水域热点追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}深红之岛追踪器"
        }
    }

    var crimsonIsleTrackerOverlay by boolean(false) {
        this.name = Translated("深红之岛追踪器")
        this.description = Translated("""
${GRAY}显示一个叠加层，包含 Fiery Scuttler & 拉格纳罗克（在热点钓鱼时）、Plhlegblast（在 Plhlegblast 池中）、雷霆 & 贾巴斯领主捕获统计。还有放射性小瓶掉落统计。
${GRAY}重置：${WHITE}/${CrimsonIsleTracker.RESET_COMMAND}
        """.trimIndent())
    }

    var resetCrimsonIsleTrackerOnGameClosed by boolean(false) {
        this.name = Translated("关闭游戏时自动重置")
        this.description = Translated("关闭 Minecraft 时自动重置深红之岛追踪器。")
    }

    init {
        button {
            title = "设置放射性小瓶数量"
            description = "在你的聊天中解释如何初始化放射性小瓶数量和最后掉落日期。"
            text = "点击获取帮助"
            onClick {
                ChatUtils.sendLocalChat("${AQUA}${BOLD}放射性小瓶设置${RESET}", true)
                ChatUtils.sendLocalChat("${YELLOW}命令：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME}${GOLD} <ITEM_ID> <COUNT> [LAST_ON_DATE]")
                ChatUtils.sendLocalChat("${DARK_AQUA}<ITEM_ID>: ${GRAY}必须是 RADIOACTIVE_VIAL")
                ChatUtils.sendLocalChat("${DARK_AQUA}<COUNT>: ${GRAY}你获得了多少个掉落")
                ChatUtils.sendLocalChat("${DARK_AQUA}[LAST_ON_DATE]: ${GRAY}可选，YYYY-MM-DD hh:mm:ss，不能是未来时间")
                ChatUtils.sendLocalChat("${GREEN}示例：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME} RADIOACTIVE_VIAL 2 2025-05-30 23:59:00")
            }
        }
    }

    var crimsonIsleTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("深红之岛追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}加拉提亚水域追踪器"
        }
    }

    var galateaWaterTrackerOverlay by boolean(false) {
        this.name = Translated("加拉提亚水域追踪器")
        this.description = Translated("""
${GRAY}在加拉提亚水域钓鱼时显示 The Loch Emperor 和尼斯湖水怪的捕获统计。
${GRAY}重置：${WHITE}/${GalateaWaterTracker.RESET_COMMAND}
        """.trimIndent())
    }

    var resetGalateaWaterTrackerOnGameClosed by boolean(false) {
        this.name = Translated("关闭游戏时自动重置")
        this.description = Translated("关闭 Minecraft 时自动重置加拉提亚水域追踪器。")
    }

    var galateaWaterTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("加拉提亚水域追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Lotus Atoll 追踪器"
        }
    }

    var lotusAtollTrackerOverlay by boolean(false) {
        this.name = Translated("Lotus Atoll 追踪器")
        this.description = Translated("""
${GRAY}在 Lotus Atoll 钓鱼时显示青蛙王子和 Puddle Jumper 捕获统计，以及王子皇冠宝石掉落统计。
${GRAY}重置：${WHITE}/${LotusAtollTracker.RESET_COMMAND}
        """.trimIndent())
    }

    var resetLotusAtollTrackerOnGameClosed by boolean(false) {
        this.name = Translated("关闭游戏时自动重置")
        this.description = Translated("关闭 Minecraft 时自动重置 Lotus Atoll 追踪器。")
    }

    init {
        button {
            title = "设置王子皇冠宝石数量"
            description = "在你的聊天中解释如何为 Lotus Atoll 追踪器初始化王子皇冠宝石数量和最后掉落日期。"
            text = "点击获取帮助"
            onClick {
                ChatUtils.sendLocalChat("${AQUA}${BOLD}王子皇冠宝石设置${RESET}", true)
                ChatUtils.sendLocalChat("${YELLOW}命令：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME}${GOLD} <ITEM_ID> <COUNT> [LAST_ON_DATE]")
                ChatUtils.sendLocalChat("${DARK_AQUA}<ITEM_ID>: ${GRAY}必须是 PRINCE_CROWN_JEWEL")
                ChatUtils.sendLocalChat("${DARK_AQUA}<COUNT>: ${GRAY}你获得了多少个掉落")
                ChatUtils.sendLocalChat("${DARK_AQUA}[LAST_ON_DATE]: ${GRAY}可选，YYYY-MM-DD hh:mm:ss，不能是未来时间")
                ChatUtils.sendLocalChat("${GREEN}示例：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME} PRINCE_CROWN_JEWEL 2 2025-05-30 23:59:00")
            }
        }
    }

    var lotusAtollTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("Lotus Atoll 追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}宝藏钓鱼"
        }
    }

    var treasureFishingTrackerOverlay by boolean(false) {
        this.name = Translated("宝藏钓鱼追踪器")
        this.description = Translated("""
${GRAY}显示一个叠加层，展示良好/优秀/卓越宝藏捕获和宝藏染料掉落统计。此叠加层有[会话]和[总计]视图模式。
${GRAY}重置会话：${WHITE}/${TreasureFishingTracker.RESET_SESSION_COMMAND}
${GRAY}重置总计：${WHITE}/${TreasureFishingTracker.RESET_TOTAL_COMMAND}
        """.trimIndent())
    }

    var resetTreasureFishingTrackerSessionOnGameClosed by boolean(true) {
        this.name = Translated("关闭游戏时自动重置[会话]")
        this.description = Translated("关闭 Minecraft 时自动重置宝藏钓鱼追踪器[会话]。")
    }

    init {
        button {
            title = "设置宝藏染料数量"
            description = "在你的聊天中解释如何设置宝藏染料数量和最后掉落日期。"
            text = "点击获取帮助"
            onClick {
                ChatUtils.sendLocalChat("${AQUA}${BOLD}宝藏染料设置${RESET}", true)
                ChatUtils.sendLocalChat("${YELLOW}命令：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME}${GOLD} <ITEM_ID> <COUNT> [LAST_ON_DATE]")
                ChatUtils.sendLocalChat("${DARK_AQUA}<ITEM_ID>: ${GRAY}必须是 DYE_TREASURE")
                ChatUtils.sendLocalChat("${DARK_AQUA}<COUNT>: ${GRAY}你获得了多少个掉落")
                ChatUtils.sendLocalChat("${DARK_AQUA}[LAST_ON_DATE]: ${GRAY}可选，YYYY-MM-DD hh:mm:ss，不能是未来时间")
                ChatUtils.sendLocalChat("${GREEN}示例：${WHITE}/${SetTrackerDropsCommand.COMMAND_NAME} DYE_TREASURE 2 2025-05-30 23:59:00")
            }
        }
    }

    var treasureFishingTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("宝藏钓鱼追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}岩浆核钓鱼"
        }
    }

    var magmaCoreFishingTrackerOverlay by boolean(false) {
        this.name = Translated("岩浆核钓鱼追踪器")
        this.description = Translated("""
${GRAY}显示一个岩浆核钓鱼的叠加层，包含在 Crystal Hollows 中的岩浆猪人/岩浆烈焰捕获统计和岩浆核掉落收益（总计和每小时）。此叠加层有[会话]和[总计]视图模式。
${GRAY}重置[会话]：${WHITE}/${MagmaCoreFishingTracker.RESET_COMMAND}
${GRAY}重置[总计]：${WHITE}/${MagmaCoreFishingTracker.RESET_TOTAL_COMMAND}
${GRAY}暂停：${WHITE}/${MagmaCoreFishingTracker.PAUSE_COMMAND}
        """.trimIndent())
    }

    var magmaCoreFishingTrackerPriceMode by ObservableEntry(
        enum(PricingModeWithNpc.SELL_OFFER) {
            this.name = Translated("价格模式")
            this.description = Translated("如何计算追踪器中岩浆核的价格。")
        }
    ) { prev, new ->
        if (prev != new) {
            MagmaCoreFishingTracker.refreshGui()
        }
    }

    var resetMagmaCoreFishingTrackerSessionOnGameClosed by boolean(true) {
        this.name = Translated("关闭游戏时自动重置[会话]")
        this.description = Translated("关闭 Minecraft 时自动重置岩浆核钓鱼追踪器[会话]。")
    }

    var magmaCoreFishingTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("岩浆核钓鱼追踪器"))
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Archfiend 骰子收益"
        }
    }

    var archfiendDiceProfitTrackerOverlay by boolean(false) {
        this.name = Translated("Archfiend 骰子收益追踪器")
        this.description = Translated("""
${GRAY}显示你的 Archfiend 骰子/高级 Archfiend 骰子收益的叠加层。此叠加层有[会话]和[总计]视图模式。
${GRAY}重置[会话]：${WHITE}/${ArchfiendDiceProfitTracker.RESET_COMMAND}
${GRAY}重置[总计]：${WHITE}/${ArchfiendDiceProfitTracker.RESET_TOTAL_COMMAND}
""".trimIndent())
    }

    var resetArchfiendDiceProfitTrackerSessionOnGameClosed by boolean(true) {
        this.name = Translated("关闭游戏时自动重置[会话]")
        this.description = Translated("关闭 Minecraft 时自动重置 Archfiend 骰子收益追踪器[会话]。")
    }

    var archfiendDiceProfitTrackerCustomStyle by boolean(true) {
        this.name = Translated("应用自定义样式")
        this.description = Translated(getCustomStyleDescription("Archfiend 骰子收益追踪器"))
    }
}
