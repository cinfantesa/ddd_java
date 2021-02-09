package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.query.QueryBus;
import com.agiletv.user.application.find_user.FindUserQuery;
import com.agiletv.user.domain.exception.UserNotFound;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FindUserController.class)
class FindUserControllerTest {
  @MockBean
  private QueryBus queryBus;
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void should_return_404_when_user_not_found() throws Exception {
    FindUserQuery query = FindUserQuery.builder()
      .username("jbond")
      .build();
    when(queryBus.ask(query)).thenThrow(new UserNotFound());
    
    mockMvc.perform(get("/api/user/jbond")
      .contentType(APPLICATION_JSON)
    ).andExpect(status().isNotFound());
  }
  
  @Test
  void should_return_200_when_user_exists() throws Exception {
    mockMvc.perform(get("/api/user/jbond")
      .contentType(APPLICATION_JSON)
    ).andExpect(status().isOk());
  
    FindUserQuery expectedQuery = FindUserQuery.builder()
      .username("jbond")
      .build();
    verify(queryBus, times(1)).ask(expectedQuery);
  }
}