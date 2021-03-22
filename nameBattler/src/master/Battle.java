package master;

import master.config.PartyType;
import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.targetSelector.DecideLowHpTarget;
import master.strategy.targetSelector.ITargetSelector;
import master.util.Console;

public class Battle {
	Console con = new Console();

	public void actionOneTurn(PartyManager currentPartyManager) {
		for (int i = currentPartyManager.getAllPlayersSize() - 1; i >= 0; i--) {
			this.currentPlayerAction(i, currentPartyManager);
			//死人がいたら離脱させる
			currentPartyManager.checkSomeoneIsDead();
			// 全滅していたらループから抜ける
			if (currentPartyManager.whichPartyIsDead()) {
				break;
			}
		}
	}

	private void currentPlayerAction(int index, PartyManager currentPartyManager) {
		Player currentPlayer = currentPartyManager.getPlayer(index);
		Player enemy = findEnemyPlayer(index, currentPartyManager);
		Party enemyParty = this.findEnemyParty(index, currentPartyManager);
		currentPlayer.action(enemyParty, currentPartyManager);
	}

	private Party findEnemyParty(int index, PartyManager currentPartyManager) {
		Player currentPlayer = currentPartyManager.getPlayer(index);
		//TargetController targetController = new DecideRandomTarget();
		//ITargetPicker targetPicker = new DecideLowHpTarget();
		Party enemyParty = Party.findEnemyParty(currentPlayer,
				currentPartyManager.getParty(PartyType.HEROES), currentPartyManager.getParty(PartyType.EVILS));
		return enemyParty;
		//Player targetPlayer = targetPicker.decideTargetPlayer(enemyParty);
		//return targetPlayer;
	}
	
	private Player findEnemyPlayer(int index, PartyManager currentPartyManager) {
		Player currentPlayer = currentPartyManager.getPlayer(index);
		//TargetController targetController = new DecideRandomTarget();
		ITargetSelector targetPicker = new DecideLowHpTarget();
		Party enemyParty = Party.findEnemyParty(currentPlayer,
				currentPartyManager.getParty(PartyType.HEROES), currentPartyManager.getParty(PartyType.EVILS));
		Player targetPlayer = targetPicker.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}
}
