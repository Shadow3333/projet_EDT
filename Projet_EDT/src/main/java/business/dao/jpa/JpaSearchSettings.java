package business.dao.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.dao.ISearchSettings;

/**
 * This class is used to search function
 * It contains two Map one for restriction with, second for restriction without
 * @author DUBUIS Michael
 * 
 */
public class JpaSearchSettings implements ISearchSettings {
	private Map<String, List<Object>> with;
	private Map<String, List<Object>> without;
	
	/**
	 * Empty constructor
	 */
	public JpaSearchSettings() {}
	
	public void withKeyToField(String field, Object... keys) throws IllogicArgumentException {
		if(with == null) {
			with = new HashMap<String, List<Object>>();
		}
		if(!with.containsKey(field)) {
			with.put(field, new ArrayList<Object>());
		}
		for(Object o : keys) {
			if(with != null
					&& with.containsKey(field)
					&& with.get(field).contains(o)) {
				throw new IllogicArgumentException("Illogical inclusion : " + o.toString() + " is in exclued field !");
			}
			with.get(field).add(o);
		}
	}
	
	public void withoutKeyToField(String field, Object... keys) throws IllogicArgumentException {
		if(without == null) {
			without = new HashMap<String, List<Object>>();
		}
		if(!without.containsKey(field)) {
			without.put(field, new ArrayList<Object>());
		}
		for(Object o : keys) {
			if(with != null
					&& with.containsKey(field)
					&& with.get(field).contains(o)) {
				throw new IllogicArgumentException("Illogical exclusion : " + o.toString() + " is in searched field !");
			}
			without.get(field).add(o);
		}
	}

	public Map<String, List<Object>> getWith() {
		return with;
	}

	public Map<String, List<Object>> getWithout() {
		return without;
	}

	public void resetWith() {
		with = null;
	}

	public void resetWithout() {
		without = null;
	}
}
