package master.job;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;

import master.config.PartyType;
import master.party.Party;
import master.party.PartyManager;
import master.util.Console;
//

// プレイヤークラス(各種ジョブの基底クラス)
public class Player {
	Console con = new Console();
	// =======================
	// フィールド変数
	// =======================
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
	Random rnd = new Random();

	/**
	 * コンストラクタ
	 * @param name : プレイヤー名
	 */
	public Player(String name) {
		this.name = name;

		// キャラクターのパラメータ生成
		makeCharacter();
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

	public String getBelongPartyName() {
		return this.belongPartyName;
	}

	public ArrayList<Player> getMyMembers(PartyManager partyManager){
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

	// =======================
	// protected メソッド
	// =======================
	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	protected void makeCharacter() {
		// ジョブごとにオーバーライドして処理を記述してください
	}

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
	 * 対象プレイヤー(target)に対して与えるダメージを計算する
	 * @param target : 対象プレイヤー
	 * @return ダメージ値(0～)
	 */
	protected int calcDamage(Player target) {
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

	/**
	 * ダメージを受ける
	 * @param damage : ダメージ値
	 */
	protected void damage(int damage) {
		// ダメージ値分、HPを減少させる
		this.hp = Math.max(this.getHP() - damage, 0);
	}

	/**
	 * 対象プレイヤーに攻撃を行う
	 * @param defender : 対象プレイヤー
	 * @param partyManager TODO
	 */
	protected void attack(Player defender, PartyManager partyManager) {
		// ジョブごとにオーバーライドして処理を記述してください
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

	// =======================
	// private メソッド
	// =======================
	private boolean isCriticalAttack() {
		int kakuritsu = rnd.nextInt(1000) + 1; //1 ~ 1000
		if (kakuritsu < getLUCK()) {
			return true;
		} else {
			return false;
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

	// =======================
	// public メソッド
	// =======================
	/**
	 * 現在のステータスを System.out で表示する
	 */
	public void PrintStatus() {
		String mess = String.format("%s (HP=%3d/%3d MP=%3d )\n",
				this.getName(), this.getHP(),this.getMaxHP(), this.getMP());
		con.typewriterNoLn(mess, 10);
	}

	public void PrintAllStatus() {
		String mess = String.format("%s/%s (速度=%3d HP=%3d MP=%3d 攻撃=%3d 防御=%3d)\n",
				this.getName(), this.getBelongPartyName(), this.getAGI(), this.getHP(), this.getMP(), this.getSTR(),
				this.getDEF());
		con.typewriterNoLn(mess, 10);
	}

	/**
	 * 状態異常確認と攻撃
	 * @param defender
	 */
	public void action(Player defender, PartyManager partyManager) {
		activePoison();
		String mess;
		if (isDead()) {
			mess = String.format("%sは毒で死んでしまった", getName());
			con.typewriter(mess);
			return;
		}
		if (activeParalize()) {
			mess = String.format("%sは麻痺で体が動かない・・・", this.getName());
			con.typewriter(mess);
			return;
		}
		attack(defender, partyManager);
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
	public void setParalize() {
		this.isParalize = true;
	}

	/**
	 * 毒状態になる
	 */
	public void setPoison() {
		this.isPoison = true;
	}
}