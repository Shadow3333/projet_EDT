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
import business.model.EU;

@ManagedBean(name="TUConverter")
@SessionScoped
@FacesConverter(value="TUConverter")
public class TUConverter implements Converter {
	@ManagedProperty(value="#{containerManager.manager}")
	private Manager manager;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			EU eu = manager.managerEus.find(value);
			return eu;
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((EU) value).getId();
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

}
