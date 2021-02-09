package com.agiletv.user.infrastructure.persistence;

import com.agiletv.user.domain.User;
import com.agiletv.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.agiletv.user.infrastructure.persistence.UserEntitySpecifications.username;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specification.where;

@Component
@AllArgsConstructor
public class H2UserRepository implements UserRepository {
  private final JpaUserRepository jpaUserRepository;
  private final UserEntityParser entityParser;
  
  @Override
  public void save(User user) {
    jpaUserRepository.save(entityParser.toEntity(user));
  }
  
  @Override
  public void saveAll(List<User> users) {
    List<UserEntity> entities = users.stream()
      .map(entityParser::toEntity)
      .collect(toList());
    
    jpaUserRepository.saveAll(entities);
  }
  
  @Override
  public List<User> findAll(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return jpaUserRepository.findAll(pageRequest)
      .map(entityParser::toDomain).getContent();
  }
  
  @Override
  public Optional<User> findById(String username) {
    Optional<UserEntity> found = jpaUserRepository.findOne(where(
      username(username)
    ));
    return found.map(entityParser::toDomain);
  }
  
  @Override
  public boolean exists(String username) {
    long foundEntities = jpaUserRepository.count(
      where(username(username))
    );
    return foundEntities == 1;
  }
  
  @Override
  public void delete(String username) {
    jpaUserRepository.deleteById(username);
  }
}
