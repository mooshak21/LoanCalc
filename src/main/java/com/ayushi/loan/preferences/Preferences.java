package com.ayushi.loan.preferences;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import com.ayushi.loan.preferences.Preference;

public class Preferences implements Serializable {
	private List<Preference> preferences = new ArrayList<Preference>();
	
	public Preferences(){
	}
	public void setPreferences(List<Preference> prefs){
		preferences = prefs;
	}
	public List<Preference> getPreferences(){
		return preferences;
	}
	public Preference getPreference(int index){
		return preferences.get(index);
	}
	public void setPreference(Preference pref, int index){
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