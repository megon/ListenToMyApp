package com.listenMyApp.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.listenMyApp.core.domain.Project;
import com.listenMyApp.dto.ProjectDTO;
 
@Service
public class ProjectAssembler {
	
	public ProjectDTO toDTO(Project project) {
		final ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setId(project.getId());
		projectDTO.setName(project.getName());
		projectDTO.setKey(project.getKey());
		projectDTO.setCntOpenedEvents(project.getCntOpenedEvents());
		projectDTO.setCntClosedEvents(project.getCntClosedEvents());
		projectDTO.setCntTotalEvents(project.getCntTotalEvents());
		return projectDTO;
	}

	public List<ProjectDTO> toDTO(List<Project> projects) {
		final List<ProjectDTO> projectsDTO = new ArrayList<ProjectDTO>();
		for (Project project : projects){
			projectsDTO.add(toDTO(project));
		}
		return projectsDTO;
	}

}
