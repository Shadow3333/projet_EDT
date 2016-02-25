package business.dao.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DUBUIS Michael
 *
 */
public class SearchSettings {
	private Map<String, List<Object>> with;
	private Map<String, List<Object>> without;
	
	/**
	 * Empty constructor
	 */
	public SearchSettings() {}
	
	/**
	 * Add all key to found in a field
	 * @param field
	 * @param keys
	 * @throws IllogicArgumentException
	 */
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
	
	/**
	 * Add all key to exclude in a field
	 * @param field
	 * @param keys
	 * @throws IllogicArgumentException
	 */
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

	/**
	 * @return the with
	 */
	public Map<String, List<Object>> getWith() {
		return with;
	}

	/**
	 * @return the without
	 */
	public Map<String, List<Object>> getWithout() {
		return without;
	}
}
