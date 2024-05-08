package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.dao.BookingRepository;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Integer countBookingByDate(LocalDate actualDate) {
        return bookingRepository.countBookingByDate(actualDate);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }
}
