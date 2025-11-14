package com.jacobsrene.carrentalapp;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableWireMock({
        @ConfigureWireMock(name = "external-api", port = 9090),
})
public class BookingControllerIntegrationTest {
    @InjectWireMock("external-api")
    WireMockServer mockApiService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldConfirmBookingSuccessfully() throws Exception {
        mockApiService.stubFor(WireMock.post("/rental/rate")
                .willReturn(okJson("""
                            {
                              "category": "SMALL",
                              "ratePerDay": 199.99
                            }
                        """)));

        mockApiService.stubFor(WireMock.post("/license/details")
                .willReturn(okJson("""
                            {
                              "ownerName": "John Doe",
                              "expiryDate": "2030-11-10"
                            }
                        """)));

        String requestBody = """
                    {
                      "drivingLicenseNumber": "ABC123",
                      "customerAge": 30,
                      "reservationStart": "2025-12-01",
                      "reservationEnd": "2025-12-05",
                      "carSegment": "SMALL"
                    }
                """;

        mockMvc.perform(post("/booking/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void shouldRetrieveBookingDetailsSuccessfully() throws Exception {
        String confirmRequest = """
                    {
                      "drivingLicenseNumber": "ABC123",
                      "customerAge": 30,
                      "reservationStart": "2025-12-01",
                      "reservationEnd": "2025-12-05",
                      "carSegment": "SMALL"
                    }
                """;

        String responseBody = mockMvc.perform(post("/booking/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(confirmRequest))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        UUID bookingId = UUID.fromString(responseBody.replace("\"", "")); // parse UUID from response

        mockMvc.perform(MockMvcRequestBuilders.get("/booking/{id}", bookingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.drivingLicenseNumber").value("ABC123"))
                .andExpect(jsonPath("$.customerAge").value(30))
                .andExpect(jsonPath("$.carSegment").value("SMALL"))
                .andExpect(jsonPath("$.rentalPrice").value(75.0));
    }
}
