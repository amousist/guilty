package io.github.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.domain.ScmUser;
import io.github.repositories.ScmUserRepository;

@Service
public class ScmUserServiceImpl implements ScmUserService {
	private ScmUserRepository scmUserRepository;

	@Autowired
	public void setScmUserRepository(ScmUserRepository scmUserRepository) {
		this.scmUserRepository = scmUserRepository;
	}

	@Override
	public Iterable<ScmUser> listAllScmUsers() {
		return scmUserRepository.findAll();
	}

	@Override
	public ScmUser getScmUserById(Integer id) {
		return scmUserRepository.findById(id);
	}

	@Override
	public ScmUser getScmUserByName(String name) {
		return scmUserRepository.findByUsername(name);
	}

	@Override
	public ScmUser saveScmUser(ScmUser scmUser) {
		return scmUserRepository.save(scmUser);
	}

	@Override
	public void deleteAll() {
		scmUserRepository.deleteAll();
	}
}
