package com.ayushi.loan.service;

import com.ayushi.loan.dao.PreferenceDao;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.Loan;
import java.util.List;
import java.io.Serializable;
import com.ayushi.loan.preferences.CheckPreference;
import java.util.function.Predicate;
import com.ayushi.loan.preferences.Preference;
import com.ayushi.loan.preferences.Preferences;

public class PreferenceService implements PreferenceAttributeService {
	private PreferenceDao preferenceDao;

	public PreferenceService(PreferenceDao preferenceDao){
		this.preferenceDao = preferenceDao;
	}
	public void setPreferenceDao(PreferenceDao preferenceDao){
		this.preferenceDao = preferenceDao;
	}
	public PreferenceDao getPreferenceDao(){
		return preferenceDao;
	}
	public void createPreference(Preference preference) throws PreferenceAccessException {
		preferenceDao.insert(preference);
	}
	public Preference retrievePreference(Preference preference) throws PreferenceAccessException {
		return (Preference)preferenceDao.find(Preference.class, preference);
	}
	public void modifyPreference(Preference preference) throws PreferenceAccessException {
		preferenceDao.update(preference);
	}
	public void removePreference(Preference preference) throws PreferenceAccessException {
		preferenceDao.remove(preference);
	}
	public List<Serializable> findPreference(String query, Object[] objVals) throws PreferenceAccessException {
		return (List<Serializable>) preferenceDao.find(query, objVals);
	}
	public void addPreferences(Loan loan, List<Integer> prefIds) throws PreferenceAccessException {
		preferenceDao.insert(loan, prefIds);
	}
	public List<Integer> processPreferences(Preferences preferences, Predicate<Preference> tester) throws PreferenceProcessException{
		return Preferences.processPreferencesWithPredicate(preferences.getPreferences(), tester);
	}
}
