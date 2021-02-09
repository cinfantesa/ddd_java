package com.agiletv.user.infrastructure.persistence;

import com.agiletv.shared.infrastructure.persistence.EntityParser;
import com.agiletv.user.domain.Gender;
import com.agiletv.user.domain.Name;
import com.agiletv.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserEntityParser implements EntityParser<UserEntity, User> {
  
  @Override
  public UserEntity toEntity(User domain) {
    return UserEntity.builder()
      .username(domain.getUsername())
      .name(domain.getName().getName())
      .firstSurname(domain.getName().getFirstSurname())
      .email(domain.getEmail())
      .gender(domain.getGender().name())
      .pictureUrl(domain.getPictureUrl())
      .build();
  }
  
  @Override
  public User toDomain(UserEntity entity) {
    Name name = new Name(entity.getName(), entity.getFirstSurname());
    Gender gender = Gender.valueOf(entity.getGender());
    
    return new User(
      entity.getUsername(),
      name,
      entity.getEmail(),
      gender,
      entity.getPictureUrl()
    );
  }
}
