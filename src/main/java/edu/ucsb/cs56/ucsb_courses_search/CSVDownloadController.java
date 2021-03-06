package edu.ucsb.cs56.ucsb_courses_search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.ucsb.cs56.ucsbapi.academics.curriculums.v1.classes.CoursePage;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CSVDownloadController {

    @Autowired
    private CurriculumService curriculumService;

    @GetMapping("/searchCSV")
    public void downloadCSV(@RequestParam(name = "subjectArea", required = true) String subjectArea,
            @RequestParam(name = "quarter", required = true) String quarter,
            @RequestParam(name = "courseLevel", required = true) String courseLevel,
            HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=courses.csv");

        String json = curriculumService.getJSON(subjectArea, quarter, courseLevel);

        CoursePage cp = CoursePage.fromJSON(json);

        CoursePageToCSV.writeSections(response.getWriter(), cp);
    }

    

}