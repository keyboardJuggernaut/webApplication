package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

import java.util.PriorityQueue;

/**
 * The {@code PriorityQueueParkingSpotResearch} implements {@code ParkingSpotResearchStrategy}
 */
public class PriorityQueueParkingSpotResearch extends ParkingSpotResearchStrategy{

    /**
     * Default parking area entry coordinates
     */
    private final int ENTRY_ROW = 0;
    private final int ENTRY_COLUMN = 0;

    private PriorityQueue<ParkingSpot> spotsQueue;

    public PriorityQueueParkingSpotResearch() {}

    /**
     * Find parking spot leveraging distance priority queue
     * Note: time complexity is O(n*m) due to initialization phase
     * @param parkingSpots bi-dimensional matrix including spots
     * @param criteria criteria to satisfy
     * @return spot found
     */
    @Override
    public ParkingSpot findSpot(ParkingSpot[][] parkingSpots, SearchCriteria criteria) {
        if(spotsQueue == null) {
            spotsQueue = new PriorityQueue<>((a, b) -> Double.compare(getDistance(a, ENTRY_ROW, ENTRY_COLUMN), getDistance(b, ENTRY_ROW, ENTRY_COLUMN)));
        }
        initPriorityQueue(parkingSpots, criteria);
        return spotsQueue.peek();
    }

    /**
     * Initializes priority queue with suitable spots
     * @param parkingSpots spots
     * @param criteria custom criteria
     */
    private void initPriorityQueue(ParkingSpot[][] parkingSpots, SearchCriteria criteria) {
        for (ParkingSpot[] parkingSpot : parkingSpots) {
            for (int j = 0; j < parkingSpots.length; j++) {
                if (criteria.meetCriteria(parkingSpot[j])) {
                    spotsQueue.offer(parkingSpot[j]);
                }
            }
        }
    }

    /**
     * Calculates Euclidean distance between a spot and the parking area entry
     * @param spot the spot
     * @param x x-coordinate
     * @param y y-coordinate
     * @return distance value
     */
    private double getDistance(ParkingSpot spot, int x, int y) {
        return Math.sqrt(Math.pow(spot.getRowNumber() - x, 2) + Math.pow(spot.getColumnNumber() - y, 2));
    }
}
