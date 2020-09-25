package com.ayushi.loan.preferences;

public class SaltPreference extends Preference {

  public static final String SALT_PREFERENCE_TYPE = "SaltPreference";

  public SaltPreference() {
    super();
    id = 7;
    type = SALT_PREFERENCE_TYPE;
    description = SALT_PREFERENCE_TYPE;
    name = SALT_PREFERENCE_TYPE;
  }

}
