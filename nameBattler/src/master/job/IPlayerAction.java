package master.job;

import master.party.PartyManager;

public interface IPlayerAction {
	boolean normalAttack(Player enemy);
	boolean magicAttack(Player enemy);
	boolean healAction(PartyManager currentPartyManager);
	boolean debuffAction(Player enemy);
}
