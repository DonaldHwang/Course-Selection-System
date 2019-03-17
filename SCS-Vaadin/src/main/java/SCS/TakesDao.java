package SCS;

import java.util.List;

public interface TakesDao {
	List<Takes> get_takes(String ID);
	public int insert(Takes takes);
	public int delete(Takes takes);
}
