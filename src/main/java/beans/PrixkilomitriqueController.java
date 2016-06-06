package beans;

import model.Prixkilomitrique;
import dao.util.JsfUtil;
import dao.util.JsfUtil.PersistAction;
import session.PrixkilomitriqueFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("prixkilomitriqueController")
@SessionScoped
public class PrixkilomitriqueController implements Serializable {

    @EJB
    private session.PrixkilomitriqueFacade ejbFacade;
    private List<Prixkilomitrique> items = null;
    private Prixkilomitrique selected;

    public PrixkilomitriqueController() {
    }

    public Prixkilomitrique getSelected() {
        return selected;
    }

    public void setSelected(Prixkilomitrique selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PrixkilomitriqueFacade getFacade() {
        return ejbFacade;
    }

    public Prixkilomitrique prepareCreate() {
        selected = new Prixkilomitrique();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PrixkilomitriqueCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PrixkilomitriqueUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PrixkilomitriqueDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Prixkilomitrique> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Prixkilomitrique getPrixkilomitrique(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Prixkilomitrique> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Prixkilomitrique> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Prixkilomitrique.class)
    public static class PrixkilomitriqueControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PrixkilomitriqueController controller = (PrixkilomitriqueController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "prixkilomitriqueController");
            return controller.getPrixkilomitrique(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Prixkilomitrique) {
                Prixkilomitrique o = (Prixkilomitrique) object;
                return getStringKey(o.getIdPrixKilo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Prixkilomitrique.class.getName()});
                return null;
            }
        }

    }

}
