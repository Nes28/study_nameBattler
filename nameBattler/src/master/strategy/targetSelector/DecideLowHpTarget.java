package master.strategy.targetSelector;

import java.util.ArrayList;
import java.util.Collections;

import master.job.Player;
import master.party.Party;

public class DecideLowHpTarget implements ITargetSelector {

	/**
	 * 低HPを狙う
	 */
	@Override
	public Player decideTargetPlayer(Party party) {
		ArrayList<Player> players = party.getMembers();
		Collections.sort(players, (p1, p2) -> p1.getHP() - p2.getHP());
		return players.get(0);
	}
}
