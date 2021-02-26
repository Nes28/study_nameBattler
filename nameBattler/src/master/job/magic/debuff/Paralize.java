package master.job.magic.debuff;

import master.config.MagicType;
import master.job.Player;
import master.util.Console;

public class Paralize {
	Console con = new Console();
	MagicType paralize = MagicType.PARALIZE;

	public void useMagic(Player me, Player defender) {
		con.typewriter(me.getName() + "は" + paralize.getName() + "を唱えた!!" + defender.getName() + "は麻痺状態になった");
		defender.setParalize();
		me.consumeMP(paralize.getMp());
	}
}
