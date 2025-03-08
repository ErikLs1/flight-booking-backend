package com.demo.flight_booking.dto.filter;

import com.demo.flight_booking.model.enums.SeatClassType;
import lombok.Data;

@Data
public class SeatRecommendationDTO {
    private Long flightId;
    private int passengerCount;

    // ECONOMY, BUSINESS, etc.
    private SeatClassType seatClassType;
    private Boolean extraLegRoomPreferred;
    private Boolean nearExitPreferred;
    private Boolean windowPreferred;
    private Boolean aislePreferred;

    // if they want to sit together
    private Boolean adjacentPreferred;
}
