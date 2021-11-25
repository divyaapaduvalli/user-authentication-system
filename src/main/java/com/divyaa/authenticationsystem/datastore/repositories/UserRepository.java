package com.divyaa.authenticationsystem.datastore.repositories;

import com.divyaa.authenticationsystem.datastore.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Divyaa P
 */
public interface UserRepository extends JpaRepository<UserModel, String> {

    Optional<UserModel> findByNameAndActiveTrue(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update UserModel set refreshToken = :refreshToken where name = :name and active = true")
    void updateRefreshToken(@Param("name") String name, @Param("refreshToken") String refreshToken);

    Optional<UserModel> findByIdAndRefreshTokenAndActiveTrue(Long userId, String refreshToken);

    Optional<UserModel> findById(String userId);
}
