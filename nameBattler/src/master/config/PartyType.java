package master.config;

public enum PartyType {
	HEROES("英雄チーム"),
	EVILS("悪人チーム"),
	UNKNOWN("不明チーム");	//swichのぬるぽ回避

	String name;
	PartyType(String name){
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public static PartyType getByName(String name) {
		for(PartyType pt : PartyType.values()) {
			if(pt.getName().equals(name)) {
				return pt;	
			}
		}
		return null;
	}
}
