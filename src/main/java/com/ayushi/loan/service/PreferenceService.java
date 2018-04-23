package com.ayushi.loan.service;

import com.ayushi.loan.dao.PreferenceDao;
import com.ayushi.loan.exception.PreferenceAccessException;
import com.ayushi.loan.exception.PreferenceProcessException;
import com.ayushi.loan.Loan;
import java.util.List;
import java.io.Serializable;
import com.ayushi.loan.preferences.CheckPreference;
import java.util.function.Predicate;
import com.ayushi.loan.preferences.Preference;
import com.ayushi.loan.preferences.Preferences;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


public class PreferenceService implements PreferenceAttributeService {
	private PreferenceDao preferenceDao;

	public PreferenceService(PreferenceDao preferenceDao){
		this.preferenceDao = preferenceDao;
	}

	public void setPreferenceDao(PreferenceDao preferenceDao){
		this.preferenceDao = preferenceDao;
	}

	public PreferenceDao getPreferenceDao(){
		return this.preferenceDao;
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

	public List<Preference> findPreference(String query, Object[] objVals) throws PreferenceAccessException {
		return (List<Preference>) preferenceDao.find(query, objVals);
	}

        public Preference findOnePreference(Integer id, String email) throws PreferenceAccessException {
            return (Preference) preferenceDao.find("select p from pref p where p.pref_emailAddress = ? and p.pref_id = ?", new Object[]{email,id}).get(0);
        }

	public void addPreferences(Loan loan, List<Integer> prefIds) throws PreferenceAccessException {
		preferenceDao.insert(loan, prefIds);
	}

        public List<Preference> processPreferences(Preferences preferences, Predicate<Preference> tester) throws PreferenceProcessException{
		return Preferences.processPreferencesWithPredicate(preferences.getPreferences(), tester);
	}
//	public List<Integer> processPreferences(Preferences preferences, Predicate<Preference> tester) throws PreferenceProcessException{
//		return Preferences.processPreferencesWithPredicate(preferences.getPreferences(), tester);
//	}
}
