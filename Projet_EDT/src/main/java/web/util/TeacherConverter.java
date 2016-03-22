package web.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import business.dao.DaoException;
import business.manager.Manager;
import business.model.users.AbstractUser;

@ManagedBean(name="TeacherConverter")
@SessionScoped
@FacesConverter(value = "TeacherConverter")
public class TeacherConverter implements Converter {

	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;

    // Actions ------------------------------------------------------------------------------------
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
        try {
			return manager.managerUsers.find(value);
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
