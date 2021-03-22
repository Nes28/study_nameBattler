package master.job;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import master.config.JobType;
import master.config.PartyType;
import master.job.magic.MagicSet;
import master.party.Party;
import master.party.PartyManager;
import master.util.Console;
// プレイヤークラス(各種ジョブの基底クラス)
public abstract class Player implements IPlayerAction {
	Console con = new Console();
	Random rnd = new Random();
	protected String name; // 名前
	protected int hp; //HP
	protected int maxHp; //maxHP
	protected int mp; //MP
	protected int str; // 攻撃力
	protected int def; // 防御力
	protected int luck; //運
	protected int agi; //素早さ
	boolean isParalize = false; //麻痺状態
	boolean isPoison = false; //毒状態
	public String belongPartyName; //所属パーティー名
	
	protected JobType jobType;
	protected MagicSet magicSet;

	public Player(String name) {
		this.name = name;
		this.setJobType();
		this.setMagicSet();
		this.makeCharacter();
		con.typewriter(String.format("%sは%sです", name, jobType.getName()));
		con.typewriter("---------------------------------");
	}

	// =======================
	// Getter / Setter
	// =======================
	public String getName() {
		return this.name;
	}

	public int getHP() {
		return this.hp;
	}

	public int getMaxHP() {
		return this.maxHp;
	}

	public int getMP() {
		return this.mp;
	}

	public int getSTR() {
		return this.str;
	}

	public int getDEF() {
		return this.def;
	}

	public int getLUCK() {
		return this.luck;
	}

	public int getAGI() {
		return this.agi;
	}
	
	public JobType getJobType() {
		return this.jobType;
	}
	
	public MagicSet getMagicSet() {
		return this.magicSet;
	}
	
	public Set getMagicAttributes() {
		return this.magicSet.getMagicAttributes();
	}

	public String getBelongPartyName() {
		return this.belongPartyName;
	}

	public ArrayList<Player> getMyMembers(PartyManager partyManager) {
		Party myParty = partyManager.getParty(PartyType.getByName(this.getBelongPartyName()));
		return myParty.getMembers();
	}

	/**
	 * 所属パーティー名をセット
	 * @param partyName
	 */
	public void setBelongPartyName(String partyName) {
		this.belongPartyName = partyName;
	}
	
	protected abstract void setJobType();
	protected abstract void setMagicSet();
	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	protected abstract void makeCharacter();

	/**
	 * 名前(name)からハッシュ値を生成し、指定された位置の数値を取り出す
	 * @param index : 何番目の数値を取り出すか
	 * @param max : 最大値(内部的に0～255の値を生成するが、0～maxまでの値に補正)
	 * @return 数値(0～max) ※maxも含む
	 */
	protected int getNumber(int index, int max) {
		try {
			// 名前からハッシュ値を生成する
			byte[] result = MessageDigest.getInstance("SHA-1").digest(this.name.getBytes());
			String digest = String.format("%040x", new BigInteger(1, result));

			// ハッシュ値から指定された位置の文字列を取り出す（２文字分）
			String hex = digest.substring(index * 2, index * 2 + 2);

			// 取り出した文字列（16進数）を数値に変換する
			int val = Integer.parseInt(hex, 16);
			return val * max / 255;
		} catch (Exception e) {
			// エラー
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 状態異常確認と攻撃
	 * @param defender
	 */
	public void action(Party enemyParty, PartyManager currentPartyManager) {
		this.activePoison();
		if (isDead()) {
			con.typewriter(this.getName() + "は毒で死んでしまった");
			return;
		}
		if (activeParalize()) {
			con.typewriter(this.getName() + "は麻痺で体が動かない・・・");
			return;
		}
		this.attack(enemyParty, currentPartyManager);
	}

	public abstract void attack(Party enemyParty, PartyManager currentPartyManager);

	@Override
	public boolean normalAttack(Player enemy) {
		int damage = calcNormalAttackDamage(enemy);
		dealDamage(enemy, damage);
		return true;
	}

	public boolean magicAttack(Player enemy) {
		return false;
	}

	public boolean healAction(PartyManager currentPartyManager) {
		return false;
	}
	
	public boolean debuffAction(Player enemy) {
		return false;
	}

	/**
	 * ターゲットに与える通常ダメージ
	 * @param target : 対象プレイヤー
	 * @return ダメージ値(0～)
	 */
	protected int calcNormalAttackDamage(Player target) {
		con.typewriterNoLn(getName() + "の通常攻撃！");
		int damage = getSTR() - target.getDEF();
		//会心の一撃
		if (isCriticalAttack()) {
			damage = getSTR();
			con.typewriter("会心の一撃！！");
		}
		if (damage < 0) {
			damage = 0;
		}
		return damage;
	}

	private boolean isCriticalAttack() {
		int criticalRate = rnd.nextInt(1000) + 1; //1 ~ 1000
		if (criticalRate < getLUCK()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 敵にダメージを与える
	 */
	protected void dealDamage(Player enemy, int damage) {
		if (damage > 0) {
			con.typewriter(enemy.getName() + "に" + damage + "のダメージ！");
		} else {
			con.typewriter("攻撃がミス");
		}
		enemy.receiveDamage(damage);
	}

	/**
	 * ダメージを受ける
	 * @param damage : ダメージ値
	 */
	protected void receiveDamage(int damage) {
		this.hp = Math.max(this.getHP() - damage, 0);
		if (this.isDead()) {
			con.typewriter(this.getName() + "は力尽きた...");
		}
	}

	
	/**
	 * 必要なMPがあるかの判定
	 * @param necessaryMP
	 * @return true : MP十分 false : MP不足
	 */
	protected boolean hasEnoughMP(int necessaryMP) {
		if (this.mp >= necessaryMP) {
			return true;
		}
		return false;
	}

	/**
	 * MPを消費する
	 * @param enoughMP 使用するMP
	 */
	public void consumeMP(int mpCost) {
		this.mp -= mpCost;
		if (this.mp < 0) {
			mp = 0;
		}
	}

	/**
	 * 麻痺の発動抽選
	 * @return true : 麻痺発動  false : 不発
	 */
	private boolean activeParalize() {
		if (!isParalize)
			return false;
		int kakuritsu = rnd.nextInt(100); //0~99
		if (kakuritsu < 20) {
			return true;
		}
		return false;
	}

	/**
	 * 毒ダメージ
	 */
	private void activePoison() {
		if (!isPoison)
			return;
		final int poisonDamage = 20;
		this.hp -= poisonDamage;
		if (this.hp < 0) {
			this.hp = 0;
		}
		String mess = String.format("%sは毒でダメージを受けた(HP : %d)", this.getName(), this.hp);
		con.typewriter(mess);
	}

	/**
	 * 現在のステータスを System.out で表示する
	 */
	public void PrintStatus() {
		String mess = String.format("%s (HP=%3d/%3d MP=%3d )\n",
				this.getName(), this.getHP(), this.getMaxHP(), this.getMP());
		con.typewriterNoLn(mess, 10);
	}

	public void PrintAllStatus() {
		String mess = String.format("%s/%s (速度=%3d HP=%3d MP=%3d 攻撃=%3d 防御=%3d)\n",
				this.getName(), this.getBelongPartyName(), this.getAGI(), this.getHP(), this.getMP(), this.getSTR(),
				this.getDEF());
		con.typewriterNoLn(mess, 10);
	}

	/**
	 * プレイヤーが死んでいるかの判定
	 * @return true : 死亡
	 */
	public boolean isDead() {
		if (this.hp <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 自身のHPが回復される
	 * @param healPoint　回復量
	 */
	public void isHealedHp(int healPoint) {
		int differenceHP = this.maxHp - this.hp;
		if (healPoint > differenceHP) {
			this.hp += differenceHP;
			con.typewriter(this.getName() + "のHPは" + differenceHP + "回復した");
		} else {
			this.hp += healPoint;
			con.typewriter(this.getName() + "のHPは" + healPoint + "回復した");
		}
	}

	/**
	 * 麻痺状態になる
	 */
	public void becomeParalize() {
		this.isParalize = true;
	}

	/**
	 * 毒状態になる
	 */
	public void becomePoison() {
		this.isPoison = true;
	}
}