/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 

package com.csc.gdn.integralpos.api.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class ApplicationTests extends BaseTest {
	private static final String PATH = "/attachments";

	@Test
	public void createEntity() throws Exception {

		HttpHeaders httpHeaders = this.getAccessToken();

		// Init Model
		MvcResult mvcResult = mockMvc.perform(get(PATH + "/model").headers(httpHeaders).content(document))
				.andExpect(status().isOk()).andReturn();

		// Create Empty model & save to DB
		mvcResult = mockMvc
				.perform(post(PATH).headers(httpHeaders).contentType(MediaType.APPLICATION_JSON_UTF8).content(document))
				.andExpect(status().isCreated()).andReturn();

		String uid = this.getIDFromMVCResult(mvcResult);

		// Find by ID
		mockMvc.perform(get(PATH + "/" + uid + "?operation=").headers(httpHeaders)).andExpect(status().isOk())
				.andReturn();

		 Delete 
		mockMvc.perform(delete(PATH + "/" + uid).headers(httpHeaders)).andExpect(status().isNoContent()).andReturn();

		// Find again to make sure it have not existed
		mockMvc.perform(get(PATH + "/" + uid + "?operation=").headers(httpHeaders)).andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	public void partiallyUpdateEntity() throws Exception {
		
		// Get token
		HttpHeaders httpHeaders = this.getAccessToken();

		// Save New Document
		MvcResult mvcResult = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON_UTF8).headers(httpHeaders).content(document))
				.andExpect(status().isCreated()).andReturn();

		// Get uid
		String uid = this.getIDFromMVCResult(mvcResult);

		// Update this document
		mockMvc.perform(patch(PATH + "/" + uid).contentType(MediaType.APPLICATION_JSON_UTF8).headers(httpHeaders)
				.content(partialDocument)).andExpect(status().isOk());
		
		// Delete by id
		mockMvc.perform(delete(PATH + "/" + uid).headers(httpHeaders)).andExpect(status().isNoContent()).andReturn();

		// Find by this id again
		mockMvc.perform(get(PATH + "/" + uid + "?operation=").headers(httpHeaders)).andExpect(status().isNotFound())
				.andReturn();

	}

	@Test
	public void deleteEntity() throws Exception {

		// Get token
		HttpHeaders httpHeaders = this.getAccessToken();

		// Save New Document
		MvcResult mvcResult = mockMvc
				.perform(post(PATH).contentType(MediaType.APPLICATION_JSON_UTF8).headers(httpHeaders).content(document))
				.andExpect(status().isCreated()).andReturn();

		// Get uid
		String uid = this.getIDFromMVCResult(mvcResult);

		// Delete by id
		mockMvc.perform(delete(PATH + "/" + uid).headers(httpHeaders)).andExpect(status().isNoContent()).andReturn();

		// Find by this id again
		mockMvc.perform(get(PATH + "/" + uid + "?operation=").headers(httpHeaders)).andExpect(status().isNotFound())
				.andReturn();
	}

}*/