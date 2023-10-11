package com.manage.school.controaler;


import com.manage.school.models.Student;
import com.manage.school.repository.StudentRepo;
import com.manage.school.services.StudentSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentsCon {

    @Autowired
    private StudentSer studentSer;
    @Autowired
    private StudentRepo studentRepo;



    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentSer.addStudent(student);
        return ResponseEntity.ok("Student added successfully");
    }

    @PostMapping("/AssignSubjectConform")
    public ResponseEntity<String> assignSubjectConform(@RequestBody Student student){
        Student student1 = studentRepo.findById(student.getID()).orElse(null);
        if (student1 != null) {
            student1.setSubjects(student.getSubjects());
            studentSer.addStudent(student1);
            return ResponseEntity.ok("Subject assigned successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with ID " + student.getID() + " not found");
        }
    }

    @GetMapping("/getStudent")
    public ResponseEntity<List<Student>> getAllStudent() {
        List<Student> students = studentSer.getStudent();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getStudent/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Student student = studentSer.getStdByID(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/update/{ID}")
    public ResponseEntity<String> updateStudent(@PathVariable("ID") long ID, @RequestBody Student newStudent) {
        Student updatedStudent = studentSer.getStdByID(ID);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        } else {
            updatedStudent.setName(newStudent.getName());
            updatedStudent.setAge(newStudent.getAge());
            updatedStudent.setGrade(newStudent.getGrade());
            updatedStudent.setClz(newStudent.getClz());
            updatedStudent.setAddress(newStudent.getAddress());
            updatedStudent.setTP(newStudent.getTP());
            updatedStudent.setEmail(newStudent.getEmail());
            studentRepo.save(updatedStudent);
            return ResponseEntity.ok("Student updated successfully");
        }
    }

    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<String> deleteStudent(@PathVariable("ID") long ID) {
        if (!studentSer.isStudentExists(ID)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with ID " + ID + " not found");
        }
        studentSer.deleteByStudentId(ID);
        return ResponseEntity.ok("Student deleted successfully");
    }
}