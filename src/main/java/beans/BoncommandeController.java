package beans;

import model.Boncommande;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import session.BoncommandeFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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
import model.Fournisseur;
import model.Lignecommande;
import model.Secteur;
import model.Secteurprincipal;
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
    private Boncommande current;
    private String type;
    private String nomFournisseur;
    private Date dateRecep;
    private Double reliquatDS;
    @Inject
    UserTransaction ut;
    private List<Boncommande> items = null;
    @EJB
    private session.BoncommandeFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public BoncommandeController() {

    }

    public Dotationsecteur getDS() {
        Dotationsecteur d = null;
        try {
            Secteur sect = getSect();
            Query req = em.createQuery("SELECT o FROM Dotationsecteur o WHERE o.idSecteur=? and o.idCompte =?").setParameter(1, sect.getIdSecteur()).setParameter(2, cpt.getIdCompte());
            d = (Dotationsecteur) req.getSingleResult();
        } catch (Exception e) {
            disablCreate = true;
            disablUpdate = true;
            disablDelete = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Se Secteur N'existe pas!", "Secteur Innexistant"));
            return null;
        }
        return d;
    }

    public Fournisseur getFournisseur() {
        Fournisseur c = null;
        try {
            Query req = em.createQuery("SELECT o FROM Fournisseur o WHERE o.nom =?").setParameter(1, this.nomFournisseur);
            c = (Fournisseur) req.getSingleResult();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure! Fournisseur innexistant !", "erreue"));
        }
        return c;
    }

    public int MaxFournisseur() {
        try {
            Query req = em.createQuery("select max(a.idFournisseur) from Fournisseur a");
            int max = (Integer) req.getSingleResult();
            if (max != 0) {
                return max;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void remplireFormulaire() {
        Dotationsecteur ds = null;
        Secteur s = null;
        Secteurprincipal sp = null;
        try {
            if (cpt == null) {
                cpt = new Compte();
            }
            type = current.getType();
            dateRecep = current.getDateReception();
            Query req = em.createQuery("SELECT o FROM Dotationsecteur o WHERE o.idDotation=?").setParameter(1, current.getIdDotation());
            ds = (Dotationsecteur) req.getSingleResult();
            cpt.setIdCompte(ds.getIdCompte());
            Query req2 = em.createQuery("SELECT o FROM Secteur o WHERE o.idSecteur= ?").setParameter(1, ds.getIdSecteur());
            s = (Secteur) req2.getSingleResult();
            this.secteur = s.getIntituleSecteur();
            Query req3 = em.createQuery("SELECT o FROM Secteurprincipal o WHERE o.idSecteurP= ?").setParameter(1, s.getIdSecteurP());
            sp = (Secteurprincipal) req3.getSingleResult();
            this.secteurP = sp.getDesignation();
            Query req4 = em.createQuery("select o from Lignecommande o where o.idBC = ?").setParameter(1, current.getIdBC());
            List<Lignecommande> list = req4.getResultList();
            Query req5 = em.createQuery("SELECT o FROM Fournisseur o WHERE o.idFournisseur= ?").setParameter(1, current.getIdFournisseur());
            Fournisseur f = (Fournisseur) req5.getSingleResult();
            this.nomFournisseur = f.getNom();
            Double HT = 0.0;
            for (Lignecommande lc : list) {
                HT = HT + lc.getMontant();
            }
            Double TTC = HT + HT * current.getTva()/100;
            current.setMontant(TTC);
            subjectSelectionChangedPBC();
        } catch (Exception e) {
            // e.printStackTrace();
            // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void subjectSelectionChangedPBC() {
        //Boolean b = current instanceof Boncommande;
        if (current == null) {
            current = new Boncommande();
        }
        ds = null;
        ds = getDS();
        if (current instanceof Boncommande && current != null) {
            type = current.getType();
            if (ds != null) {
                Boncommande bc = getbon();
                current.setIdDotation(ds.getIdDotation());
                if (bc instanceof Boncommande && bc != null) {
                    Users user = getUser();
                    if (user.getIdUser() == bc.getIdUser()) {
                        current.setIdBC(bc.getIdBC());
                        current.setDateCommande(bc.getDateCommande());
                        current.setIdUser(user.getIdUser());
                        current.setDateReception(bc.getDateReception());
                        type = bc.getType();
                        disablCreate = true;
                        disablUpdate = false;
                        disablDelete = false;
                    } else {
                        current.setDateReception(dateRecep);
                        disablCreate = true;
                        disablUpdate = true;
                        disablDelete = true;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! vous n'etes pas le proprietaire de cet Engagement !", "erreue"));
                    }
                }
            } else {
                disablCreate = true;
                disablUpdate = true;
                disablDelete = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! secteur innexistant dans ce secteur principal", "erreue"));
            }
            current.setDateCommande(new Date());
        }
    }

    public void remplireFormulaireGBC() {
        Dotationsecteur ds = null;
        Secteur s = null;
        Secteurprincipal sp = null;
        try {
            if (cpt == null) {
                cpt = new Compte();
            }
            type = current.getType();
            dateRecep = current.getDateReception();
            Query req = em.createQuery("SELECT o FROM Dotationsecteur o WHERE o.idDotation=?").setParameter(1, current.getIdDotation());
            ds = (Dotationsecteur) req.getSingleResult();
            reliquatDS=ds.getReliquat();
            cpt.setIdCompte(ds.getIdCompte());
            Query req2 = em.createQuery("SELECT o FROM Secteur o WHERE o.idSecteur= ?").setParameter(1, ds.getIdSecteur());
            s = (Secteur) req2.getSingleResult();
            this.secteur = s.getIntituleSecteur();
            Query req3 = em.createQuery("SELECT o FROM Secteurprincipal o WHERE o.idSecteurP= ?").setParameter(1, s.getIdSecteurP());
            sp = (Secteurprincipal) req3.getSingleResult();
            this.secteurP = sp.getDesignation();
            Query req4 = em.createQuery("select o from Lignecommande o where o.idBC = ?").setParameter(1, current.getIdBC());
            List<Lignecommande> list = req4.getResultList();
            Query req5 = em.createQuery("SELECT o FROM Fournisseur o WHERE o.idFournisseur= ?").setParameter(1, current.getIdFournisseur());
            Fournisseur f = (Fournisseur) req5.getSingleResult();
            this.nomFournisseur = f.getNom();
            Double HT = 0.0;
            for (Lignecommande lc : list) {
                //HT = HT + lc.getMontant();
                HT = HT + lc.getPu()*lc.getQuantite();
            }
            Double TTC = HT + HT * current.getTva()/100;
            current.setMontant(TTC);
            subjectSelectionChanged();
        } catch (Exception e) {
             e.printStackTrace();
            // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void subjectSelectionChanged() {
        //Boolean b = current instanceof Boncommande;
        if (current == null) {
            current = new Boncommande();
        }
        ds = null;
        ds = getDS();
        if (current instanceof Boncommande && current != null) {
            type = current.getType();
            if (ds != null) {
                Boncommande bc = getbon();
                current.setIdDotation(ds.getIdDotation());
                if (bc instanceof Boncommande && bc != null) {
                    Users user = getUser();
                    if (user.getIdUser() == bc.getIdUser()) {
                        current.setIdBC(bc.getIdBC());
                        current.setDateCommande(bc.getDateCommande());
                        current.setIdUser(user.getIdUser());
                        current.setDateReception(bc.getDateReception());
                        type = bc.getType();
                        //current.setMontant(current.getMontant()+current.getMontant()*current.getTva()/100);
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! vous n'etes pas le proprietaire de cet Engagement !", "erreue"));
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! secteur innexistant dans ce secteur principal", "erreue"));
            }
        }
    }

    public Boncommande getbon() {
        ds = getDS();
        Boncommande bc = null;
        try {
            Query req2 = em.createQuery("SELECT o FROM Boncommande o WHERE o.idBC =? and o.idDotation=?").setParameter(1, current.getIdBC()).setParameter(2, ds.getIdDotation());
            bc = (Boncommande) req2.getSingleResult();
        } catch (Exception e) {
            disablCreate = false;
            disablUpdate = true;
            disablDelete = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bon de Commande inexistante!", "Information"));
            return null;
        }
        return bc;
    }

    public List<Boncommande> completeText(String id) {
        List<Boncommande> FiltredComptes = new ArrayList<Boncommande>();
        try {
            List<Boncommande> AllComptes = getAllItemes();
            for (Boncommande bc : AllComptes) {
                if (bc.getIdBC().toString().startsWith(id)) {
                    FiltredComptes.add(bc);
                }
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! probleme de conversion !", "Erreur"));
        }
        return FiltredComptes;
    }

    public Secteur getSect() {
        try {
            Query query = em.createQuery("SELECT o FROM Secteur o WHERE o.intituleSecteur= ? and o.idSecteurP in (select sp.idSecteurP from Secteurprincipal sp where sp.designation=?)").setParameter(1, this.secteur).setParameter(2, this.secteurP);
            Secteur s = (Secteur) query.getSingleResult();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Se Secteur N'existe pas!", "Secteur Innexistant dans ce secteur principal"));
            return null;
        }
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Aucune commande n'est enregistrer pour l'utilisateur courant!", "aucun iteme"));
        }
        return items;
    }

    public List<Boncommande> getItemesCours() {
        Users user = getUser();
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.idUser=? and o.etat='enCours'").setParameter(1, user.getIdUser());
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Aucune commande n'est enregistrer pour l'utilisateur courant!", "aucun iteme"));
        }
        return items;
    }

    public List<Boncommande> getAllItemes() {
        Users user = getUser();
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.idUser=?").setParameter(1, user.getIdUser());
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Aucune commande n'est enregistrer pour l'utilisateur courant!", "aucun iteme"));
        }
        return items;
    }

    public List<Boncommande> getAllItemesBCG() {
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.etat='enTraitement'");
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure!Aucune Commande n'est en cours de traitement !", "Secteur Innexistant"));
        }
        return items;
    }

    public List<Boncommande> getAllItemesRech() {
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.etat='enTraitement' and o.type=?").setParameter(1, this.type);
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure! Aucune Commande de ce type n'est en cours de traitement !", "Secteur Innexistant"));
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

    public Boncommande getCurrent() {
        /* if (current == null) {
            current = new Boncommande();
            selectedItemIndex = -1;
        }*/
        return current;
    }

    public void setCurrent(Boncommande current) {
        this.current = current;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
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

            //boolean b=current instanceof Boncommande;
            if (current == null) {
                current = new Boncommande();
            }
            this.current.setEtat("enCours");
            this.current.setDateCommande(new Date());
            current.setMontant(0.0);
            current.setTva(0);
            current.setDateReception(dateRecep);
            current.setType(type);
            ds = null;
            ds = getDS();
            Users user = getUser();
            current.setIdUser(user.getIdUser());
            if (current instanceof Boncommande && current != null) {
                if (ds != null) {
                    Boncommande bc = getbon();
                    current.setIdDotation(ds.getIdDotation());
                    if (bc instanceof Boncommande && bc != null) {
                        if (user.getIdUser() == bc.getIdUser()) {
                            current.setIdBC(bc.getIdBC());
                            current.setDateCommande(bc.getDateCommande());
                            current.setIdUser(user.getIdUser());
                            current.setDateReception(bc.getDateReception());
                        }
                    }
                }
            }
            getFacade().edit(current);
            Query req = em.createQuery("select max(a.idBC) from Boncommande a");
            int max = (Integer) req.getSingleResult();
            current.setIdBC(max);
            getItemesCours();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Boncommande Number:+'" + max + "' Created !", "Succes"));
            return prepareCreate();
        } catch (Exception e) {
            e.printStackTrace();
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! '" + current.getIdBC() + "'", "Erreur"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Ajout non effectué !" + e.getMessage(), "Erreur"));
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
            this.current.setEtat("enCours");
            current.setMontant(0.0);
            current.setTva(0);
            current.setDateReception(dateRecep);
            current.setType(type);
            getFacade().edit(current);
            getItemesCours();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeUpdated"));
            return "View";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Modification non effectué !", "Erreur"));
            return null;
        }
    }

    public String updateBC() {
        try {
            Fournisseur a = getFournisseur();
            if (a == null) {
                ut.begin();
                em.joinTransaction();
                a = new Fournisseur();
                a.setNom(nomFournisseur);
                em.persist(a);
                ut.commit();
                current.setIdFournisseur(MaxFournisseur());
            } else {
                current.setIdFournisseur(a.getIdFournisseur());
            }
            if (current.getEtat().equals("valide")) {
                Query req = em.createQuery("select o.reliquat from Dotationsecteur o where o.idDotation=?").setParameter(1, current.getIdDotation());
                Double d = (Double) req.getSingleResult();
                if (d >= current.getMontant()) {
                    ut.begin();
                    em.joinTransaction();
                    Query req2 = em.createQuery("update Dotationsecteur o set o.reliquat = o.reliquat - :Reliquat where o.idDotation = :ds").setParameter("Reliquat", current.getMontant()).setParameter("ds", current.getIdDotation());
                    int n = req2.executeUpdate();
                    ut.commit();
                    if (n > 0) {
                        getFacade().edit(current);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous avez dépassé le Reliquat de ce compte !", "Erreur"));
                }
            } else {
                getFacade().edit(current);
            }
            getAllItemesBCG();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeUpdated"));
            return "View";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure! Modification non effectué !", "Erreur"));
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

    public void performDestroyBCG() {
        try {
            Query req = em.createQuery("DELETE from Lignecommande where o.idBC=?").setParameter(1, current.getIdBC());
            req.executeUpdate();
            getFacade().remove(current);
            getAllItemesBCG();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeDeleted"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Suppression non effectué !", "Erreur"));
        }
    }

    public void performDestroy() {
        try {
            // Query req = em.createQuery("DELETE from Lignecommande where o.idBC=?").setParameter(1, current.getIdBC());
            //  req.executeUpdate();
            getFacade().remove(current);
            getItemesCours();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeDeleted"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Suppression non effectué !", "Erreur"));
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Boncommande> getItems() {
        return items;
    }

    public void setItems(List<Boncommande> items) {
        this.items = items;
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

    public Double getReliquatDS() {
        return reliquatDS;
    }

    public void setReliquatDS(Double reliquatDS) {
        this.reliquatDS = reliquatDS;
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

    public Date getDateRecep() {
        return dateRecep;
    }

    public void setDateRecep(Date dateRecep) {
        this.dateRecep = dateRecep;
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

    public void setPagination(PaginationHelper pagination) {
        this.pagination = pagination;
    }

}
