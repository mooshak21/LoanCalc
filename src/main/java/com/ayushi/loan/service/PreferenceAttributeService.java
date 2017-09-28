package com.ayushi.loan.service;

import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.exception.PreferenceProcessException;
import com.ayushi.loan.Loan;
import java.util.List;
import java.io.Serializable;
import com.ayushi.loan.preferences.CheckPreference;
import java.util.function.Predicate;
import com.ayushi.loan.preferences.Preference;
import com.ayushi.loan.preferences.Preferences;

public interface PreferenceAttributeService {
	public void createPreference(Preference preference) throws PreferenceAccessException;
	public Preference retrievePreference(Preference preference) throws PreferenceAccessException;
	public void modifyPreference(Preference preference) throws PreferenceAccessException;
	public void removePreference(Preference preference) throws PreferenceAccessException;
	public List<Preference> findPreference(String query, Object[] objVals) throws PreferenceAccessException;
        public Preference findOnePreference(Integer id, String email) throws PreferenceAccessException;
	public void addPreferences(Loan loan, List<Integer> prefIds) throws PreferenceAccessException;
        public List<Preference> processPreferences(Preferences prefs, Predicate<Preference> tester) throws PreferenceProcessException;
//	public List<Integer> processPreferences(Preferences prefs, Predicate<Preference> tester) throws PreferenceProcessException;
}