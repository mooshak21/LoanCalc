package com.ayushi.loan.preferences;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import com.ayushi.loan.preferences.CheckPreference;

public class Preferences implements Serializable {
	private List<CheckPreference> preferences = new ArrayList<CheckPreference>();
	
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
	public static List<Integer> processPreferencesWithPredicate(List<Preference> prefs, Predicate<Preference> tester) {
		List<Integer> prefIds = new ArrayList<Integer>(prefs.size());
		for (Preference p : prefs) {
	        	if (tester.test(p)) {
	        	    prefIds.add(p.process());
		        }
	    	}
		
		return prefIds;
	}	
}