package io.github.repositories;

import org.springframework.data.repository.CrudRepository;
import io.github.domain.ScmUser;

public interface ScmUserRepository extends CrudRepository<ScmUser, Integer>{
	public ScmUser findByUsername(String username);
	public ScmUser findById(Integer id);
}
