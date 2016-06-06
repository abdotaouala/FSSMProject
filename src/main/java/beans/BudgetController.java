package beans;

import model.Budget;
import dao.util.JsfUtil;
import dao.util.JsfUtil.PersistAction;
import session.BudgetFacade;

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

@Named("budgetController")
@SessionScoped
public class BudgetController implements Serializable {

    @EJB
    private session.BudgetFacade ejbFacade;
    private List<Budget> items = null;
    private Budget selected;

    public BudgetController() {
    }

    public Budget getSelected() {
        return selected;
    }

    public void setSelected(Budget selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getBudgetPK().setAnnee(selected.getAnneebudgetaire().getAnnee());
        selected.getBudgetPK().setIdCompte(selected.getCompte().getIdCompte());
    }

    protected void initializeEmbeddableKey() {
        selected.setBudgetPK(new model.BudgetPK());
    }

    private BudgetFacade getFacade() {
        return ejbFacade;
    }

    public Budget prepareCreate() {
        selected = new Budget();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("BudgetCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("BudgetUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("BudgetDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Budget> getItems() {
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

    public Budget getBudget(model.BudgetPK id) {
        return getFacade().find(id);
    }

    public List<Budget> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Budget> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Budget.class)
    public static class BudgetControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BudgetController controller = (BudgetController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "budgetController");
            return controller.getBudget(getKey(value));
        }

        model.BudgetPK getKey(String value) {
            model.BudgetPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new model.BudgetPK();
            key.setIdCompte(Integer.parseInt(values[0]));
            key.setAnnee(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(model.BudgetPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdCompte());
            sb.append(SEPARATOR);
            sb.append(value.getAnnee());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Budget) {
                Budget o = (Budget) object;
                return getStringKey(o.getBudgetPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Budget.class.getName()});
                return null;
            }
        }

    }

}
