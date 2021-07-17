package booking.service;

import booking.command.CreateAccommodationCommand;
import booking.command.CreateReservationCommand;
import booking.command.UpdatePriceCommand;
import booking.entity.Accommodation;
import booking.entity.AccommodationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookingService {

    private final List<Accommodation> accommodations = new ArrayList<>();

    private AtomicLong idGenerator = new AtomicLong();

    private final ModelMapper modelMapper;

    public BookingService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<AccommodationDTO> listAccommodations(Optional<String> city) {
        return accommodations.stream()
                .filter(accommodation -> city.isEmpty() || accommodation.getCity().equalsIgnoreCase(city.get()))
                .map(accommodation -> modelMapper.map(accommodation, AccommodationDTO.class))
                .toList();
    }

    public AccommodationDTO findAccommodationById(Long id) {
        Accommodation result = findById(id);
        return modelMapper.map(result, AccommodationDTO.class);
    }

    public AccommodationDTO createAccommodation(CreateAccommodationCommand command) {
        Accommodation accommodation = new Accommodation(
                idGenerator.incrementAndGet(),
                command.getName(),
                command.getCity(),
                command.getMaxCapacity(),
                command.getPrice());
        accommodations.add(accommodation);
        return modelMapper.map(accommodation, AccommodationDTO.class);
    }

    public AccommodationDTO reserveSpaceById(Long id, CreateReservationCommand command) {
        Accommodation result = findById(id);
        result.setAvailableCapacity(command.getReservation());
        return modelMapper.map(result, AccommodationDTO.class);
    }

    public void deleteAll() {
        idGenerator = new AtomicLong();
        accommodations.clear();
    }

    private Accommodation findById(Long id) {
        return accommodations.stream()
                .filter(accommodation -> accommodation.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find id: " + id));
    }

    public AccommodationDTO updatePrice(Long id, UpdatePriceCommand command) {
        Accommodation result = findById(id);
        result.setPrice(command.getPrice());
        return modelMapper.map(result, AccommodationDTO.class);
    }
}
