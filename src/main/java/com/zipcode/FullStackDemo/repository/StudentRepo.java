package com.zipcode.FullStackDemo.repository;

import com.zipcode.FullStackDemo.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository contains the full API of CrudRepository and PagingAndSortingRepository.
 * Using JPA because rather than getting an Iterable of all available entities,
 * we can get a List
 * i.e. getStudents in StudentController returns a list
 **/
@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    
}
