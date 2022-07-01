package com.adp.payment;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.adp.payment.controller.PaymentController;
import com.adp.payment.exception.InvalidBillAmountException;
import com.adp.payment.exception.NotEnoughCoinsException;
import com.adp.payment.service.PaymentService;
import com.adp.payment.service.impl.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentControllerTest {

	@Autowired
    private MockMvc mockMvc;
 
	@InjectMocks
	PaymentController paymentController;
	
    @MockBean
    private PaymentService paymentService;
 
    private static ObjectMapper mapper = new ObjectMapper();
 
    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

    }
    @Test
    @Order(1)
    public void testGetExample() throws Exception {
		Map<Integer,Integer> coinMap = new HashMap<>();
		coinMap.put(1, 1);
		coinMap.put(10, 1);
        Mockito.when(paymentService.getChangeDue(anyInt())).thenReturn(coinMap);
        mockMvc.perform(get("/change").param("bill", "11")).andExpect(status().isOk());
    }
 
    @Test
    @Order(2)
    public void testGetExample_emptyBill() throws Exception {
		Map<Integer,Integer> coinMap = new HashMap<>();
		coinMap.put(1, 1);
		coinMap.put(10, 1);
        Mockito.when(paymentService.getChangeDue(anyInt())).thenReturn(coinMap);
        mockMvc.perform(get("/change/")).andExpect(status().isBadRequest());
    }
    
//    @Test(expected=NotEnoughCoinsException.class)
//    @Order(3)
//    public void testGetExample_invalidBill() throws Exception {
//      //  Mockito.when(paymentService.getChangeDue(anyInt())).thenThrow(new NotEnoughCoinsException("Not enough coins available"));
//        mockMvc.perform(get("/change/").param("bill", "110")).andExpect(status().is4xxClientError());
//    }
}
