package SCS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;


@Route("main")
public class MainView extends VerticalLayout {
	// Some components
	private Label information = new Label();
	private Label credit = new Label();
	private Button logout = new Button("Logout",new Icon(VaadinIcon.SIGN_OUT));
	private HorizontalLayout toolbar = new HorizontalLayout();
	private Grid<Course> all_course = new Grid<>();
	private Grid<Takes> selected_course = new Grid<>();
	private Grid<Takes> completed_course = new Grid<>();
    public MainView() {
    	
    	// Get the basic information of the student through student ID get from login page
    	information.setText("Hi " + LoginView.get_name() + "! Welcome to course selection system. Your department: " + LoginView.get_dept());
    	information.getStyle().set("margin", "30px 0 0 22px");
    	information.getStyle().set("font-size", "16px");
    	
    	credit.setText("You have already selected "+get_selected_credits() + 
    			" credits. Your remaining credits for this semester are "+ (21-get_selected_credits())+ " credtis.");
    	credit.setClassName("credit");
    	
    	// Set the style and function for the logout button
    	logout.getStyle().set("position", "absolute");
    	logout.getStyle().set("right", "10px");
    	logout.getStyle().set("top", "10px");
    	logout.addClickListener( e-> {
    		LoginView.set_id();
	        logout.getUI().ifPresent(ui -> ui.navigate("login"));
    	});
    	
    	toolbar.add(information, logout);
    	Tab tab1 = new Tab("All the Courses");
    	all_course.setSizeFull();
    	// Create a grid to store course list
    	all_course.addColumn(Course::getCourse_id).setHeader("Course ID");
    	all_course.addColumn(Course::getTitle).setHeader("Course Title");
    	all_course.addColumn(Course::getDept_name).setHeader("Department");
    	all_course.addColumn(Course::getCredits).setHeader("Credits");
    	all_course.addColumn(new NativeButtonRenderer<>("Select", item -> {
    		Dialog dialog = new Dialog();
    		dialog.setCloseOnEsc(false);
    		dialog.setCloseOnOutsideClick(false);
    		VerticalLayout dialog_vertical = new VerticalLayout();
    		HorizontalLayout dialog_horizontal = new HorizontalLayout();
    		Label message = new Label("Are you sure to choose this course?");
    		Button confirm = new Button("Confirm", event ->{
    			
    			Course select = item;
        		List<Section> section = DataService.getSection(select.getCourse_id());
        		if(time_conflict(section.get(0).getTime_slot_id()) == false && prereq_conflict(select.getCourse_id()) == false) {
        			Takes take = new Takes();
            		take.setCourse_id(select.getCourse_id());
            		take.setID(LoginView.get_id());
            		take.setSec_id(section.get(0).getSec_id());
            		take.setSemester(section.get(0).getSemester());
            		take.setYear(section.get(0).getYear());
            		take.setGrade(null);
            		try {
        				DataService.insert(take);
        				update_selected_course();
        			} catch (Exception e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
            		credit.setText("You have already selected "+get_selected_credits() + 
            				" credits. Your remaining credits for this semester are "+ (21-get_selected_credits())+ " credtis.");
            		dialog.close();
            		
            		// add a dialog to inform successfully selected
            		Dialog confirm_info = new Dialog();
            		VerticalLayout dialog_vertical2 = new VerticalLayout();
            		dialog_vertical2.setAlignItems(Alignment.CENTER);
        			Label message1 = new Label("Successfully Selected !");
        			Button confirm1 = new Button("OK", event1 ->{
        				confirm_info.close();
        			});
        			dialog_vertical2.add(message1, confirm1);
        			confirm_info.add(dialog_vertical2);
        			confirm_info.open();
        			update_selected_course();
    			}
        		else {
        			dialog.close();
        			// add a dialog to inform time has conflict
        			Dialog repeat = new Dialog();
        			VerticalLayout dialog_vertical3 = new VerticalLayout();
        			dialog_vertical3.setAlignItems(Alignment.CENTER);
        			Label message2 = new Label("Time Conflict or Prerequisite Course Required !");
        			Button confirm2 = new Button("OK", event1 ->{
        				repeat.close();
        			});
        			dialog_vertical3.add(message2, confirm2);
        			repeat.add(dialog_vertical3);
        			repeat.open();
        		}
        		
    		});
    		Button cancel = new Button("Cancel ", event->{
    			dialog.close();
    		});
    		dialog_vertical.setAlignItems(Alignment.CENTER);
    		cancel.getStyle().set("margin-left", "20px");
    		dialog_horizontal.add(confirm,cancel);
    		dialog_vertical.add(message,dialog_horizontal);
    		dialog.add(dialog_vertical);
    		dialog.open();
    	})).setFlexGrow(0);
    	update_all_course();
    	
    	// Selected courses grid main components
    	Tab tab2 = new Tab("Selected Courses");
    	selected_course.addColumn(Takes::getCourse_id).setHeader("Course ID");
    	selected_course.addColumn(Takes::getSec_id).setHeader("Sec ID");
    	selected_course.addColumn(Takes::getSemester).setHeader("Semester");
    	selected_course.addColumn(Takes::getYear).setHeader("Year");
    	selected_course.addColumn(new NativeButtonRenderer<>("Cancel", item -> {
    		Dialog dialog = new Dialog();
    		dialog.setCloseOnEsc(false);
    		dialog.setCloseOnOutsideClick(false);
    		VerticalLayout dialog_vertical = new VerticalLayout();
    		HorizontalLayout dialog_horizontal = new HorizontalLayout();
    		Label message = new Label("Are you sure to cancel this course?");
    		Button confirm = new Button("Confirm", event ->{
    			Takes cancel = item;
        		try {
        			DataService.delete(cancel);
        			update_selected_course();
    			} catch (Exception e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
        		credit.setText("You have already selected "+get_selected_credits() + 
        				" credits. Your remaining credits for this semester are "+ (21-get_selected_credits())+ " credits.");
        		dialog.close();
        		Dialog confirm_info = new Dialog();
        		VerticalLayout dialog_vertical2 = new VerticalLayout();
        		dialog_vertical2.setAlignItems(Alignment.CENTER);
    			Label message1 = new Label("Successfully Cancel !");
    			Button confirm1 = new Button("OK", event1 ->{
    				confirm_info.close();
    			});
    			dialog_vertical2.add(message1, confirm1);
    			confirm_info.add(dialog_vertical2);
    			confirm_info.open();
    			update_selected_course();
    		});
    		
    		Button cancel = new Button("Cancel ", event->{
    			dialog.close();
    		});
    		
    		dialog_vertical.setAlignItems(Alignment.CENTER);
    		cancel.getStyle().set("margin-left", "20px");
    		dialog_horizontal.add(confirm,cancel);
    		dialog_vertical.add(message,dialog_horizontal);
    		dialog.add(dialog_vertical);
    		dialog.open();
    	})).setFlexGrow(0);
    	
    	selected_course.setVisible(false);
    	update_selected_course();
    	
    	// Completed courses grid main components
    	Tab tab3 = new Tab("Completed Courses");
    	completed_course.addColumn(Takes::getCourse_id).setHeader("Course ID");
    	completed_course.addColumn(Takes::getSec_id).setHeader("Sec ID");
    	completed_course.addColumn(Takes::getSemester).setHeader("Semester");
    	completed_course.addColumn(Takes::getYear).setHeader("Year");
    	completed_course.addColumn(Takes::getGrade).setHeader("Grade");
    	completed_course.setVisible(false);
    	update_completed_course();
    	
    	// set the Map for corresponding tabs and grids
    	Map<Tab, Component> tabsToPages = new HashMap<>();
    	tabsToPages.put(tab1, all_course);
    	tabsToPages.put(tab2, selected_course);
    	tabsToPages.put(tab3, completed_course);
    	Tabs tabs = new Tabs(tab1,tab2,tab3);
    	Set<Component> pagesShown = Stream.of(all_course)
    	        .collect(Collectors.toSet());
    	
    	// add a listener for the grid change
    	tabs.addSelectedChangeListener(event -> {
    	    pagesShown.forEach(page -> page.setVisible(false));
    	    pagesShown.clear();
    	    Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
    	    selectedPage.setVisible(true);
    	    pagesShown.add(selectedPage);
    	});
    	
        add(toolbar,tabs,credit,all_course,selected_course,completed_course); // add all the components
        setHeight("100vh");
        
    }
    
    // Update the grid of all the courses
    public void update_all_course() {
    	all_course.setItems(DataService.getCourse()); // Fetch data from MySql
    }
    
    // Update the grid of completed courses
    public void update_completed_course() {
    	completed_course.setItems(DataService.getTakes(LoginView.get_id()));
    }
    
    // Update the grid of selected courses
    public void update_selected_course() {
    	selected_course.setItems();
    	List<Takes> temp = new ArrayList<Takes>();
    	List<Takes> takes = DataService.getTakes(LoginView.get_id());
    	for (int i = 0; i < takes.size(); i++) {
    		if(takes.get(i).getGrade() == null) {
    			temp.add(takes.get(i));
    			
    		}
    	}
    	selected_course.setItems(temp);
    }
    
    public Boolean time_conflict(String time_slot) {
    	List<Takes> temp = new ArrayList<Takes>();
    	List<Takes> takes = DataService.getTakes(LoginView.get_id());
    	for (int i = 0; i < takes.size(); i++) {
    		if(takes.get(i).getGrade() == null) {
    			temp.add(takes.get(i));
    		}
    	}
    	for (int i = 0; i < temp.size(); i++) {
    		if (time_slot.equals(DataService.getSection(temp.get(i).getCourse_id()).get(0).getTime_slot_id())) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public Boolean prereq_conflict(String course_id) {
    	List<Takes> temp = new ArrayList<Takes>();
    	List<Takes> takes = DataService.getTakes(LoginView.get_id());
    	for (int i = 0; i < takes.size(); i++) {
    		if(takes.get(i).getGrade() != null) {
    			temp.add(takes.get(i));
    		}
    	}
    	List<Prereq> prereq = DataService.getPrereq();
    	for (int i = 0; i < prereq.size(); i++) {
    		if(course_id.equals(prereq.get(i).getCourse_id())) {
    			for (int j = 0; j < temp.size(); j++) {
    	    		if(prereq.get(i).getPrereq_id().equals(temp.get(j).getCourse_id())) {
    	    			return false;
    	    		}
    	    	}
    			return true;
    		}
    	}
    	return false;
    }
    
    // Get the credits for the courses selected this semester.
    public int get_selected_credits() {
    	int credits = 0;
    	List<Takes> takes = DataService.getTakes(LoginView.get_id());
    	for (int i = 0; i < takes.size(); i++) {
    		if(takes.get(i).getGrade() == null) {
    			Course temp = DataService.getOneCourse(takes.get(i).getCourse_id());
    			int credit = Integer.parseInt(temp.getCredits());
    			credits += credit;
    		}
    	}
    	return credits;
    }
}
