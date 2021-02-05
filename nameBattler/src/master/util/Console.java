package master.util;

public class Console {

	public void out(String s) {
		System.out.println(s);
	}

	public void out() {
		System.out.println();
	}

	public void outNoLn(String s) {
		System.out.print(s);
	}

	/**
	 * 固定時間でメッセージ表示
	 * @param mess 表示するメッセージ
	 */
	public void typewriter(String mess) {
		this.typewriter(mess, 10);
	}

	/**
	 * 任意の速度でメッセージ表示
	 * @param mess　表示するメッセージ
	 * @param speed　表示するスピード
	 */
	public void typewriter(String mess, int speed) {
		this.typewriterNoLn(mess, speed);
		this.out();
	}

	/**
	 * 任意の速度でメッセージ表示(改行なし）
	 * @param mess　表示するメッセージ
	 * @param speed　表示するスピード
	 */
	public void typewriterNoLn(String mess, int speed) {
		for (char c : mess.toCharArray()) {
			this.outNoLn(String.valueOf(c));
			this.sleep(speed);
		}
	}

	/**
	 * 指定した時間プログラムを止める
	 * @param speed 止める時間
	 */
	private void sleep(int speed) {
		try {
			Thread.sleep(speed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
