package booking.controller;

import booking.command.CreateAccommodationCommand;
import booking.command.CreateReservationCommand;
import booking.command.UpdatePriceCommand;
import booking.entity.AccommodationDTO;
import booking.service.BookingService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accommodations")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping
    public List<AccommodationDTO> listAccomodations(@RequestParam Optional<String> city) {
        return service.listAccommodations(city);
    }

    @GetMapping("/{id}")
    public AccommodationDTO findAccommodationById(@PathVariable Long id) {
        return service.findAccommodationById(id);
    }

    @PostMapping
    public AccommodationDTO createAccommodation(@RequestBody @Valid CreateAccommodationCommand command) {
        return service.createAccommodation(command);
    }

    @PostMapping("/{id}/book")
    public AccommodationDTO reserveSpaceById(@PathVariable Long id, @RequestBody CreateReservationCommand command) {
        return service.reserveSpaceById(id, command);
    }

    @PutMapping("/{id}")
    public AccommodationDTO updatePrice(@PathVariable Long id, @RequestBody UpdatePriceCommand command) {
        return service.updatePrice(id, command);
    }

    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }
}
