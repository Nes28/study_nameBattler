package master.magic.detail;

import java.util.Random;

import master.config.MagicType;
import master.job.Player;
import master.magic.DamageMagicType;
import master.util.Console;
public class Thunder implements DamageMagicType {
	MagicType thunder = MagicType.THUNDER;
	private int minDamage = 10;
	private int maxDamage = 30;
	Console con = new Console();
	Random rnd = new Random();

	@Override
	public int useMagic(Player attacker) {
		con.typewriterNoLn(thunder.getName() + "を唱えた!", 20);
		int damage = rnd.nextInt(maxDamage - minDamage + 1) + (minDamage);
		attacker.consumeMP(thunder.getMp());
		return damage;
	}
}
