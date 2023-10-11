package com.manage.school.services;

import com.manage.school.models.Student;
import com.manage.school.models.Subjects;
import com.manage.school.repository.SubjectsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectsSer {
    @Autowired
    private SubjectsRepo subjectsRepo;
    public Subjects addSubjects(Subjects subjects) {return subjectsRepo.save(subjects);
    }

    public List<Subjects> getAllSubjects(){
        return subjectsRepo.findAll();
    }
    public Subjects getSbjByID(long ID){

        Optional<Subjects> model=subjectsRepo.findById(ID);

        if (model.isPresent())
        {
            return model.get();
        }
        return null;
    }


}
