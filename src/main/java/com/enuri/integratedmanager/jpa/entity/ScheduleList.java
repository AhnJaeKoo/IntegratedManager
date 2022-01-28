package com.enuri.integratedmanager.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
		@UniqueConstraint(columnNames= {"serverId", "cron"})
})
public class ScheduleList {

	@Id @GeneratedValue private Long id;
	@Positive private Long groupId;
	@Positive private Long serverId;
	private String runCmd;
	private String baseUrl;
	private String uri;
	private String parameter;
	@NotBlank private String cron;
	@NotBlank private String useFlag;
	@NotBlank private String remark;	//비고 : 사람이 알아볼기 쉽게 스케줄 요약내용 기입

	@ManyToOne
	@JoinColumn(name = "groupId", insertable = false, updatable = false)
	private ProgramGroup groupInfo;
}
