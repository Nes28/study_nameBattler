package master.job.magic;
import master.job.Player;
import master.job.magic.damage.Fire;
import master.job.magic.damage.IDamageMagic;
import master.job.magic.damage.Thunder;
import master.job.magic.debuff.Paralize;
import master.job.magic.debuff.Poison;
import master.job.magic.heal.Heal;
public class CommonMagic{
	IDamageMagic fire = new Fire();
	IDamageMagic thunder = new Thunder();
	Heal heal = new Heal();
	Poison poison = new Poison();
	Paralize paralize = new Paralize();
	
	public int useFire(Player attacker) {
		return fire.useMagic(attacker);
	}

	public int useThunder(Player attacker) {
		return thunder.useMagic(attacker);
	}

	public void useHeal(Player me, Player player) {
		heal.useMagic(me, player);
	}

	public void useParalize(Player me, Player defender) {
		paralize.useMagic(me, defender);
	}

	public void usePoison(Player me, Player defender) {
		poison.useMagic(me, defender);
	}
}
