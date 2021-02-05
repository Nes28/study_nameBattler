package master.job;

import master.magic.CommonMagic;
public class Wizard extends Player {
	CommonMagic commonMagic = new CommonMagic();

	public Wizard(String name) {
		super(name);
		con.typewriter(String.format("%sは魔法使いです", name));
		con.typewriter("---------------------------------");
	}

	/**
	 * 名前からパラメーターを設定
	 */
	@Override
	protected void makeCharacter() {
		this.hp = getNumber(0, 100) + 50; //50 ~ 150
		this.maxHp = this.hp;
		this.mp = getNumber(1, 50) + 30; //30 ~ 80
		this.str = getNumber(2, 49) + 1; //1 ~ 50
		this.def = getNumber(3, 49) + 1; //1 ~ 50
		this.luck = getNumber(4, 99) + 1; //1 ~ 100
		this.agi = getNumber(5, 40) + 20; //20 ~ 60
	}

	@Override
	public void attack(Player defender) {
		// 与えるダメージを求める
		con.typewriterNoLn(getName() + "の攻撃！", 20);
		int damage;
		if (hasEnoughMP(10)) {
			//魔法攻撃
			damage = calcMagicDamage();
		} else {
			//通常攻撃
			damage = calcDamage(defender);
		}
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
	 * マジックダメージの算出
	 * @return マジックダメージ
	 */
	private int calcMagicDamage() {
		int magicNumber = rnd.nextInt(2);
		//MP:10~19
		if (!hasEnoughMP(20)) {
			return commonMagic.useFire(this);
		}
		//MP:20~
		if (hasEnoughMP(20)) {
			if (magicNumber == 1) {
				return commonMagic.useFire(this);
			} else {
				return commonMagic.useThunder(this);
			}
		}
		return 0;
	}
}
