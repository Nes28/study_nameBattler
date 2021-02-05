package master;
import master.config.PartyType;
import master.job.Player;
import master.party.Party;
import master.party.PartyManagerInterface;
import master.strategy.DecideLowHpTarget;
import master.strategy.TargetController;
import master.util.Console;
public class BattleManager implements BattleManagerInterface {
	Console con = new Console();
	
	@Override
	public void actionOneTurn(PartyManagerInterface partyManager) {
		for (int i = partyManager.getAllPlayersSize() - 1; i >= 0; i--) {
			currentPlayerAction(i, partyManager);
			//死人がいたら離脱させる
			partyManager.checkSomeoneIsDead();
			// 全滅していたらループから抜ける
			if (partyManager.whichPartyIsDead()) {
				break;
			}
		}
	}
	
	private Player findTargetPlayer(int i, PartyManagerInterface partyManager) {
		Player currentPlayer = partyManager.getPlayer(i);
		//TargetController targetController = new DecideRandomTarget();
		TargetController targetController = new DecideLowHpTarget();
		Party enemyParty = Party.findEnemyParty(currentPlayer, 
				partyManager.getParty(PartyType.HEROES), partyManager.getParty(PartyType.EVILS));
		Player targetPlayer = targetController.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}

	//@Override
	private void currentPlayerAction(int i,PartyManagerInterface partyManager) {
		Player currentPlayer = partyManager.getPlayer(i);
		currentPlayer.action(findTargetPlayer(i, partyManager));
	}

	@Override
	public void gameOver(PartyManagerInterface partyManager) {
		con.out();
		Party heroParty = partyManager.getParty(PartyType.HEROES);
		Party evilParty = partyManager.getParty(PartyType.EVILS);
		if (heroParty.getMemberSize() > evilParty.getMemberSize()) {
			con.typewriter(heroParty.getPartyName() + "の勝利！！");
		}
		if (heroParty.getMemberSize() < evilParty.getMemberSize()) {
			con.typewriter(evilParty.getPartyName() + "の勝利！！");
		}
		if (heroParty.getMemberSize() == evilParty.getMemberSize()) {
			con.typewriter("引き分け");
		}
	}

}
