package beans;

import model.Boncommande;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.BoncommandeFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import model.Compte;
import model.Dotationsecteur;
import model.Secteur;
import model.Users;
import org.example.shiro.bean.security.ShiroLoginBean;

@Named("boncommandeController")
@SessionScoped
@ManagedBean
public class BoncommandeController implements Serializable {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;
    private String secteurP;
    private String secteur;
    private Compte cpt;
    private Dotationsecteur ds;
    @Inject
    UserTransaction ut;
    private Boncommande current;
    private List<Boncommande> items = null;
    @EJB
    private session.BoncommandeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public BoncommandeController() {
        
    }

    public void subjectSelectionChanged() {
        this.current.setDateCommande(new Date());
        Secteur sect = getSect();
        if (current instanceof Boncommande && current != null) {
            try {
                Query req = em.createQuery("SELECT o FROM Dotationsecteur o WHERE o.idSecteur=? and o.idCompte =?").setParameter(1, sect.getIdSecteur()).setParameter(2, cpt.getIdCompte());
                this.ds = (Dotationsecteur) req.getSingleResult();
                if (ds != null) {
                    current.setIdDotation(ds.getIdDotation());
                    Query req2 = em.createQuery("SELECT o FROM Boncommande o WHERE o.idBC =?").setParameter(1, current.getIdBC());
                    Boncommande bc = (Boncommande) req2.getSingleResult();
                    if (bc != null) {
                        current.setIdBC(bc.getIdBC());
                        current.setDateCommande(bc.getDateCommande());
                        disablCreate = true;
                        disablUpdate = false;
                        disablDelete = false;
                    } else {
                        disablCreate = false;
                        disablUpdate = true;
                        disablDelete = true;
                    }
                } else {
                    disablCreate = true;
                    disablUpdate = true;
                    disablDelete = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! secteur innexistant dans ce secteur principal","erreue"));
                }
            } catch (Exception e) {
                disablCreate = true;
                disablUpdate = true;
                disablDelete = true;
                e.printStackTrace();
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Secteur getSect() {
        Query query = em.createQuery("SELECT o FROM Secteur o WHERE o.intituleSecteur= ? and o.idSecteurP in (select sp.idSecteurP from Secteurprincipal sp where sp.designation=?)").setParameter(1, this.secteur).setParameter(2, this.secteurP);
        Secteur s = (Secteur) query.getSingleResult();
        return s;
    }

    public Users getUser() {
        ShiroLoginBean slb = (ShiroLoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("shiroLoginBean");
        Users user = null;
        try {
            Query req = em.createQuery("SELECT o FROM Users o where o.login=? and o.password=?").setParameter(1, slb.getUsername()).setParameter(2, slb.getPassword());
            user = (Users) req.getResultList();
            if (user != null) {
                this.current.setIdUser(user.getIdUser());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return user;
    }

    public Dotationsecteur getDs() {
        return ds;
    }

    public void setDs(Dotationsecteur ds) {
        this.ds = ds;
    }

    public List<Boncommande> getItemes() {
        Users user = getUser();
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.idUser=?").setParameter(1, user.getIdUser());
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return items;
    }

    public Boncommande getSelected() {
        if (current == null) {
            current = new Boncommande();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BoncommandeFacade getFacade() {
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

    /*public String prepareView() {
        current = (Boncommande) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }*/
    public String prepareCreate() {
        current = new Boncommande();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            this.current.setEtat("enCours");
            this.current.setDateCommande(new Date());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /* public String prepareEdit() {
        current = (Boncommande) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }*/
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /* public String destroy() {
        current = (Boncommande) getItems().getRowData();
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

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeDeleted"));
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

    public List<Boncommande> getItems() {
        return items;
    }

    public void setItems(List<Boncommande> items) {
        this.items = items;
    }

    public void setCurrent(Boncommande current) {
        this.current = current;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
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

    public String getSecteurP() {
        return secteurP;
    }

    public void setSecteurP(String secteurP) {
        this.secteurP = secteurP;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public Compte getCpt() {
        return cpt;
    }

    public void setCpt(Compte cpt) {
        this.cpt = cpt;
    }

    public UserTransaction getUt() {
        return ut;
    }

    public void setUt(UserTransaction ut) {
        this.ut = ut;
    }

    public BoncommandeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(BoncommandeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
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

    public Boncommande getBoncommande(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Boncommande.class)
    public static class BoncommandeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BoncommandeController controller = (BoncommandeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "boncommandeController");
            return controller.getBoncommande(getKey(value));
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
            if (object instanceof Boncommande) {
                Boncommande o = (Boncommande) object;
                return getStringKey(o.getIdBC());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Boncommande.class.getName());
            }
        }

    }

}
