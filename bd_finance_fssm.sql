-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Dim 12 Juin 2016 à 02:05
-- Version du serveur :  5.7.9
-- Version de PHP :  5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `bd_finance_fssm`
--

-- --------------------------------------------------------

--
-- Structure de la table `anneebudgetaire`
--

DROP TABLE IF EXISTS `anneebudgetaire`;
CREATE TABLE IF NOT EXISTS `anneebudgetaire` (
  `annee` int(11) NOT NULL,
  `montantRap` decimal(8,0) DEFAULT NULL,
  `reliquatRap` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`annee`)
) ENGINE=MyISAM AUTO_INCREMENT=2017 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `anneebudgetaire`
--

INSERT INTO `anneebudgetaire` (`annee`, `montantRap`, `reliquatRap`) VALUES
(2016, '200', NULL),
(2012, '200', NULL),
(2014, '200', NULL),
(2013, '200', NULL),
(2020, '333', NULL),
(2015, '333', NULL),
(2019, '1000', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `idArticle` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(254) DEFAULT NULL,
  `pu` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idArticle`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `boncommande`
--

DROP TABLE IF EXISTS `boncommande`;
CREATE TABLE IF NOT EXISTS `boncommande` (
  `idBC` int(11) NOT NULL AUTO_INCREMENT,
  `idFournisseur` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idDotation` int(11) NOT NULL,
  `dateCommande` datetime DEFAULT NULL,
  `tva` int(11) DEFAULT NULL,
  `dateReception` datetime DEFAULT NULL,
  `etat` varchar(254) DEFAULT NULL,
  `montant` decimal(8,0) DEFAULT NULL,
  `type` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idBC`),
  KEY `FK_fournir_BN_Forns` (`idFournisseur`),
  KEY `FK_payer_BN_sect` (`idDotation`),
  KEY `FK_proposer_BN_Users` (`idUser`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `bordereauautorisation`
--

DROP TABLE IF EXISTS `bordereauautorisation`;
CREATE TABLE IF NOT EXISTS `bordereauautorisation` (
  `idBordAut` int(11) NOT NULL AUTO_INCREMENT,
  `anneeUniversitaire` int(11) DEFAULT NULL,
  PRIMARY KEY (`idBordAut`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `bordereaucomptable`
--

DROP TABLE IF EXISTS `bordereaucomptable`;
CREATE TABLE IF NOT EXISTS `bordereaucomptable` (
  `idBordComp` int(11) NOT NULL AUTO_INCREMENT,
  `annee` int(11) NOT NULL,
  `dateExercice` datetime DEFAULT NULL,
  `totalIr` decimal(8,0) DEFAULT NULL,
  `totalNet` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idBordComp`),
  KEY `FK_derouler_compt_Bcompt` (`annee`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `budget`
--

DROP TABLE IF EXISTS `budget`;
CREATE TABLE IF NOT EXISTS `budget` (
  `idCompte` int(11) NOT NULL,
  `annee` int(11) NOT NULL,
  `budgetAnnuel` decimal(8,0) DEFAULT NULL,
  `reliquat` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idCompte`,`annee`),
  KEY `FK_BUDGET_annee` (`annee`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `budget`
--

INSERT INTO `budget` (`idCompte`, `annee`, `budgetAnnuel`, `reliquat`) VALUES
(1234, 2016, '3000', NULL),
(123, 2016, '100', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

DROP TABLE IF EXISTS `compte`;
CREATE TABLE IF NOT EXISTS `compte` (
  `idCompte` int(11) NOT NULL,
  `intitule` varchar(254) DEFAULT NULL,
  `rap` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idCompte`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Contenu de la table `compte`
--

INSERT INTO `compte` (`idCompte`, `intitule`, `rap`) VALUES
(1234, 'FSSM', '2016'),
(123, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `comptebc`
--

DROP TABLE IF EXISTS `comptebc`;
CREATE TABLE IF NOT EXISTS `comptebc` (
  `idCptBc` int(11) NOT NULL AUTO_INCREMENT,
  `cinPpr` varchar(254) NOT NULL,
  `intitule` varchar(254) DEFAULT NULL,
  `bc` varchar(254) DEFAULT NULL,
  `rib` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idCptBc`),
  KEY `FK_appartient_compte` (`cinPpr`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `departement`
--

DROP TABLE IF EXISTS `departement`;
CREATE TABLE IF NOT EXISTS `departement` (
  `idDep` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) DEFAULT NULL,
  `nomDep` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idDep`),
  KEY `FK_gerer_dept` (`idUser`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `deplacement`
--

DROP TABLE IF EXISTS `deplacement`;
CREATE TABLE IF NOT EXISTS `deplacement` (
  `idDeplacement` int(11) NOT NULL AUTO_INCREMENT,
  `cinPpr` varchar(254) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idPays` int(11) DEFAULT NULL,
  `nbrJours` int(11) DEFAULT NULL,
  `dateDepart` datetime DEFAULT NULL,
  `dateArrive` datetime DEFAULT NULL,
  `annee` int(11) DEFAULT NULL,
  `motifDeplacement` varchar(254) DEFAULT NULL,
  `dateCreation` datetime DEFAULT NULL,
  `statutMnt` int(11) DEFAULT NULL,
  `indice` int(11) DEFAULT NULL,
  `echelle` int(11) DEFAULT NULL,
  `grade` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idDeplacement`),
  KEY `FK_faitA` (`idPays`),
  KEY `FK_intervenantsDeplacement` (`cinPpr`),
  KEY `FK_usersDeplacement` (`idUser`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `detail`
--

DROP TABLE IF EXISTS `detail`;
CREATE TABLE IF NOT EXISTS `detail` (
  `idDetail` int(11) NOT NULL AUTO_INCREMENT,
  `salaireAnnuelleBrut` decimal(8,0) DEFAULT NULL,
  `allocationFamiliale` decimal(8,0) DEFAULT NULL,
  `brutAdditionner` decimal(8,0) DEFAULT NULL,
  `amo` decimal(8,0) DEFAULT NULL,
  `retenuCmr` decimal(8,0) DEFAULT NULL,
  `mutuelleMutialiste` decimal(8,0) DEFAULT NULL,
  `mutuelleCaisse` decimal(8,0) DEFAULT NULL,
  `rachatCmr` decimal(8,0) DEFAULT NULL,
  `sommeDeduire` decimal(8,0) DEFAULT NULL,
  `nbrEnfant` int(11) DEFAULT NULL,
  `conjoint` tinyint(1) DEFAULT NULL,
  `chargeFamiliale` decimal(8,0) DEFAULT NULL,
  `irSource` decimal(8,0) DEFAULT NULL,
  `irComplement` decimal(8,0) DEFAULT NULL,
  `net` decimal(8,0) DEFAULT NULL,
  `ir` decimal(8,0) DEFAULT NULL,
  `echelle` varchar(254) DEFAULT NULL,
  `echelon` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idDetail`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `dossierhsupp`
--

DROP TABLE IF EXISTS `dossierhsupp`;
CREATE TABLE IF NOT EXISTS `dossierhsupp` (
  `idDossier` int(11) NOT NULL AUTO_INCREMENT,
  `cinPpr` varchar(254) NOT NULL,
  `idBordAut` int(11) DEFAULT NULL,
  `idDossierProv` int(11) NOT NULL,
  `idBordComp` int(11) DEFAULT NULL,
  `idDotation` int(11) NOT NULL,
  `idGrade` int(11) DEFAULT NULL,
  `idDetail` int(11) NOT NULL,
  `nbrHeures` int(11) DEFAULT NULL,
  `mois` varchar(254) DEFAULT NULL,
  `semestre` varchar(254) DEFAULT NULL,
  `dateCreance` datetime DEFAULT NULL,
  `montantHsupp` decimal(8,0) DEFAULT NULL,
  `statutDossier` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idDossier`),
  KEY `FK_appartenir_sect_HSupp` (`idDotation`),
  KEY `FK_attribuer_grade_HSupp` (`idGrade`),
  KEY `FK_avoir` (`idDetail`),
  KEY `FK_concerner_doss_HSupp` (`idDossierProv`),
  KEY `FK_contenir_HSupp_Autors` (`idBordAut`),
  KEY `FK_contenir_HSupp_Bcompt` (`idBordComp`),
  KEY `FK_effectuer_interv_HSupp` (`cinPpr`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `dossierprovisoir`
--

DROP TABLE IF EXISTS `dossierprovisoir`;
CREATE TABLE IF NOT EXISTS `dossierprovisoir` (
  `idDossierProv` int(11) NOT NULL AUTO_INCREMENT,
  `idRelever` int(11) DEFAULT NULL,
  `idGrade` int(11) DEFAULT NULL,
  `nomComplet` varchar(254) DEFAULT NULL,
  `dernierDiplome` varchar(254) DEFAULT NULL,
  `nbrHeures` int(11) DEFAULT NULL,
  `module` varchar(254) DEFAULT NULL,
  `etat` tinyint(1) DEFAULT NULL,
  `dateDebut` datetime DEFAULT NULL,
  `dateFin` datetime DEFAULT NULL,
  PRIMARY KEY (`idDossierProv`),
  KEY `FK_attribuer_grade_dossPrev` (`idGrade`),
  KEY `FK_contenir_doss_relever` (`idRelever`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `dossierrejete`
--

DROP TABLE IF EXISTS `dossierrejete`;
CREATE TABLE IF NOT EXISTS `dossierrejete` (
  `idDossierRejete` int(11) NOT NULL AUTO_INCREMENT,
  `idDossier` int(11) NOT NULL,
  `Dos_idDossier` int(11) NOT NULL,
  `motifRejet` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idDossierRejete`),
  KEY `FK_rejeter_Hsupp` (`Dos_idDossier`),
  KEY `FK_rejeter_vacation` (`idDossier`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `dossiervacataire`
--

DROP TABLE IF EXISTS `dossiervacataire`;
CREATE TABLE IF NOT EXISTS `dossiervacataire` (
  `idDossier` int(11) NOT NULL AUTO_INCREMENT,
  `cinPpr` varchar(254) NOT NULL,
  `idBordAut` int(11) DEFAULT NULL,
  `idDossierProv` int(11) NOT NULL,
  `idBordComp` int(11) DEFAULT NULL,
  `idDotation` int(11) NOT NULL,
  `idGrade` int(11) DEFAULT NULL,
  `nbrHeures` int(11) DEFAULT NULL,
  `mois` varchar(254) DEFAULT NULL,
  `semestre` varchar(254) DEFAULT NULL,
  `dateCreance` datetime DEFAULT NULL,
  `net` decimal(8,0) DEFAULT NULL,
  `ir` decimal(8,0) DEFAULT NULL,
  `statutDossier` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idDossier`),
  KEY `FK_appartenir_sect_vacat` (`idDotation`),
  KEY `FK_attribuer_grade_vacataire` (`idGrade`),
  KEY `FK_concerner_dossProvis_vacat` (`idDossierProv`),
  KEY `FK_contenir_Bcompt_vacat` (`idBordComp`),
  KEY `FK_contenir_vacat_autoris` (`idBordAut`),
  KEY `FK_effectuer_interv_vacat` (`cinPpr`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `dotationsecteur`
--

DROP TABLE IF EXISTS `dotationsecteur`;
CREATE TABLE IF NOT EXISTS `dotationsecteur` (
  `idCompte` int(11) NOT NULL,
  `idSecteur` int(11) NOT NULL,
  `idDotation` int(11) NOT NULL AUTO_INCREMENT,
  `montantInitial` decimal(8,0) DEFAULT NULL,
  `reliquat` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idDotation`),
  KEY `FK_repartirSecteur_compte` (`idCompte`),
  KEY `FK_repartirSecteur_sect` (`idSecteur`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `etatdossier`
--

DROP TABLE IF EXISTS `etatdossier`;
CREATE TABLE IF NOT EXISTS `etatdossier` (
  `idEtat` int(11) NOT NULL AUTO_INCREMENT,
  `idDeplacement` int(11) NOT NULL,
  `etat` int(11) DEFAULT NULL,
  `motif` varchar(254) DEFAULT NULL,
  `rmq` varchar(254) DEFAULT NULL,
  `observation` varchar(254) DEFAULT NULL,
  `dateEtat` datetime DEFAULT NULL,
  PRIMARY KEY (`idEtat`),
  KEY `FK_accepterEtat` (`idDeplacement`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `filiere`
--

DROP TABLE IF EXISTS `filiere`;
CREATE TABLE IF NOT EXISTS `filiere` (
  `idFiliere` int(11) NOT NULL AUTO_INCREMENT,
  `idDep` int(11) NOT NULL,
  `idType` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `intitule` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idFiliere`),
  KEY `FK_assumer_fill_users` (`idUser`),
  KEY `FK_contenir_dept_fill` (`idDep`),
  KEY `FK_inclure_type_form_fill` (`idType`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `fournisseur`
--

DROP TABLE IF EXISTS `fournisseur`;
CREATE TABLE IF NOT EXISTS `fournisseur` (
  `idFournisseur` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(254) DEFAULT NULL,
  `adresse` varchar(254) DEFAULT NULL,
  `tel` varchar(254) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idFournisseur`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `graddiplome`
--

DROP TABLE IF EXISTS `graddiplome`;
CREATE TABLE IF NOT EXISTS `graddiplome` (
  `idGrade` int(11) NOT NULL AUTO_INCREMENT,
  `intituleGrade` varchar(254) DEFAULT NULL,
  `taux` int(11) DEFAULT NULL,
  PRIMARY KEY (`idGrade`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `indemnetedeplacementetranger`
--

DROP TABLE IF EXISTS `indemnetedeplacementetranger`;
CREATE TABLE IF NOT EXISTS `indemnetedeplacementetranger` (
  `idDeplacement` int(11) NOT NULL AUTO_INCREMENT,
  `idDepEx` int(11) NOT NULL,
  `idPrixSej` int(11) DEFAULT NULL,
  `idDotation` int(11) NOT NULL,
  `mntDepEx` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDeplacement`,`idDepEx`),
  KEY `FK_calculePrixSejour` (`idPrixSej`),
  KEY `FK_indmntEtrng` (`idDotation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `indemnetedeplacementinterne`
--

DROP TABLE IF EXISTS `indemnetedeplacementinterne`;
CREATE TABLE IF NOT EXISTS `indemnetedeplacementinterne` (
  `idDeplacement` int(11) NOT NULL AUTO_INCREMENT,
  `idIndDep` int(11) NOT NULL,
  `idPrixRepas` int(11) DEFAULT NULL,
  `idDotation` int(11) NOT NULL,
  `montantDepInt` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idDeplacement`,`idIndDep`),
  KEY `FK_calculePrixRepas` (`idPrixRepas`),
  KEY `FK_cptDeplacement` (`idDotation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `indemntekm`
--

DROP TABLE IF EXISTS `indemntekm`;
CREATE TABLE IF NOT EXISTS `indemntekm` (
  `idDeplacement` int(11) NOT NULL AUTO_INCREMENT,
  `idIndKm` int(11) NOT NULL,
  `idDotation` int(11) NOT NULL,
  `montantDepInt` decimal(8,0) DEFAULT NULL,
  `kmRoute` decimal(8,0) DEFAULT NULL,
  `kmPiste` decimal(8,0) DEFAULT NULL,
  `mntKm` int(11) DEFAULT NULL,
  PRIMARY KEY (`idDeplacement`,`idIndKm`),
  KEY `FK_cptKilomitrique` (`idDotation`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `intervenant`
--

DROP TABLE IF EXISTS `intervenant`;
CREATE TABLE IF NOT EXISTS `intervenant` (
  `cinPpr` varchar(254) NOT NULL,
  `idUser` int(11) NOT NULL,
  `nomComplet` varchar(254) DEFAULT NULL,
  `nomArabe` varchar(254) DEFAULT NULL,
  `telephone` varchar(254) DEFAULT NULL,
  `mail` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`cinPpr`),
  KEY `FK_gerer_interv_user` (`idUser`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `lignecommande`
--

DROP TABLE IF EXISTS `lignecommande`;
CREATE TABLE IF NOT EXISTS `lignecommande` (
  `idBC` int(11) NOT NULL,
  `idArticle` int(11) NOT NULL,
  `idLigne` int(11) NOT NULL AUTO_INCREMENT,
  `quantite` int(11) DEFAULT NULL,
  `pu` decimal(8,0) DEFAULT NULL,
  `montant` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idLigne`),
  KEY `FK_LIGNECOMMANDE_article` (`idArticle`),
  KEY `FK_LIGNECOMMANDE_BC` (`idBC`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `pays`
--

DROP TABLE IF EXISTS `pays`;
CREATE TABLE IF NOT EXISTS `pays` (
  `idPays` int(11) NOT NULL AUTO_INCREMENT,
  `ville` varchar(254) DEFAULT NULL,
  `nomPays` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idPays`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `piecejustificativedeplacement`
--

DROP TABLE IF EXISTS `piecejustificativedeplacement`;
CREATE TABLE IF NOT EXISTS `piecejustificativedeplacement` (
  `idPiece` int(11) NOT NULL AUTO_INCREMENT,
  `idDeplacement` int(11) NOT NULL,
  `datePiece` datetime DEFAULT NULL,
  `intutilePiece` varchar(254) DEFAULT NULL,
  `piece` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idPiece`),
  KEY `FK_justification` (`idDeplacement`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `piecejustificativevacation`
--

DROP TABLE IF EXISTS `piecejustificativevacation`;
CREATE TABLE IF NOT EXISTS `piecejustificativevacation` (
  `idPiece` int(11) NOT NULL AUTO_INCREMENT,
  `idDossier` int(11) NOT NULL,
  `Dos_idDossier` int(11) NOT NULL,
  `datePiece` datetime DEFAULT NULL,
  `intutilePiece` varchar(254) DEFAULT NULL,
  `piece` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idPiece`),
  KEY `FK_concerner_HSupp` (`Dos_idDossier`),
  KEY `FK_concerner_vacataire` (`idDossier`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `prixkilomitrique`
--

DROP TABLE IF EXISTS `prixkilomitrique`;
CREATE TABLE IF NOT EXISTS `prixkilomitrique` (
  `idPrixKilo` int(11) NOT NULL AUTO_INCREMENT,
  `kmSup` decimal(8,0) DEFAULT NULL,
  `kmInf` decimal(8,0) DEFAULT NULL,
  `prixRoute` decimal(8,0) DEFAULT NULL,
  `prixPiste` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idPrixKilo`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `prixrepas`
--

DROP TABLE IF EXISTS `prixrepas`;
CREATE TABLE IF NOT EXISTS `prixrepas` (
  `idPrixRepas` int(11) NOT NULL AUTO_INCREMENT,
  `indiceSup` int(11) DEFAULT NULL,
  `indiceInf` int(11) DEFAULT NULL,
  `prixDs` decimal(8,0) DEFAULT NULL,
  `prixD` decimal(8,0) DEFAULT NULL,
  `prixRs` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`idPrixRepas`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `prixsejour`
--

DROP TABLE IF EXISTS `prixsejour`;
CREATE TABLE IF NOT EXISTS `prixsejour` (
  `idPrixSej` int(11) NOT NULL AUTO_INCREMENT,
  `grade` int(11) DEFAULT NULL,
  `prix` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPrixSej`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `releve`
--

DROP TABLE IF EXISTS `releve`;
CREATE TABLE IF NOT EXISTS `releve` (
  `idRelever` int(11) NOT NULL AUTO_INCREMENT,
  `idFiliere` int(11) NOT NULL,
  `semestre` varchar(254) DEFAULT NULL,
  `anneeUniversitaire` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idRelever`),
  KEY `FK_appartenir_relever_fill` (`idFiliere`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `idRole` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idRole`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `role`
--

INSERT INTO `role` (`idRole`, `nom`) VALUES
(1, 'operateur');

-- --------------------------------------------------------

--
-- Structure de la table `secteur`
--

DROP TABLE IF EXISTS `secteur`;
CREATE TABLE IF NOT EXISTS `secteur` (
  `idSecteur` int(11) NOT NULL AUTO_INCREMENT,
  `idSecteurP` int(11) DEFAULT NULL,
  `intituleSecteur` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idSecteur`),
  KEY `FK_inclureSecteur` (`idSecteurP`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `secteurprincipal`
--

DROP TABLE IF EXISTS `secteurprincipal`;
CREATE TABLE IF NOT EXISTS `secteurprincipal` (
  `idSecteurP` int(11) NOT NULL AUTO_INCREMENT,
  `designation` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idSecteurP`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `typeformations`
--

DROP TABLE IF EXISTS `typeformations`;
CREATE TABLE IF NOT EXISTS `typeformations` (
  `idType` int(11) NOT NULL AUTO_INCREMENT,
  `nomType` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(254) DEFAULT NULL,
  `password` varchar(254) DEFAULT NULL,
  `nom` varchar(254) DEFAULT NULL,
  `prenom` varchar(254) DEFAULT NULL,
  `telephone` varchar(254) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `img` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`idUser`, `login`, `password`, `nom`, `prenom`, `telephone`, `email`, `img`) VALUES
(1, 'operateur', '22f256eca1f336a97eef2b260773cb0d81d900c208ff26e94410d292d605fed8', 'El Mezouari', 'Asmae', '0693764350', 'elmezouari.asmae@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `idUser` int(11) NOT NULL,
  `idRole` int(11) NOT NULL,
  `idUserRole` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idUserRole`),
  KEY `FK_USER_ROLE` (`idRole`),
  KEY `FK_USER_ROLE_user` (`idUser`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user_role`
--

INSERT INTO `user_role` (`idUser`, `idRole`, `idUserRole`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `voiture`
--

DROP TABLE IF EXISTS `voiture`;
CREATE TABLE IF NOT EXISTS `voiture` (
  `idVoiture` int(11) NOT NULL AUTO_INCREMENT,
  `idPrixKilo` int(11) NOT NULL,
  `idDeplacement` int(11) NOT NULL,
  `idIndKm` int(11) NOT NULL,
  `marque` varchar(254) DEFAULT NULL,
  `puissance` int(11) DEFAULT NULL,
  PRIMARY KEY (`idVoiture`),
  KEY `FK_calculePrixVoiture` (`idPrixKilo`),
  KEY `FK_faitPar` (`idDeplacement`,`idIndKm`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
