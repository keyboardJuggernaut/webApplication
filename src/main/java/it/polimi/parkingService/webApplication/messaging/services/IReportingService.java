package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.models.Reporting;

import java.util.List;

public interface IReportingService {
    public List<Reporting> findAll();

    void save(Reporting reporting);
}
