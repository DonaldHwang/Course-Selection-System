package SCS;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout {
	private Label title = new Label("Course Selection System");
	private static TextField userID = new TextField("User ID");
	private PasswordField password = new PasswordField("Password");
	private Button login = new Button("Sign In",new Icon(VaadinIcon.SIGN_IN));
	private Label forget = new Label("Forget Password?");
	private HorizontalLayout horizontal = new HorizontalLayout();
	private VerticalLayout layout = new VerticalLayout();
	private Image logo = new Image();
	public LoginView(){
		logo.setSrc("https://www.jnu.edu.cn/_upload/tpl/00/f5/245/template245/images/home/logo.png");
		logo.setHeight("80px");
		logo.setWidth("240px");
		layout.add(logo);
	    layout.add(title);
	    layout.add(userID);
	    layout.add(password);
	    horizontal.add(login);
	    horizontal.add(forget);
	    layout.add(horizontal);
	    add(layout);
	    set_id();
	    login.addClickListener( e-> {
	        login.getUI().ifPresent(ui -> ui.navigate("main"));
	   });
	    
	    
	    // CSS style
	    userID.setWidth("320px");
	    password.setWidth("320px");
	    login.setWidth("120px");
	    login.setHeight("42px");
	    forget.setWidth("150px");
	    
	    layout.setAlignItems(Alignment.CENTER);
	    
	    title.getStyle().set("font-size", "30px");
		title.getStyle().set("color", "grey");
		forget.getStyle().set("margin-top", "10px");
		forget.getStyle().set("margin-left", "25px");
		forget.getStyle().set("color", "grey");
	}
	
	public static String get_name() {
	    String id = userID.getValue();
	    return DataService.getStudent(id).get(0).getName();
	}
	
	public static String get_dept() {
	    String id = userID.getValue();
	    return DataService.getStudent(id).get(0).getDept_name();
	}
	
	public static String get_id() {
		return userID.getValue();
	}
	
	public static void set_id() {
		userID.clear();
	}
}
