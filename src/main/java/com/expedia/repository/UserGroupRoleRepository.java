package com.expedia.repository;

import com.expedia.domain.UserGroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRoleRepository extends JpaRepository<UserGroupRole,Integer> {

    Optional<List<UserGroupRole>> findByUserId(Integer userId);

}
