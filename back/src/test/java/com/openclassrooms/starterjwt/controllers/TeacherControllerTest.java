package com.openclassrooms.starterjwt.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

@SpringBootTest
@ActiveProfiles("test")
class TeacherControllerTest {
	
	 @Mock
	 private TeacherService teacherService;

	 @Mock
	 private TeacherMapper teacherMapper;

	 @InjectMocks
	 private TeacherController teacherController;

	 private Teacher mockTeacher;

	 @BeforeEach
	 public void setup() {	     
	     this.mockTeacher = new Teacher(1L, "Toto", "TOTO", LocalDateTime.now(), LocalDateTime.now());
	 }
	 
	 @Test
	 void findByIdSuccessTest() {
		 
		 String teacherId = "1";
		 Teacher teacher = this.mockTeacher;
		 when(teacherService.findById(anyLong())).thenReturn(teacher);
		 
		 ResponseEntity<?> response = teacherController.findById(teacherId);
		 
		 assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		 assertThat(response.getBody()).isEqualTo(this.teacherMapper.toDto(teacher));
		 
	 }
	 @Test
	 void findByIdNotFoundTest() {
		 
	     String teacherId = "1";
	     when(teacherService.findById(anyLong())).thenReturn(null);

	     ResponseEntity<?> response = teacherController.findById(teacherId);

	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	 
	 }

	 @Test
	 void findByIdBadRequestTest() {
	        
	     String invalidTeacherId = "invalidId";

	     ResponseEntity<?> response = teacherController.findById(invalidTeacherId);

	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	    
	 }
	 
	 @Test
	 void findAllSuccessTest() {
	        
	     List<Teacher> teachers = List.of(this.mockTeacher);
	     when(teacherService.findAll()).thenReturn(teachers);

	     ResponseEntity<?> response = teacherController.findAll();

	     assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	     assertThat(response.getBody()).isEqualTo(this.teacherMapper.toDto(teachers));
	    }


}