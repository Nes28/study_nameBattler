package master;

import master.config.PartyType;
import master.job.Player;
import master.party.Party;
import master.strategy.DecideLowHpTarget;
import master.strategy.TargetController;
import master.util.Console;

public class GameManager {
	Console con = new Console();
	Party allPlayers = new Party(PartyType.ALLPLAYERS);
	Party heroParty = new Party(PartyType.HEROES);
	Party evilParty = new Party(PartyType.EVILS);

	public void init() {
		con.typewriter(" ネームバトラー   スタート！！   ", 40);
		final int playersNum = 6;
		for (int i = 1; i < (playersNum + 1); i++) {
			GameStartSetting gameStartSetting = new GameStartSetting();
			Player newPlayer = gameStartSetting.generatePlayer(i);
			//奇数番は英雄へ、偶数番は悪人パーティーへ加える
			if (i % 2 == 1) {
				heroParty.AppendPlayer(newPlayer);
			} else {
				evilParty.AppendPlayer(newPlayer);
			}
		}
		//allPlayersに全プレイヤーを集める
		for (int j = 0; j < 3; j++) {
			Player p = heroParty.getPlayer(j);
			allPlayers.AppendPlayer(p);
			p = evilParty.getPlayer(j);
			allPlayers.AppendPlayer(p);
		}
		allPlayers.sortAgi();
	}

	public void battle() {
		con.out();
		con.typewriter("=== バトル開始 ===");

		int turnNumber = 1;
		// 最大でも20ターンまで
		while (turnNumber <= 20) {
			con.typewriter("--------------------------------");
			con.typewriter(String.format("- ターン%d -", turnNumber));
			for (int i = allPlayers.getMemberSize() - 1; i >= 0; i--) {
				Player currentPlayer = allPlayers.getPlayer(i);
				//TargetController targetController = new DecideRandomTarget();
				TargetController targetController = new DecideLowHpTarget();
				Party enemyParty = Party.findEnemyParty(currentPlayer, heroParty, evilParty);
				Player targetPlayer = targetController.decideTargetPlayer(enemyParty);
				currentPlayer.action(targetPlayer);

				//死人がいたら離脱させる
				allPlayers.checkSomeoneIsDead();
				heroParty.checkSomeoneIsDead();
				evilParty.checkSomeoneIsDead();

				// 全滅しているかの判定
				if (whichPartyIsDead(heroParty, evilParty)) {
					break;
				}
			}
			//全滅しているかの判定
			if (whichPartyIsDead(heroParty, evilParty)) {
				con.typewriter("パーティーが全滅した");
				break;
			}
			// ターン終了時のステータスを表示
			con.out();
			heroParty.printPartyStatus();
			evilParty.printPartyStatus();
			// 次のターン
			turnNumber = turnNumber + 1;
		}

	}

	public void gameover() {
		con.out();
		if (heroParty.getMemberSize() > evilParty.getMemberSize()) {
			con.typewriter(heroParty.getPartyName() + "の勝利！！");
		}
		if (heroParty.getMemberSize() < evilParty.getMemberSize()) {
			con.typewriter(evilParty.getPartyName() + "の勝利！！");
		}
		if (heroParty.getMemberSize() == evilParty.getMemberSize()) {
			con.typewriter("引き分け");
		}
	}

	/**
	 * どちらかのパーティーが全滅しているかの判定
	 * @param p1
	 * @param p2
	 * @return true : どっちか全滅　false : どっちも生存
	 */
	private boolean whichPartyIsDead(Party p1, Party p2) {
		if (p1.isAllDead() || p2.isAllDead()) {
			return true;
		}
		return false;
	}
}
