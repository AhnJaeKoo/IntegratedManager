package com.enuri.integratedmanager.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(uniqueConstraints= {
		@UniqueConstraint(columnNames= {"ip", "port"})
})
public class ServerInfo {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "SERVER_ID") private Long id;
	@Column(unique = true) @NotBlank private String serverName;
	@NotBlank private String ip;
	@Positive private int port;
	@NotBlank private String user;
	@NotBlank private String password;
}
