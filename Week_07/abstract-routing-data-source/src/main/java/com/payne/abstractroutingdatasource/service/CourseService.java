package com.payne.abstractroutingdatasource.service;

import com.payne.abstractroutingdatasource.entity.Course;
import com.payne.abstractroutingdatasource.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Kairou Zeng
 */
@Component
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public List<Course> findAll() {
        return courseMapper.findAll();
    }
}
