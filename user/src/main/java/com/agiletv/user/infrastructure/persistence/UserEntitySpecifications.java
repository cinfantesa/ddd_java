package com.agiletv.user.infrastructure.persistence;

import org.springframework.data.jpa.domain.Specification;

public class UserEntitySpecifications {
  public static Specification<UserEntity> username(String username) {
    return (Specification<UserEntity>)
      (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
        .equal(root.get("username"), username);
  }
}
