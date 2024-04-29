package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

import java.util.PriorityQueue;

public class PriorityQueueParkingSpotResearch extends ParkingSpotResearchStrategy{

    private int entryRow = 0;
    private int entryColumn = 0;

    private PriorityQueue<ParkingSpot> spotsQueue;

    public PriorityQueueParkingSpotResearch() {}

    public PriorityQueueParkingSpotResearch(int entryRow, int entryColumn) {
        this.entryRow = entryRow;
        this.entryColumn = entryColumn;
    }

    private void initPriorityQueue(ParkingSpot[][] parkingSpots, SearchCriteria criteria) {

        for (ParkingSpot[] parkingSpot : parkingSpots) {
            for (int j = 0; j < parkingSpots.length; j++) {
                if (criteria.meetCriteria(parkingSpot[j])) {
                    spotsQueue.offer(parkingSpot[j]);
                }
            }
        }

    }

    private double getDistance(ParkingSpot spot, int x, int y) {
        return Math.sqrt(Math.pow(spot.getRowNumber() - x, 2) + Math.pow(spot.getColumnNumber() - y, 2));
    }

    @Override
    public ParkingSpot findSpot(ParkingSpot[][] parkingSpots, SearchCriteria criteria) {
        if(spotsQueue == null) {
            spotsQueue = new PriorityQueue<>((a, b) -> Double.compare(getDistance(a, entryRow, entryColumn), getDistance(b, entryRow, entryColumn)));
        }
        initPriorityQueue(parkingSpots, criteria);
        return spotsQueue.peek();
    }

    public int getEntryRow() {
        return entryRow;
    }

    public int getEntryColumn() {
        return entryColumn;
    }

}
