package SCS;

public class Section {
	private String course_id;
	private String sec_id;
	private String semester;
	private String year;
	private String building;
	private String room_number;
	private String time_slot_id;
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getSec_id() {
		return sec_id;
	}
	public void setSec_id(String sec_id) {
		this.sec_id = sec_id;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getRoom_number() {
		return room_number;
	}
	public void setRoom_number(String room_number) {
		this.room_number = room_number;
	}
	public String getTime_slot_id() {
		return time_slot_id;
	}
	public void setTime_slot_id(String time_slot_id) {
		this.time_slot_id = time_slot_id;
	}
}
