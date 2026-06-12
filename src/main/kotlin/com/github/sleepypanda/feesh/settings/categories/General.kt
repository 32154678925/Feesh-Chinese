package com.github.sleepypanda.feesh.settings.categories

import com.github.sleepypanda.feesh.utils.enums.ColorCodes.*
import com.github.sleepypanda.feesh.utils.enums.FormattingCodes.*
import com.github.sleepypanda.feesh.utils.PriceUtils
import com.github.sleepypanda.feesh.utils.data.PersistentDataManager
import com.teamresourceful.resourcefulconfig.api.annotations.Category
import com.teamresourceful.resourcefulconfig.api.annotations.Comment
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType
import com.teamresourceful.resourcefulconfigkt.api.CategoryKt
import com.teamresourceful.resourcefulconfigkt.api.ObservableEntry
import net.minecraft.Util

enum class AuctionPriceApiMode(val displayName: String) {
    ELITE_SKYBLOCK_NEU("精英（最低BIN）"),
    ELITE_SKYBLOCK_7DAY_AVG("精英（7天平均值）");

    override fun toString(): String = displayName
}

enum class SoundMode(val displayName: String) {
    MEME("搞怪"),
    NORMAL("普通"),
    OFF("关闭");

    override fun toString(): String = displayName
}

object General : CategoryKt("常规") {
    init {
        separator {
            this.title = "${AQUA}${BOLD}声音"
        }
    }

    var soundMode by enum(SoundMode.NORMAL) {
        this.name = Translated("声音模式")
        this.description = Translated("设置模组的声音模式。搞怪模式播放搞怪声音（可自定义），普通模式播放默认MC声音，关闭模式禁用所有声音。")
    }

    init {
        button {
            title = "自定义声音指南"
            description = "打开设置搞怪声音模式下自定义声音的指南。"
            text = "打开"
            onClick {
                Util.getPlatform().openUri("https://github.com/Sleepy-Panda/Feesh/blob/develop/docs/Custom%20sounds%20guide.md")
            }
        }
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}价格 API"
        }
    }

    var auctionPriceApi by ObservableEntry(
        enum(AuctionPriceApiMode.ELITE_SKYBLOCK_NEU) {
            this.name = Translated("拍卖价格 API")
            this.description = Translated("用于拍卖物品 LBIN 价格的源 API。你可以选择最新价格或受控价格操作。")
        }
    ) { prev, new ->
        if (prev != new) {
            PriceUtils.refreshAuctionPrices()
        }
    }

    init {
        separator {
            this.title = "${AQUA}${BOLD}其他"
        }

        button {
            title = "打开备份文件夹"
            description = "打开游戏关闭时存储数据备份的文件夹。"
            text = "打开"
            onClick {
                val dir = PersistentDataManager.backupDir
                if (!dir.exists()) dir.mkdirs()
                Util.getPlatform().openUri(dir.toURI().toString())
            }
        }
    }
}
