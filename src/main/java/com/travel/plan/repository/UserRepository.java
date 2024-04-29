package com.travel.plan.repository;

import com.travel.plan.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {



}
