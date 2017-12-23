package beans;

import model.Boncommande;
import beans.util.JsfUtil;
import beans.util.PaginationHelper;
import java.io.IOException;
import session.BoncommandeFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import model.Article;
import model.Compte;
import model.Dotationsecteur;
import model.Fournisseur;
import model.Lignecommande;
import model.Secteur;
import model.Secteurprincipal;
import model.Users;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
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
            Double TTC = HT + HT * current.getTva() / 100;
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
            reliquatDS = ds.getReliquat();
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
                HT = HT + lc.getPu() * lc.getQuantite();
            }
            Double TTC = HT + HT * current.getTva() / 100;
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
            items = new ArrayList<Boncommande>();
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.idUser=? and o.etat='enCours'").setParameter(1, user.getIdUser());
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Aucune commande n'est enregistrer pour l'utilisateur courant!", "aucun iteme"));
        }
        return items;
    }

    public List<Boncommande> getItemesEtatBC() {
        Users user = getUser();
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.idUser=? and (o.etat='enTraitement' or o.etat='valide' or o.etat='invalide' or o.etat='paye')").setParameter(1, user.getIdUser());
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

    public List<Boncommande> getAllItemesValides() {
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where o.etat='valide' or o.etat='invalide' ");
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure!Aucune Commande n'est validés !", "Secteur Innexistant"));
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

    public List<Boncommande> getAllItemesRechValides() {
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where (o.etat='valide' or o.etat='invalide') and o.type=?").setParameter(1, this.type);
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure! Aucune Commande de ce type n'est en cours de traitement !", "Secteur Innexistant"));
        }
        return items;
    }

    public List<Boncommande> getAllItemesRechValidesTraite() {
        try {
            Query req = em.createQuery("SELECT o FROM Boncommande o where (o.etat='valide' or o.etat='invalide' or o.etat='enTraitement' or o.etat='paye') and o.type=?").setParameter(1, this.type);
            items = (List<Boncommande>) req.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure! Aucune Commande de ce type n'est en cours de traitement !", "Secteur Innexistant"));
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
            current.setIdBC(null);
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
            if(current.getDateReception()!=null){
            current.setEtat("paye");
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
            if (current.getEtat().equals("valide")) {
                if (current.getDateReception() != null) {
                    this.current.setEtat("paye");
                }
            } else {
                current.setEtat("enCours");
            }
            current.setMontant(0.0);
            current.setTva(0);
            current.setDateReception(dateRecep);
            current.setType(type);
            if(current.getDateReception()!=null){
            current.setEtat("paye");
            }
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
                if (current.getDateReception() != null) {
                    this.current.setEtat("paye");
                }
                Query req = em.createQuery("select o from Dotationsecteur o where o.idDotation=?").setParameter(1, current.getIdDotation());
                Dotationsecteur ds = (Dotationsecteur) req.getSingleResult();
                Double d = ds.getReliquat();
                if (d >= current.getMontant()) {
                    Double ancienMontant = ds.getMontantInitial() - ds.getReliquat();
                    d = d - (ancienMontant - current.getMontant());
                    ut.begin();
                    em.joinTransaction();
                    Query req2 = em.createQuery("update Dotationsecteur o set o.reliquat = :Reliquat where o.idDotation = :ds").setParameter("Reliquat", d).setParameter("ds", current.getIdDotation());
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
            e.printStackTrace();
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

    public String updateBCValide() {
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
            if (current.getEtat().equals("valide") || current.getEtat().equals("paye")) {
                if (current.getDateReception() != null) {
                    this.current.setEtat("paye");
                }
                Query req = em.createQuery("select o from Dotationsecteur o where o.idDotation=?").setParameter(1, current.getIdDotation());
                Dotationsecteur ds = (Dotationsecteur) req.getSingleResult();
                Double d = ds.getReliquat();
                if (d >= current.getMontant()) {
                    Double ancienMontant = ds.getMontantInitial() - ds.getReliquat();
                    d = d - (ancienMontant - current.getMontant());
                    ut.begin();
                    em.joinTransaction();
                    Query req2 = em.createQuery("update Dotationsecteur o set o.reliquat = :Reliquat where o.idDotation = :ds").setParameter("Reliquat", d).setParameter("ds", current.getIdDotation());
                    int n = req2.executeUpdate();
                    ut.commit();
                    if (n > 0) {
                        getFacade().edit(current);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous avez dépassé le Reliquat de ce compte !", "Erreur"));
                }
            } else {
                ut.begin();
                em.joinTransaction();
                Query req2 = em.createQuery("update Dotationsecteur o set o.reliquat = o.reliquat + :reliquat where o.idDotation =:dot").setParameter("reliquat", current.getMontant()).setParameter("dot", current.getIdDotation());
                int updateCount = req2.executeUpdate();
                ut.commit();
                if (updateCount > 0) {
                    getFacade().edit(current);
                }
            }
            getAllItemesValides();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeUpdated"));
            return "View";
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure! Modification non effectué !", "Erreur"));
            return null;
        }
    }

    public void performDestroyBCG() {
        try {
            ut.begin();
            em.joinTransaction();
            Query req2 = em.createQuery("update Dotationsecteur o set o.reliquat = o.reliquat + :reliquat where o.idDotation =:dot").setParameter("reliquat", current.getMontant()).setParameter("dot", current.getIdDotation());
            int updateCount = req2.executeUpdate();
            ut.commit();
            if (updateCount > 0) {
                ut.begin();
                em.joinTransaction();
                Query req = em.createQuery("DELETE from Lignecommande o where o.idBC=?").setParameter(1, current.getIdBC());
                req.executeUpdate();
                ut.commit();
                getFacade().remove(current);
            }
            getAllItemesBCG();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeDeleted"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Suppression non effectué !", "Erreur"));
        }
    }

    public void performDestroyBCGValide() {
        try {
            ut.begin();
            em.joinTransaction();
            Query req2 = em.createQuery("update Dotationsecteur o set o.reliquat = o.reliquat + :reliquat where o.idDotation =:dot").setParameter("reliquat", current.getMontant()).setParameter("dot", current.getIdDotation());
            int updateCount = req2.executeUpdate();
            ut.commit();
            if (updateCount > 0) {
                ut.begin();
                em.joinTransaction();
                Query req = em.createQuery("DELETE from Lignecommande o where o.idBC=?").setParameter(1, current.getIdBC());
                req.executeUpdate();
                ut.commit();
                getFacade().remove(current);
            }
            getAllItemesValides();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeDeleted"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Suppression non effectué !", "Erreur"));
        }
    }

    public void performDestroy() {
        try {
            ut.begin();
            em.joinTransaction();
            Query req = em.createQuery("DELETE from Lignecommande o where o.idBC=?").setParameter(1, current.getIdBC());
            req.executeUpdate();
            ut.commit();
            getFacade().remove(current);
            getItemesCours();
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BoncommandeDeleted"));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreure! Suppression non effectué !", "Erreure"));
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
    public Article getArticle(int i){
        Article a=null;
     try {
            Query req = em.createQuery("SELECT o FROM Article o where o.idArticle=?").setParameter(1,i);
            a = (Article) req.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreure!Aucune Commande n'est en cours de traitement !", "Secteur Innexistant"));
        }
    return a;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    //////******************************************************///////*************
    /*
    Etat
     */
    private List<Boncommande> listOfUsers = new ArrayList<Boncommande>();
    HttpServletResponse httpServletResponse;
    ServletOutputStream servletOutputStream;

    JasperPrint jasperPrint;

    public void initBon() throws JRException {
        subjectSelectionChanged();
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
            reliquatDS = ds.getReliquat();
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
            listOfUsers.add(current);
            Lignecommande l1=new Lignecommande();
        Lignecommande l2=new Lignecommande();
        Lignecommande l3=new Lignecommande();
        HashMap map = new HashMap();
            if(list.size()==1){
         l1=list.get(0);
        map.put("cmp", cpt.getIdCompte()+","+cpt.getIntitule());
        map.put("NomFourniseur", f.getNom());
        map.put("qulite", f.getAdresse());
        map.put("cmpt", cpt.getIdCompte());
        map.put("intitule",cpt.getIntitule());
        Article a1=new Article();
         a1=getArticle(l1.getIdArticle());
        map.put("art1", a1.getDescription());
        //map.put("totale", " DH");
       // map.put("totalettc", " DH");
        
        map.put("qte1", l1.getQuantite());
        map.put("pu1", l1.getPu()+" DH");
        map.put("mnt1", list.get(0).getMontant()+" DH");
        map.put("depatement", s.getIntituleSecteur());
        Double ht=l1.getMontant();
        map.put("totalht",ht+" DH");
        map.put("totaltva", current.getTva()+" %");
        map.put("ttc",ht+ht*current.getTva()+" DH");
        //map.put("totalttc", current.getMontant());
        map.put("titre", current.getType());
        
        }else if(list.size()==2){
        l1=list.get(0);
        l2=list.get(1);
        map.put("cmp", cpt.getIdCompte()+","+cpt.getIntitule());
        map.put("NomFourniseur", f.getNom());
        map.put("qulite", f.getAdresse());
        map.put("cmpt", cpt.getIdCompte());
        map.put("intitule",cpt.getIntitule());
        Article a1=new Article();
        Article a2=new Article();
        a1=getArticle(list.get(0).getIdArticle());
        a2=getArticle(list.get(1).getIdArticle());
        map.put("art1", a1.getDescription());
        map.put("art2", a2.getDescription());
        map.put("qte1", l1.getQuantite());
        map.put("qte2",l2.getQuantite());
        map.put("pu1", l1.getPu()+" DH");
        map.put("pu2",l2.getPu()+" DH");
        map.put("mnt1", list.get(0).getMontant()+" DH");
        map.put("mnt2", list.get(1).getMontant()+" DH");
        map.put("depatement", s.getIntituleSecteur());
        Double ht=l1.getMontant()+l2.getMontant();
        map.put("totalht",ht+" DH");
        map.put("totaltva", current.getTva()+" %");
        map.put("ttc",ht+ht*current.getTva()+" DH");
        //map.put("totalttc", current.getMontant());
        map.put("titre", current.getType());
        
        }else if(list.size()==3){
        l1=list.get(0);
        l2=list.get(1);
        l3=list.get(2);
        map.put("cmp", cpt.getIdCompte()+","+cpt.getIntitule());
        map.put("NomFourniseur", f.getNom());
        map.put("qulite", f.getAdresse());
        map.put("cmpt", cpt.getIdCompte());
        map.put("intitule",cpt.getIntitule());
        Article a1=new Article();
        Article a2=new Article();
        Article a3=new Article();
        a1=getArticle(list.get(0).getIdArticle());
        a2=getArticle(list.get(1).getIdArticle());
        a3=getArticle(list.get(2).getIdArticle());
        
        map.put("art1", a1.getDescription());
        map.put("art2", a2.getDescription());
        map.put("art3", a3.getDescription());
        //map.put("totale", " DH");
       // map.put("totalettc", " DH");
        
        map.put("qte1", l1.getQuantite());
        map.put("qte2",l2.getQuantite());
        map.put("qte3", l3.getQuantite());
        map.put("pu1", l1.getPu()+" DH");
        map.put("pu2",l2.getPu()+" DH");
        map.put("pu3",l3.getPu()+" DH");
        map.put("mnt1", list.get(0).getMontant()+" DH");
        map.put("mnt2", list.get(1).getMontant()+" DH");
        map.put("mnt3", list.get(2).getMontant()+" DH");
        map.put("depatement", s.getIntituleSecteur());
        Double ht=l1.getMontant()+l2.getMontant()+l3.getMontant();
        map.put("totalht",ht+" DH");
        map.put("totaltva", current.getTva()+" %");
        map.put("ttc",ht+ht*current.getTva()+" DH");
        }else{
        l1=new Lignecommande();
         l2=new Lignecommande();
        l3=new Lignecommande();
        
        }
/*
        HashMap map = new HashMap();
        //map.put("nom", current.getType());
      //  map.put("grp", current.getIdBC());
        map.put("cmp", cpt.getIdCompte()+","+cpt.getIntitule());

       // map.put("nbrjr", new Date());
        
        //map.put("prix", 100 + " DH");
        //map.put("prix1", " DH");
       // map.put("ttl", " DH");
        map.put("NomFourniseur", f.getNom());
        map.put("qulite", f.getAdresse());
        map.put("cmpt", cpt.getIdCompte());
        map.put("intitule",cpt.getIntitule());
        Article a1=new Article();
        Article a2=new Article();
        Article a3=new Article();
        if(list.size()==1){
         a1=getArticle(list.get(1).getIdArticle());
        }else if(list.size()==2){
        a1=getArticle(list.get(1).getIdArticle());
        a2=getArticle(list.get(2).getIdArticle());
        }else if(list.size()==3){
        a1=getArticle(list.get(1).getIdArticle());
        a2=getArticle(list.get(2).getIdArticle());
        a3=getArticle(list.get(3).getIdArticle());
        }else{
        a1=new Article();
         a2=new Article();
        a3=new Article();
        }
        map.put("art1", a1.getDescription());
        map.put("art2", a2.getDescription());
        map.put("art3", a3.getDescription());
        //map.put("totale", " DH");
       // map.put("totalettc", " DH");
        
        map.put("qte1", l1.getQuantite());
        map.put("qte2",l2.getQuantite());
        map.put("qte3", l3.getQuantite());
        map.put("pu1", l1.getPu()+" DH");
        map.put("pu2",l2.getPu()+" DH");
        map.put("pu3",l3.getPu()+" DH");
        map.put("mnt1", list.get(1).getMontant()+" DH");
        map.put("mnt2", list.get(2).getMontant()+" DH");
        map.put("mnt3", list.get(3).getMontant()+" DH");
        map.put("depatement", s.getIntituleSecteur());
        Double ht=l1.getMontant()+l2.getMontant()+l3.getMontant();
        map.put("totalht",ht+" DH");
        map.put("totaltva", current.getTva()+" %");
        map.put("ttc",ht+ht*current.getTva()+" DH");
        //map.put("totalttc", current.getMontant());
        map.put("titre", current.getType());
*/
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfUsers, false);
        listOfUsers = new ArrayList<Boncommande>();
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("report/report3.jasper");
        jasperPrint = JasperFillManager.fillReport(reportPath, map, beanCollectionDataSource);
        httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            // JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
        
    }

    public void PDBon(ActionEvent actionEvent) throws JRException, IOException {
        initBon();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
        servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void DOCXBon(ActionEvent actionEvent) throws JRException, IOException {
        initBon();
        httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.docx");
        servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
    }

    public void XLSXBon(ActionEvent actionEvent) throws JRException, IOException {
        initBon();
        httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
        servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
    }

}
