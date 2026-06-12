package com.github.sleepypanda.feesh.settings.categories

import com.github.sleepypanda.feesh.utils.enums.ColorCodes.*
import com.github.sleepypanda.feesh.utils.enums.FormattingCodes.*
import com.github.sleepypanda.feesh.settings.models.AlertableSeaCreatureTypes
import com.github.sleepypanda.feesh.constants.RareDropTypes
import com.github.sleepypanda.feesh.utils.enums.DeployableTypes
import com.github.sleepypanda.feesh.utils.enums.PricingModeWithNpc
import com.teamresourceful.resourcefulconfigkt.api.CategoryKt
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue

enum class AlertSource(val displayName: String) {
    OWN_AND_PARTY("自己和队伍"),
    OWN("自己");

    override fun toString(): String = displayName
}

enum class RareDropPriceScope(val displayName: String) {
    OWN("自己"),
    OWN_AND_PARTY("自己和队伍"),
    OFF("关闭");

    override fun toString(): String = displayName
}

object Alerts : CategoryKt("提醒") {
    override val description: TranslatableValue
        get() = Literal(
            "在屏幕上、本地聊天或播放声音中显示的个人提醒。"
        )

    init {
        separator {
            this.title = "${AQUA}${BOLD}海洋生物"
        }
    }

    var alertOnRareSeaCreatures by boolean(true) {
        this.name = Translated("稀有海洋生物提醒")
        this.description = Translated("当你或队伍成员捕获特定海洋生物时，显示标题并播放声音。每种生物的声音可从列表中自定义。请启用 ${YELLOW}Skyblock 设置 -> 个人 -> 钓鱼设置 -> 海洋生物聊天")
    }

    var alertOnSeaCreaturesList by select(
        *AlertableSeaCreatureTypes.values().filter { it.isEnabledByDefault }.toTypedArray(),
    ) {
        this.name = Translated("选择需要提醒的海洋生物")
        this.searchTerms = AlertableSeaCreatureTypes.values().map { it.displayName }.toList()
    }

    var alertOnSeaCreaturesIncludeCocooned by boolean(true) {
        this.name = Translated("茧化海洋生物提醒")
        this.description = Translated("当选定海洋生物被你或队伍成员茧化时也发出提醒。")
    }

    var alertOnRareSeaCreaturesSource by enum(AlertSource.OWN_AND_PARTY) {
        this.name = Translated("提醒来源")
        this.description = Translated("\"自己和队伍\" = 你和队伍成员的捕获；\"自己\" = 仅你的捕获。")
    }

    var alertOnAnyReindrake by boolean(false) {
        this.name = Translated("大厅中任何雷因德拉肯提醒")
        this.description = Translated("当大厅中有雷因德拉肯生成时显示标题并播放声音，即使不是由你或队伍成员捕获。")
    }

    var alertOnPlayerDeath by boolean(true) {
        this.name = Translated("你或队伍成员被钓鱼Boss击杀提醒")
        this.description = Translated("当你或队伍成员被雷霆/贾巴斯领主/拉格纳罗克/威基蒂基/泰坦巨蚺/尼斯湖水怪击杀时，显示标题并播放声音。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}稀有掉落"
        }
    }

    var alertOnRareDrops by boolean(true) {
        this.name = Translated("稀有掉落提醒")
        this.description = Translated("当你或队伍成员掉落稀有物品时，显示标题并播放声音。每种物品的声音可从列表中自定义。")
    }

    var alertOnRareDropTypes by select(RareDropTypes.LUCKY_CLOVER_CORE, *RareDropTypes.values()) {
        this.name = Translated("选择需要提醒的稀有掉落")
        this.searchTerms = RareDropTypes.values().map { it.displayName }.toList()
    }

    var alertOnRareDropsSource by enum(AlertSource.OWN_AND_PARTY) {
        this.name = Translated("提醒来源")
        this.description = Translated("\"自己和队伍\" = 你和队伍成员的掉落；\"自己\" = 仅你的掉落。")
    }

    var rareDropAlertShowPriceFor by enum(RareDropPriceScope.OWN_AND_PARTY) {
        this.name = Translated("在标题中显示掉落物品价格")
        this.description = Translated("在提醒中显示掉落物品的价格。\"自己\" = 仅你的掉落；\"自己和队伍\" = 你和队伍成员的掉落；\"关闭\" = 不显示价格。")
    }

    var alertOnRareDropsPriceMode by enum(PricingModeWithNpc.SELL_OFFER) {
        this.name = Translated("稀有掉落价格模式")
        this.description = Translated("定义如何计算掉落物品的价格。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}尼斯湖水怪"
        }
    }

    var alertOnNessieDestination by boolean(true) {
        this.name = Translated("尼斯湖水怪选择目的地时提醒")
        this.description = Translated("当尼斯湖水怪游向 Driptoad Delve 或 Jade Dragon 洞穴时，显示标题并发送本地聊天消息。仅在你最近钓过鱼时追踪尼斯湖水怪！")
    }

    var autoShareNessieDestination by boolean(false) {
        this.name = Translated("自动分享到队伍聊天")
        this.description = Translated("自动将尼斯湖水怪选择的目的地分享到队伍聊天。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Puddle Jumper"
        }
    }

    var alertOnPuddleJumperTimer by boolean(false) {
        this.name = Translated("Puddle Jumper 计时器提醒")
        this.description = Translated("当你的 Puddle Jumper 即将到达目的地时，显示标题并发送本地聊天消息。\n可用于在 Puddle Jumper 完成跳跃并给予战利品前更换装备。")
    }

    var puddleJumperTimerSeconds by int(40) {
        this.name = Translated("Puddle Jumper 计时器（秒）")
        this.description = Translated("捕获 Puddle Jumper 后经过多少秒显示提醒。如果 Puddle Jumper 计时器提醒已禁用则忽略。\n通常 Puddle Jumper 随机需要 43-55 秒完成跳跃并给予战利品，因此调整计时器以在其到达目的地前收到提醒。")
        this.range = 30..50
        this.slider = true
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}奖杯"
        }
    }

    var alertOnTrophyFrogDiscovered by boolean(true) {
        this.name = Translated("发现新奖杯蛙提醒")
        this.description = Translated("当你在 Lotus Atoll 发现新的奖杯蛙时，显示标题并播放声音。")
    }

    var alertOnTrophyFishDiscovered by boolean(true) {
        this.name = Translated("发现新奖杯鱼提醒")
        this.description = Translated("当你在 Crimson Isle 发现新的奖杯鱼时，显示标题并播放声音。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}灵息面具"
        }
    }

    var alertOnSpiritMaskUsed by boolean(true) {
        this.name = Translated("灵息面具被使用提醒")
        this.description = Translated("当你的灵息面具的第二风能力被激活时，显示标题并播放声音。")
    }

    var alertOnSpiritMaskBack by boolean(false) {
        this.name = Translated("灵息面具恢复提醒")
        this.description = Translated("当你的灵息面具的第二风能力被激活后恢复时，显示标题并播放声音。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}Barn 钓鱼计时器"
        }
    }

    var alertOnSeaCreaturesPersonalCap by boolean(true) {
        this.name = Translated("个人海洋生物数量上限提醒")
        this.description = Translated("当达到个人海洋生物数量上限，Skyblock 提示\"没有足够的空间生成另一个海洋生物！\"时，显示标题并播放声音。")
    }

    var alertOnSeaCreaturesTimerThreshold by boolean(true) {
        this.name = Translated("自己/他人的海洋生物存活5分钟以上时提醒")
        this.description = Translated("当附近的海洋生物已存活5分钟以上即将消失时，显示标题并播放声音。不检查这些是本人还是他人的海洋生物。如果你的快捷栏中没有钓鱼竿则禁用！")
    }

    var alertOnSeaCreaturesCountThreshold by boolean(true) {
        this.name = Translated("自己/他人的海洋生物数量达到阈值时提醒")
        this.description = Translated("当附近海洋生物数量达到指定阈值时，显示标题并播放声音。不检查这些是本人还是他人的海洋生物。如果你的快捷栏中没有钓鱼竿则禁用！")
    }

    var seaCreaturesCountThreshold_Hub by int(50) {
        this.name = Translated("海洋生物数量阈值 - 枢纽")
        this.description = Translated("在枢纽中触发提醒所需的附近海洋生物数量。不检查这些是本人还是他人的海洋生物。如果海洋生物数量提醒已禁用则忽略。")
        this.range = 5..60
        this.slider = true
    }

    var seaCreaturesCountThreshold_CrimsonIsle by int(20) {
        this.name = Translated("海洋生物数量阈值 - 深红之岛")
        this.description = Translated("在深红之岛中触发提醒所需的附近海洋生物数量。不检查这些是本人还是他人的海洋生物。如果海洋生物数量提醒已禁用则忽略。")
        this.range = 5..60
        this.slider = true
    }

    var seaCreaturesCountThreshold_CrystalHollows by int(20) {
        this.name = Translated("海洋生物数量阈值 - 水晶洞窟")
        this.description = Translated("在水晶洞窟中触发提醒所需的附近海洋生物数量。不检查这些是本人还是他人的海洋生物。如果海洋生物数量提醒已禁用则忽略。")
        this.range = 5..60
        this.slider = true
    }

    var seaCreaturesCountThreshold_Galatea by int(30) {
        this.name = Translated("海洋生物数量阈值 - 加拉提亚")
        this.description = Translated("在加拉提亚中触发提醒所需的附近海洋生物数量。不检查这些是本人还是他人的海洋生物。如果海洋生物数量提醒已禁用则忽略。")
        this.range = 5..60
        this.slider = true
    }

    var seaCreaturesCountThreshold_Default by int(50) {
        this.name = Translated("海洋生物数量阈值 - 其他")
        this.description = Translated("在其他地点触发提醒所需的附近海洋生物数量。不检查这些是本人还是他人的海洋生物。如果海洋生物数量提醒已禁用则忽略。")
        this.range = 5..60
        this.slider = true
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}可部署物品"
        }
    }

    var alertOnDeployableExpiresSoon by boolean(true) {
        this.name = Translated("可部署物品即将过期提醒")
        this.description = Translated("当你的可部署物品在10秒后过期时，显示标题并播放声音。")
    }

    var alertOnDeployableTypes by select(DeployableTypes.TOTEM_OF_CORRUPTION, *DeployableTypes.values()) {
        this.name = Translated("选择需要提醒的可部署物品")
        this.searchTerms = DeployableTypes.values().map { it.displayName }.toList()
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}消耗品"
        }
    }

    var alertOnConsumableExpiresSoon by boolean(true) {
        this.name = Translated("Moby-Duck 即将过期提醒")
        this.description = Translated("当 Moby-Duck 在10秒后过期时，显示标题并播放声音。")
    }

    var alertOnSaltExpired by boolean(true) {
        this.name = Translated("盐已过期提醒")
        this.description = Translated("当盐已过期时，显示标题并播放声音。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}宠物"
        }
    }

    var alertOnPetLevelUp by boolean(true) {
        this.name = Translated("宠物达到最大等级提醒")
        this.description = Translated("当宠物达到最大等级时，显示标题并播放声音。")
    }

    var showPetLevelUpPrice by boolean(true) {
        this.name = Translated("显示宠物升级预估价格")
        this.description = Translated("在聊天中显示宠物升级的预估价格。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}热点"
        }
    }

    var alertOnHotspotGone by boolean(true) {
        this.name = Translated("热点消失提醒")
        this.description = Translated("当你最近钓鱼的热点消失时，显示标题并播放声音。")
    }

    var alertOnWormholeGone by boolean(true) {
        this.name = Translated("虫洞消失提醒")
        this.description = Translated("当你最近钓鱼的虫洞消失时，显示标题并播放声音。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}雨、雷、暴风雪"
        }
    }

    var alertOnRainEndingSoon by boolean(false) {
        this.name = Translated("雨/雷/暴风雪即将结束时提醒")
        this.description = Translated("${GRAY}当活跃的雨/雷/暴风雪即将结束时，显示标题并播放声音。适用于公园、蜘蛛巢穴、Lotus Atoll、Backwater Bayou 和 Jerry's Workshop。请启用 ${YELLOW}TabList 设置 -> 通用信息小部件 -> 显示雨/显示暴风雪")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}钓鱼节"
        }
    }

    var alertOnFishingFestivalEnded by boolean(true) {
        this.name = Translated("钓鱼节结束提醒")
        this.description = Translated("当钓鱼节结束时显示标题，并在聊天中发送鲨鱼计数。需要节日期间叠加层或追踪处于活动状态。")
    }

    var trackPersonalBestFishingFestival by boolean(true) {
        this.name = Translated("追踪个人最佳纪录")
        this.description = Translated("追踪钓鱼节期间捕获的鲨鱼总数和大白鲨数量的个人最佳纪录。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}鱼饵"
        }
    }

    var alertOnFishingBagDisabled by boolean(true) {
        this.name = Translated("钓鱼袋禁用时提醒")
        this.description = Translated("当当前玩家在钓鱼袋禁用状态下开始钓鱼时，显示标题并播放声音。\n${YELLOW}启用该设置后，请先打开你的钓鱼袋以初始化其状态！")
    }

    var alertOnBaitChanged by boolean(true) {
        this.name = Translated("鱼饵变更时提醒")
        this.description = Translated("钓鱼时鱼饵发生变更，显示标题并播放声音。")
    }

    var alertOnBaitRunningOut by boolean(true) {
        this.name = Translated("鱼饵即将耗尽时提醒")
        this.description = Translated("钓鱼时鱼饵即将耗尽，显示标题并播放声音。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}其他"
        }
    }

    var alertOnNonFishingArmor by boolean(true) {
        this.name = Translated("未装备钓鱼盔甲时提醒")
        this.description = Translated("当当前玩家未穿着钓鱼盔甲进行钓鱼时，显示标题并播放声音。")
    }

    var alertOnLootshareMessage by boolean(true) {
        this.name = Translated("队伍聊天中出现\"Lootshare!\"消息时提醒")
        this.description = Translated("当\"Lootshare!\"消息出现在队伍聊天中时，显示标题并播放声音。")
    }

    var alertOnChumBucketAutoPickup by boolean(true) {
        this.name = Translated("Chum/Chumcap 桶自动拾取时提醒")
        this.description = Translated("当你的 Chum/Chumcap 桶因距离太远而被自动拾取时，显示标题并播放声音。")
    }

    var alertOnGoldenFishSpawn by boolean(true) {
        this.name = Translated("金鱼生成时提醒")
        this.description = Translated("当金鱼生成时，显示标题并播放声音。")
    }

    var alertOnThunderBottleCharged by boolean(true) {
        this.name = Translated("雷/风暴/飓风瓶充满时提醒")
        this.description = Translated("当你的雷瓶、风暴瓶或飓风瓶完全充能时，显示标题并播放声音。")
    }

    var alertOnWormTheFishCaught by boolean(false) {
        this.name = Translated("钓到蠕虫鱼时提醒")
        this.description = Translated("当在世界中检测到蠕虫鱼时（Dirt Rod 钓鱼），显示标题并播放声音。")
    }
}
