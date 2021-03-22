package master.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.targetSelector.DecideLowHpTarget;
import master.strategy.targetSelector.ITargetSelector;

/**
 * いろいろやろうぜ
 */
public class DoVarious extends ActionStrategy {
	List<String> actionList;
	Random rnd = new Random();

	public DoVarious(Party enemyParty) {
		super(enemyParty);
	}

	@Override
	public void decideAction(Player me, PartyManager currentPartyManager) {
		this.setupActionList(me);
		this.doRandomAction(me, currentPartyManager);
	}

	@Override
	public Player decideTargetPlayer() {
		ITargetSelector targetSelector = new DecideLowHpTarget();
		Player targetPlayer = targetSelector.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}

	//魔法の属性＋通常攻撃
	private void setupActionList(Player me) {
		actionList = new ArrayList<String>(me.getMagicAttributes());
		actionList.add("通常攻撃");
	}

	/**
	 * 行動可能なアクションをランダムに行う
	 */
	private void doRandomAction(Player me, PartyManager currentPartyManager) {
		while(true) {
			int actionIndex = rnd.nextInt(actionList.size());
			String actionAttribute = actionList.get(actionIndex);
			actionList.remove(actionIndex);
			boolean doneAction = false;
			doneAction = this.selectedAction(actionAttribute, me, currentPartyManager);
			if(doneAction) break;
		}
	}

	private boolean selectedAction(String attribute, Player me, PartyManager currentPartyManager) {
		boolean doneAction = false;
		Player enemy = this.decideTargetPlayer();
		switch (attribute) {
		case "通常攻撃":
			return doneAction = me.normalAttack(enemy);
		case "ダメージ":
			return doneAction = me.magicAttack(enemy);
		case "回復":
			return doneAction = me.healAction(currentPartyManager);
		case "デバフ":
			return doneAction = me.debuffAction(enemy);
		default:
			return doneAction = me.normalAttack(enemy);
		}
	}
}
