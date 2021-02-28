package master.job.magic.debuff;

import master.config.MagicType;
import master.job.Player;
import master.util.Console;

public class Poison {
	Console con = new Console();
	MagicType poison = MagicType.POISON;

	public void useMagic(Player me, Player defender) {
		con.typewriter(me.getName() + "は" + poison.getName() + "を唱えた!!" + defender.getName() + "は毒状態になった");
		defender.becomePoison();
		me.consumeMP(poison.getMp());
	}
}
