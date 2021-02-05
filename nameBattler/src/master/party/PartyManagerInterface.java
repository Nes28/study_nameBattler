package master.party;

public interface PartyManagerInterface {
	int getAllPlayersSize();
	void partySetup();
	void collectPlayersOneParty();
	void sortAgility();
	void currentPlayerFindEnemy(int i);
	void currentPlayerAction();
	void printPartysStatus();
	void checkSomeoneIsDead();
	boolean whichPartyIsDead();
	void gameOver();
}
