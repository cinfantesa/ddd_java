package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.delete_user.DeleteUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeleteUserController.class)
class DeleteUserControllerTest {
  @MockBean
  private CommandBus commandBus;
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  void should_return_200_when_user_is_deleted() throws Exception {
    mockMvc.perform(delete("/api/user/jbond")
      .contentType(APPLICATION_JSON)
    ).andExpect(status().isOk());
  
    DeleteUserCommand expectedCommand = DeleteUserCommand.builder()
      .username("jbond")
      .build();
    verify(commandBus, times(1)).dispatch(expectedCommand);
  }
}