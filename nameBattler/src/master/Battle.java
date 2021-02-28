package master;

import master.config.PartyType;
import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.DecideLowHpTarget;
import master.strategy.ITargetPicker;
import master.util.Console;

public class Battle {
	Console con = new Console();

	public void actionOneTurn(PartyManager partyManager) {
		for (int i = partyManager.getAllPlayersSize() - 1; i >= 0; i--) {
			this.currentPlayerAction(i, partyManager);
			//死人がいたら離脱させる
			partyManager.checkSomeoneIsDead();
			// 全滅していたらループから抜ける
			if (partyManager.whichPartyIsDead()) {
				break;
			}
		}
	}

	private void currentPlayerAction(int index, PartyManager partyManager) {
		Player currentPlayer = partyManager.getPlayer(index);
		Player enemy = findTargetPlayer(index, partyManager);
		currentPlayer.action(enemy, partyManager);
	}

	private Player findTargetPlayer(int index, PartyManager partyManager) {
		Player currentPlayer = partyManager.getPlayer(index);
		//TargetController targetController = new DecideRandomTarget();
		ITargetPicker targetPicker = new DecideLowHpTarget();
		Party enemyParty = Party.findEnemyParty(currentPlayer,
				partyManager.getParty(PartyType.HEROES), partyManager.getParty(PartyType.EVILS));
		Player targetPlayer = targetPicker.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}
}
