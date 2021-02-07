package master.job.magic;
import master.job.Player;
public interface IMagicController {
	int useFire(Player attacker);
	int useThunder(Player attacker);
	void useHeal(Player me, Player player);
	void useParalize(Player me, Player defender);
	void usePoison(Player me, Player defender);
}
