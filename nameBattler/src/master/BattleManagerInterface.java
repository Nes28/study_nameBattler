package master;

import master.party.PartyManagerInterface;

public interface BattleManagerInterface {
	void actionOneTurn(PartyManagerInterface partyManager);
	void gameOver(PartyManagerInterface partyManager);
}
