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

@ManagedBean(name="teacherConverter")
@SessionScoped
@FacesConverter(value = "teacherConverter")
public class TeacherConverter implements Converter {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;
    
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
			return manager.managerUsers.find(value);
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((AbstractUser) value).getEmail();
    }

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
}
