package SCS;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class DataService {
	
	public static SqlSession initial() throws Exception {
		String resource = "mybatis.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(reader);
        
        SqlSession session = factory.openSession();
        return session;
	}
	
	public static Course CourseDao(String course_id) throws Exception {
		SqlSession session = initial();
		CourseDao courseDao = session.getMapper(CourseDao.class);
		Course course = courseDao.select_course(course_id);
		return course;
	}
	
	public static Course getOneCourse(String course_id) {
		try {
			Course course= CourseDao(course_id);
			return course;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Course> CourseDao() throws Exception{
		SqlSession session = initial();
        CourseDao courseDao = session.getMapper(CourseDao.class);
        List<Course> course = courseDao.select_all();
        return course;
	}
	
	public static List<Course> getCourse(){
		try {
			List<Course> course= CourseDao();
			return course;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Student> StudentDao(String ID) throws Exception{
		SqlSession session = initial();
        StudentDao studentDao = session.getMapper(StudentDao.class);
        List<Student> student = studentDao.get_information(ID);
        return student;
	}
	
	public static List<Student> getStudent(String id){
		try {
			List<Student> student= StudentDao(id);
			return student;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Takes> TakesDao(String ID) throws Exception {
		SqlSession session = initial();
		TakesDao takesDao = session.getMapper(TakesDao.class);
		List<Takes> takes = takesDao.get_takes(ID);
		return takes;
	}
	
	public static List<Takes> getTakes(String id){
		try {
			List<Takes> takes= TakesDao(id);
			return takes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Prereq> PrereqDao() throws Exception{
		SqlSession session = initial();
		PrereqDao prereqDao = session.getMapper(PrereqDao.class);
		List<Prereq> prereq = prereqDao.get_prereq();
		return prereq;
	}
	
	public static List<Prereq> getPrereq() {
		try {
			List<Prereq> prereq= PrereqDao();
			return prereq;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void insert(Takes takes) throws Exception {
		SqlSession session = initial();
		TakesDao takesDao = session.getMapper(TakesDao.class);
		takesDao.insert(takes);
		session.commit();
		session.close();
	}
	
	public static List<Section> SectionDao(String course_id) throws Exception {
		SqlSession session = initial();
		SectionDao sectionDao = session.getMapper(SectionDao.class);
		List<Section> section = sectionDao.get_section(course_id);
		return section;
	}
	
	public static List<Section> getSection(String course_id){
		try {
			List<Section> section= SectionDao(course_id);
			return section;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void delete(Takes takes) throws Exception {
		SqlSession session = initial();
		TakesDao takesDao = session.getMapper(TakesDao.class);
		takesDao.delete(takes);
		session.commit();
		session.close();
	}
}
