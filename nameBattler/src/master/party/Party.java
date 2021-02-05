package master.party;

import java.util.ArrayList;
import java.util.Collections;

import master.config.PartyType;
import master.job.Player;
import master.job.PlayerComparator;
import master.util.Console;

public class Party {
	// =======================
	// フィールド変数
	// =======================
	private ArrayList<Player> members;
	String partyName;
	Console con = new Console();

	// =======================
	// コンストラクタ
	// =======================
	public Party(PartyType partyType) {
		members = new ArrayList<Player>();
		this.partyName = partyType.getName();
	}

	// =======================
	// Getter / Setter
	// =======================
	public ArrayList<Player> getMembers() {
		return members;
	}

	/**
	 * i番目のプレイヤーを取得する
	 * @param i	: index
	 * @return i番目のPlayer
	 */
	public Player getPlayer(int i) {
		return members.get(i);
	}

	public int getMemberSize() {
		return members.size();
	}
	
	public String getPartyName() {
		return this.partyName;
	}

	// =======================
	// protected メソッド
	// =======================
	// =======================
	// private メソッド
	// =======================
	// =======================
	// public メソッド
	// =======================
	/**
	* パーティーにプレイヤーを追加する
	* @param player : 追加するプレイヤー */
	public void AppendPlayer(Player player) {
		if (partyName == PartyType.HEROES.getName() || partyName == PartyType.EVILS.getName()) {
			player.setBelongPartyName(partyName);
		}
		members.add(player);
	}

	/**
	* パーティーからプレイヤーを離脱させる
	* * @param player : 離脱させるプレイヤー */
	public void RemovePlayer(Player player) {
		members.remove(player);
	}

	/**
	 * 速度順に並べる
	 */
	public void sortAgi() {
		Collections.sort(members, new PlayerComparator());
		System.out.println("--速度順に並び直しました--");
		for (int i = members.size() - 1; i >= 0; i--) {
			Player p = members.get(i);
			p.PrintAllStatus();
		}
	}

	/**
	 * メンバーが死んでいたらパーティから離脱させる
	 */
	public void checkSomeoneIsDead() {
		for (int i = 0; i < members.size(); i++) {
		//for (int i = members.size()-1; i > 0; i--) {
			Player p = members.get(i);
			if (p.isDead()) {
				RemovePlayer(p);
			}
		}
	}

	/**
	 * パーティーが全滅しているかの判定
	 * @return true : 全滅 , false : 生き残っている
	 */
	public boolean isAllDead() {
		if (members.size() <= 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 相手パーティーを見つける
	 * @param p 攻撃するプレイヤー
	 * @param heroes　英雄パーティー
	 * @param evils　悪人パーティー
	 * @return pにっての敵パーティー
	 */
	public static Party findEnemyParty(Player p, Party heroes, Party evils) {
		String belongPartyName = p.getBelongPartyName();
		if (belongPartyName == PartyType.HEROES.getName()) {
			return evils;
		}
		if (belongPartyName == PartyType.EVILS.getName()) {
			return heroes;
		}
		System.out.println("所属パーティーを確認してください");
		return null;
	}

	/**
	 * メンバー全員のステータスを表示
	 */
	public void printPartyStatus() {
		con.typewriter("--現在のステータス (" + this.getPartyName() + ") --");
		for (Player p : members) {
			p.PrintStatus();
		}
	}
}
