package master.strategy.targetSelector;

import java.util.Random;

import master.job.Player;
import master.party.Party;
public class DecideRandomTarget implements ITargetSelector {
	Random rnd = new Random();
	public DecideRandomTarget() {
		
	}
	/**
	 * ランダムにターゲットを決める
	 */
	@Override
	public Player decideTargetPlayer(Party party) {
		int num = party.getMemberSize();
		Player targetPlayer = party.getPlayer(rnd.nextInt(num));
		return targetPlayer;
	}
}
