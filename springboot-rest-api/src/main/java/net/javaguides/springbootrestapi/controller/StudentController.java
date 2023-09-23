package net.javaguides.springbootrestapi.controller;

import net.javaguides.springbootrestapi.bean.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    // approach1
//    //http://localhost:8080/students/student
//    @GetMapping("student")
//    public Student getStudent() {
//        Student student = new Student(1, "Shuangke", "Li");
//        return student;
//    }

    // approach 2
    @GetMapping("student")
    public ResponseEntity<Student> getStudent() {
        Student student = new Student(1, "He", "Li");
//        return ResponseEntity.ok()
//                .header("custom-header", "shuangke haha")
//                .body(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    //http://localhost:8080/students
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Mary", "Li"));
        students.add(new Student(2, "Ben", "Wang"));
        students.add(new Student(3, "Bobby", "Lee"));
        students.add(new Student(4, "Ellen", "Qian"));
        return ResponseEntity.ok(students);
    }

    /**
     *
     * {id} - URI template variable
     */
    @GetMapping("{id}/{first-name}/{last-name}")
    //http://localhost:8080/student/4/shuangke/li
    public ResponseEntity<Student> studentPathVariable(@PathVariable("id") int studentId,
                                       @PathVariable("first-name") String firstName,
                                       @PathVariable("last-name") String lastName) {
        return ResponseEntity.ok(new Student(studentId, firstName, lastName));
    }

    /**
     *  Query
     *  http://localhost:8080/student/query?id=1&firstName=shuangke&lastName=Li
     */
    @GetMapping("query")
    public ResponseEntity<Student> studentRequestVariable(@RequestParam int id, @RequestParam String firstName,
                                          @RequestParam String lastName) {
        return ResponseEntity.ok(new Student(id, firstName, lastName));
    }

    //Springboot rest API that handles http POST requests
    //@PostMapping
    // @RequestBody convert JSON into java object
    @PostMapping("create")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        System.out.println(student.getId());
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable("id") int studentId) {
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return ResponseEntity.ok(student);
    }

   @DeleteMapping("{id}/delete")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") int studentId) {
        return ResponseEntity.ok("Student id: " + studentId + " delete successfully!");
   }
}
