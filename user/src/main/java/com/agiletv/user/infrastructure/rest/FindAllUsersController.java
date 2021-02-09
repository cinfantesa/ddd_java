package com.agiletv.user.infrastructure.rest;

import com.agiletv.shared.domain.bus.query.QueryBus;
import com.agiletv.shared.domain.bus.query.Response;
import com.agiletv.user.application.find_all.FindAllUsersQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@AllArgsConstructor
public class FindAllUsersController {
  private final QueryBus queryBus;
  
  @GetMapping(path = "/api/user")
  public ResponseEntity<Response> findAllUser(@NotNull Pageable page) {
    FindAllUsersQuery query = FindAllUsersQuery.builder()
      .page(page.getPageNumber())
      .size(page.getPageSize())
      .build();
  
    return ok(queryBus.ask(query));
  }
}
