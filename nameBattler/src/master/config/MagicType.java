package master.config;

public enum MagicType {
	FIRE("ファイア", 10),
	THUNDER("サンダー", 20),
	HEAL("ヒール", 20),
	PARALIZE("パライズ", 10),
	POISON("ポイズン", 10);
	
	private String name;
	private int mp;
	
	MagicType(String name, int mp){
		this.name = name;
		this.mp = mp;
	}
	public String getName() {
		return this.name;
	}
	
	public int getMp() {
		return this.mp;
	}
}
