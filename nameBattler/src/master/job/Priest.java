package master.job;

import java.util.ArrayList;
import java.util.Collections;

import master.job.magic.CommonMagic;
import master.party.PartyManager;

public class Priest extends Player {
	CommonMagic commonMagic = new CommonMagic();

	public Priest(String name) {
		super(name);
		con.typewriter(String.format("%sは僧侶です", name));
		con.typewriter("---------------------------------");
	}

	/**
	 * 名前からパラメーターを設定
	 */
	@Override
	protected void makeCharacter() {
		this.hp = getNumber(0, 120) + 80; //80 ~ 200
		this.maxHp = this.getHP(); //HPの最大値
		this.mp = getNumber(1, 30) + 20; //20 ~ 50
		this.str = getNumber(2, 49) + 1; //1 ~ 50
		this.def = getNumber(3, 49) + 1; //1 ~ 50
		this.luck = getNumber(4, 99) + 1; //1 ~ 100
		this.agi = getNumber(5, 40) + 20; //20 ~ 60
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	@Override
	public void attack(Player defender, PartyManager partyManager) {
		//味方が死にかけなら回復
		if (existDyingMember(partyManager) && hasEnoughMP(20)) {
			commonMagic.useHeal(this, findWorstHpMember(partyManager));
			return;
		}

		//相手が麻痺していなかったら
		if (!defender.isParalize) {
			commonMagic.useParalize(this, defender);
			return;
		}

		//相手が毒じゃなかったら
		if (!defender.isPoison) {
			commonMagic.usePoison(this, defender);
			return;
		}

		// 与えるダメージを求める
		con.typewriterNoLn(getName() + "の攻撃！", 20);
		int damage = calcDamage(defender);

		// 求めたダメージを対象プレイヤーに与える
		if (damage > 0) {
			con.typewriter(defender.getName() + "に" + damage + "のダメージ！");
		} else {
			con.typewriter("攻撃がミス");
		}
		defender.damage(damage);

		// 倒れた判定
		if (defender.isDead()) {
			con.typewriter(defender.getName() + "は力尽きた...");
		}
	}

	/**
	 * HP半分以下のメンバーがいるかどうか
	 * @param partyManager
	 * @return HP半分以下のメンバーがいたらtrue
	 */
	private boolean existDyingMember(PartyManager partyManager) {
		ArrayList<Player> myMembers = this.getMyMembers(partyManager);
		for (int i = 0; i < myMembers.size(); i++) {
			Player p =myMembers.get(i);
			if(p.getHP() <= (p.getMaxHP()/2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 最もHPの低い味方を探す
	 * @param partyManager
	 * @return 最もHPの低いPlayer
	 */
	private Player findWorstHpMember(PartyManager partyManager) {
		ArrayList<Player> myMembers = this.getMyMembers(partyManager);
		Collections.sort(myMembers, (p1, p2) -> p1.getHP() - p2.getHP());
		return myMembers.get(0);
	}
}
