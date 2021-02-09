package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.query.QueryBus;
import com.agiletv.user.application.find_all.FindAllUsersQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FindAllUsersController.class)
class FindAllUsersControllerTest {
  @MockBean
  private QueryBus queryBus;
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void should_return_200_with_all_existing_users() throws Exception {
    mockMvc.perform(get("/api/user")
      .contentType(APPLICATION_JSON)
    ).andExpect(status().isOk());
  
    FindAllUsersQuery query = FindAllUsersQuery.builder()
      .page(0)
      .size(20)
      .build();
    verify(queryBus, times(1)).ask(query);
  }
}