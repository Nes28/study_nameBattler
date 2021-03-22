package master.strategy;

import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.targetSelector.DecideLowHpTarget;
import master.strategy.targetSelector.ITargetSelector;
/**
 * 作戦：MP節約
 */

public class EconomyMp extends ActionStrategy {
	public EconomyMp(Party enemyParty) {
		super(enemyParty);
	}

	@Override
	public void decideAction(Player me, PartyManager currentPartyManager) {
		Player enemy = this.decideTargetPlayer();
		me.normalAttack(enemy);
		//PlayerでnoramlAttackを定義しないとここで引っかかる
	}

	@Override
	public Player decideTargetPlayer() {
		ITargetSelector targetSelector = new DecideLowHpTarget();
		Player targetPlayer = targetSelector.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}
}
