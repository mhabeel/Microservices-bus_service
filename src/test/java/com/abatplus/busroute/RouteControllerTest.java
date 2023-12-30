package com.abatplus.busroute;
import com.fasterxml.jackson.core.type.TypeReference;
import com.abatplus.busroute.model.Bus;
import com.abatplus.busroute.model.Route;
import com.abatplus.busroute.services.BusServices;
import com.abatplus.busroute.services.RouteServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteServices routeServices;

    @MockBean
    private BusServices busServices;

    @Test
    public void testAddRoute() throws Exception {
        // Create a sample route
        Route route = new Route("Start", "End");

        // Mock the routeServices to return the saved route
        when(routeServices.saveRoute(any(Route.class))).thenReturn(route);

        // Perform a POST request to add a route
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/post")
                        .content("{ \"aktuelOrt\": \"Start\", \"zielOrt\": \"End\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Deserialize the response to a Route object
        Route responseRoute = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Route.class);

        // Verify that the saved route has the correct values
        assertEquals(route.getAktuelOrt(), responseRoute.getAktuelOrt());
        assertEquals(route.getZielOrt(), responseRoute.getZielOrt());
    }

    @Test
    public void testGetAlleRoute() throws Exception {
        // Create a sample list of routes
        List<Route> routes = Arrays.asList(new Route("Start1", "End1"), new Route("Start2", "End2"));

        // Mock the routeServices to return the list of routes
        when(routeServices.getAlleRoute()).thenReturn(routes);

        // Perform a GET request to retrieve all routes
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/routs"))
                .andExpect(status().isOk())
                .andReturn();

        // Deserialize the response to a list of Route objects
        List<Route> responseRoutes = new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<Route>>() {});

        // Verify that the retrieved routes match the expected list
        assertEquals(routes.size(), responseRoutes.size());
        assertEquals(routes.get(0).getAktuelOrt(), responseRoutes.get(0).getAktuelOrt());
        assertEquals(routes.get(1).getZielOrt(), responseRoutes.get(1).getZielOrt());
    }

    // Add more tests for other controller methods as needed
}
