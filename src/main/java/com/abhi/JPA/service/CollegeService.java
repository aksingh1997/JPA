package com.abhi.JPA.service;

import com.abhi.JPA.models.Book;
import com.abhi.JPA.models.BookRepo;
import com.abhi.JPA.models.Student;
import com.abhi.JPA.models.StudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CollegeService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    BookRepo bookRepo;

    // Utility
    public List<String> getNameFromStudentList(List<Student> studentList) {
        return studentList.stream().filter(Objects::nonNull).map(Student::getName).collect(Collectors.toList());
    }

    public void addStudents(List<Student> studentList) {
        studentRepo.saveAll(studentList);
    }

    public List<Student> getAllStudent() {
        return studentRepo.findAll();
    }

    public List<String> getStudentNameByNameAndAge(String name, int age) {
        return getNameFromStudentList(studentRepo.findByNameAndAge(name, age));
    }

    public List<String> getAgeBetween(int from, int to) {
        return getNameFromStudentList(studentRepo.findByAgeBetween(from, to));
    }

    public List<String> getAgeLessThan(int age) {
        return getNameFromStudentList(studentRepo.findByAgeLessThan(age));
    }

    public List<String> getNameStartingWith(String match) {
        return getNameFromStudentList(studentRepo.findByNameStartingWith(match));
    }

    public List<Student> sortedByAge() {
        return studentRepo.findAllByOrderByAgeDesc();
    }

    public List<Student> getStudentWithStartingNameAndAgeDerived(String match, int age) {
        return studentRepo.getStudentWithStartingNameAndAgeDerived(match, age);
    }

    public List<Student> getStudentByStream(String stream) {
        return studentRepo.getStudentByStream(stream);
    }

    //update query.
    /*
    this is not efficient if we want to update just 1 field, better use JPQL for that and write a query.
    this can be useful in scenarios where we want to update multiple fields, or we want to avoid writing any complex update sql query
     */
    @Modifying // without this, JPA consider that queries are read only. This is required for update/delete
    @Transactional // This is required inorder to persist the data in actual database. Without this data will only
    // be persisted in persistent context, but not in actual database.
    public void updateStreamById(int id, String stream) {
        Student s = studentRepo.findById(id).orElseThrow();
        // here we are trying to update the managed entity by JPA
        // managed entity means those entities that are persist in persistent context
        s.setSubjectStream(stream);
    }

    public void updateAgeById(int id, int age) {
        studentRepo.updateAgeById(age, id);
    }

    public void saveBooks(List<Book> bookList) {
        bookList.forEach(book -> book.setStudent(studentRepo.findById(book.getStudent().getId()).orElse(null)));
        bookRepo.saveAll(bookList);
    }

    public String getStudentNameByBookId(int id) {
        return bookRepo.findStudentById(id).getName();
    }
}
