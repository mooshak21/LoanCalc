/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ayushi.loan.preferences;

/**
 *
 * @author Gagan Jain 
 */
public class PasswordPreference extends Preference{

    public static final String PASSWORD_PREFERENCE_TYPE = "PasswordPreference";

    public PasswordPreference() {
        super();
         type = PASSWORD_PREFERENCE_TYPE;
        name = PASSWORD_PREFERENCE_TYPE;
        description = PASSWORD_PREFERENCE_TYPE;
        
    }
    
}
