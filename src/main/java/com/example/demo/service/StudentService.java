package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student addStudent(Student student) {

        boolean emailExists =
                studentRepository.findByEmail(student.getEmail()).isPresent();

        if (emailExists) {
            throw new RuntimeException("Student email exists");
        }

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));
    }
}