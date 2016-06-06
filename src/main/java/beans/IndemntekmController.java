package beans;

import model.Indemntekm;
import dao.util.JsfUtil;
import dao.util.JsfUtil.PersistAction;
import session.IndemntekmFacade;

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

@Named("indemntekmController")
@SessionScoped
public class IndemntekmController implements Serializable {

    @EJB
    private session.IndemntekmFacade ejbFacade;
    private List<Indemntekm> items = null;
    private Indemntekm selected;

    public IndemntekmController() {
    }

    public Indemntekm getSelected() {
        return selected;
    }

    public void setSelected(Indemntekm selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getIndemntekmPK().setIdDeplacement(selected.getDeplacement().getIdDeplacement());
    }

    protected void initializeEmbeddableKey() {
        selected.setIndemntekmPK(new model.IndemntekmPK());
    }

    private IndemntekmFacade getFacade() {
        return ejbFacade;
    }

    public Indemntekm prepareCreate() {
        selected = new Indemntekm();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("IndemntekmCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("IndemntekmUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("IndemntekmDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Indemntekm> getItems() {
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

    public Indemntekm getIndemntekm(model.IndemntekmPK id) {
        return getFacade().find(id);
    }

    public List<Indemntekm> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Indemntekm> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Indemntekm.class)
    public static class IndemntekmControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            IndemntekmController controller = (IndemntekmController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "indemntekmController");
            return controller.getIndemntekm(getKey(value));
        }

        model.IndemntekmPK getKey(String value) {
            model.IndemntekmPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new model.IndemntekmPK();
            key.setIdDeplacement(Integer.parseInt(values[0]));
            key.setIdIndKm(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(model.IndemntekmPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdDeplacement());
            sb.append(SEPARATOR);
            sb.append(value.getIdIndKm());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Indemntekm) {
                Indemntekm o = (Indemntekm) object;
                return getStringKey(o.getIndemntekmPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Indemntekm.class.getName()});
                return null;
            }
        }

    }

}
