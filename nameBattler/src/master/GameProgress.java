package master;

import master.party.PartyManager;
import master.util.Console;

public class GameProgress {
	Console con = new Console();
	PartyManager partyManager = new PartyManager();
	
	public void init() {
		con.typewriter(" ネームバトラー   スタート！！   ", 40);
		partyManager.partySetup();
	}

	public void battle() {
		con.typewriter("\n=== バトル開始 ===");

		int turnNumber = 1;
		while (turnNumber <= 20) {
			con.typewriter("--------------------------------");
			con.typewriter(String.format("- ターン%d -", turnNumber));
			
			Battle battle = new Battle();
			battle.actionOneTurn(partyManager);
			
			//全滅しているかの判定
			if (partyManager.whichPartyIsDead()) {
				con.typewriter("パーティーが全滅した");
				break;
			}
			// ターン終了時のステータスを表示
			partyManager.printPartysStatus();
			// 次のターン
			turnNumber = turnNumber + 1;
		}

	}

	public void gameover() {
		Battle battle = new Battle();
		battle.gameOver(partyManager);
	}
}
