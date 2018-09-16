package com.tvestergaard.jpql;

@FunctionalInterface
public interface TriConsumer
{

    void consume(
            SemesterRepository semesterRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository) throws Exception;
}
