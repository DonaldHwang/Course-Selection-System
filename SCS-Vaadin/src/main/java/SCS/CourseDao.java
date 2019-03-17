package SCS;
import java.util.List;

public interface CourseDao {
	Course select_course(String course_id);
	List<Course> select_all();

}
