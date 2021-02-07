package master.job.magic;
import master.job.Player;
import master.job.magic.detail.Fire;
import master.job.magic.detail.Heal;
import master.job.magic.detail.Paralize;
import master.job.magic.detail.Poison;
import master.job.magic.detail.Thunder;
public class MagicController implements IMagicController {
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
