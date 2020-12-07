package com.payne.abstractroutingdatasource.mapper;

import com.payne.abstractroutingdatasource.entity.Course;

import java.util.List;

/**
 * @author Kairou Zeng
 */
public interface CourseMapper {

    List<Course> findAll();
}
