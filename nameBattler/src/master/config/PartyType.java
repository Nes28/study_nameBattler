package master.config;

public enum PartyType {
	HEROES("英雄チーム"),
	EVILS("悪人チーム"),
	ALLPLAYERS("全プレイヤー");

	String name;
	PartyType(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
