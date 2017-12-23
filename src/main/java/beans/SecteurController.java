package beans;

import model.Secteur;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.SecteurFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Secteurprincipal;

@Named("secteurController")
@SessionScoped
@ManagedBean
public class SecteurController implements Serializable {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private Secteur current;
    private List<Secteur> items = null;
    private String newDesignation;
    @EJB
    private session.SecteurFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String secteurP;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;

    public SecteurController() {
        items=getItemes();
    }

    public void subjectSelectionChanged() {
        if (current instanceof Secteur && current != null) {
            try {
                Query req = em.createQuery("SELECT o FROM Secteur o WHERE o.intituleSecteur = ? and o.idSecteurP in(select sp.idSecteurP from Secteurprincipal sp where sp.designation= ?)").setParameter(1, current.getIntituleSecteur()).setParameter(2,this.secteurP);
                Secteur c = (Secteur) req.getSingleResult();
                if (c != null) {
                    disablCreate = true;
                    disablUpdate = false;
                    disablDelete = false;
                } else {
                    disablCreate = false;
                    disablUpdate = true;
                    disablDelete = true;
                }
            } catch (Exception e) {
                disablCreate = false;
                disablUpdate = true;
                disablDelete = true;
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Secteur> getItemes() {
        try {
            this.items = new ArrayList<Secteur>();
            Query req = em.createQuery("SELECT o FROM Secteur o");
            List<Secteur> l = (List<Secteur>) req.getResultList();
            items = l;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return items;
    }

    public List<String> completeText(String id){
        List<String> FiltredS=new ArrayList<String>();
        try {
                List<Secteur> AllS=getItemes();
                for(Secteur c:AllS){
                    if(c.getIntituleSecteur().startsWith(id)) {
                       FiltredS.add(c.getIntituleSecteur());
                    }
                }
            } catch (Exception e) {
              JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        return FiltredS;
    }

    public String getNewDesignation() {
        return newDesignation;
    }

    public void setNewDesignation(String newDesignation) {
        this.newDesignation = newDesignation;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Secteur> getItems() {
        return items;
    }

    public void setItems(List<Secteur> items) {
        this.items = items;
    }
    public boolean isDisablCreate() {
        return disablCreate;
    }

    public void setDisablCreate(boolean disablCreate) {
        this.disablCreate = disablCreate;
    }

    public boolean isDisablUpdate() {
        return disablUpdate;
    }

    public void setDisablUpdate(boolean disablUpdate) {
        this.disablUpdate = disablUpdate;
    }

    public boolean isDisablDelete() {
        return disablDelete;
    }

    public void setDisablDelete(boolean disablDelete) {
        this.disablDelete = disablDelete;
    }

    public Secteur getSelected() {
        if (current == null) {
            current = new Secteur();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setCurrent(Secteur current) {
        this.current = current;
    }

    public SecteurFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(SecteurFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public String getSecteurP() {
        return secteurP;
    }

    public void setSecteurP(String secteurP) {
        this.secteurP = secteurP;
    }

    private SecteurFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

   /* public String prepareView() {
        current = (Secteur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }*/

    public String prepareCreate() {
        current = new Secteur();
        selectedItemIndex = -1;
        return "Create";
    }
    public int getIDSP(){
        try {
    Query req = em.createQuery("select sp.idSecteurP from Secteurprincipal sp where sp.designation= ?").setParameter(1,this.secteurP);
           int idSP = (Integer) req.getSingleResult();
           current.setIdSecteur(idSP);
           return idSP;
           } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return 0;
        }
    }

    public String create() {
        try {
            current.setIdSecteurP(getIDSP());
            getFacade().edit(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

   /* public String prepareEdit() {
        current = (Secteur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }*/

    public String update() {
        try {
            Query req = em.createQuery("SELECT o FROM Secteur o WHERE o.intituleSecteur = ? and o.idSecteurP in(select sp.idSecteurP from Secteurprincipal sp where sp.designation= ?)").setParameter(1, current.getIntituleSecteur()).setParameter(2,this.secteurP);
            this.setCurrent((Secteur) req.getSingleResult());
            current.setIntituleSecteur(newDesignation);
            getFacade().edit(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

   /* public String destroy() {
        current = (Secteur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }*/

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    public void performDestroy() {
        try {
            Query req = em.createQuery("SELECT o FROM Secteur o WHERE o.intituleSecteur = ? and o.idSecteurP in(select sp.idSecteurP from Secteurprincipal sp where sp.designation= ?)").setParameter(1, current.getIntituleSecteur()).setParameter(2,this.secteurP);
            this.setCurrent((Secteur) req.getSingleResult());
            getFacade().remove(current);
            items=getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SecteurDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    /*public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }*/

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Secteur getSecteur(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Secteur.class)
    public static class SecteurControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecteurController controller = (SecteurController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secteurController");
            return controller.getSecteur(getKey(value));
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
            if (object instanceof Secteur) {
                Secteur o = (Secteur) object;
                return getStringKey(o.getIdSecteur());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Secteur.class.getName());
            }
        }

    }

}
