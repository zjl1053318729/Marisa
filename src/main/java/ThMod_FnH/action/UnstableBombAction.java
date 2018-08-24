package ThMod_FnH.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class UnstableBombAction extends AbstractGameAction{
	private DamageInfo info;
	private static final float DURATION = 0.01F;
	private int numTimes;
	private int min;
	private int max;
	private int dmg;
  
	public UnstableBombAction(AbstractCreature target,int min,int max, int numTimes){
		this.min = min;
		this.max = max;
		this.dmg = AbstractDungeon.miscRng.random(min, max);
		this.info = new DamageInfo(AbstractDungeon.player, dmg) ;
		this.target = target;
		this.actionType = AbstractGameAction.ActionType.DAMAGE;
		this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
		this.duration = DURATION;
		this.numTimes = numTimes;
	}
  
	public void update() {
		if (this.target == null) {
			this.isDone = true;
			return;
		}
		if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			AbstractDungeon.actionManager.clearPostCombatActions();
			this.isDone = true;
			return;
		}
		if (this.target.currentHealth > 0) {
			/*
			this.target.damageFlash = true;
			this.target.damageFlashFrames = 4;
			AbstractDungeon.effectList.add(
					new FlashAtkImgEffect(
							this.target.hb.cX, this.target.hb.cY, this.attackEffect
							)
					);
					*/
			//this.info.applyPowers(this.info.owner, this.target);
			AbstractDungeon.actionManager.addToBottom(
					new DamageAction(
							this.target,
							this.info,
							this.attackEffect
							)
					);
			//this.target.damage(this.info);
			if ((this.numTimes > 1) && (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
				this.numTimes --;
				AbstractDungeon.actionManager.addToTop(
						new UnstableBombAction(
								AbstractDungeon.getMonsters().getRandomMonster(true),
								min,
								max, 
								this.numTimes
								)
						);
			}
			AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
		}
		this.isDone = true;
	}
}