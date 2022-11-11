package tn.esprit.rh.achat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
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

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.CategorieFournisseur;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.ReglementRepository;

@ContextConfiguration(classes = {ReglementServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ReglementServiceImplTest {
    @MockBean
    private FactureRepository factureRepository;

    @MockBean
    private ReglementRepository reglementRepository;

    @Autowired
    private ReglementServiceImpl reglementServiceImpl;




    @Test
    void testAddReglement() {


        Facture facture = new Facture();
        facture.setArchivee(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 8, 15).atStartOfDay();
        facture.setDateCreationFacture(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult2 = LocalDate.of(2022, 8, 17).atStartOfDay();
        facture.setDateDerniereModificationFacture(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        facture.setDetailsFacture(new HashSet<>());
        facture.setIdFacture(1L);
        facture.setMontantFacture(10.0f);
        facture.setMontantRemise(10.0f);
        facture.setReglements(new HashSet<>());

        Reglement reglement = new Reglement();
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        reglement.setDateReglement(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        reglement.setFacture(facture);
        reglement.setIdReglement(1L);
        reglement.setMontantPaye(10.0f);
        reglement.setMontantRestant(10.0f);
        reglement.setPayee(true);
        when(reglementRepository.save((Reglement) any())).thenReturn(reglement);


        Facture facture1 = new Facture();
        facture1.setArchivee(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(2022, 5, 22).atStartOfDay();
        facture1.setDateCreationFacture(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult6 = LocalDate.of(2022, 5, 21).atStartOfDay();
        facture1.setDateDerniereModificationFacture(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        facture1.setDetailsFacture(new HashSet<>());
        facture1.setIdFacture(1L);
        facture1.setMontantFacture(10.0f);
        facture1.setMontantRemise(10.0f);
        facture1.setReglements(new HashSet<>());

        Reglement reglement1 = new Reglement();
        LocalDateTime atStartOfDayResult7 = LocalDate.of(2022, 5, 22).atStartOfDay();
        reglement1.setDateReglement(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        reglement1.setFacture(facture1);
        reglement1.setIdReglement(1L);
        reglement1.setMontantPaye(10.0f);
        reglement1.setMontantRestant(10.0f);
        reglement1.setPayee(true);
        assertSame(reglement1, reglementServiceImpl.addReglement(reglement1));
        verify(reglementRepository).save((Reglement) any());
    }


    @Test
    void testGetReglementById() {

        Reglement reglement = new Reglement();
        LocalDateTime atStartOfDayResult3 = LocalDate.of(2022, 6, 8).atStartOfDay();
        reglement.setDateReglement(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));

        reglement.setIdReglement(1L);
        reglement.setMontantPaye(10.0f);
        reglement.setMontantRestant(10.0f);
        reglement.setPayee(true);
        Optional<Reglement> ofResult = Optional.of(reglement);
        when(reglementRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(reglement, reglementServiceImpl.retrieveReglement(123L));
        verify(reglementRepository).findById((Long) any());
    }


    @Test
    void testGetReglementByFacture() {
        ArrayList<Reglement> reglementList = new ArrayList<>();
        when(reglementRepository.retrieveReglementByFacture((Long) any())).thenReturn(reglementList);
        List<Reglement> actualRetrieveReglementByFactureResult = reglementServiceImpl.retrieveReglementByFacture(1L);
        assertSame(reglementList, actualRetrieveReglementByFactureResult);
        assertTrue(actualRetrieveReglementByFactureResult.isEmpty());
        verify(reglementRepository).retrieveReglementByFacture((Long) any());
    }


    @Test
    void testGetChiffreAffaireEntreDeuxDate() {
        when(reglementRepository.getChiffreAffaireEntreDeuxDate((Date) any(), (Date) any())).thenReturn(10.0f);
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 5, 18).atStartOfDay();
        Date startDate = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 8, 21).atStartOfDay();
        assertEquals(10.0f, reglementServiceImpl.getChiffreAffaireEntreDeuxDate(startDate,
                Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant())));
        verify(reglementRepository).getChiffreAffaireEntreDeuxDate((Date) any(), (Date) any());
    }
}

