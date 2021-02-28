package master.party;

import master.PlayerGenerator;
import master.config.PartyType;
import master.job.Player;
import master.util.Console;

public class PartyManager{
	Console con = new Console();
	private Party allPlayers = new Party(PartyType.UNKNOWN);
	private Party heroParty = new Party(PartyType.HEROES);
	private Party evilParty = new Party(PartyType.EVILS);
	
	public Player getPlayer(int i) {
		return allPlayers.getPlayer(i);
	}
	
	public Party getParty(PartyType partyType) {
		switch(partyType) {
		case HEROES:
			return heroParty;
			
		case EVILS:
			return evilParty;
		
		case UNKNOWN:	//デフォルトにフォールスルー
		default :
			return null;
		}
	}

	public int getAllPlayersSize() {
		return allPlayers.getMemberSize();
	}

	public void partySetup() {
		allocatePartys();
		collectPlayersOneParty();
		sortAgility();
	}
	
	private void allocatePartys() {
		final int playersNum = 6;
		for (int i = 1; i < (playersNum + 1); i++) {
			PlayerGenerator gameStartSetting = new PlayerGenerator();
			Player newPlayer = gameStartSetting.generatePlayer(i);
			//奇数番は英雄へ、偶数番は悪人パーティーへ加える
			if (i % 2 == 1) {
				heroParty.AppendPlayer(newPlayer);
			} else {
				evilParty.AppendPlayer(newPlayer);
			}
		}
	}

	private void collectPlayersOneParty() {
		for (int j = 0; j < 3; j++) {
			Player p = heroParty.getPlayer(j);
			allPlayers.AppendPlayer(p);
			p = evilParty.getPlayer(j);
			allPlayers.AppendPlayer(p);
		}
	}

	private void sortAgility() {
		allPlayers.sortAgi();
	}

	public void printPartysStatus() {
		con.out();
		heroParty.printPartyStatus();
		evilParty.printPartyStatus();
	}

	public void checkSomeoneIsDead() {
		allPlayers.checkSomeoneIsDead();
		heroParty.checkSomeoneIsDead();
		evilParty.checkSomeoneIsDead();
	}

	public boolean whichPartyIsDead() {
		if (heroParty.isAllDead() || evilParty.isAllDead()) {
			return true;
		}
		return false;
	}
}
