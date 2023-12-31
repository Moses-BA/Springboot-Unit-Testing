package com.ltp.gradesubmission;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ltp.gradesubmission.pojo.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {
    
    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;


    @Test
    public void getGradesFromRepoTest(){
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Potions", "C-"),
            new Grade("Ronald", "Charms", "B+")
        ));

        List<Grade> results = gradeService.getGrades();
        assertEquals("Harry", results.get(0).getName());
        assertEquals("Charms", results.get(1).getSubject());
    }

    @Test
    public void getGradeIndexTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        int valid = gradeService.getGradeIndex(grade.getId());
        int notValid = gradeService.getGradeIndex("123");

        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, notValid);
    }

    @Test
    public void getGradeByIdTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        Grade valid = gradeService.getGradeById(grade.getId());

        assertEquals("Potions", valid.getSubject());
    }

    @Test
    public void submitGradeTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        Grade grade2 = new Grade("Hermoine", "Arithmancy", "C-");
        gradeService.submitGrade(grade2);
        verify(gradeRepository, times(1)).addGrade(grade2);
    }

    @Test
    public void updateGradeTest(){
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        when(gradeRepository.getGrade(0)).thenReturn(grade);

        grade.setScore("B+");
        gradeService.submitGrade(grade);
        verify(gradeRepository, times(1)).updateGrade(grade, 0);
    }
}
