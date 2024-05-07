package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.dao.ReportingRepository;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.parking.dao.ParkingRepository;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingService implements IReportingService{
    private ReportingRepository reportingRepository;

    public ReportingService(ReportingRepository reportingRepository) {
        this.reportingRepository = reportingRepository;
    }

    @Override
    public List<Reporting> findAll() {
        return reportingRepository.findAll();
    }

    @Override
    public void save(Reporting reporting) {
        reportingRepository.save(reporting);
    }


}
