package pl.kflorczyk.lrucache.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.kflorczyk.lrucache.controllers.LRUCacheController;
import pl.kflorczyk.lrucache.dto.GetValueResponse;
import pl.kflorczyk.lrucache.services.LRUCacheService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LRUCacheController.class)
public class LRUCacheControllerTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	LRUCacheService lruCacheService;

	@Rule
    public ExpectedException exception = ExpectedException.none();

	@Before
	public void init() {
		objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
	}

	@Test(expected = Exception.class)
	public void capacityCallWithIncorrectParameterShouldReturnInternalServerError() throws Exception {
		doThrow(IllegalArgumentException.class).when(lruCacheService).changeCapactity(1);

		mockMvc.perform(post("/capacity/1"))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void capacityCallWithCorrectValueShouldReturnOk() throws Exception {
		doNothing().when(lruCacheService).changeCapactity(5);

		mockMvc.perform(post("/capacity/5"))
				.andExpect(status().isOk());
	}

	@Test
	public void getCallWhenKeyIsNotPresentShouldReturnAppropriateMessage() throws Exception {
		when(lruCacheService.get("keyNotPresent")).thenReturn(null);

		String expectedJson = objectMapper.writeValueAsString(new GetValueResponse(false, null));

		mockMvc.perform(get("/keyNotPresent"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}

	@Test
	public void getCallWhenKeyIsPresentShouldReturnAppropriateMessage() throws Exception {
		when(lruCacheService.get("keyPresent")).thenReturn("valuePresent");

		String expectedJson = objectMapper.writeValueAsString(new GetValueResponse(true, "valuePresent"));

		mockMvc.perform(get("/keyPresent"))
				.andExpect(status().isOk())
				.andExpect(content().json(expectedJson));
	}
}
