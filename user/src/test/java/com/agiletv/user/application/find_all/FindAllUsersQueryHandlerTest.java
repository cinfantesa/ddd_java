package com.agiletv.user.application.find_all;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindAllUsersQueryHandlerTest {
  @Mock
  private FindAllUsers findAllUsers;
  
  private FindAllUsersQueryHandler queryHandler;
  
  @BeforeEach
  void setUp() {
    queryHandler = new FindAllUsersQueryHandler(findAllUsers);
  }
  
  @Test
  void should_call_application_service() {
    FindAllUsersQuery query = FindAllUsersQuery.builder()
      .page(0)
      .size(5)
      .build();
    
    queryHandler.handle(query);
  
    verify(findAllUsers, times(1)).find(0, 5);
    verifyNoMoreInteractions(findAllUsers);
  }
}