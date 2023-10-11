package com.manage.school.controaler;


import com.manage.school.models.Student;
import com.manage.school.models.Subjects;
import com.manage.school.services.StudentSer;
import com.manage.school.services.SubjectsSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectsCon {
    @Autowired
    private SubjectsSer subjectsSer;
    @Autowired
    private StudentSer studentSer;



    @PostMapping("/add")
    public ResponseEntity<String> addSubjects(@RequestBody Subjects subjects){
        subjectsSer.addSubjects(subjects);
        return ResponseEntity.ok("Subject added successfully");
    }

    @GetMapping("/getSubject")
    public ResponseEntity<List<Subjects>> getSubjects() {
        List<Subjects> subjects = subjectsSer.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    @PostMapping("/assign-subject")
    public ResponseEntity<String> assignSubjectToStudent(@RequestBody AssignSubjectRequest request) {
        Student student = studentSer.getStdByID(request.getStudentId());
        Subjects subject = subjectsSer.getSbjByID(request.getSubjectId());

        if (student != null && subject != null) {
            student.getSubjects().add(subject);
            studentSer.addStudent(student);
            return ResponseEntity.ok("Subject assigned to student successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student or Subject not found");
        }
    }
}


