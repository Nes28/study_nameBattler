package master.strategy;

import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.targetSelector.DecideRandomTarget;
import master.strategy.targetSelector.ITargetSelector;

/**
 * いのち大事に
 */
public class TakeCareHp extends ActionStrategy {

	public TakeCareHp(Party enemyParty) {
		super(enemyParty);
	}

	@Override
	public void decideAction(Player me, PartyManager currentPartyManager) {
		boolean doneHeal = false;
		//回復を使えるかの判断
		//Yes
		if (me.getMagicSet().containMagicAttribute("回復")) {
			doneHeal = me.healAction(currentPartyManager);
			if(doneHeal) return;
		}
		//No
		ActionStrategy actionStrategy = new DoVarious(enemyParty);
		actionStrategy.decideAction(me, currentPartyManager);
		//layer enemy = this.decideTargetPlayer();
		//me.normalAttack(enemy);
		
	}

	@Override
	public Player decideTargetPlayer() {
		ITargetSelector targetSelector = new DecideRandomTarget();
		Player targetPlayer = targetSelector.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}

}
