package com.demo.flight_booking.dto.filter;

import com.demo.flight_booking.model.enums.SeatClassType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRecommendationDTO {
    private Long flightId;
    private int passengerCount;

    // ECONOMY, BUSINESS, etc.
    private SeatClassType seatClassType;
    private Boolean extraLegRoomPreferred;
    private Boolean nearExitPreferred;
    private Boolean windowPreferred;
    private Boolean aislePreferred;

    // if users want to sit together
    private Boolean adjacentPreferred;
}
