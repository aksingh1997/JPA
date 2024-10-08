package com.abhi.JPA.controller;

import com.abhi.JPA.models.Book;
import com.abhi.JPA.models.Student;
import com.abhi.JPA.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CollegeController {

    // swagger - http://localhost:8080/swagger-ui/index.html

    @Autowired
    CollegeService collegeService;

    @PutMapping("/add-students")
    public String addStudent(@RequestBody List<Student> studentList) {
        collegeService.addStudents(studentList);
        return "Updated Successfully";
    }

    @GetMapping("/get-all-student")
    public List<Student> getAllStudent() {
        return collegeService.getAllStudent();
    }

    @GetMapping("/get-student-by-name-age")
    public List<String> getStudentByNameAge(@RequestParam String name, @RequestParam int age) {
        return collegeService.getStudentNameByNameAndAge(name, age);
    }

    @GetMapping("/get-student-age-between")
    public List<String> getAgeBetween(@RequestParam Integer from, @RequestParam Integer to) {
        return collegeService.getAgeBetween(from, to);
    }

    @GetMapping("/get-student-age-less-than")
    public List<String> getAgeLessThan(@RequestParam int age) {
        return collegeService.getAgeLessThan(age);
    }

    @GetMapping("/get-student-name-starting-with")
    public List<String> getNameStartingWith(@RequestParam String match) {
        return collegeService.getNameStartingWith(match);
    }

    @GetMapping("/get-student-age-desc")
    public List<Student> getNameLike() {
        return collegeService.sortedByAge();
    }

    @GetMapping("/derived/get-student-nameStart-age")
    public List<Student> getStudentWithStartingNameAndAgeDerived(@RequestParam String match, @RequestParam int age) {
        return collegeService.getStudentWithStartingNameAndAgeDerived(match, age);
    }

    @GetMapping("/native/get-student-by-stream")
    public List<Student> getStudentByStream(@RequestParam String stream) {
        return collegeService.getStudentByStream(stream);
    }

    @PutMapping("/update/stream-by-id")
    public String updateStreamById(@RequestParam int id, @RequestParam String stream) {
        collegeService.updateStreamById(id, stream);
        return "updated!!";
    }

    @PutMapping("/update/age-by-id")
    public String updateAgeById(@RequestParam int id, @RequestParam int age ) {
        collegeService.updateAgeById(id, age);
        return "Updated!!";
    }

    @PutMapping("/save-books")
    public String saveBooks(@RequestBody List<Book> bookList) {
        collegeService.saveBooks(bookList);
        return "books saved!!";
    }

    @GetMapping("/get-student-by-book-id")
    public String getStudentNameByBookId(@RequestParam int id) {
        return collegeService.getStudentNameByBookId(id);
    }

}
