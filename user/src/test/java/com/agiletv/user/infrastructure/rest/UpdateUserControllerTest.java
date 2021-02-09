package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.update_user.UpdateUserCommand;
import com.agiletv.user.infrastructure.rest.request.UpdateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UpdateUserController.class)
class UpdateUserControllerTest {
  @MockBean
  private CommandBus commandBus;
  
  @Autowired
  private MockMvc mockMvc;
  
  private ObjectMapper objectMapper;
  
  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(NON_NULL);
  }
  
  @Test
  void should_return_400_when_name_is_empty() throws Exception {
    mockMvc.perform(put("/api/user/jbond")
      .contentType(APPLICATION_JSON)
    ).andExpect(status().isBadRequest());
  
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_400_when_first_surname_is_empty() throws Exception {
    UpdateUserRequest request = UpdateUserRequest.builder()
      .name("test")
      .build();
  
    mockMvc.perform(put("/api/user/jbond")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
  
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_400_when_email_is_empty() throws Exception {
    UpdateUserRequest request = UpdateUserRequest.builder()
      .name("test")
      .firstSurname("bond")
      .build();
  
    mockMvc.perform(put("/api/user/jbond")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
  
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_400_when_email_is_invalid() throws Exception {
    UpdateUserRequest request = UpdateUserRequest.builder()
      .name("test")
      .firstSurname("bond")
      .email("notavalidmail@")
      .build();
    
    mockMvc.perform(put("/api/user/jbond")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
    
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_200_when_user_is_updated() throws Exception {
    UpdateUserRequest request = UpdateUserRequest.builder()
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender("FEMALE")
      .pictureUrl("thisIs_an_url")
      .build();
    
    mockMvc.perform(put("/api/user/jbond")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isNoContent());
  
    UpdateUserCommand expectedCommand = UpdateUserCommand.builder()
      .username("jbond")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender("FEMALE")
      .pictureUrl("thisIs_an_url")
      .build();
    verify(commandBus, times(1)).dispatch(expectedCommand);
  }
}