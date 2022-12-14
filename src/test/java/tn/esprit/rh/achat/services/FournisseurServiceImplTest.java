package tn.esprit.rh.achat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.CategorieFournisseur;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;

@ContextConfiguration(classes = {FournisseurServiceImpl.class})
@ExtendWith(SpringExtension.class)
class FournisseurServiceImplTest {
    @MockBean
    private DetailFournisseurRepository detailFournisseurRepository;

    @MockBean
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurServiceImpl fournisseurServiceImpl;

    @MockBean
    private ProduitRepository produitRepository;

    @MockBean
    private SecteurActiviteRepository secteurActiviteRepository;

    /**
     * Method under test: {@link FournisseurServiceImpl#retrieveAllFournisseurs()}
     */
    @Test
    void testRetrieveAllFournisseurs() {
        ArrayList<Fournisseur> fournisseurList = new ArrayList<>();
        when(this.fournisseurRepository.findAll()).thenReturn(fournisseurList);
        List<Fournisseur> actualRetrieveAllFournisseursResult = this.fournisseurServiceImpl.retrieveAllFournisseurs();
        assertSame(fournisseurList, actualRetrieveAllFournisseursResult);
        assertTrue(actualRetrieveAllFournisseursResult.isEmpty());
        verify(this.fournisseurRepository).findAll();
    }

    /**
     * Method under test: {@link FournisseurServiceImpl#retrieveAllFournisseurs()}
     */
    @Test
    void testRetrieveAllFournisseurs2() {
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        detailFournisseur.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur.setDateDebutCollaboration(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur.setEmail("jane.doe@example.org");
        detailFournisseur.setFournisseur(new Fournisseur());
        detailFournisseur.setIdDetailFournisseur(1L);
        detailFournisseur.setMatricule("Matricule");

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur.setCode("Code");
        fournisseur.setDetailFournisseur(detailFournisseur);
        fournisseur.setFactures(new HashSet<>());
        fournisseur.setIdFournisseur(1L);
        fournisseur.setLibelle("Libelle");
        fournisseur.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur1 = new DetailFournisseur();
        detailFournisseur1.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur1.setDateDebutCollaboration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur1.setEmail("jane.doe@example.org");
        detailFournisseur1.setFournisseur(fournisseur);
        detailFournisseur1.setIdDetailFournisseur(1L);
        detailFournisseur1.setMatricule("Matricule");

        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur1.setCode("Code");
        fournisseur1.setDetailFournisseur(detailFournisseur1);
        fournisseur1.setFactures(new HashSet<>());
        fournisseur1.setIdFournisseur(1L);
        fournisseur1.setLibelle("Libelle");
        fournisseur1.setSecteurActivites(new HashSet<>());

        ArrayList<Fournisseur> fournisseurList = new ArrayList<>();
        fournisseurList.add(fournisseur1);
        when(this.fournisseurRepository.findAll()).thenReturn(fournisseurList);
        List<Fournisseur> actualRetrieveAllFournisseursResult = this.fournisseurServiceImpl.retrieveAllFournisseurs();
        assertSame(fournisseurList, actualRetrieveAllFournisseursResult);
        assertEquals(1, actualRetrieveAllFournisseursResult.size());
        verify(this.fournisseurRepository).findAll();
    }

    /**
     * Method under test: {@link FournisseurServiceImpl#addFournisseur(Fournisseur)}
     */
    @Test
    void testAddFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur.setCode("Code");
        fournisseur.setDetailFournisseur(new DetailFournisseur());
        fournisseur.setFactures(new HashSet<>());
        fournisseur.setIdFournisseur(1L);
        fournisseur.setLibelle("Libelle");
        fournisseur.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur = new DetailFournisseur();
        detailFournisseur.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur.setDateDebutCollaboration(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur.setEmail("jane.doe@example.org");
        detailFournisseur.setFournisseur(fournisseur);
        detailFournisseur.setIdDetailFournisseur(1L);
        detailFournisseur.setMatricule("Matricule");

        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur1.setCode("Code");
        fournisseur1.setDetailFournisseur(detailFournisseur);
        fournisseur1.setFactures(new HashSet<>());
        fournisseur1.setIdFournisseur(1L);
        fournisseur1.setLibelle("Libelle");
        fournisseur1.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur1 = new DetailFournisseur();
        detailFournisseur1.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur1.setDateDebutCollaboration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur1.setEmail("jane.doe@example.org");
        detailFournisseur1.setFournisseur(fournisseur1);
        detailFournisseur1.setIdDetailFournisseur(1L);
        detailFournisseur1.setMatricule("Matricule");

        Fournisseur fournisseur2 = new Fournisseur();
        fournisseur2.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur2.setCode("Code");
        fournisseur2.setDetailFournisseur(detailFournisseur1);
        fournisseur2.setFactures(new HashSet<>());
        fournisseur2.setIdFournisseur(1L);
        fournisseur2.setLibelle("Libelle");
        fournisseur2.setSecteurActivites(new HashSet<>());
        when(this.fournisseurRepository.save((Fournisseur) any())).thenReturn(fournisseur2);

        DetailFournisseur detailFournisseur2 = new DetailFournisseur();
        detailFournisseur2.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur2.setDateDebutCollaboration(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur2.setEmail("jane.doe@example.org");
        detailFournisseur2.setFournisseur(new Fournisseur());
        detailFournisseur2.setIdDetailFournisseur(1L);
        detailFournisseur2.setMatricule("Matricule");

        Fournisseur fournisseur3 = new Fournisseur();
        fournisseur3.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur3.setCode("Code");
        fournisseur3.setDetailFournisseur(detailFournisseur2);
        fournisseur3.setFactures(new HashSet<>());
        fournisseur3.setIdFournisseur(1L);
        fournisseur3.setLibelle("Libelle");
        fournisseur3.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur3 = new DetailFournisseur();
        detailFournisseur3.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur3.setDateDebutCollaboration(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur3.setEmail("jane.doe@example.org");
        detailFournisseur3.setFournisseur(fournisseur3);
        detailFournisseur3.setIdDetailFournisseur(1L);
        detailFournisseur3.setMatricule("Matricule");

        Fournisseur fournisseur4 = new Fournisseur();
        fournisseur4.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur4.setCode("Code");
        fournisseur4.setDetailFournisseur(detailFournisseur3);
        fournisseur4.setFactures(new HashSet<>());
        fournisseur4.setIdFournisseur(1L);
        fournisseur4.setLibelle("Libelle");
        fournisseur4.setSecteurActivites(new HashSet<>());
        assertSame(fournisseur4, this.fournisseurServiceImpl.addFournisseur(fournisseur4));
        verify(this.fournisseurRepository).save((Fournisseur) any());
    }

    /**
     * Method under test: {@link FournisseurServiceImpl#updateFournisseur(Fournisseur)}
     */
    @Test
    void testUpdateFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur.setCode("Code");
        fournisseur.setDetailFournisseur(new DetailFournisseur());
        fournisseur.setFactures(new HashSet<>());
        fournisseur.setIdFournisseur(1L);
        fournisseur.setLibelle("Libelle");
        fournisseur.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur = new DetailFournisseur();
        detailFournisseur.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur.setDateDebutCollaboration(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur.setEmail("jane.doe@example.org");
        detailFournisseur.setFournisseur(fournisseur);
        detailFournisseur.setIdDetailFournisseur(1L);
        detailFournisseur.setMatricule("Matricule");

        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur1.setCode("Code");
        fournisseur1.setDetailFournisseur(detailFournisseur);
        fournisseur1.setFactures(new HashSet<>());
        fournisseur1.setIdFournisseur(1L);
        fournisseur1.setLibelle("Libelle");
        fournisseur1.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur1 = new DetailFournisseur();
        detailFournisseur1.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur1.setDateDebutCollaboration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur1.setEmail("jane.doe@example.org");
        detailFournisseur1.setFournisseur(fournisseur1);
        detailFournisseur1.setIdDetailFournisseur(1L);
        detailFournisseur1.setMatricule("Matricule");

        Fournisseur fournisseur2 = new Fournisseur();
        fournisseur2.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur2.setCode("Code");
        fournisseur2.setDetailFournisseur(detailFournisseur1);
        fournisseur2.setFactures(new HashSet<>());
        fournisseur2.setIdFournisseur(1L);
        fournisseur2.setLibelle("Libelle");
        fournisseur2.setSecteurActivites(new HashSet<>());
        when(this.fournisseurRepository.save((Fournisseur) any())).thenReturn(fournisseur2);

        DetailFournisseur detailFournisseur2 = new DetailFournisseur();
        detailFournisseur2.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur2.setDateDebutCollaboration(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur2.setEmail("jane.doe@example.org");
        detailFournisseur2.setFournisseur(new Fournisseur());
        detailFournisseur2.setIdDetailFournisseur(1L);
        detailFournisseur2.setMatricule("Matricule");

        Fournisseur fournisseur3 = new Fournisseur();
        fournisseur3.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur3.setCode("Code");
        fournisseur3.setDetailFournisseur(detailFournisseur2);
        fournisseur3.setFactures(new HashSet<>());
        fournisseur3.setIdFournisseur(1L);
        fournisseur3.setLibelle("Libelle");
        fournisseur3.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur3 = new DetailFournisseur();
        detailFournisseur3.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur3.setDateDebutCollaboration(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur3.setEmail("jane.doe@example.org");
        detailFournisseur3.setFournisseur(fournisseur3);
        detailFournisseur3.setIdDetailFournisseur(1L);
        detailFournisseur3.setMatricule("Matricule");

        Fournisseur fournisseur4 = new Fournisseur();
        fournisseur4.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur4.setCode("Code");
        fournisseur4.setDetailFournisseur(detailFournisseur3);
        fournisseur4.setFactures(new HashSet<>());
        fournisseur4.setIdFournisseur(1L);
        fournisseur4.setLibelle("Libelle");
        fournisseur4.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur4 = new DetailFournisseur();
        detailFournisseur4.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur4.setDateDebutCollaboration(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur4.setEmail("jane.doe@example.org");
        detailFournisseur4.setFournisseur(fournisseur4);
        detailFournisseur4.setIdDetailFournisseur(1L);
        detailFournisseur4.setMatricule("Matricule");
        when(this.detailFournisseurRepository.save((DetailFournisseur) any())).thenReturn(detailFournisseur4);

        DetailFournisseur detailFournisseur5 = new DetailFournisseur();
        detailFournisseur5.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur5.setDateDebutCollaboration(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur5.setEmail("jane.doe@example.org");
        detailFournisseur5.setFournisseur(new Fournisseur());
        detailFournisseur5.setIdDetailFournisseur(1L);
        detailFournisseur5.setMatricule("Matricule");

        Fournisseur fournisseur5 = new Fournisseur();
        fournisseur5.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur5.setCode("Code");
        fournisseur5.setDetailFournisseur(detailFournisseur5);
        fournisseur5.setFactures(new HashSet<>());
        fournisseur5.setIdFournisseur(1L);
        fournisseur5.setLibelle("Libelle");
        fournisseur5.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur6 = new DetailFournisseur();
        detailFournisseur6.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur6.setDateDebutCollaboration(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur6.setEmail("jane.doe@example.org");
        detailFournisseur6.setFournisseur(fournisseur5);
        detailFournisseur6.setIdDetailFournisseur(1L);
        detailFournisseur6.setMatricule("Matricule");

        Fournisseur fournisseur6 = new Fournisseur();
        fournisseur6.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur6.setCode("Code");
        fournisseur6.setDetailFournisseur(detailFournisseur6);
        fournisseur6.setFactures(new HashSet<>());
        fournisseur6.setIdFournisseur(1L);
        fournisseur6.setLibelle("Libelle");
        fournisseur6.setSecteurActivites(new HashSet<>());
        Fournisseur actualUpdateFournisseurResult = this.fournisseurServiceImpl.updateFournisseur(fournisseur6);
        assertSame(fournisseur6, actualUpdateFournisseurResult);
        assertSame(detailFournisseur6, actualUpdateFournisseurResult.getDetailFournisseur());
        verify(this.fournisseurRepository).save((Fournisseur) any());
        verify(this.detailFournisseurRepository).save((DetailFournisseur) any());
    }

    /**
     * Method under test: {@link FournisseurServiceImpl#deleteFournisseur(Long)}
     */
    @Test
    void testDeleteFournisseur() {
        doNothing().when(this.fournisseurRepository).deleteById((Long) any());
        this.fournisseurServiceImpl.deleteFournisseur(123L);
        verify(this.fournisseurRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link FournisseurServiceImpl#retrieveFournisseur(Long)}
     */
    @Test
    void testRetrieveFournisseur() {
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        detailFournisseur.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur.setDateDebutCollaboration(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur.setEmail("jane.doe@example.org");
        detailFournisseur.setFournisseur(new Fournisseur());
        detailFournisseur.setIdDetailFournisseur(1L);
        detailFournisseur.setMatricule("Matricule");

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur.setCode("Code");
        fournisseur.setDetailFournisseur(detailFournisseur);
        fournisseur.setFactures(new HashSet<>());
        fournisseur.setIdFournisseur(1L);
        fournisseur.setLibelle("Libelle");
        fournisseur.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur1 = new DetailFournisseur();
        detailFournisseur1.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur1.setDateDebutCollaboration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur1.setEmail("jane.doe@example.org");
        detailFournisseur1.setFournisseur(fournisseur);
        detailFournisseur1.setIdDetailFournisseur(1L);
        detailFournisseur1.setMatricule("Matricule");

        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur1.setCode("Code");
        fournisseur1.setDetailFournisseur(detailFournisseur1);
        fournisseur1.setFactures(new HashSet<>());
        fournisseur1.setIdFournisseur(1L);
        fournisseur1.setLibelle("Libelle");
        fournisseur1.setSecteurActivites(new HashSet<>());
        Optional<Fournisseur> ofResult = Optional.of(fournisseur1);
        when(this.fournisseurRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(fournisseur1, this.fournisseurServiceImpl.retrieveFournisseur(123L));
        verify(this.fournisseurRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link FournisseurServiceImpl#assignSecteurActiviteToFournisseur(Long, Long)}
     */
    @Test
    void testAssignSecteurActiviteToFournisseur() {
        SecteurActivite secteurActivite = new SecteurActivite();
        secteurActivite.setCodeSecteurActivite("Code Secteur Activite");
        secteurActivite.setFournisseurs(new HashSet<>());
        secteurActivite.setIdSecteurActivite(1L);
        secteurActivite.setLibelleSecteurActivite("Libelle Secteur Activite");
        Optional<SecteurActivite> ofResult = Optional.of(secteurActivite);
        when(this.secteurActiviteRepository.findById((Long) any())).thenReturn(ofResult);

        DetailFournisseur detailFournisseur = new DetailFournisseur();
        detailFournisseur.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur.setDateDebutCollaboration(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur.setEmail("jane.doe@example.org");
        detailFournisseur.setFournisseur(new Fournisseur());
        detailFournisseur.setIdDetailFournisseur(1L);
        detailFournisseur.setMatricule("Matricule");

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur.setCode("Code");
        fournisseur.setDetailFournisseur(detailFournisseur);
        fournisseur.setFactures(new HashSet<>());
        fournisseur.setIdFournisseur(1L);
        fournisseur.setLibelle("Libelle");
        fournisseur.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur1 = new DetailFournisseur();
        detailFournisseur1.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur1.setDateDebutCollaboration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur1.setEmail("jane.doe@example.org");
        detailFournisseur1.setFournisseur(fournisseur);
        detailFournisseur1.setIdDetailFournisseur(1L);
        detailFournisseur1.setMatricule("Matricule");

        Fournisseur fournisseur1 = new Fournisseur();
        fournisseur1.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur1.setCode("Code");
        fournisseur1.setDetailFournisseur(detailFournisseur1);
        fournisseur1.setFactures(new HashSet<>());
        fournisseur1.setIdFournisseur(1L);
        fournisseur1.setLibelle("Libelle");
        fournisseur1.setSecteurActivites(new HashSet<>());
        Optional<Fournisseur> ofResult1 = Optional.of(fournisseur1);

        Fournisseur fournisseur2 = new Fournisseur();
        fournisseur2.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur2.setCode("Code");
        fournisseur2.setDetailFournisseur(new DetailFournisseur());
        fournisseur2.setFactures(new HashSet<>());
        fournisseur2.setIdFournisseur(1L);
        fournisseur2.setLibelle("Libelle");
        fournisseur2.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur2 = new DetailFournisseur();
        detailFournisseur2.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur2.setDateDebutCollaboration(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur2.setEmail("jane.doe@example.org");
        detailFournisseur2.setFournisseur(fournisseur2);
        detailFournisseur2.setIdDetailFournisseur(1L);
        detailFournisseur2.setMatricule("Matricule");

        Fournisseur fournisseur3 = new Fournisseur();
        fournisseur3.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur3.setCode("Code");
        fournisseur3.setDetailFournisseur(detailFournisseur2);
        fournisseur3.setFactures(new HashSet<>());
        fournisseur3.setIdFournisseur(1L);
        fournisseur3.setLibelle("Libelle");
        fournisseur3.setSecteurActivites(new HashSet<>());

        DetailFournisseur detailFournisseur3 = new DetailFournisseur();
        detailFournisseur3.setAdresse("Adresse");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        detailFournisseur3.setDateDebutCollaboration(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        detailFournisseur3.setEmail("jane.doe@example.org");
        detailFournisseur3.setFournisseur(fournisseur3);
        detailFournisseur3.setIdDetailFournisseur(1L);
        detailFournisseur3.setMatricule("Matricule");

        Fournisseur fournisseur4 = new Fournisseur();
        fournisseur4.setCategorieFournisseur(CategorieFournisseur.ORDINAIRE);
        fournisseur4.setCode("Code");
        fournisseur4.setDetailFournisseur(detailFournisseur3);
        fournisseur4.setFactures(new HashSet<>());
        fournisseur4.setIdFournisseur(1L);
        fournisseur4.setLibelle("Libelle");
        fournisseur4.setSecteurActivites(new HashSet<>());
        when(this.fournisseurRepository.save((Fournisseur) any())).thenReturn(fournisseur4);
        when(this.fournisseurRepository.findById((Long) any())).thenReturn(ofResult1);
        this.fournisseurServiceImpl.assignSecteurActiviteToFournisseur(1L, 1L);
        verify(this.secteurActiviteRepository).findById((Long) any());
        verify(this.fournisseurRepository).save((Fournisseur) any());
        verify(this.fournisseurRepository).findById((Long) any());
    }
}

