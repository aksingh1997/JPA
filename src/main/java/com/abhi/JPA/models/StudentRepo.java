package com.abhi.JPA.models;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    //Useful methods provided by JPA - https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    // following method's implementation are provided by JPA. From name itself it is intuitive what each method says.
    // I have provided useful ones, others can be found in the link above
    public List<Student> findByNameAndAge(String name, int age);
    public List<Student> findByAgeBetween(int from, int to);
    public List<Student> findByAgeLessThan(int age);
    public List<Student> findByAgeGreaterThan(int age);
    public List<Student> findByNameStartingWith(String match);
    public List<Student> findByNameEndingWith(String match);
    public List<Student> findByNameContaining(String match);
    public List<Student> findByNameNotLike(String match);
    public List<Student> findAllByOrderByAgeDesc();

    // derived query - It is default under @Query. class and feild names have to be used instead of table name and column name
    @Query(value = "select u from Student u where name like ?1% and age = ?2") // Note: don't put single inverted commas('') after like
    public List<Student> getStudentWithStartingNameAndAgeDerived(String match, int age);

    // native query - It has to be provided, it is like normal sql query, hence the name native
    @Query(value = "select * from student where subject_stream = ?1", nativeQuery = true)
    public List<Student> getStudentByStream(String subjectStream);



    @Modifying // required for modifying the data, or else will consider it as read data
    @Transactional // if we don't provide this and use @modifying alone , this will throw transaction required exception.
    @Query(value = "update student set age = ?1 where id = ?2", nativeQuery = true)
    public void updateAgeById(int age, int id);
}
