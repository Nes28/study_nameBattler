package master;

public class NameBattler {

	public static void main(String[] args) {
		// ==================================================
		// バトル開始準備
		// ==================================================
		GameProgress controller = new GameProgress();
		
		controller.init();
		controller.battle();
		controller.gameover();
}
//
}