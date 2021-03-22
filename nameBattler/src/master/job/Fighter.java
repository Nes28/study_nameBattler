package master.job;

import master.config.JobType;
import master.job.magic.MagicSet;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.ActionStrategy;
import master.strategy.MagicAttackPriority;
// プレイヤー：戦士
public class Fighter extends Player{

	public Fighter(String name) {
		super(name);
	}
	
	@Override
	protected void setJobType() {
		jobType = JobType.FIGHTER;
	}
	@Override
	protected void setMagicSet() {
		magicSet = new MagicSet(jobType);
	}

	/**
	 * 名前(name)からキャラクターに必要なパラメータを生成する
	 */
	@Override
	protected void makeCharacter() {
		this.hp = getNumber(0, 100) * 3; //100 ~ 300
		this.maxHp = this.hp;
		this.mp = 0; //0
		this.str = getNumber(2, 70) + 30; //30 ~ 100
		this.def = getNumber(3, 70) + 30; //30 ~ 100
		this.luck = getNumber(4, 99) + 1; //1 ~ 100
		this.agi = getNumber(5, 49) + 1; //1 ~ 50
	}

	@Override
	public void attack(Party enemyParty,PartyManager currentPartyManager) {
		ActionStrategy actionStrategy = new MagicAttackPriority(enemyParty);
		actionStrategy.decideAction(this, currentPartyManager);
	}

}
