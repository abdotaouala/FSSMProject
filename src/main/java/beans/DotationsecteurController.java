package beans;

import model.Dotationsecteur;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.DotationsecteurFacade;

import java.io.Serializable;
import java.util.Calendar;
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
import model.Budget;
import model.Compte;
import model.Secteur;

@Named("dotationsecteurController")
@ManagedBean
@SessionScoped
public class DotationsecteurController implements Serializable {

    @PersistenceContext(unitName = "AppFinanciere")
    private EntityManager em;
    private Dotationsecteur current;
    private List<Dotationsecteur> items = null;
    private boolean disablCreate = false;
    private boolean disablUpdate = true;
    private boolean disablDelete = true;
    private Double reliquatCompte;
    private String secteurP;
    private String secteur;
    private Compte cpt;
    private static int year;
    @Inject
    UserTransaction ut;
    @EJB
    private session.DotationsecteurFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DotationsecteurController() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
    }

    public Secteur getSect() {
        Query query = em.createQuery("SELECT o FROM Secteur o WHERE o.intituleSecteur= ? and o.idSecteurP in (select sp.idSecteurP from Secteurprincipal sp where sp.designation=?)").setParameter(1, this.secteur).setParameter(2, this.secteurP);
        Secteur s = (Secteur) query.getSingleResult();
        return s;
    }

    public void subjectSelectionChanged() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        Secteur sect = getSect();
        if (current instanceof Dotationsecteur && current != null) {
            try {
                current.setIdCompte(cpt.getIdCompte());
                Query query = em.createQuery("SELECT o FROM Budget o WHERE o.budgetPK.idCompte=? and o.budgetPK.annee=? ").setParameter(1, cpt.getIdCompte()).setParameter(2, year);
                Budget b = (Budget) query.getSingleResult();
                this.reliquatCompte = b.getReliquat();
                Query req = em.createQuery("SELECT o FROM Dotationsecteur o WHERE o.idSecteur=? and o.idCompte =?").setParameter(1, sect.getIdSecteur()).setParameter(2, current.getIdCompte());
                Dotationsecteur d = (Dotationsecteur) req.getSingleResult();
                if (d != null && b != null) {
                    current.setIdDotation(d.getIdDotation());
                    current.setMontantInitial(d.getMontantInitial());
                    current.setReliquat(d.getReliquat());
                    disablCreate = true;
                    disablUpdate = false;
                    disablDelete = false;
                } else {
                    current.setMontantInitial(-1.0);
                    current.setReliquat(-1.0);
                    disablCreate = false;
                    disablUpdate = true;
                    disablDelete = true;
                }
            } catch (Exception e) {
                current.setMontantInitial(-1.0);
                current.setReliquat(-1.0);
                disablCreate = false;
                disablUpdate = true;
                disablDelete = true;
                e.printStackTrace();
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Dotationsecteur> getItemes() {
        try {
            Query req = em.createQuery("SELECT o FROM Dotationsecteur o");
            items = (List<Dotationsecteur>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        return items;
    }

    public Dotationsecteur getSelected() {
        if (current == null) {
            current = new Dotationsecteur();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DotationsecteurFacade getFacade() {
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

    /*public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Dotationsecteur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }*/
    public String prepareCreate() {
        current = new Dotationsecteur();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            ut.begin();
            em.joinTransaction();
            current.setIdCompte(cpt.getIdCompte());
            Secteur sect = getSect();
            Query req = em.createQuery("SELECT o FROM Budget o WHERE o.budgetPK.annee=? and o.budgetPK.idCompte =?").setParameter(1, year).setParameter(2, cpt.getIdCompte());
            Budget d = (Budget) req.getSingleResult();
            Double reliquat = d.getReliquat();
            if (current.getMontantInitial() < reliquat) {
                reliquat = reliquat - current.getMontantInitial();
                Query req2 = em.createQuery("update Budget o set reliquat = :reliquat where o.budgetPK.annee =:annee and o.budgetPK.idCompte=:idcpt");
                req2.setParameter("reliquat", reliquat);
                req2.setParameter("annee", year);
                req2.setParameter("idcpt", cpt.getIdCompte());
                int updateCount = req2.executeUpdate();
                ut.commit();
                if (updateCount > 0) {
                    current.setIdSecteur(sect.getIdSecteur());
                    this.reliquatCompte = reliquat;
                    current.setReliquat(current.getMontantInitial());
                    getFacade().edit(current);
                }
                getItemes();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DotationsecteurCreated"));
                return prepareCreate();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! vous avez depassé le budget annuel !", "vous avez depassé le Budget de Ce Compte"));
                return null;
            }
        } catch (Exception e) {
            System.out.println("anne:" + year);
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /* public String prepareEdit() {
        current = (Dotationsecteur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }*/
    public String update() {
        try {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            ut.begin();
            em.joinTransaction();
            Secteur sect = getSect();
            Query req = em.createQuery("SELECT o FROM Budget o WHERE o.budgetPK.annee=? and o.budgetPK.idCompte =?").setParameter(1, year).setParameter(2, cpt.getIdCompte());
            Budget d = (Budget) req.getSingleResult();
            Double reliquat = d.getReliquat();
            if (current.getMontantInitial() < reliquat) {
                Double reliquatInitial = current.getReliquat();
                reliquat = reliquat - (current.getMontantInitial() - reliquatInitial);
                Query req2 = em.createQuery("update Budget o set reliquat = :reliquat where o.budgetPK.annee =:annee and o.budgetPK.idCompte=:idcpt");
                req2.setParameter("reliquat", reliquat);
                req2.setParameter("annee", year);
                req2.setParameter("idcpt", cpt.getIdCompte());
                int updateCount = req2.executeUpdate();
                ut.commit();
                if (updateCount > 0) {
                    current.setReliquat(current.getMontantInitial());
                    current.setIdSecteur(sect.getIdSecteur());
                    this.reliquatCompte = reliquat;
                    getFacade().edit(current);
                }
                getItemes();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DotationsecteurUpdated"));
                return "View";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! vous avez depassé le budget annuel !", "vous avez depassé le Budget de Ce Compte"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /*
    public String destroy() {
        current = (Dotationsecteur) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

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
    }*/
    public void performDestroy() {
        try {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            ut.begin();
            em.joinTransaction();
            Secteur sect = getSect();
            Query req = em.createQuery("SELECT o FROM Budget o WHERE o.budgetPK.annee=? and o.budgetPK.idCompte =?").setParameter(1, year).setParameter(2, cpt.getIdCompte());
            Budget d = (Budget) req.getSingleResult();
            Double reliquat = d.getReliquat();
            reliquat = reliquat + current.getMontantInitial();
            Query req2 = em.createQuery("update Budget o set reliquat = :reliquat where o.budgetPK.annee =:annee and o.budgetPK.idCompte=:idcpt");
            req2.setParameter("reliquat", reliquat);
            req2.setParameter("annee", year);
            req2.setParameter("idcpt", cpt.getIdCompte());
            int updateCount = req2.executeUpdate();
            ut.commit();
            if (updateCount > 0) {
                current.setIdSecteur(sect.getIdSecteur());
                this.reliquatCompte = reliquat;
                current.setReliquat(current.getMontantInitial());
                getFacade().remove(current);
            }
            getItemes();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DotationsecteurDeleted"));
        } catch (Exception e) {
            e.printStackTrace();
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

    /*  public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }*/
    private void recreatePagination() {
        pagination = null;
    }

    public List<Dotationsecteur> getItems() {
        return items;
    }

    public void setItems(List<Dotationsecteur> items) {
        this.items = items;
    }

    public static int getYear() {
        return year;
    }

    /*  public String next() {
    getPagination().nextPage();
    recreateModel();
    return "List";
    }
    public String previous() {
    getPagination().previousPage();
    recreateModel();
    return "List";
    }*/
    public static void setYear(int year) {
        DotationsecteurController.year = year;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void setCurrent(Dotationsecteur current) {
        this.current = current;
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

    public Double getReliquatCompte() {
        return reliquatCompte;
    }

    public void setReliquatCompte(Double reliquatCompte) {
        this.reliquatCompte = reliquatCompte;
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

    public DotationsecteurFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(DotationsecteurFacade ejbFacade) {
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

    public Dotationsecteur getDotationsecteur(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Dotationsecteur.class)
    public static class DotationsecteurControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DotationsecteurController controller = (DotationsecteurController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dotationsecteurController");
            return controller.getDotationsecteur(getKey(value));
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
            if (object instanceof Dotationsecteur) {
                Dotationsecteur o = (Dotationsecteur) object;
                return getStringKey(o.getIdDotation());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Dotationsecteur.class.getName());
            }
        }

    }

}
