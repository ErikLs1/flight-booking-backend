package com.demo.flight_booking.model;

import com.demo.flight_booking.model.enums.SeatClassType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "SEAT_CLASS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatClassId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeatClassType seatClassName;

    @Column(nullable = false)
    private Boolean extraLegRoom;

    @Column(nullable = false)
    private Boolean nearExit;

    @Column(name = "window_seat", nullable = false)
    private Boolean window;

    @Column(nullable = false)
    private Boolean aisle;

    @Column(nullable = false)
    private Double basePrice;

    @OneToMany(mappedBy = "seatClass", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
