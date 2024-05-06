package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.dao.BookingRepository;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService implements IBookingService {

    private BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findByDate(LocalDate date) {
        return bookingRepository.findByDate(date);
    }

    @Override
    public int countBookingByCustomerUserAndDate(User customerUser, LocalDate date) {
        return bookingRepository.countBookingByCustomerUserAndDate(customerUser, date);
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }



    @Override
    public Booking findByCustomerUserAndClaimedFalseAndDate(User customerUser, LocalDate date) {
        return bookingRepository.findByCustomerUserAndClaimedFalseAndDate(customerUser, date);
    }

    @Override
    @Transactional
    public void update(long id, boolean claimed) {
        bookingRepository.update(id, claimed);
    }
}