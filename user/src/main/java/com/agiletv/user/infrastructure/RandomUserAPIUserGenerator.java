package com.agiletv.user.infrastructure;

import com.agiletv.user.domain.Gender;
import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserGenerator;
import com.agiletv.user.domain.exception.InvalidQuantity;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Component
@AllArgsConstructor
public class RandomUserAPIUserGenerator implements UserGenerator {
  private final RestTemplate restTemplate;
  
  @Override
  public List<User> generate(int quantity) {
    ensureQuantityIsPositive(quantity);
    
    String url = "https://randomuser.me/api/?inc=gender,name,email,picture,id&results=";
    ApiResponse response = restTemplate.getForObject(url + quantity, ApiResponse.class);
    
    return requireNonNull(response).getResults().stream()
      .map(apiResponse -> {
        String username = apiResponse.getName().getFirst() + apiResponse.getName().getLast();
        Name name = new Name(apiResponse.getName().getFirst(), apiResponse.getName().getLast());
        Gender gender = Gender.from(apiResponse.getGender());
      
        return User.create(username, name, apiResponse.getEmail(), gender, apiResponse.getPicture().getMedium());
      })
      .collect(Collectors.toList());
  }
  
  private void ensureQuantityIsPositive(int quantity) {
    if (quantity < 1) {
      throw new InvalidQuantity();
    }
  }
}

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class ApiResponse {
  private List<UserApiResponse> results;
}

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class UserApiResponse {
  private String gender;
  private NameApiResponse name;
  private String email;
  private PictureApiResponse picture;
}

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class NameApiResponse {
  private String first;
  private String last;
}

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class PictureApiResponse {
  private String medium;
}
