package master.job.magic.detail;

import java.util.Random;

import master.config.MagicType;
import master.job.Player;
import master.job.magic.IDamageMagic;
import master.util.Console;
public class Fire implements IDamageMagic{
	MagicType fire = MagicType.FIRE;
	private int minDamage = 10;
	private int maxDamage = 30;
	Console con = new Console();
	Random rnd = new Random();

	@Override
	public int useMagic(Player attacker) {
		con.typewriterNoLn(fire.getName() + "を唱えた!", 20);
		int damage = rnd.nextInt(maxDamage - minDamage + 1) + (minDamage);
		attacker.consumeMP(fire.getMp());
		return damage;
	}

}
