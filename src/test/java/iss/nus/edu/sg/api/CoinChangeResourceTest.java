/**
 * Unit Test references:
 * - https://nickb.dev/blog/dropwizard-getting-started-testing/
 * - https://www.dropwizard.io/en/stable/manual/testing.html
 * */

package iss.nus.edu.sg.api;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CoinChangeResourceTest {
    private CoinChangeResource resource;

    @BeforeEach
    public void setup() {
        resource = new CoinChangeResource();
    }

    @Test
    public void testCoinChangeSuccessOne() {
        List<Double> denominations = Arrays.asList(0.01, 0.5, 1.0, 2.0);
        Response response = resource.coinChange(5.0, denominations);
        @SuppressWarnings("unchecked")
        List<Double> result = (List<Double>) response.getEntity();
        assertThat(response.getStatus(), equalTo(200));
        assertThat(result, equalTo(Arrays.asList(1.0, 2.0, 2.0)));
    }

    @Test
    public void testCoinChangeSuccessTwo() {
        List<Double> denominations = Arrays.asList(0.01, 0.5, 1.0, 5.0, 10.0);
        Response response = resource.coinChange(7.03, denominations);
        @SuppressWarnings("unchecked")
        List<Double> result = (List<Double>) response.getEntity();
        assertThat(response.getStatus(), equalTo(200));
        assertThat(result, equalTo(Arrays.asList(0.01, 0.01, 0.01, 1.0, 1.0, 5.0)));
    }

    @Test
    public void testCoinChangeSuccessThree() {
        List<Double> denominations = Arrays.asList(1.0, 2.0, 50.0);
        Response response = resource.coinChange(103, denominations);
        @SuppressWarnings("unchecked")
        List<Double> result = (List<Double>) response.getEntity();
        assertThat(response.getStatus(), equalTo(200));
        assertThat(result, equalTo(Arrays.asList(1.0, 2.0, 50.0, 50.0)));
    }

    @Test
    public void testCoinChangeFailureOne() {
        // Empty denomination list
        List<Double> denominations = new ArrayList<>();
        Response response = resource.coinChange(5.0, denominations);
        String result = (String) response.getEntity();
        assertThat(response.getStatus(), equalTo(400));
        assertThat(result, equalTo("coinDenominations parameter is required."));
    }

    @Test
    public void testCoinChangeFailureTwo() {
        // Contain illegal dominations
        List<Double> denominations = Arrays.asList(0.01, 0.5, 1.0, 2.0, 8.888);
        Response response = resource.coinChange(10.0, denominations);
        String result = (String) response.getEntity();
        assertThat(response.getStatus(), equalTo(400));
        assertThat(result, equalTo( "Invalid coin denomination: 8.888. Allowed denominations are: [0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0]"));
    }

    @Test
    public void testCoinChangeFailureThree() {
        // Contain negative 'target' parameter
        List<Double> denominations = Arrays.asList(0.01, 0.5, 1.0, 2.0);
        Response response = resource.coinChange(-100.0, denominations);
        String result = (String) response.getEntity();
        assertThat(response.getStatus(), equalTo(400));
        assertThat(result, equalTo("Target parameter should be equal to or greater than 0."));
    }

    @Test
    public void testCoinChangeFailureFour() {
        // 'target' parameter > 10,000 (e.g. 100,000)
        List<Double> denominations = Arrays.asList(0.01, 0.5, 1.0, 2.0);
        Response response = resource.coinChange(100000.00, denominations);
        String result = (String) response.getEntity();
        assertThat(response.getStatus(), equalTo(400));
        assertThat(result, equalTo("Target parameter should be equal to or less than 10,000."));
    }
}
