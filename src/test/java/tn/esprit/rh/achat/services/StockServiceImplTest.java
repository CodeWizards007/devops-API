package tn.esprit.rh.achat.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

@ContextConfiguration(classes = {StockServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StockServiceImplTest {
    @MockBean
    private StockRepository stockRepository;

    @Autowired
    private StockServiceImpl stockServiceImpl;

    /**
     * Method under test: {@link StockServiceImpl#retrieveAllStocks()}
     */
    @Test
    void testRetrieveAllStocks() {
        ArrayList<Stock> stockList = new ArrayList<>();
        when(this.stockRepository.findAll()).thenReturn(stockList);
        List<Stock> actualRetrieveAllStocksResult = this.stockServiceImpl.retrieveAllStocks();
        assertSame(stockList, actualRetrieveAllStocksResult);
        assertTrue(actualRetrieveAllStocksResult.isEmpty());
        verify(this.stockRepository).findAll();
    }

    /**
     * Method under test: {@link StockServiceImpl#retrieveAllStocks()}
     */
    @Test
    void testRetrieveAllStocks2() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("In method retrieveAllStocks");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);

        ArrayList<Stock> stockList = new ArrayList<>();
        stockList.add(stock);
        when(this.stockRepository.findAll()).thenReturn(stockList);
        List<Stock> actualRetrieveAllStocksResult = this.stockServiceImpl.retrieveAllStocks();
        assertSame(stockList, actualRetrieveAllStocksResult);
        assertEquals(1, actualRetrieveAllStocksResult.size());
        verify(this.stockRepository).findAll();
    }

    /**
     * Method under test: {@link StockServiceImpl#addStock(Stock)}
     */
    @Test
    void testAddStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("Libelle Stock");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);
        when(this.stockRepository.save((Stock) any())).thenReturn(stock);

        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setLibelleStock("Libelle Stock");
        stock1.setProduits(new HashSet<>());
        stock1.setQte(1);
        stock1.setQteMin(1);
        assertSame(stock, this.stockServiceImpl.addStock(stock1));
        verify(this.stockRepository).save((Stock) any());
    }

    /**
     * Method under test: {@link StockServiceImpl#deleteStock(Long)}
     */
    @Test
    void testDeleteStock() {
        doNothing().when(this.stockRepository).deleteById((Long) any());
        this.stockServiceImpl.deleteStock(123L);
        verify(this.stockRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link StockServiceImpl#updateStock(Stock)}
     */
    @Test
    void testUpdateStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("Libelle Stock");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);
        when(this.stockRepository.save((Stock) any())).thenReturn(stock);

        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setLibelleStock("Libelle Stock");
        stock1.setProduits(new HashSet<>());
        stock1.setQte(1);
        stock1.setQteMin(1);
        assertSame(stock, this.stockServiceImpl.updateStock(stock1));
        verify(this.stockRepository).save((Stock) any());
    }

    /**
     * Method under test: {@link StockServiceImpl#retrieveStock(Long)}
     */
    @Test
    void testRetrieveStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("Libelle Stock");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);
        Optional<Stock> ofResult = Optional.of(stock);
        when(this.stockRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(stock, this.stockServiceImpl.retrieveStock(123L));
        verify(this.stockRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link StockServiceImpl#retrieveStatusStock()}
     */
    @Test
    void testRetrieveStatusStock() {
        when(this.stockRepository.retrieveStatusStock()).thenReturn(new ArrayList<>());
        assertEquals("", this.stockServiceImpl.retrieveStatusStock());
        verify(this.stockRepository).retrieveStatusStock();
    }

    /**
     * Method under test: {@link StockServiceImpl#retrieveStatusStock()}
     */
    @Test
    void testRetrieveStatusStock2() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("yyyy-MM-dd HH:mm:ss.SSS");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);

        ArrayList<Stock> stockList = new ArrayList<>();
        stockList.add(stock);
        when(this.stockRepository.retrieveStatusStock()).thenReturn(stockList);
        this.stockServiceImpl.retrieveStatusStock();
        verify(this.stockRepository).retrieveStatusStock();
    }
}

