package master.strategy.targetSelector;
import master.job.Player;
import master.party.Party;
public interface ITargetSelector {
	//作戦（標的の選び方)用のインターフェース
	public Player decideTargetPlayer(Party party);
}
