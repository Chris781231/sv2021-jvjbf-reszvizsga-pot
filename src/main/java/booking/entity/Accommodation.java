package booking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {

    private Long id;

    private String name;

    private String city;

    private List<Integer> reserved = new ArrayList<>();

    private int maxCapacity;

    private int availableCapacity;

    private int price;

    public Accommodation(Long id, String name, String city, int maxCapacity, int price) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.maxCapacity = maxCapacity;
        this.availableCapacity = maxCapacity;
        this.price = price;
    }

    public void setAvailableCapacity(int space) {
        if (space > availableCapacity) {
            throw new IllegalStateException("Not enough available space");
        }
        reserved.add(space);
        availableCapacity -= space;
    }
}
