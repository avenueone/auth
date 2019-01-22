package com.avenueone.identity.repository;

import com.avenueone.identity.model.AvoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AvoUser, Long> {

    public AvoUser findByUsername(String username) ;
}
