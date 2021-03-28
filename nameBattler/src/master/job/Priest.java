package master.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import master.config.JobType;
import master.job.magic.CommonMagic;
import master.job.magic.MagicSet;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.ActionStrategy;
import master.strategy.TakeCareHp;

public class Priest extends Player implements IPlayerAction {
	CommonMagic commonMagic = new CommonMagic();

	public Priest(String name) {
		super(name);
	}

	@Override
	protected void setJobType() {
		jobType = JobType.PRIEST;
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
		this.hp = getNumber(0, 120) + 80; //80 ~ 200
		this.maxHp = this.getHP(); //HPの最大値
		this.mp = getNumber(1, 30) + 20; //20 ~ 50
		this.str = getNumber(2, 49) + 1; //1 ~ 50
		this.def = getNumber(3, 49) + 1; //1 ~ 50
		this.luck = getNumber(4, 99) + 1; //1 ~ 100
		this.agi = getNumber(5, 40) + 20; //20 ~ 60
	}

	@Override
	public void attack(Party enemyParty, PartyManager currentPartyManager) {
		ActionStrategy actionStrategy = new TakeCareHp(enemyParty);
		actionStrategy.decideAction(this, currentPartyManager);
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

	@Override
	public boolean healAction(PartyManager currentPartyManager) {
		if (existDyingMember(currentPartyManager) && hasEnoughMP(20)) {
			commonMagic.useHeal(this, findWorstHpMember(currentPartyManager));
			return true;
		}
		return false;
	}

	@Override
	public boolean debuffAction(Player enemy) {
		if (!hasEnoughMP(10)) {
			return false;
		}

		boolean isParalize = enemy.getParalizeState();
		boolean isPoison = enemy.getPoisonState();

		if (isParalize && isPoison) {
			return false;
		}

		if (isParalize && !isPoison) {
			commonMagic.usePoison(this, enemy);
			return true;
		}

		if (!isParalize && isPoison) {
			commonMagic.useParalize(this, enemy);
			return true;
		}

		if (!isParalize && !isPoison) {
			int rate = new Random().nextInt(100);
			if (rate < 50) {
				commonMagic.useParalize(this, enemy);
			} else {
				commonMagic.usePoison(this, enemy);
			}
			return true;
		}

		return false;
	}

	/**
	 * HP半分以下のメンバーがいるかどうか
	 * @param partyManager
	 * @return HP半分以下のメンバーがいたらtrue
	 */
	private boolean existDyingMember(PartyManager partyManager) {
		ArrayList<Player> myMembers = this.getMyMembers(partyManager);
		for (int i = 0; i < myMembers.size(); i++) {
			Player p = myMembers.get(i);
			if (p.getHP() <= (p.getMaxHP() / 2)) {
				return true;
			}
		}
		return false;
	}
}
