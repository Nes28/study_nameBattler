package master.job;
import master.party.IPartyManager;
import master.util.Console;
// プレイヤー：戦士
public class Fighter extends Player {

	public Fighter(String name) {
		super(name);
		Console con = new Console();
		con.typewriter(String.format("%sは戦士です", name));
		con.typewriter("---------------------------------");
	}

	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	@Override
	protected void makeCharacter() {
		// 戦士のパラメータを名前から生成する
		this.hp = getNumber(0, 100) * 3; //100 ~ 300
		this.maxHp = this.hp;
		this.mp = 0; //0
		this.str = getNumber(2, 70) + 30; //30 ~ 100
		this.def = getNumber(3, 70) + 30; //30 ~ 100
		this.luck = getNumber(4, 99) + 1; //1 ~ 100
		this.agi = getNumber(5, 49) + 1; //1 ~ 50
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	@Override
	public void attack(Player defender, IPartyManager partyManager) {
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

	// =======================
	// private メソッド
	// =======================

	// =======================
	// public メソッド
	// =======================
}
