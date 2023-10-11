package com.manage.school.controaler;


import com.manage.school.models.Subjects;
import com.manage.school.models.Teachers;
import com.manage.school.services.SubjectsSer;
import com.manage.school.services.TeachersSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.util.List;

@RestController
public class TeachersCon {
    @Autowired
    private TeachersSer teachersService;

    @Autowired
    private SubjectsSer subjectsService;

    @PostMapping("/addTeacher")
    public ResponseEntity<?> addTeacher(@RequestBody Teachers teachers) {
        //teachers.setSubjects(subjects);
        teachersService.addTeacher(teachers);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teachers")
    public ResponseEntity<?> getAllTeachers() {
        List<Teachers> teachers = teachersService.getTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/teachers/{ID}")
    public ResponseEntity<?> getTeacherById(@PathVariable("ID") long ID) {
        Teachers teacher = teachersService.getTeachByID(ID);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/teachers/{ID}")
    public ResponseEntity<?> updateTeacher(@PathVariable("ID") long ID, @RequestBody Teachers teachers) {
        Teachers existingTeacher = teachersService.getTeachByID(ID);
        if (existingTeacher != null) {
            existingTeacher.setName(teachers.getName());
            // Update other properties as needed
            teachersService.addTeacher(existingTeacher);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/deleteTeacher/{ID}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("ID") long ID) {
        if (!teachersService.isStudentExists(ID)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student with ID " + ID + " not found");
        }
        teachersService.deleteByTeacherId(ID);
        return ResponseEntity.ok("Student deleted successfully");
    }
}


