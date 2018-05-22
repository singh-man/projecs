package tr;

// Generated May 24, 2012 11:06:45 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * MmServer generated by hbm2java
 */
public class MmServer implements java.io.Serializable {

	private int id;
	private String name;
	private String desc;
	private Set mmProvisionservers = new HashSet(0);

	public MmServer() {
	}

	public MmServer(int id) {
		this.id = id;
	}

	public MmServer(int id, String name, String desc, Set mmProvisionservers) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.mmProvisionservers = mmProvisionservers;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set getMmProvisionservers() {
		return this.mmProvisionservers;
	}

	public void setMmProvisionservers(Set mmProvisionservers) {
		this.mmProvisionservers = mmProvisionservers;
	}

}