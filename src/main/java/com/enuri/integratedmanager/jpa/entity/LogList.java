package com.enuri.integratedmanager.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
//@Entity(name = "LOG_LIST") // @Entity가 붙은 클래스는 JPA가 관리하는 클래스이고, 테이블과 매핑할 테이블은 해당 어노테이션을 붙인다.
@Entity
@Builder
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "schduleId", "backupPath", "fileExtension", "serverId" }) })
public class LogList {
	// @Id PK컬럼이다.
	// @GeneratedValue(strategy = GenerationType.IDENTITY) => AUTO_INCREMENT 일때
	// GenerationType.IDENTITY는 기본 키 생성을 데이터베이스에 위임하는 방식

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue
	private Long id;

	@PositiveOrZero
	private int schduleId; // 어떤 스케줄과 연관된건지..연관없으면 0

	@Min(1)
	private Long serverId;

	@NotBlank
	private String backupPath;

	@NotBlank
	private String fileExtension;

	@Min(1)
	private int delCycle;

	@NotBlank
	private String dayCut; // Y/N
	private String cutLogPath;
	private String cutLogFile;

	@Column(columnDefinition = "varchar(1) default 'N'")
	private String directoryDel;
}
