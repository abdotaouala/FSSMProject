package beans;

import model.Lignecommande;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.LignecommandeFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import model.Article;
import model.Boncommande;
import model.Users;
import org.example.shiro.bean.security.ShiroLoginBean;

@Named("lignecommandeController")
@SessionScoped
@ManagedBean
public class LignecommandeController implements Serializable {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private String description;
    private Boncommande bc;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;
    private Lignecommande current;
    private List<Lignecommande> items = null;
    @Inject
    UserTransaction ut;
    @EJB
    private session.LignecommandeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public LignecommandeController() {
    }

    public Article getArticle() {
        Article c = null;
        try {
            Query req = em.createQuery("SELECT o FROM Article o WHERE o.description =?").setParameter(1, description);
            c = (Article) req.getSingleResult();
        } catch (Exception e) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Article innexistant !", "erreue"));
        }
        return c;

    }

    public Users getUser() {
        ShiroLoginBean slb = (ShiroLoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("shiroLoginBean");
        Users user = null;
        /*try {
            Query req = em.createQuery("SELECT o FROM Users o where o.login=? and o.password=?").setParameter(1, slb.getUsername()).setParameter(2, slb.getPassword());
            user = (Users) req.getResultList();
            return user;
        } catch (Exception e) {
            user = new Users();
            user.setIdUser(2);
            return user;
            //  e.printStackTrace();
            // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }*/
        user = new Users();
        user.setIdUser(2);
        return user;
    }
public void remplireFormulaire(){
    bc.setIdBC(current.getIdBC());
    try {
            Query req = em.createQuery("SELECT o.description FROM Article o where o.idArticle= ? ").setParameter(1,current.getIdArticle());
            this.description= (String) req.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    current.setMontant(current.getQuantite()*current.getPu());

}
    public List<Lignecommande> getAllItemes() {
        try {
            Users user = getUser();
            this.items = new ArrayList<Lignecommande>();
            Query req = em.createQuery("SELECT o FROM Lignecommande o where o.idBC=? ").setParameter(1, bc.getIdBC());
            List<Lignecommande> l = (List<Lignecommande>) req.getResultList();
            items = l;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return items;
    }

    public Boncommande getBC() {
        try {
            Users user = getUser();
            Query req = em.createQuery("SELECT o FROM Boncommande o WHERE o.idBC =? and o.idUser=?").setParameter(1, bc.getIdBC()).setParameter(2, user.getIdUser());
            Boncommande c = (Boncommande) req.getSingleResult();
            if (c != null && c instanceof Boncommande) {
                return c;
            }
        } catch (Exception e) {
            disablCreate = true;
            disablUpdate = true;
            disablDelete = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Engagement innexistant ou vous n'etes pas le proprietaire de cet Engagement !", "erreue"));
        }
        return null;
    }

    public List<Lignecommande> changeBC() {
        try {
            if(current==null){current=new Lignecommande();}
            current.setIdBC(bc.getIdBC());
            Users user = getUser();
            this.items = new ArrayList<Lignecommande>();
            Query req = em.createQuery("SELECT o FROM Lignecommande o where o.idBC=? ").setParameter(1, bc.getIdBC());
            List<Lignecommande> l = (List<Lignecommande>) req.getResultList();
            items = l;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return items;

    }

    public void subjectSelectionChanged() {
        items = getItemes();
        if (current instanceof Lignecommande && current != null) {
            current.setIdBC(bc.getIdBC());
            Boncommande bc = getBC();
            Article a = getArticle();
            try {
                if (bc instanceof Boncommande && bc != null) {
                    if (a instanceof Article && a != null) {
                        current.setPu(a.getPu());
                        current.setIdArticle(a.getIdArticle());
                        Query req = em.createQuery("SELECT o FROM Lignecommande o WHERE o.idBC =? and o.idArticle in (select a.idArticle from Article a where a.description=?)").setParameter(1, bc.getIdBC()).setParameter(2, this.description);
                        Lignecommande lc = (Lignecommande) req.getSingleResult();
                        if (lc != null && lc instanceof Lignecommande) {
                            current.setIdBC(lc.getIdBC());
                            current.setIdLigne(lc.getIdLigne());
                            disablCreate = true;
                            disablUpdate = false;
                            disablDelete = false;
                        } else {
                            current.setIdLigne(null);
                            disablCreate = false;
                            disablUpdate = true;
                            disablDelete = true;
                        }
                    } else {
                        current.setPu(0.0);
                        current.setIdLigne(null);
                        disablCreate = false;
                        disablUpdate = true;
                        disablDelete = true;
                    }
                }
            } catch (Exception e) {
                current.setIdLigne(null);
                e.printStackTrace();
                disablCreate = false;
                disablUpdate = true;
                disablDelete = true;
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Lignecommande> getItemes() {
        try {
            Users user = getUser();
            this.items = new ArrayList<Lignecommande>();
            Query req = em.createQuery("SELECT o FROM Lignecommande o where o.idBC=? and o.idBC in(select bc.idBC from Boncommande bc where bc.idUser=?)").setParameter(1, bc.getIdBC()).setParameter(2, user.getIdUser());
            List<Lignecommande> l = (List<Lignecommande>) req.getResultList();
            items = l;
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return items;
    }

    public Lignecommande getSelected() {
        if (current == null) {
            current = new Lignecommande();
            selectedItemIndex = -1;
        }
        return current;
    }

    private LignecommandeFacade getFacade() {
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
        current = (Lignecommande) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }*/
    public String prepareCreate() {
        current = new Lignecommande();
        selectedItemIndex = -1;
        return "Create";
    }

    public int MaxArticle() {
        try {
            Query req = em.createQuery("select max(a.idArticle) from Article a");
            int max = (Integer) req.getSingleResult();
            if (max != 0) {
                return max;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String create() {
        try {
            Article a = getArticle();
            if (a == null) {
                ut.begin();
                em.joinTransaction();
                a = new Article();
                a.setDescription(description);
                em.persist(a);
                ut.commit();
                current.setIdArticle(MaxArticle());
            } else {
                current.setIdArticle(a.getIdArticle());
            }
            getFacade().edit(current);
            getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LignecommandeCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /*public String prepareEdit() {
        current = (Lignecommande) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }*/
    public String update() {
        try {
            Article a = getArticle();
            if (a == null) {
                ut.begin();
                em.joinTransaction();
                a = new Article();
                a.setDescription(description);
                em.persist(a);
                ut.commit();
                current.setIdArticle(MaxArticle());
            } else {
                current.setIdArticle(a.getIdArticle());
            }
            getFacade().edit(current);
            getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LignecommandeUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /*public String destroy() {
        current = (Lignecommande) getItems().getRowData();
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
            getFacade().remove(current);
            getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LignecommandeDeleted"));
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

    /* public DataModel getItems() {
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

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boncommande getBc() {
        return bc;
    }

    public void setBc(Boncommande bc) {
        this.bc = bc;
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

    public void setSelected(Lignecommande selected) {
        this.current = selected;
    }

    public void setCurrent(Lignecommande current) {
        this.current = current;
    }

    public List<Lignecommande> getItems() {
        return items;
    }

    public void setItems(List<Lignecommande> items) {
        this.items = items;
    }

    public UserTransaction getUt() {
        return ut;
    }

    public void setUt(UserTransaction ut) {
        this.ut = ut;
    }

    public LignecommandeFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(LignecommandeFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setSelectedItemIndex(int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Lignecommande getLignecommande(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Lignecommande.class)
    public static class LignecommandeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LignecommandeController controller = (LignecommandeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lignecommandeController");
            return controller.getLignecommande(getKey(value));
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
            if (object instanceof Lignecommande) {
                Lignecommande o = (Lignecommande) object;
                return getStringKey(o.getIdLigne());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Lignecommande.class.getName());
            }
        }

    }

}
