package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.command.CommandBus;
import com.agiletv.user.application.create_user.CreateUserCommand;
import com.agiletv.user.infrastructure.rest.request.CreateUserRequest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreateUserController.class)
class CreateUserControllerTest {
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
  void should_return_400_when_username_is_empty() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder().build();
    
    mockMvc.perform(post("/api/user")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
  
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_400_when_name_is_empty() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder()
      .username("testUserName")
      .build();
  
    mockMvc.perform(post("/api/user")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
  
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_400_when_first_surname_is_empty() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder()
      .username("testUserName")
      .name("test")
      .build();
  
    mockMvc.perform(post("/api/user")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
  
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_400_when_email_is_empty() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder()
      .username("testUserName")
      .name("test")
      .firstSurname("bond")
      .build();
  
    mockMvc.perform(post("/api/user")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
  
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_400_when_email_is_invalid() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder()
      .username("testUserName")
      .name("test")
      .firstSurname("bond")
      .email("notavalidmail@")
      .build();
    
    mockMvc.perform(post("/api/user")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isBadRequest());
    
    verifyNoInteractions(commandBus);
  }
  
  @Test
  void should_return_200_when_user_is_created() throws Exception {
    CreateUserRequest request = CreateUserRequest.builder()
      .username("testUserName")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender("FEMALE")
      .pictureUrl("thisIs_an_url")
      .build();
    
    mockMvc.perform(post("/api/user")
      .contentType(APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(request))
    ).andExpect(status().isCreated());
  
    CreateUserCommand expectedCommand = CreateUserCommand.builder()
      .username("testUserName")
      .name("test")
      .firstSurname("bond")
      .email("test@mail.com")
      .gender("FEMALE")
      .pictureUrl("thisIs_an_url")
      .build();
    verify(commandBus, times(1)).dispatch(expectedCommand);
  }
}