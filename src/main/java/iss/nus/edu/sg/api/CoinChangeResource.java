package iss.nus.edu.sg.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Path("coinChange")
public class CoinChangeResource {
    // Allowed coin denominations
    private static final List<Double> ALLOWED_DENOMINATIONS = Arrays.asList(0.01, 0.05, 0.1, 0.2, 0.5, 1.0, 2.0, 5.0, 10.0, 50.0, 100.0, 1000.0);

    @GET
    public Response coinChange(
            @QueryParam("target") double target,
            @QueryParam("coinDenominations") List<Double> coinDenominations
    ) {

        /*
         * Validation checks & assumptions:
         *   - Coin denomination cannot be empty
         *   - Coin denomination must contain one of the following values [0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000]
         *   - Target must be between 0 and 10,000.00 (inclusive)
         * */

        if (coinDenominations == null || coinDenominations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("coinDenominations parameter is required.")
                    .build();
        }

        for (Double denomination : coinDenominations) {
            if (!ALLOWED_DENOMINATIONS.contains(denomination)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid coin denomination: " + denomination + ". Allowed denominations are: " + ALLOWED_DENOMINATIONS)
                        .build();
            }
        }

        if (target < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Target parameter should be equal to or greater than 0.")
                    .build();
        }

        if (target > 10000) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Target parameter should be equal to or less than 10,000.")
                    .build();
        }

        // Once pass validation, calculate the coin change
        List<Double> result = calculateCoinChange(target, coinDenominations);

        return Response.ok(result).build();
    }

    private List<Double> calculateCoinChange(double target, List<Double> coinDenominations) {
        List<Double> result = new ArrayList<>();

        // Sort coinDenominations first as it may be unsorted from frontend
        Collections.sort(coinDenominations);

        for (int i = coinDenominations.size() - 1; i >= 0; i--) {
            while (target >= coinDenominations.get(i)) {
                result.add(coinDenominations.get(i));
                target -= coinDenominations.get(i);
                target = Math.round(target * 100.0) / 100.0; // Bug-fix: Avoid floating-point precision issues
            }
        }

        // Return result in asc order
        Collections.sort(result);
        return result;
    }
}
