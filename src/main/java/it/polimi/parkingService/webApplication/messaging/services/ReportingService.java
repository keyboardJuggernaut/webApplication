package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.dao.ReportingRepository;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.parking.dao.ParkingRepository;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Reporting findById(long id) {
        Optional<Reporting> result = reportingRepository.findById(id);

        Reporting reporting = null;

        if (result.isPresent()) {
            reporting = result.get();
            return reporting;
        }
        return reporting;
    }

    @Override
    public void save(Reporting reporting) {
        reportingRepository.save(reporting);
    }


}
