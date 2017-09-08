package com.ericsson.cm.manager.datamanagement.dto;

import java.util.List;

import com.ericsson.cm.manager.datamanagement.Ignore;

public class ServerGroupDTO extends VersionedEntity {

	private Long id;
	private String name;
	private List<AbstractServerDTO<?>> serverDTOs;

	public ServerGroupDTO() {}

	public ServerGroupDTO(String name, List<AbstractServerDTO<?>> serverDTOs) {
		super();
		this.name = name;
		this.serverDTOs = serverDTOs;
	}
	
	public Long getId() {
		return id;
	}

	@Ignore
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AbstractServerDTO<?>> getServerDTOs() {
		return serverDTOs;
	}

	public void setServerDTOs(List<AbstractServerDTO<?>> serverDTOs) {
		this.serverDTOs = serverDTOs;
	}
}
