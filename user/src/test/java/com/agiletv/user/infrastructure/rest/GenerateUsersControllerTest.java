package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.generate_users.GenerateUsersCommand;
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

@WebMvcTest(GenerateUsersController.class)
class GenerateUsersControllerTest {
  @MockBean
  private CommandBus commandBus;
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void should_return_200_when_generating_users() throws Exception {
    mockMvc.perform(get("/api/user/generate/2")
      .contentType(APPLICATION_JSON)
    ).andExpect(status().isOk());
  
    GenerateUsersCommand command = GenerateUsersCommand.builder()
      .quantity(2)
      .build();
    verify(commandBus, times(1)).dispatch(command);
  }
}