package com.ayushi.loan.preferences;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Preferences<CheckPreference> implements Serializable {
	private List<CheckPreference> preferences = new ArrayList<CheckPrefence>();
	
	public Preferences(){
	}
	public void setPreferences(List<CheckPreference> prefs){
		preferences = prefs;
	}
	public List<CheckPreference> getPreferences(){
		return preferences;
	}
	public CheckPreference getPreference(int index){
		return preferences.get(index);
	}
	public void setPreference(CheckPreference pref, int index){
		preferences.add(index, pref);
	}
	public static List<Integer> processPreferencesWithPredicate(List<CheckPreference> prefs, Predicate<CheckPreference> tester) {
		List<Integer> prefIds = new ArrayList<Integer>(prefs.size());
		for (CheckPreference p : prefs) {
	        	if (tester.test(p)) {
	        	    prefIds.add(p.process());
		        }
	    	}
		
		return prefIds;
	}	
}