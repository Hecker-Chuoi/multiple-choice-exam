package com.hecker.exam.repository;

import com.hecker.exam.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("select t from Test t where t.isDeleted = :isDeleted")
    List<Test> findAllByDeleted(@Param("isDeleted") boolean isDeleted);
}
