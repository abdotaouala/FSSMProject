/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  29/05/2016 18:50:31                      */
/*==============================================================*/


drop table if exists AnneeBudgetaire;

drop table if exists Article;

drop table if exists BUDGET;

drop table if exists BonCommande;

drop table if exists BordereauAutorisation;

drop table if exists BordereauComptable;

drop table if exists Compte;

drop table if exists CompteBc;

drop table if exists Departement;

drop table if exists Deplacement;

drop table if exists Detail;

drop table if exists DossierHSupp;

drop table if exists DossierProvisoir;

drop table if exists DossierRejete;

drop table if exists DossierVacataire;

drop table if exists DotationSecteur;

drop table if exists EtatDossier;

drop table if exists Filiere;

drop table if exists Fournisseur;

drop table if exists GradDiplome;

drop table if exists IndemneteDeplacementEtranger;

drop table if exists IndemneteDeplacementInterne;

drop table if exists IndemnteKm;

drop table if exists Intervenant;

drop table if exists LIGNECOMMANDE;

drop table if exists Pays;

drop table if exists PieceJustificativeDeplacement;

drop table if exists PieceJustificativeVacation;

drop table if exists PrixKilomitrique;

drop table if exists PrixRepas;

drop table if exists PrixSejour;

drop table if exists Releve;

drop table if exists Role;

drop table if exists Secteur;

drop table if exists SecteurPrincipal;

drop table if exists TypeFormations;

drop table if exists USER_ROLE;

drop table if exists Users;

drop table if exists Voiture;

/*==============================================================*/
/* Table : AnneeBudgetaire                                      */
/*==============================================================*/
create table AnneeBudgetaire
(
   annee                int not null auto_increment,
   montantRap           numeric(8,0),
   reliquatRap          numeric(8,0),
   primary key (annee)
);

/*==============================================================*/
/* Table : Article                                              */
/*==============================================================*/
create table Article
(
   idArticle            int not null auto_increment,
   description          varchar(254),
   pu                   numeric(8,0),
   primary key (idArticle)
);

/*==============================================================*/
/* Table : BUDGET                                               */
/*==============================================================*/
create table BUDGET
(
   idCompte             int not null,
   annee                int not null,
   budgetAnnuel         numeric(8,0),
   reliquat             numeric(8,0),
   primary key (idCompte, annee)
);

/*==============================================================*/
/* Table : BonCommande                                          */
/*==============================================================*/
create table BonCommande
(
   idBC                 int not null auto_increment,
   idFournisseur        int not null,
   idUser               int not null,
   idDotation           int not null,
   dateCommande         datetime,
   tva                  int,
   dateReception        datetime,
   etat                 varchar(254),
   montant              numeric(8,0),
   type                 varchar(254),
   primary key (idBC)
);

/*==============================================================*/
/* Table : BordereauAutorisation                                */
/*==============================================================*/
create table BordereauAutorisation
(
   idBordAut            int not null auto_increment,
   anneeUniversitaire   int,
   primary key (idBordAut)
);

/*==============================================================*/
/* Table : BordereauComptable                                   */
/*==============================================================*/
create table BordereauComptable
(
   idBordComp           int not null auto_increment,
   annee                int not null,
   dateExercice         datetime,
   totalIr              numeric(8,0),
   totalNet             numeric(8,0),
   primary key (idBordComp)
);

/*==============================================================*/
/* Table : Compte                                               */
/*==============================================================*/
create table Compte
(
   idCompte             int not null auto_increment,
   intitule             varchar(254),
   rap                  numeric(8,0),
   primary key (idCompte)
);

/*==============================================================*/
/* Table : CompteBc                                             */
/*==============================================================*/
create table CompteBc
(
   idCptBc              int not null auto_increment,
   cinPpr               varchar(254) not null,
   intitule             varchar(254),
   bc                   varchar(254),
   rib                  varchar(254),
   primary key (idCptBc)
);

/*==============================================================*/
/* Table : Departement                                          */
/*==============================================================*/
create table Departement
(
   idDep                int not null auto_increment,
   idUser               int,
   nomDep               varchar(254),
   primary key (idDep)
);

/*==============================================================*/
/* Table : Deplacement                                          */
/*==============================================================*/
create table Deplacement
(
   idDeplacement        int not null auto_increment,
   cinPpr               varchar(254) not null,
   idUser               int not null,
   idPays               int,
   nbrJours             int,
   dateDepart           datetime,
   dateArrive           datetime,
   annee                int,
   motifDeplacement     varchar(254),
   dateCreation         datetime,
   statutMnt            int,
   indice               int,
   echelle              int,
   grade                varchar(254),
   primary key (idDeplacement)
);

/*==============================================================*/
/* Table : Detail                                               */
/*==============================================================*/
create table Detail
(
   idDetail             int not null auto_increment,
   salaireAnnuelleBrut  numeric(8,0),
   allocationFamiliale  numeric(8,0),
   brutAdditionner      numeric(8,0),
   amo                  numeric(8,0),
   retenuCmr            numeric(8,0),
   mutuelleMutialiste   numeric(8,0),
   mutuelleCaisse       numeric(8,0),
   rachatCmr            numeric(8,0),
   sommeDeduire         numeric(8,0),
   nbrEnfant            int,
   conjoint             bool,
   chargeFamiliale      numeric(8,0),
   irSource             numeric(8,0),
   irComplement         numeric(8,0),
   net                  numeric(8,0),
   ir                   numeric(8,0),
   echelle              varchar(254),
   echelon              varchar(254),
   primary key (idDetail)
);

/*==============================================================*/
/* Table : DossierHSupp                                         */
/*==============================================================*/
create table DossierHSupp
(
   idDossier            int not null auto_increment,
   cinPpr               varchar(254) not null,
   idBordAut            int,
   idDossierProv        int not null,
   idBordComp           int,
   idDotation           int not null,
   idGrade              int,
   idDetail             int not null,
   nbrHeures            int,
   mois                 varchar(254),
   semestre             varchar(254),
   dateCreance          datetime,
   montantHsupp         numeric(8,0),
   statutDossier        varchar(254),
   primary key (idDossier)
);

/*==============================================================*/
/* Table : DossierProvisoir                                     */
/*==============================================================*/
create table DossierProvisoir
(
   idDossierProv        int not null auto_increment,
   idRelever            int,
   idGrade              int,
   nomComplet           varchar(254),
   dernierDiplome       varchar(254),
   nbrHeures            int,
   module               varchar(254),
   etat                 bool,
   dateDebut            datetime,
   dateFin              datetime,
   primary key (idDossierProv)
);

/*==============================================================*/
/* Table : DossierRejete                                        */
/*==============================================================*/
create table DossierRejete
(
   idDossierRejete      int not null auto_increment,
   idDossier            int not null,
   Dos_idDossier        int not null,
   motifRejet           varchar(254),
   primary key (idDossierRejete)
);

/*==============================================================*/
/* Table : DossierVacataire                                     */
/*==============================================================*/
create table DossierVacataire
(
   idDossier            int not null auto_increment,
   cinPpr               varchar(254) not null,
   idBordAut            int,
   idDossierProv        int not null,
   idBordComp           int,
   idDotation           int not null,
   idGrade              int,
   nbrHeures            int,
   mois                 varchar(254),
   semestre             varchar(254),
   dateCreance          datetime,
   net                  numeric(8,0),
   ir                   numeric(8,0),
   statutDossier        varchar(254),
   primary key (idDossier)
);

/*==============================================================*/
/* Table : DotationSecteur                                      */
/*==============================================================*/
create table DotationSecteur
(
   idCompte             int not null,
   idSecteur            int not null,
   idDotation           int not null auto_increment,
   montantInitial       numeric(8,0),
   reliquat             numeric(8,0),
   primary key (idDotation)
);

/*==============================================================*/
/* Table : EtatDossier                                          */
/*==============================================================*/
create table EtatDossier
(
   idEtat               int not null auto_increment,
   idDeplacement        int not null,
   etat                 int,
   motif                varchar(254),
   rmq                  varchar(254),
   observation          varchar(254),
   dateEtat             datetime,
   primary key (idEtat)
);

/*==============================================================*/
/* Table : Filiere                                              */
/*==============================================================*/
create table Filiere
(
   idFiliere            int not null auto_increment,
   idDep                int not null,
   idType               int not null,
   idUser               int not null,
   intitule             varchar(254),
   primary key (idFiliere)
);

/*==============================================================*/
/* Table : Fournisseur                                          */
/*==============================================================*/
create table Fournisseur
(
   idFournisseur        int not null auto_increment,
   nom                  varchar(254),
   adresse              varchar(254),
   tel                  varchar(254),
   email                varchar(254),
   primary key (idFournisseur)
);

/*==============================================================*/
/* Table : GradDiplome                                          */
/*==============================================================*/
create table GradDiplome
(
   idGrade              int not null auto_increment,
   intituleGrade        varchar(254),
   taux                 int,
   primary key (idGrade)
);

/*==============================================================*/
/* Table : IndemneteDeplacementEtranger                         */
/*==============================================================*/
create table IndemneteDeplacementEtranger
(
   idDeplacement        int not null auto_increment,
   idDepEx              int not null,
   idPrixSej            int,
   idDotation           int not null,
   mntDepEx             int,
   primary key (idDeplacement, idDepEx)
);

/*==============================================================*/
/* Table : IndemneteDeplacementInterne                          */
/*==============================================================*/
create table IndemneteDeplacementInterne
(
   idDeplacement        int not null auto_increment,
   idIndDep             int not null,
   idPrixRepas          int,
   idDotation           int not null,
   montantDepInt        numeric(8,0),
   primary key (idDeplacement, idIndDep)
);

/*==============================================================*/
/* Table : IndemnteKm                                           */
/*==============================================================*/
create table IndemnteKm
(
   idDeplacement        int not null auto_increment,
   idIndKm              int not null,
   idDotation           int not null,
   montantDepInt        numeric(8,0),
   kmRoute              numeric(8,0),
   kmPiste              numeric(8,0),
   mntKm                int,
   primary key (idDeplacement, idIndKm)
);

/*==============================================================*/
/* Table : Intervenant                                          */
/*==============================================================*/
create table Intervenant
(
   cinPpr               varchar(254) not null,
   idUser               int not null,
   nomComplet           varchar(254),
   nomArabe             varchar(254),
   telephone            varchar(254),
   mail                 varchar(254),
   primary key (cinPpr)
);

/*==============================================================*/
/* Table : LIGNECOMMANDE                                        */
/*==============================================================*/
create table LIGNECOMMANDE
(
   idBC                 int not null,
   idArticle            int not null,
   idLigne              int not null auto_increment,
   quantite             int,
   pu                   numeric(8,0),
   montant              numeric(8,0),
   primary key (idLigne)
);

/*==============================================================*/
/* Table : Pays                                                 */
/*==============================================================*/
create table Pays
(
   idPays               int not null auto_increment,
   ville                varchar(254),
   nomPays              varchar(254),
   primary key (idPays)
);

/*==============================================================*/
/* Table : PieceJustificativeDeplacement                        */
/*==============================================================*/
create table PieceJustificativeDeplacement
(
   idPiece              int not null auto_increment,
   idDeplacement        int not null,
   datePiece            datetime,
   intutilePiece        varchar(254),
   piece                varchar(254),
   primary key (idPiece)
);

/*==============================================================*/
/* Table : PieceJustificativeVacation                           */
/*==============================================================*/
create table PieceJustificativeVacation
(
   idPiece              int not null auto_increment,
   idDossier            int not null,
   Dos_idDossier        int not null,
   datePiece            datetime,
   intutilePiece        varchar(254),
   piece                varchar(254),
   primary key (idPiece)
);

/*==============================================================*/
/* Table : PrixKilomitrique                                     */
/*==============================================================*/
create table PrixKilomitrique
(
   idPrixKilo           int not null auto_increment,
   kmSup                numeric(8,0),
   kmInf                numeric(8,0),
   prixRoute            numeric(8,0),
   prixPiste            numeric(8,0),
   primary key (idPrixKilo)
);

/*==============================================================*/
/* Table : PrixRepas                                            */
/*==============================================================*/
create table PrixRepas
(
   idPrixRepas          int not null auto_increment,
   indiceSup            int,
   indiceInf            int,
   prixDs               numeric(8,0),
   prixD                numeric(8,0),
   prixRs               numeric(8,0),
   primary key (idPrixRepas)
);

/*==============================================================*/
/* Table : PrixSejour                                           */
/*==============================================================*/
create table PrixSejour
(
   idPrixSej            int not null auto_increment,
   grade                int,
   prix                 int,
   primary key (idPrixSej)
);

/*==============================================================*/
/* Table : Releve                                               */
/*==============================================================*/
create table Releve
(
   idRelever            int not null auto_increment,
   idFiliere            int not null,
   semestre             varchar(254),
   anneeUniversitaire   varchar(254),
   primary key (idRelever)
);

/*==============================================================*/
/* Table : Role                                                 */
/*==============================================================*/
create table Role
(
   idRole               int not null auto_increment,
   nom                  varchar(254),
   primary key (idRole)
);

/*==============================================================*/
/* Table : Secteur                                              */
/*==============================================================*/
create table Secteur
(
   idSecteur            int not null auto_increment,
   idSecteurP           int,
   intituleSecteur      varchar(254),
   primary key (idSecteur)
);

/*==============================================================*/
/* Table : SecteurPrincipal                                     */
/*==============================================================*/
create table SecteurPrincipal
(
   idSecteurP           int not null auto_increment,
   designation          varchar(254),
   primary key (idSecteurP)
);

/*==============================================================*/
/* Table : TypeFormations                                       */
/*==============================================================*/
create table TypeFormations
(
   idType               int not null auto_increment,
   nomType              varchar(254),
   primary key (idType)
);

/*==============================================================*/
/* Table : USER_ROLE                                            */
/*==============================================================*/
create table USER_ROLE
(
   idUser               int not null,
   idRole               int not null,
   idUserRole           int not null auto_increment,
   primary key (idUserRole)
);

/*==============================================================*/
/* Table : Users                                                */
/*==============================================================*/
create table Users
(
   idUser               int not null auto_increment,
   login                varchar(254),
   password             varchar(254),
   nom                  varchar(254),
   prenom               varchar(254),
   telephone            varchar(254),
   email                varchar(254),
   img                  varchar(254),
   primary key (idUser)
);

/*==============================================================*/
/* Table : Voiture                                              */
/*==============================================================*/
create table Voiture
(
   idVoiture            int not null auto_increment,
   idPrixKilo           int not null,
   idDeplacement        int not null,
   idIndKm              int not null,
   marque               varchar(254),
   puissance            int,
   primary key (idVoiture)
);

alter table BUDGET add constraint FK_BUDGET_annee foreign key (annee)
      references AnneeBudgetaire (annee) on delete restrict on update restrict;

alter table BUDGET add constraint FK_BUDGET_compte foreign key (idCompte)
      references Compte (idCompte) on delete restrict on update restrict;

alter table BonCommande add constraint FK_fournir_BN_Forns foreign key (idFournisseur)
      references Fournisseur (idFournisseur) on delete restrict on update restrict;

alter table BonCommande add constraint FK_payer_BN_sect foreign key (idDotation)
      references DotationSecteur (idDotation) on delete restrict on update restrict;

alter table BonCommande add constraint FK_proposer_BN_Users foreign key (idUser)
      references Users (idUser) on delete restrict on update restrict;

alter table BordereauComptable add constraint FK_derouler_compt_Bcompt foreign key (annee)
      references AnneeBudgetaire (annee) on delete restrict on update restrict;

alter table CompteBc add constraint FK_appartient_compte foreign key (cinPpr)
      references Intervenant (cinPpr) on delete restrict on update restrict;

alter table Departement add constraint FK_gerer_dept foreign key (idUser)
      references Users (idUser) on delete restrict on update restrict;

alter table Deplacement add constraint FK_faitA foreign key (idPays)
      references Pays (idPays) on delete restrict on update restrict;

alter table Deplacement add constraint FK_intervenantsDeplacement foreign key (cinPpr)
      references Intervenant (cinPpr) on delete restrict on update restrict;

alter table Deplacement add constraint FK_usersDeplacement foreign key (idUser)
      references Users (idUser) on delete restrict on update restrict;

alter table DossierHSupp add constraint FK_appartenir_sect_HSupp foreign key (idDotation)
      references DotationSecteur (idDotation) on delete restrict on update restrict;

alter table DossierHSupp add constraint FK_attribuer_grade_HSupp foreign key (idGrade)
      references GradDiplome (idGrade) on delete restrict on update restrict;

alter table DossierHSupp add constraint FK_avoir foreign key (idDetail)
      references Detail (idDetail) on delete restrict on update restrict;

alter table DossierHSupp add constraint FK_concerner_doss_HSupp foreign key (idDossierProv)
      references DossierProvisoir (idDossierProv) on delete restrict on update restrict;

alter table DossierHSupp add constraint FK_contenir_HSupp_Autors foreign key (idBordAut)
      references BordereauAutorisation (idBordAut) on delete restrict on update restrict;

alter table DossierHSupp add constraint FK_contenir_HSupp_Bcompt foreign key (idBordComp)
      references BordereauComptable (idBordComp) on delete restrict on update restrict;

alter table DossierHSupp add constraint FK_effectuer_interv_HSupp foreign key (cinPpr)
      references Intervenant (cinPpr) on delete restrict on update restrict;

alter table DossierProvisoir add constraint FK_attribuer_grade_dossPrev foreign key (idGrade)
      references GradDiplome (idGrade) on delete restrict on update restrict;

alter table DossierProvisoir add constraint FK_contenir_doss_relever foreign key (idRelever)
      references Releve (idRelever) on delete restrict on update restrict;

alter table DossierRejete add constraint FK_rejeter_Hsupp foreign key (Dos_idDossier)
      references DossierHSupp (idDossier) on delete restrict on update restrict;

alter table DossierRejete add constraint FK_rejeter_vacation foreign key (idDossier)
      references DossierVacataire (idDossier) on delete restrict on update restrict;

alter table DossierVacataire add constraint FK_appartenir_sect_vacat foreign key (idDotation)
      references DotationSecteur (idDotation) on delete restrict on update restrict;

alter table DossierVacataire add constraint FK_attribuer_grade_vacataire foreign key (idGrade)
      references GradDiplome (idGrade) on delete restrict on update restrict;

alter table DossierVacataire add constraint FK_concerner_dossProvis_vacat foreign key (idDossierProv)
      references DossierProvisoir (idDossierProv) on delete restrict on update restrict;

alter table DossierVacataire add constraint FK_contenir_Bcompt_vacat foreign key (idBordComp)
      references BordereauComptable (idBordComp) on delete restrict on update restrict;

alter table DossierVacataire add constraint FK_contenir_vacat_autoris foreign key (idBordAut)
      references BordereauAutorisation (idBordAut) on delete restrict on update restrict;

alter table DossierVacataire add constraint FK_effectuer_interv_vacat foreign key (cinPpr)
      references Intervenant (cinPpr) on delete restrict on update restrict;

alter table DotationSecteur add constraint FK_repartirSecteur_compte foreign key (idCompte)
      references Compte (idCompte) on delete restrict on update restrict;

alter table DotationSecteur add constraint FK_repartirSecteur_sect foreign key (idSecteur)
      references Secteur (idSecteur) on delete restrict on update restrict;

alter table EtatDossier add constraint FK_accepterEtat foreign key (idDeplacement)
      references Deplacement (idDeplacement) on delete restrict on update restrict;

alter table Filiere add constraint FK_assumer_fill_users foreign key (idUser)
      references Users (idUser) on delete restrict on update restrict;

alter table Filiere add constraint FK_contenir_dept_fill foreign key (idDep)
      references Departement (idDep) on delete restrict on update restrict;

alter table Filiere add constraint FK_inclure_type_form_fill foreign key (idType)
      references TypeFormations (idType) on delete restrict on update restrict;

alter table IndemneteDeplacementEtranger add constraint FK_GENERALISATION_3 foreign key (idDeplacement)
      references Deplacement (idDeplacement) on delete restrict on update restrict;

alter table IndemneteDeplacementEtranger add constraint FK_calculePrixSejour foreign key (idPrixSej)
      references PrixSejour (idPrixSej) on delete restrict on update restrict;

alter table IndemneteDeplacementEtranger add constraint FK_indmntEtrng foreign key (idDotation)
      references DotationSecteur (idDotation) on delete restrict on update restrict;

alter table IndemneteDeplacementInterne add constraint FK_GENERALISATION_2 foreign key (idDeplacement)
      references Deplacement (idDeplacement) on delete restrict on update restrict;

alter table IndemneteDeplacementInterne add constraint FK_calculePrixRepas foreign key (idPrixRepas)
      references PrixRepas (idPrixRepas) on delete restrict on update restrict;

alter table IndemneteDeplacementInterne add constraint FK_cptDeplacement foreign key (idDotation)
      references DotationSecteur (idDotation) on delete restrict on update restrict;

alter table IndemnteKm add constraint FK_GENERALISATION_4 foreign key (idDeplacement)
      references Deplacement (idDeplacement) on delete restrict on update restrict;

alter table IndemnteKm add constraint FK_cptKilomitrique foreign key (idDotation)
      references DotationSecteur (idDotation) on delete restrict on update restrict;

alter table Intervenant add constraint FK_gerer_interv_user foreign key (idUser)
      references Users (idUser) on delete restrict on update restrict;

alter table LIGNECOMMANDE add constraint FK_LIGNECOMMANDE_article foreign key (idArticle)
      references Article (idArticle) on delete restrict on update restrict;

alter table LIGNECOMMANDE add constraint FK_LIGNECOMMANDE_BC foreign key (idBC)
      references BonCommande (idBC) on delete restrict on update restrict;

alter table PieceJustificativeDeplacement add constraint FK_justification foreign key (idDeplacement)
      references Deplacement (idDeplacement) on delete restrict on update restrict;

alter table PieceJustificativeVacation add constraint FK_concerner_HSupp foreign key (Dos_idDossier)
      references DossierHSupp (idDossier) on delete restrict on update restrict;

alter table PieceJustificativeVacation add constraint FK_concerner_vacataire foreign key (idDossier)
      references DossierVacataire (idDossier) on delete restrict on update restrict;

alter table Releve add constraint FK_appartenir_relever_fill foreign key (idFiliere)
      references Filiere (idFiliere) on delete restrict on update restrict;

alter table Secteur add constraint FK_inclureSecteur foreign key (idSecteurP)
      references SecteurPrincipal (idSecteurP) on delete restrict on update restrict;

alter table USER_ROLE add constraint FK_USER_ROLE foreign key (idRole)
      references Role (idRole) on delete restrict on update restrict;

alter table USER_ROLE add constraint FK_USER_ROLE_user foreign key (idUser)
      references Users (idUser) on delete restrict on update restrict;

alter table Voiture add constraint FK_calculePrixVoiture foreign key (idPrixKilo)
      references PrixKilomitrique (idPrixKilo) on delete restrict on update restrict;

alter table Voiture add constraint FK_faitPar foreign key (idDeplacement, idIndKm)
      references IndemnteKm (idDeplacement, idIndKm) on delete restrict on update restrict;
