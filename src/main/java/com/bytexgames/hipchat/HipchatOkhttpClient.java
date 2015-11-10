package com.bytexgames.hipchat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;

import java.io.IOException;

/**
 * Hipchat REST API (v2) client, built on top of Jackson and okhttp
 * <p>Description: </p>
 * Date: 11/10/15 - 3:29 PM
 *
 * @author Ruslan Balkin <a href="mailto:baron@baron.su">baron@baron.su</a>
 * @version 1.0.0.0
 */
public class HipchatOkhttpClient {
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private final ObjectMapper objectMapper = new ObjectMapper();

	private String domain;

	private String authToken;

	public HipchatOkhttpClient() {
	}

	public HipchatOkhttpClient(String domain, String authToken) {
		this.domain = domain;
		this.authToken = authToken;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * Send message to room
	 *
	 * @param room    room id
	 * @param message message
	 * @return true, if everything was ok
	 */
	public boolean message(String room, Message message) {
		try {
			final RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(message));
			final String url = String.format("https://%s/v2/room/%s/notification?auth_token=%s", domain, room, authToken);
			final OkHttpClient client = new OkHttpClient();
			final Request request = new Request.Builder().url(url).post(body).build();
			final Response response = client.newCall(request).execute();
			return true;
		} catch (JsonProcessingException ignored) {
		} catch (IOException ignored) {
		}
		return false;
	}
}
