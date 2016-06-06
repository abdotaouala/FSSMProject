package beans;

import model.Indemnetedeplacementinterne;
import dao.util.JsfUtil;
import dao.util.JsfUtil.PersistAction;
import session.IndemnetedeplacementinterneFacade;

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

@Named("indemnetedeplacementinterneController")
@SessionScoped
public class IndemnetedeplacementinterneController implements Serializable {

    @EJB
    private session.IndemnetedeplacementinterneFacade ejbFacade;
    private List<Indemnetedeplacementinterne> items = null;
    private Indemnetedeplacementinterne selected;

    public IndemnetedeplacementinterneController() {
    }

    public Indemnetedeplacementinterne getSelected() {
        return selected;
    }

    public void setSelected(Indemnetedeplacementinterne selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getIndemnetedeplacementinternePK().setIdDeplacement(selected.getDeplacement().getIdDeplacement());
    }

    protected void initializeEmbeddableKey() {
        selected.setIndemnetedeplacementinternePK(new model.IndemnetedeplacementinternePK());
    }

    private IndemnetedeplacementinterneFacade getFacade() {
        return ejbFacade;
    }

    public Indemnetedeplacementinterne prepareCreate() {
        selected = new Indemnetedeplacementinterne();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("IndemnetedeplacementinterneCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("IndemnetedeplacementinterneUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("IndemnetedeplacementinterneDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Indemnetedeplacementinterne> getItems() {
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

    public Indemnetedeplacementinterne getIndemnetedeplacementinterne(model.IndemnetedeplacementinternePK id) {
        return getFacade().find(id);
    }

    public List<Indemnetedeplacementinterne> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Indemnetedeplacementinterne> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Indemnetedeplacementinterne.class)
    public static class IndemnetedeplacementinterneControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IndemnetedeplacementinterneController controller = (IndemnetedeplacementinterneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "indemnetedeplacementinterneController");
            return controller.getIndemnetedeplacementinterne(getKey(value));
        }

        model.IndemnetedeplacementinternePK getKey(String value) {
            model.IndemnetedeplacementinternePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new model.IndemnetedeplacementinternePK();
            key.setIdDeplacement(Integer.parseInt(values[0]));
            key.setIdIndDep(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(model.IndemnetedeplacementinternePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdDeplacement());
            sb.append(SEPARATOR);
            sb.append(value.getIdIndDep());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Indemnetedeplacementinterne) {
                Indemnetedeplacementinterne o = (Indemnetedeplacementinterne) object;
                return getStringKey(o.getIndemnetedeplacementinternePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Indemnetedeplacementinterne.class.getName()});
                return null;
            }
        }

    }

}
