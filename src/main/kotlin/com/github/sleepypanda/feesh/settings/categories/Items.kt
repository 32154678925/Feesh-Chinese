package com.github.sleepypanda.feesh.settings.categories

import com.github.sleepypanda.feesh.features.items.background.BackgroundHighlighterManager
import com.github.sleepypanda.feesh.features.items.background.TrashBooksHighlighter
import com.github.sleepypanda.feesh.features.items.slottext.SlotTextRendererManager
import com.github.sleepypanda.feesh.features.items.tooltip.TooltipManager
import com.github.sleepypanda.feesh.utils.enums.ColorCodes.*
import com.github.sleepypanda.feesh.utils.enums.FormattingCodes.*
import com.teamresourceful.resourcefulconfigkt.api.CategoryKt
import com.teamresourceful.resourcefulconfigkt.api.ObservableEntry

object Items : CategoryKt("物品") {

    init {
        separator {
            this.title = "${AQUA}${BOLD}背景"
        }
    }

    var trashBooksHighlighter by ObservableEntry(boolean(false) {
        this.name = Translated("垃圾附魔书")
        this.description = Translated(
            "高亮显示钓鱼时涌入你背包的垃圾附魔书所在的格子。你可以用它快速找到要丢弃或即时出售的书。"
        )
    }) { prev, new ->
        if (prev != new) {
            BackgroundHighlighterManager.refreshEnabledHighlighters()
        }
    }

    var trashBooksHighlighterNames by ObservableEntry(
        strings("Corruption I,Corruption 1") {
            this.name = Translated("要搜索的垃圾附魔书")
            this.description = Translated("逗号分隔的要搜索的书名。应为精确的书名。例如：Corruption I,Frail VI,No Pain No Gain I。")
        }
    ) { prev, new ->
        if (prev != new) {
            TrashBooksHighlighter.setSearchBookNames()
        }
    }
   
    var katWrongPetsHighlighter by ObservableEntry(boolean(false) {
        this.name = Translated("提供给 Kat 的错误宠物")
        this.description = Translated(
            "${GRAY}当你在 Kat 的 GUI 中可能错误地提供某些宠物（史诗级巨齿鲨）时高亮显示该格子。${DARK_GRAY}给那些经常被 Kat 骗了的人，把巨齿鲨给了她而不是 George（说的就是我）。"
        )
    }) { prev, new ->
        if (prev != new) {
            BackgroundHighlighterManager.refreshEnabledHighlighters()
        }
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}格子文本"
        }
    }

    var showThunderBottleProgress by ObservableEntry(boolean(false) {
        this.name = Translated("雷瓶充能进度")
        this.description = Translated("在物品格子中显示雷/风暴/飓风瓶的充能进度百分比。")
    }) { prev, new ->
        if (prev != new) {
            SlotTextRendererManager.refreshEnabledRenderers()
        }
    }

    var showMobyDuckProgress by ObservableEntry(boolean(false) {
        this.name = Translated("Moby-Duck 进化进度")
        this.description = Translated("在物品格子中显示 Moby-Duck 的进化进度百分比。")
    }) { prev, new ->
        if (prev != new) {
            SlotTextRendererManager.refreshEnabledRenderers()
        }
    }

    var showAutoRecombFlag by ObservableEntry(boolean(false) {
        this.name = Translated("自动重铸标记")
        this.description = Translated("在物品格子中为自动重铸的钓鱼掉落物显示重铸升级标记（R）。")
    }) { prev, new ->
        if (prev != new) {
            SlotTextRendererManager.refreshEnabledRenderers()
        }
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}工具提示"
        }
    }

    var showExpertiseKillsTooltip by ObservableEntry(boolean(false) {
        this.name = Translated("专精击杀计数器")
        this.description = Translated("在钓鱼竿上显示精确的专精击杀计数器。")
    }) { prev, new ->
        if (prev != new) {
            TooltipManager.refreshEnabledAdders()
        }
    }
}
