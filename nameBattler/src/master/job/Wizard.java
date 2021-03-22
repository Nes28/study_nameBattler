package master.job;

import master.config.JobType;
import master.job.magic.CommonMagic;
import master.job.magic.MagicSet;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.ActionStrategy;
import master.strategy.MagicAttackPriority;
public class Wizard extends Player implements IPlayerAction{
	CommonMagic commonMagic = new CommonMagic();

	public Wizard(String name) {
		super(name);
	}
	@Override
	protected void setJobType() {
		jobType = JobType.WIZARD;
	}
	@Override
	protected void setMagicSet() {
		magicSet = new MagicSet(jobType);
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
	public void attack(Party enemyParty,PartyManager currentPartyManager) {
		ActionStrategy actionStrategy = new MagicAttackPriority(enemyParty);
		actionStrategy.decideAction(this, currentPartyManager);
	}

	@Override
	public boolean magicAttack(Player enemy) {
		int damage = this.calcMagicDamage(enemy);
		dealDamage(enemy, damage);
		return true;
	}
	
	/**
	 * マジックダメージの算出
	 * @return マジックダメージ
	 */
	private int calcMagicDamage(Player enemy) {
		con.typewriterNoLn(getName() + "の魔法攻撃！");
		//MP:~9
		if(!hasEnoughMP(10)) {
			con.typewriterNoLn("しかしMPが足りない・・・");
			return calcNormalAttackDamage(enemy);
		}
		
		//MP:10~19
		if (!hasEnoughMP(20)) {
			return commonMagic.useFire(this);
		}
		
		//MP:20~
		int magicNumber = rnd.nextInt(2);
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
