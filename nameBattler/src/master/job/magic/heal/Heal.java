package master.job.magic.heal;

import master.config.MagicType;
import master.job.Player;
import master.util.Console;

public class Heal {
	MagicType heal = MagicType.HEAL;
	private int healPoint = 50;
	Console con = new Console();

	public void useMagic(Player me, Player player) {
		con.typewriterNoLn(me.getName() + "は" + heal.getName() + "を唱えた!!");
		me.consumeMP(heal.getMp());
		player.isHealedHp(healPoint);
	}
}
