package com.github.sleepypanda.feesh.settings.categories

import com.github.sleepypanda.feesh.utils.enums.ColorCodes.*
import com.github.sleepypanda.feesh.utils.enums.FormattingCodes.*
import com.github.sleepypanda.feesh.features.rendering.HidePlayersNearBobber
import com.github.sleepypanda.feesh.features.rendering.RareMobHighlight
import com.github.sleepypanda.feesh.settings.models.HighlightableSeaCreatureTypes
import com.teamresourceful.resourcefulconfig.api.types.options.TranslatableValue
import com.teamresourceful.resourcefulconfigkt.api.ObservableEntry
import com.teamresourceful.resourcefulconfigkt.api.CategoryKt

object WorldRendering : CategoryKt("世界渲染") {
    override val description: TranslatableValue
        get() = Literal(
            "修改世界和实体的功能。"
        )

    init {
        separator {
            this.title = "${AQUA}${BOLD}鱼钩"
        }
    }

    var hideOtherPlayersFishingHooks by boolean(false) {
        this.name = Translated("隐藏其他玩家的鱼钩")
        this.description = Translated("隐藏属于其他玩家的鱼钩。")
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}高亮"
        }
    }

    var highlightSeaCreatures by ObservableEntry(boolean(false) {
        this.name = Translated("高亮海洋生物")
        this.description = Translated("为选定的海洋生物应用发光轮廓。轮廓颜色取决于海洋生物的稀有度。${RED}不能透过墙壁看到，但请自行承担风险！")
    }
    ) { prev, new ->
        if (prev != new) {
            RareMobHighlight.clearHighlightedEntities()
        }
    }

    var highlightSeaCreaturesList by ObservableEntry(select(
            *HighlightableSeaCreatureTypes.values().filter { it.isEnabledByDefault }.toTypedArray(),
        ) {
            this.name = Translated("选择海洋生物")
            this.description = Translated("哪些海洋生物应应用发光轮廓。")
            this.searchTerms = HighlightableSeaCreatureTypes.values().map { it.displayName }.toList()
        }
    ) { prev, new ->
        if (!prev.contentEquals(new)) {
            RareMobHighlight.updateEnabledMobTypes()
        }
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}玩家"
        }
    }

    var hidePlayersNearBobber by boolean(false) {
        this.name = Translated("隐藏浮标附近的玩家")
        this.description = Translated("当你的钓鱼竿抛出后，隐藏距离你鱼钩在设定范围内的其他玩家。")
    }

    var hidePlayersNearBobberDistance by int(5) {
        this.name = Translated("距浮标距离")
        this.description = Translated("距离你的鱼钩的最大距离（方块），在此范围内的其他玩家将被隐藏。")
        this.range = 1..10
        this.slider = true
    }

    var hidePlayersNearBobberUnhideDelay by ObservableEntry(int(0) {
        this.name = Translated("取消隐藏延迟")
        this.description = Translated("你的浮标消失后，保持其他玩家隐藏的延迟秒数。")
        this.range = 0..5
        this.slider = true
    }) { prev, new ->
        if (prev != new) {
            HidePlayersNearBobber.onUnhideDelayChanged()
        }
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}世界声音"
        }
    }

    var muteJadeDragon by boolean(false) {
        this.name = Translated("静音翡翠龙")
        this.description = Translated("当你在翡翠龙洞穴中时，静音翡翠龙的声音。")
    }

    var muteReindrakeGifts by boolean(false) {
        this.name = Translated("静音雷因德拉肯礼物")
        this.description = Translated("静音从雷因德拉肯拾取礼物时响亮的\"图腾使用\"声音。")
    }
}
