package com.abhi.JPA.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepo extends JpaRepository<Book, Integer> {

    @Query("select student from Book u where u.id = ?1")
    Student findStudentById(int id);
}
