package com.ayushi.loan.service;

import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import java.io.Serializable;
import com.ayushi.loan.preferences.CheckPreference;
import java.util.function.Predicate;
import com.ayushi.loan.preferences.Preference;

public interface PreferenceAttributeService {
	public void createPreference(Preference preference) throws PreferenceAccessException;
	public Preference retrievePreference(Preference preference) throws PreferenceAccessException;
	public void modifyPreference(Preference preference) throws PreferenceAccessException;
	public void removePreference(Preference preference) throws PreferenceAccessException;
	public List<Serializable> findPreference(String query, Object[] objVals) throws PreferenceAccessException;
	public void addPreferences(Loan loan, List<Integer> prefIds) throws PreferenceAccessException;
	public List<Integer> processPreferences(List<CheckPreference> prefs, Predicate<CheckPreference> tester);
}