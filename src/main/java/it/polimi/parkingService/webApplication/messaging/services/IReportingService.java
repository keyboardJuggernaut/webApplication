package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.models.Reporting;

import java.util.List;

public interface IReportingService {
    public List<Reporting> findAll();
    public Reporting findById(long id);
    public void save(Reporting reporting);
}
