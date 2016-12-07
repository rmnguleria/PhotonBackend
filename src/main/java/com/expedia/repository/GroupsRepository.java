package com.expedia.repository;

import com.expedia.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsRepository extends JpaRepository<Groups,Integer> {

    List<Groups> findByType(String type);
}

