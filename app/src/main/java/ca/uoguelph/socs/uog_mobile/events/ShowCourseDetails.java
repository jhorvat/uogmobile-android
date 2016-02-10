package ca.uoguelph.socs.uog_mobile.events;

import ca.uoguelph.socs.uog_mobile.data.web_advisor.models.Course;

/**
 * Created by julianhorvat on 2016-02-09.
 */
public class ShowCourseDetails {
    private final Course courseModel;

    public ShowCourseDetails(Course courseModel) {
        this.courseModel = courseModel;
    }

    public Course getCourse() {
        return courseModel;
    }
}
