package tn.esprit.rh.achat.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.services.IStockService;

@ContextConfiguration(classes = {StockRestController.class})
@ExtendWith(SpringExtension.class)
class StockRestControllerTest {
    @MockBean
    private IStockService iStockService;

    @Autowired
    private StockRestController stockRestController;


    @Test
    void testRetrieveStock() throws Exception {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("hhkj2jop8kp5j");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);
        when(this.iStockService.retrieveStock((Long) any())).thenReturn(stock);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/retrieve-stock/{stock-id}", 1L);
        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"idStock\":1,\"libelleStock\":\"hhkj2jop8kp5j\",\"qte\":1,\"qteMin\":1}"));
    }


    @Test
    void testRetrieveStock2() throws Exception {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("hhkj2jop8kp5j");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);
        when(this.iStockService.retrieveStock((Long) any())).thenReturn(stock);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/stock/retrieve-stock/{stock-id}", 1L);

        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"idStock\":1,\"libelleStock\":\"hhkj2jop8kp5j\",\"qte\":1,\"qteMin\":1}"));
    }


    @Test
    void testAddStock() throws Exception {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("hhkj2jop8kp5j");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);
        when(this.iStockService.addStock((Stock) any())).thenReturn(stock);

        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setLibelleStock("jlokj2jop8kp5j");
        stock1.setProduits(new HashSet<>());
        stock1.setQte(1);
        stock1.setQteMin(1);
        String content = (new ObjectMapper()).writeValueAsString(stock1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stock/add-stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"idStock\":1,\"libelleStock\":\"hhkj2jop8kp5j\",\"qte\":1,\"qteMin\":1}"));
    }


    @Test
    void testGetStocks() throws Exception {
        when(this.iStockService.retrieveAllStocks()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stock/retrieve-all-stocks");
        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetStocks2() throws Exception {
        when(this.iStockService.retrieveAllStocks()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/stock/retrieve-all-stocks");

        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testModifyStock() throws Exception {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setLibelleStock("hhkj2jop8kp5j");
        stock.setProduits(new HashSet<>());
        stock.setQte(1);
        stock.setQteMin(1);
        when(this.iStockService.updateStock((Stock) any())).thenReturn(stock);

        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setLibelleStock("hhkj2jop8kp5j");
        stock1.setProduits(new HashSet<>());
        stock1.setQte(1);
        stock1.setQteMin(1);
        String content = (new ObjectMapper()).writeValueAsString(stock1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/stock/modify-stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"idStock\":1,\"libelleStock\":\"hhkj2jop8kp5j\",\"qte\":1,\"qteMin\":1}"));
    }


    @Test
    void testRemoveStock() throws Exception {
        doNothing().when(this.iStockService).deleteStock((Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/stock/remove-stock/{stock-id}", 1L);
        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testRemoveStock2() throws Exception {
        doNothing().when(this.iStockService).deleteStock((Long) any());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/stock/remove-stock/{stock-id}", 1L);

        MockMvcBuilders.standaloneSetup(this.stockRestController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

