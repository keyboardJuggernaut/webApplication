package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

/**
 * The {@code LinearSearchParkingSpotResearchStrategy} implements {@code ParkingSpotResearchStrategy}
 */
public class LinearSearchParkingSpotResearchStrategy extends ParkingSpotResearchStrategy {

    /**
     * Find parking spot iterating over spots and checking if criteria are satisfied
     * Note: time complexity is O(n*m)
     * @param parkingSpots bi-dimensional matrix including spots
     * @param criteria criteria to satisfy
     * @return spot found
     */
    @Override
    public ParkingSpot findSpot(ParkingSpot[][] parkingSpots, SearchCriteria criteria) {
        int rows = parkingSpots.length;
        int cols = parkingSpots[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (criteria.meetCriteria(parkingSpots[i][j])) {
                    return parkingSpots[i][j];
                }
            }
        }
        return null;
    }
}
