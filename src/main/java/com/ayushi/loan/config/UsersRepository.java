package com.ayushi.loan.config;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsersRepository extends JpaRepository<Users, String>{


    @Query("SELECT u FROM Users u WHERE UPPER(TRIM(u.userName))=UPPER(TRIM(:username))")
    public Users findByUsername(@Param("username")String username);

}
