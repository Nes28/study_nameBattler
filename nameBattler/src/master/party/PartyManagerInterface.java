package master.party;
import master.config.PartyType;
import master.job.Player;
public interface PartyManagerInterface {
	//getter
	Player getPlayer(int i);
	Party getParty(PartyType partyType);
	int getAllPlayersSize();
	//メソッド
	void partySetup();
	void printPartysStatus();
	void checkSomeoneIsDead();
	boolean whichPartyIsDead();
}
