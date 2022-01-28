package com.enuri.integratedmanager.core.util;

import java.util.Map;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MapUtil {

	public boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public MultiValueMap<String, String> jsonToMultiValueMap(String param) {
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    	ObjectMapper mapper = new ObjectMapper();

		if (param.length() < 1)
			return params;

        try {
            Map<String, String> map = mapper.readValue(param, Map.class);
			/*TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>(){};
			HashMap<String, String> map = mapper.convertValue(param, typeRef);*/
            params.setAll(map);
        } catch (Exception e) {
            log.error("Parameter 변환중 오류가 발생했습니다. Parameter = {}", param, e);
        }

		return params;
    }
}
