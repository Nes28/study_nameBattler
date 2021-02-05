package master.job;
import master.magic.CommonMagic;
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
	public void attack(Player defender) {
		//HPが減少していたら回復
		if (hasLowHP() && hasEnoughMP(20)) {
			commonMagic.useHeal(this, this);
			return;
		}

		//相手が麻痺していなかったら
		if (!defender.isParalize) {
			commonMagic.useParalize(this, defender);
			return;
		}

		//相手が毒じゃなかったら
		if(!defender.isPoison) {
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
	 * HPの状態
	 * @return 最大値の半分以下ならtrue
	 */
	private boolean hasLowHP() {
		return this.getHP() <= (this.getMaxHP() / 2);
	}

}
