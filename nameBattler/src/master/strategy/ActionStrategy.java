package master.strategy;
import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.util.Console;
public abstract class ActionStrategy {
	protected Party enemyParty;
	Console con = new Console();
	public ActionStrategy(Party enemyParty) {
		this.enemyParty = enemyParty;
	}
	
	protected Party getEnemyParty() {
		return this.enemyParty;
	}

	abstract public void decideAction(Player me, PartyManager currentPartyManager);
	abstract public Player decideTargetPlayer();
}
