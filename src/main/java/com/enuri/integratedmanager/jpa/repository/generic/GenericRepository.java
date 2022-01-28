package com.enuri.integratedmanager.jpa.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
  * @description : JpaGenericController에서 공통사용될 메소드 이곳에 추가
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */
@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long> {

	@Transactional
	public Long removeById(Long id);
}
