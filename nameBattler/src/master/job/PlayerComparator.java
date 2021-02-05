package master.job;
import java.util.Comparator;
public class PlayerComparator implements Comparator<Player> {
	@Override
	public int compare(Player p1, Player p2) {
		return p1.getAGI() > p2.getAGI() ? 1: -1;
	}
}
