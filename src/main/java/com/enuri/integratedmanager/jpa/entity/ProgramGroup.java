package com.enuri.integratedmanager.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter @Setter
@Table
public class ProgramGroup {

	@Id @GeneratedValue
	@Column(name = "GROUP_ID")
	@Positive private Long id;
	@NotBlank private String groupName;
}
