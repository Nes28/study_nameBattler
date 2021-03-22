package master.strategy;

import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.targetSelector.DecideLowHpTarget;
import master.strategy.targetSelector.ITargetSelector;

/**
 * 魔法攻撃優先
 */
public class MagicAttackPriority extends ActionStrategy {

	public MagicAttackPriority(Party enemyParty) {
		super(enemyParty);
	}

	@Override
	public void decideAction(Player me, PartyManager currentPartyManager) {
		Player enemy = this.decideTargetPlayer();
		//魔法を使えるかの判断
		//Yes
		if(me.getMagicSet().containMagicAttribute("ダメージ")) {
			me.magicAttack(enemy);
		} else {
			me.normalAttack(enemy);
		}
	}

	@Override
	public Player decideTargetPlayer() {
		ITargetSelector targetSelector = new DecideLowHpTarget();
		Player targetPlayer = targetSelector.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}

}
