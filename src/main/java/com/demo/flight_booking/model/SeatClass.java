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

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeatClassType seatClassName;

    @Column(nullable = false)
    private Double basePrice;

    @OneToMany(mappedBy = "seatClass", cascade = CascadeType.ALL)
    private List<Seat> seats;
}
