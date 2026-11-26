package com.romeo.spaza;

import com.romeo.spaza.dto.SaleRequest;
import com.romeo.spaza.dto.SaleResponse;
import com.romeo.spaza.model.Product;
import com.romeo.spaza.repository.ProductRepository;
import com.romeo.spaza.service.SaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpazaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleService saleService;

    @Test
    void contextLoads() {
    }

    @Test
    void shopDataRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isForbidden());
    }

    @Test
    void productionFrontendAssetsAreAvailableWithoutAuthentication() throws Exception {
        String html = new String(resourceLoader.getResource("classpath:/static/index.html")
                .getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        Matcher assets = Pattern.compile("(?:src|href)=\"(?:\\./)?(assets/[^\"]+)\"")
                .matcher(html);
        int assetCount = 0;
        while (assets.find()) {
            mockMvc.perform(get("/" + assets.group(1)))
                    .andExpect(status().isOk());
            assetCount++;
        }

        assertTrue(assetCount >= 2, "Expected the app entry page to reference JavaScript and CSS assets");
    }

    @Test
    void registrationRequiresOwnerAuthentication() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"newstaff\",\"password\":\"long-enough-password\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void staffCannotCreateProducts() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New item\",\"price\":1.00,\"quantity\":1,\"reorderLevel\":1}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void recordingSaleUsesLockedProductAndUpdatesStock() {
        Product product = productRepository.save(Product.builder()
                .name("Lock test product")
                .price(new BigDecimal("3.50"))
                .quantity(3)
                .reorderLevel(1)
                .build());
        SaleRequest request = new SaleRequest();
        request.setProductId(product.getId());
        request.setQuantity(2);

        SaleResponse response = saleService.recordSale(request);

        assertEquals(1, response.getRemainingStock());
        assertEquals(1, productRepository.findById(product.getId()).orElseThrow().getQuantity());
    }
}
