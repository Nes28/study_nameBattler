package master.strategy;
import master.job.Player;
import master.party.Party;
public interface ITargetPicker {
	//作戦（標的の選び方)用のインターフェース
	public Player decideTargetPlayer(Party party);
}
