package master.job.magic;

import java.util.HashSet;
import java.util.Set;

import master.config.JobType;
import master.config.MagicType;

public class MagicSet {
	private Set<MagicType> magics = new HashSet<>();
	private Set<String> magicAttributes = new HashSet<>();

	public MagicSet(JobType jobType){
		this.setupMagics(jobType);
		this.collectAttributes();
	}
	
	public Set getMagicAttributes() {
		return this.magicAttributes;
	}
	
	private void setupMagics(JobType jobType) {
		switch(jobType) {
		case FIGHTER:
			this.setupFighterMagicSet();
			break;
		case WIZARD:
			this.setupWizardMagicSet();
			break;
		case PRIEST:
			this.setupPriestMagicSet();
			break;
		case MONK:
			this.setupMonkMagicSet();
			break;
		}
	}
	
	private void setupFighterMagicSet() {
		return;
	}

	private  void setupWizardMagicSet() {
		magics.add(MagicType.FIRE);
		magics.add(MagicType.THUNDER);
	
	}

	private  void setupPriestMagicSet() {
		magics.add(MagicType.HEAL);
		magics.add(MagicType.PARALIZE);
		magics.add(MagicType.POISON);
	
	}

	private  void setupMonkMagicSet() {
		return;
	}

	private void collectAttributes() {
		for(MagicType magic : magics) {
			magicAttributes.add(magic.getMagicAttribute());
		}
	}

	/**
	 * 特定のマジック属性が含まれているのか
	 * @param magicAttribute
	 * @return 含まれていれば : true
	 */
	public boolean containMagicAttribute(String magicAttribute) {
		if(magicAttributes.contains(magicAttribute)) {
			return true;
		}else {
			return false;
		}
	}
}
