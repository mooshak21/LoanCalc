package com.ayushi.loan.preferences;

import java.io.Serializable;
import com.ayushi.loan.preferences.CheckPreference;
import java.util.Objects;

public class Preference implements Serializable, CheckPreference {
	protected long serializableId = 0L;
	protected Integer id;
        protected String emailAddress;
	protected String type;
	protected String name;
	protected String value;
	protected String description;
	protected String active;
	protected Boolean flag;

	public Preference(Integer id, String emailAddress, String type, String nm, String val, String desc, String actv, Boolean flg){
		this.id = id;
		this.type = type;
                this.emailAddress = emailAddress;
		name = nm;
		value = val;
		description = desc;
		active = actv;
		flag = flg;		
	}
	
        public Preference(){
		this.id = 0;
                emailAddress= "";
		type = "default";
		name = "default";
		value = "default";
		description = "description";
		active = "N";
		flag = false;
	}

        public long getSerializableId() {
            return serializableId;
        }

        public void setSerializableId(long serializableId) {
            this.serializableId = serializableId;
        }
        
        public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
	}

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
        
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setValue(String val){
		this.value = val;
	}
	public String getValue(){
		return value;
	}
	public void setDescription(String desc){
		this.description = desc;
	}
	public String getDescription(){
		return description;
	}
	public void setActive(String actv){
		active = actv;
	}
	public String getActive(){
		return active;
	}
	public void setFlag(Boolean flg){
		flag = flg;
	}
	public Boolean getFlag(){
		return flag;
	}
        @Override
	public Integer process(){
		return id;
	}

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.id);
            hash = 17 * hash + Objects.hashCode(this.emailAddress);
            hash = 17 * hash + Objects.hashCode(this.value);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Preference other = (Preference) obj;
            if (!Objects.equals(this.emailAddress, other.emailAddress)) {
                return false;
            }
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            return true;
        }

        
        
}	
	
