package web.util;

import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import business.dao.DaoException;
import business.manager.Manager;
import business.model.users.AbstractUser;

public class TeacherConverter implements Converter {

	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
        try {
			return manager.managerSessions.find(value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        return ((AbstractUser) value).getEmail();
    }
	
}
