package master.strategy;

import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.targetSelector.DecideLowHpTarget;
import master.strategy.targetSelector.ITargetSelector;

public class LowHpEnemyPriority extends ActionStrategy {

	public LowHpEnemyPriority(Party enemyParty) {
		super(enemyParty);
	}

	@Override
	public void decideAction(Player me, PartyManager currentPartyManager) {
		Player enemy = this.decideTargetPlayer();
		DoVarious doVarious = new DoVarious(enemyParty);
		doVarious.decideAction(enemy, me, currentPartyManager);
	}

	@Override
	public Player decideTargetPlayer() {
		ITargetSelector targetSelector = new DecideLowHpTarget();
		Player targetPlayer = targetSelector.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}

}
