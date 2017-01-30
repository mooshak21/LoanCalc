package com.ayushi.loan.preferences;

import java.io.Serializable;

public class Preference implements Serializable, CheckPreference {
	protected long serializableId = 0L;
	protected Integer id;
	protected String type;
	protected String name;
	protected String value;
	protected String description;
	protected String active;
	protected Boolean flag;

	public Preference(Integer id, String type, String nm, String val, String desc, String actv, Boolean flg){
		this.id = id;
		this.type = type;
		name = nm;
		value = val;
		description = desc;
		active = actv;
		flag = flg;		
	}
	public Preference(){
		this.id = 0;
		type = "default";
		name = "default";
		value = "default";
		description = "description";
		active = "N";
		flag = false;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
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
	public Integer process(){
		return id;
	}

}	
	
