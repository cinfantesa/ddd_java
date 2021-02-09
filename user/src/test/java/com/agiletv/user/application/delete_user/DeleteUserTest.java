package com.agiletv.user.application.delete_user;

import com.agiletv.shared.domain.bus.event.EventBus;
import com.agiletv.user.domain.UserRepository;
import com.agiletv.user.domain.exception.UserNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private EventBus eventBus;
  
  private DeleteUser deleteUser;
  
  @BeforeEach
  void setUp() {
    deleteUser = new DeleteUser(userRepository, eventBus);
  }
  
  @Test
  void should_throw_exception_when_user_not_exists() {
    when(userRepository.exists("jbond")).thenReturn(false);
    
    assertThrows(UserNotFound.class, () -> deleteUser.delete("jbond"));
    verify(userRepository, times(1)).exists("jbond");
    verifyNoMoreInteractions(userRepository);
  }
  
  @Test
  void should_delete_existing_user() {
    when(userRepository.exists("jbond")).thenReturn(true);
    
    deleteUser.delete("jbond");
  
    verify(userRepository, times(1)).exists("jbond");
    verify(userRepository, times(1)).delete("jbond");
    verifyNoMoreInteractions(userRepository);
  }
}