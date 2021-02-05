package master.magic;
import master.job.Player;
import master.magic.detail.Fire;
import master.magic.detail.Heal;
import master.magic.detail.Paralize;
import master.magic.detail.Poison;
import master.magic.detail.Thunder;
public class CommonMagic implements MagicController {
	Fire fire = new Fire();
	Thunder thunder = new Thunder();
	Heal heal = new Heal();
	Poison poison = new Poison();
	Paralize paralize = new Paralize();
	@Override
	public int useFire(Player attacker) {
		return fire.useMagic(attacker);
	}

	@Override
	public int useThunder(Player attacker) {
		return thunder.useMagic(attacker);
	}

	@Override
	public void useHeal(Player me, Player player) {
		heal.useMagic(me, player);
	}

	@Override
	public void useParalize(Player me, Player defender) {
		paralize.useMagic(me, defender);
	}

	@Override
	public void usePoison(Player me, Player defender) {
		poison.useMagic(me, defender);
	}
}
