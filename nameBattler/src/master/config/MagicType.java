package master.config;

public enum MagicType {
	FIRE("ファイア", 10, "ダメージ"),
	THUNDER("サンダー", 20, "ダメージ"),
	HEAL("ヒール", 20, "回復"),
	PARALIZE("パライズ", 10, "デバフ"),
	POISON("ポイズン", 10, "デバフ");

	private String name;
	private int mp;
	private String magicAttribute;

	MagicType(String name, int mp, String magicAttribute){
		this.name = name;
		this.mp = mp;
		this.magicAttribute = magicAttribute;
	}
	public String getName() {
		return this.name;
	}

	public int getMp() {
		return this.mp;
	}

	public String getMagicAttribute() {
		return this.magicAttribute;
	}
}
