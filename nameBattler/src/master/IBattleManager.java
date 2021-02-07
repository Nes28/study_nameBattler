package master;

import master.party.IPartyManager;

public interface IBattleManager {
	void actionOneTurn(IPartyManager partyManager);
	void gameOver(IPartyManager partyManager);
}
