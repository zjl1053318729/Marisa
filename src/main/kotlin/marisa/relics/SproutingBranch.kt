package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.RegenPower
import com.megacrit.cardcrawl.relics.AbstractRelic

class SproutingBranch : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.SPECIAL,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun makeCopy(): AbstractRelic {
        return SproutingBranch()
    }

    override fun onEquip() {
        AbstractDungeon.rareRelicPool.remove("Dead Branch")
    }

    override fun atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                RegenPower(AbstractDungeon.player, 5),
                5
            )
        )
    }

    companion object {
        const val ID = "SproutingBranch"
        private const val IMG = "img/relics/sproutingBranch.png"
        private const val IMG_OTL = "img/relics/outline/sproutingBranch.png"
    }
}
