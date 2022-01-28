package com.enuri.integratedmanager.quartz.job.impl;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : ApiCall 방식의 job
  * @Since : 2021. 07. 27
  * @Author : AnJaeKoo
  * @History :
  */

@Slf4j
public class ApiCallJobExecutor implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 설정정보 인식
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String remark = context.getJobDetail().getDescription();
		String jobKey = jobDataMap.getString("jobKey");
		String baseUrl = jobDataMap.getString("baseUrl").trim();
		String uri = jobDataMap.getString("uri").trim();
        String parameter = convertUrlEncoding(jobDataMap.getString("parameter").trim());

        log.info("job : {}({}) - Running...", jobKey, remark);
        apiCallAsync(baseUrl, uri, parameter);
	}

	// 비동기 방식으로 호출
	private void apiCallAsync(String baseUrl, String uri, String parameter) {

		String reqUri = "http://localhost:8082/rest/apiCall"
				+ "?baseUrl=" + baseUrl
				+ "&uri=" + uri
				+ "&parameter=" + parameter;

		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(reqUri))
	    		.GET()
	            .setHeader("User-Agent", "ApiCallJobExecutor-apiCallAsync")
	            .build();

		client.sendAsync(request, BodyHandlers.ofString()) //응답 문자형태
			        .thenApply(HttpResponse::body)	//결과 리턴값
			        .thenAccept(log::info);	//로그로 찍어준다.
	}

	// Url Encoding
	private String convertUrlEncoding(String text) {
		String result = "";

		try {
			result = URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("", e);
		}

		return result;
	}
}