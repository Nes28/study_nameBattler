package master.job;

import master.util.Console;

public class Monk extends Player {
	boolean isEndurance = false; //我慢状態
	int endurancePoint = 0; //我慢値
	int normalStr;

	public Monk(String name) {
		super(name);
		Console con = new Console();
		con.typewriter(String.format("%sはモンクです", name));
		con.typewriter("---------------------------------");
	}

	/**
	 * 名前からパラメーターを設定
	 */
	@Override
	protected void makeCharacter() {
		this.hp = getNumber(0, 100) + 100; //100 ~ 200
		this.maxHp = this.hp;
		this.mp = 0;
		this.str = getNumber(2, 69) + 1; //1 ~ 70
		this.normalStr = this.str; //元の攻撃力
		this.def = getNumber(3, 59) + 1; //1 ~ 60
		this.luck = getNumber(4, 99) + 1; //1 ~ 100
		this.agi = getNumber(5, 60) + 20; //20 ~ 80
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 */
	@Override
	public void attack(Player defender) {
		if(!isEndurance) {
			activeEndurance();
			return;
		}
		powerUpStr();
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

		returnStrAndEndurance();
	}

	/**
	 * ダメージを受ける
	 * @param damage : ダメージ値
	 */
	@Override
	protected void damage(int damage) {
		// ダメージ値分、HPを減少させる
		this.hp = Math.max(this.getHP() - damage, 0);
		if (this.isDead()) return;
		if (isEndurance) {
			chargeEndurance(damage);
		}
	}

	private void activeEndurance() {
		isEndurance = true;
		con.typewriter(this.getName() + "は我慢状態に入った。次に受けるダメージを力に変える！");
	}

	/**
	 * 受けたダメージを我慢ちとしてチャージ
	 * @param damage
	 */
	private void chargeEndurance(int damage) {
		if(damage <= 0)return;
		endurancePoint += damage;
		String mess = String.format("%sは%dダメージを自らの力として溜めている・・・", getName(), damage);
		con.typewriter(mess);
	}

	/**
	 * 攻撃力に我慢値を上乗せ
	 */
	private void powerUpStr() {
		if (endurancePoint <= 0)
			return;
		this.str += endurancePoint;
		con.typewriter(getName() + "は我慢を開放し攻撃力に転化した！！");
	}

	/**
	 * 元の攻撃力、我慢値に戻す
	 */
	private void returnStrAndEndurance() {
		if (endurancePoint <= 0)
			return;
		this.str = this.normalStr;
		this.endurancePoint = 0;
		isEndurance = false;
		con.typewriter(getName() + "の攻撃力と我慢値が元に戻った");
	}
}
