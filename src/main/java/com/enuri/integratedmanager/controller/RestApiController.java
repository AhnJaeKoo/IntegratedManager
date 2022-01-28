package com.enuri.integratedmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.enuri.integratedmanager.core.util.MapUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @description : 관리차원에서 필요한 사항들 모음
 * @Since : 2020. 11. 9
 * @Author : AnJaeKoo
 * @History :
 */

@Slf4j
@RestController
@RequestMapping("rest")
public class RestApiController {

	@Autowired
	private WebClient webClientBrndMkrSynd;

	@GetMapping("/apiCall")
	public Mono<String> apiCall(@RequestParam("baseUrl") String baseUrl,
			@RequestParam("uri") String uri,
			@RequestParam("parameter") String parameter) {

		log.info("baseUrl = {}, uri = {}, parameter = {}", baseUrl, uri, parameter);

		Mono<String> result = webClientBrndMkrSynd.mutate()
	    		.baseUrl(baseUrl)
	    		.build()
	    		.get()
	    		.uri(uriBuilder -> uriBuilder.path(uri)
			    		.queryParams(MapUtil.jsonToMultiValueMap(parameter))
			    		.build())
	    		.retrieve()
	    		.bodyToMono(String.class);

	    return result;
	}
}