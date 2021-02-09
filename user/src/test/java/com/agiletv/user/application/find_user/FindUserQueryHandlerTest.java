package com.agiletv.user.application.find_user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindUserQueryHandlerTest {
  @Mock
  private FindUser findUser;
  
  private FindUserQueryHandler queryHandler;
  
  @BeforeEach
  void setUp() {
    queryHandler = new FindUserQueryHandler(findUser);
  }
  
  @Test
  void should_call_application_service() {
    FindUserQuery query = FindUserQuery.builder()
      .username("jbond")
      .build();
    
    queryHandler.handle(query);
  
    verify(findUser, times(1)).find("jbond");
    verifyNoMoreInteractions(findUser);
  }
}