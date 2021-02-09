package com.agiletv.user.infrastructure.persistence;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Entity
@Builder
@ToString
@DynamicUpdate
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(
  name = "USER",
  indexes = {
    @Index(name = "byUsername", unique = true, columnList = "USERNAME")
  })
public class UserEntity {
  @Id
  @Column(name = "USERNAME")
  private String username;
  
  @Column(name = "NAME", nullable = false)
  private String name;
  @Column(name = "FIRST_SURNAME", nullable = false)
  private String firstSurname;
  
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "GENDER", length = 10)
  private String gender;
  @Column(name = "PICTURE_URL", length = 300)
  private String pictureUrl;
}
