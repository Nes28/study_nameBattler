package master.config;
public enum JobType {
	FIGHTER(1, "戦士"),
	WIZARD(2, "魔法使い"),
	PRIEST(3, "プリースト"),
	MONK(4, "モンク");

	int id;
	String name;

	JobType(int id, String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	/**
	 * idでJobListを返す
	 * @param data
	 * @return JobList
	 */
	public static JobType getById(int data) {
		for(JobType job : JobType.values()) {
			if(job.getId() == data) {
				return job;
			}
		}
		return null;
	}
}
