package com.agiletv.user.infrastructure;

import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.exception.InvalidQuantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.agiletv.user.domain.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RandomUserAPIUserGeneratorTest {
  @Mock
  private RestTemplate restTemplate;
  
  private RandomUserAPIUserGenerator apiUserGenerator;
  
  @BeforeEach
  void setUp() {
    apiUserGenerator = new RandomUserAPIUserGenerator(restTemplate);
  }
  
  @Test
  void should_throw_exception_when_quantity_is_below_one() {
    assertThrows(InvalidQuantity.class, () -> apiUserGenerator.generate(0));
  }
  
  @Test
  void should_return_generated_users() {
    ApiResponse response = ApiResponse.builder()
      .results(List.of(UserApiResponse.builder()
        .gender("male")
        .name(NameApiResponse.builder()
          .first("James")
          .last("Bond")
          .build())
        .picture(PictureApiResponse.builder()
          .medium("url")
          .build())
        .email("james@bond.com")
        .build()
      ))
      .build();
    when(restTemplate.getForObject("https://randomuser.me/api/?inc=gender,name,email,picture,id&results=1", ApiResponse.class)).thenReturn(response);
    
    List<User> actualUsers = apiUserGenerator.generate(1);
  
    verify(restTemplate, times(1)).getForObject("https://randomuser.me/api/?inc=gender,name,email,picture,id&results=1", ApiResponse.class);
    verifyNoMoreInteractions(restTemplate);
    
    List<User> expectedUsers = List.of(User.create("JamesBond", new Name("James", "Bond"), "james@bond.com", MALE, "url"));
    assertThat(actualUsers).isEqualTo(expectedUsers);
  }
}