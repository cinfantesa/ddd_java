package com.agiletv.shared.infrastructure.persistence;

public interface EntityParser<E, D> {
  E toEntity(D domain);
  D toDomain(E entity);
}
