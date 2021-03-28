package master.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import master.job.Player;
import master.party.Party;
import master.party.PartyManager;
import master.strategy.targetSelector.DecideRandomTarget;
import master.strategy.targetSelector.ITargetSelector;

/**
 * 魔法優先
 */
public class MagicPriority extends ActionStrategy {
	private List<String> magicActionList;

	public MagicPriority(Party enemyParty) {
		super(enemyParty);
	}

	@Override
	public void decideAction(Player me, PartyManager currentPartyManager) {
		this.setupActionList(me);
		this.doRandomMagicAction(me, currentPartyManager);	
	}

	@Override
	public Player decideTargetPlayer() {
		ITargetSelector targetSelector = new DecideRandomTarget();
		Player targetPlayer = targetSelector.decideTargetPlayer(enemyParty);
		return targetPlayer;
	}

	//魔法の属性
	private void setupActionList(Player me) {
		magicActionList = new ArrayList<String>(me.getMagicAttributes());
	}

	/**
	 * 行動可能なアクションをランダムに行う
	 */
	private void doRandomMagicAction(Player me, PartyManager currentPartyManager) {
		while (true) {
			if(magicActionList.size() <= 0) {
				Player enemy = this.decideTargetPlayer();
				me.normalAttack(enemy);
				break;
			}
			int actionIndex = new Random().nextInt(magicActionList.size());
			String actionAttribute = magicActionList.get(actionIndex);
			magicActionList.remove(actionIndex);
			boolean doneAction = false;
			doneAction = this.selectedAction(actionAttribute, me, currentPartyManager);
			if (doneAction) {
				break;
			}
		}
	}
	
	private boolean selectedAction(String attribute, Player me, PartyManager currentPartyManager) {
		Player enemy = this.decideTargetPlayer();
		switch (attribute) {
		case "ダメージ":
			return me.magicAttack(enemy);
		case "回復":
			return me.healAction(currentPartyManager);
		case "デバフ":
			return me.debuffAction(enemy);
		default:
			return me.magicAttack(enemy);
		}
	}

}
