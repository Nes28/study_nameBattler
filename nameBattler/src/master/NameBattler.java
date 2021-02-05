package master;

public class NameBattler {

	public static void main(String[] args) {
		// ==================================================
		// バトル開始準備
		// ==================================================
		GameManager controller = new GameManager();
		
		controller.init();
		controller.battle();
		controller.gameover();
}
//
}