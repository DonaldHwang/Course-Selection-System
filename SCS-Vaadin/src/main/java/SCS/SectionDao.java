package SCS;

import java.util.List;

public interface SectionDao {
	List<Section> get_section(String course_id);
}
