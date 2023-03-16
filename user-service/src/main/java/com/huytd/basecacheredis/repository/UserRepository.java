package com.huytd.basecacheredis.repository;

import com.huytd.basecacheredis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
