package com.abdullahturhan.repository;

import com.abdullahturhan.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskManagementRepository extends JpaRepository<Task,String> {
    Optional<List<Task>> findTaskByLabel(String label);
}
