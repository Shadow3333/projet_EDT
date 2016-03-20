package web.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import manager.SessionManager;
import models.AbstractUser;

public class TeacherConverter implements Converter {

	private static SessionManager sM = new SessionManager();

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
        return sM.find(value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        return ((AbstractUser) value).getEmail();
    }
	
}
