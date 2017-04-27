package io.github.services;

import io.github.domain.ScmUser;

public interface ScmUserService {
    Iterable<ScmUser> listAllScmUsers();
    ScmUser getScmUserById(Integer id);
    ScmUser getScmUserByName(String name);
    ScmUser saveScmUser(ScmUser scmUser);
    void deleteAll();
}
