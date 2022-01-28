package com.enuri.integratedmanager.controller.rest.generic;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enuri.integratedmanager.jpa.repository.generic.GenericRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : 기본적인 CRUD 처리 부모 클래스. 중복코드 방지위해
  * @Since : 2020. 11. 14
  * @Author : AnJaeKoo
  * @History :
  */
@Slf4j
public abstract class JpaGenericController<R extends GenericRepository<T>, T> {

	@Autowired
	private R repository;

	Class<?> clazz = null;

	@PostConstruct
	private void init() {
		getGenericParameterClass();
	}

	private Class<?> getGenericParameterClass() {
		try {
			String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			log.error("", e);
		}

		return clazz;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONObject> getAll() {

		JSONObject jsonObject = null;
		JSONParser jsonParser = new JSONParser();
		JSONArray jarry = new JSONArray();
		HttpStatus status = HttpStatus.OK;
		var map = new HashMap<String, JSONArray>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			List<T> list = repository.findAll();
			jarry = (JSONArray) jsonParser.parse(mapper.writeValueAsString(list));
			map.put(clazz.getSimpleName(), jarry);
			jsonObject = new JSONObject(map);
		} catch (Exception e) {
			log.error("", e);
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<JSONObject>(jsonObject, status);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JSONObject> getId(@PathVariable("id") Long id) {

		JSONObject jsonObject = null;
		JSONObject json = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		HttpStatus status = HttpStatus.OK;
		var map = new HashMap<String, JSONObject>();

		try {
			ObjectMapper mapper = new ObjectMapper();
			Optional<T> search = repository.findById(id);
			json = (JSONObject) jsonParser.parse(mapper.writeValueAsString(search.get()));
			map.put(clazz.getSimpleName(), json);
			jsonObject = new JSONObject(map);
		} catch (Exception e) {
			log.error("", e);
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<JSONObject>(jsonObject, status);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		String result = "Delete 0";
		HttpStatus status = HttpStatus.OK;

		try {
			if (repository.removeById(id) > 0) {
				result = "Delete Success";
			}
		} catch (Exception e) {
			result = "Delete fail";
			log.error("", e);
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<String>(result, status);
	}

	//@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@SuppressWarnings("unchecked")
	//@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.PUT, RequestMethod.POST})
	public ResponseEntity<String> upsert(@RequestBody String jsonStrting) {
		ObjectMapper objectMapper = new ObjectMapper();
		String result = "Success";
		List<T> entityList = new ArrayList<>();
		HttpStatus status = HttpStatus.OK;

		try {
			JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStrting);
			JSONArray jsonArray = (JSONArray) jsonObject.get(clazz.getSimpleName());

			for (int i = 0 ; i < jsonArray.size() ; i++) {
				JSONObject json = (JSONObject) jsonArray.get(i);
				T entity = (T) objectMapper.readValue(json.toString(), clazz);
				//T entity = objectMapper.readValue(json.toString(), new TypeReference<T>(){});	//이거 실제 처리에서는 에러난다.
				entityList.add(entity);
			}
			repository.saveAll(entityList);

		} catch (ParseException | JsonProcessingException e) {
			result = "fail";
			log.error("", e);
			status = HttpStatus.BAD_REQUEST;
		}

		return new ResponseEntity<String>(result, status);
	}
}
