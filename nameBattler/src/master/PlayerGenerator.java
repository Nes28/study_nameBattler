package master;

import java.util.Random;
import java.util.Scanner;

import master.config.JobType;
import master.job.Fighter;
import master.job.Monk;
import master.job.Player;
import master.job.Priest;
import master.job.Wizard;
import master.util.Console;

public class PlayerGenerator {
	Console con = new Console();
	Scanner scanner = new Scanner(System.in);

	public Player generatePlayer(int i) {
		return generatePlayer(decidePlayerName(i), decideJobType(i));
	}

	/**
	 * プレイヤーを生成
	 * @param name　プレイヤー名
	 * @param job　JobType
	 * @return プレイヤー
	 */
	private Player generatePlayer(String name, JobType job) {
		Player player;
		String jobName = job.getName();
		switch (jobName) {
		case "戦士":
			player = new Fighter(name);
			break;
		case "魔法使い":
			player = new Wizard(name);
			break;
		case "プリースト":
			player = new Priest(name);
			break;
		case "モンク":
			player = new Monk(name);
			break;
		default:
			player = new Fighter(name);
			break;
		}
		return player;
	}

	/**
	 * プレイヤーの名前を決定
	 * @param i 全体で何番目のプレイヤーか
	 * @return 名前（自動か入力）
	 */
	private String decidePlayerName(int i) {
		String mess = String.format("プレイヤー%dの名前を入力してください : ", i);
		con.typewriter(mess);
		return "【プレイヤー"+ i + "】" ;
		//↓手動にする場合
		//return scanner.next();
	}

	/**
	 * ジョブを決定
	 * @param i 全体で何番目のプレイヤーか
	 * @return JobType（自動か入力）
	 */
	private JobType decideJobType(int i) {
		System.out.println("1:戦士\n2:魔法使い\n3:僧侶\n4:モンク");
		System.out.printf("プレイヤー%dの職業を数字で選んでください : \n", i);
		con.typewriterNoLn("・・・・・・・・", 80);
		int jobNumber = new Random().nextInt(4) + 1;
		//↓手動にする場合
		//jobNumber = scanner.nextInt();
		return JobType.getById(jobNumber);
	}
}
