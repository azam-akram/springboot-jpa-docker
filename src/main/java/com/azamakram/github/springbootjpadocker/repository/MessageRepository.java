package com.azamakram.github.springbootjpadocker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.azamakram.github.springbootjpadocker.model.entity.MessageEntity;
//import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
//public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {

    Optional<MessageEntity> findByMessageKey(String messageKey);

    List<MessageEntity> findTop2ByOrderByIdDesc();

    Optional<MessageEntity> findByMessageKeyAndSavedAtAfter(String messageKey, LocalDateTime after);

    @Query(value = "SELECT * FROM message order by id desc limit :row_limit", nativeQuery = true)
    List<MessageEntity> findLastNEmployees(@Param("row_limit") Integer row_limit);

    void deleteBySavedAtBefore(LocalDateTime since);
}
