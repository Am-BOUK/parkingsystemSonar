package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		/*******
		 * getTime() is in milliseconds type of getTime() is long
		 */
		long inHour = ticket.getInTime().getTime();
		long outHour = ticket.getOutTime().getTime();

		/*******
		 * get duration is in ms type of duration must be double
		 */
		double duration = outHour - inHour;

		// Free if 30-min parking
		if (duration > 30 * 60 * 1000) {
			switch (ticket.getParkingSpot().getParkingType()) {
			case CAR: {

				ticket.setPrice(duration / (60 * 60 * 1000) * Fare.CAR_RATE_PER_HOUR);

				break;
			}
			case BIKE: {
				ticket.setPrice(duration / (60 * 60 * 1000) * Fare.BIKE_RATE_PER_HOUR);
				break;
			}
			default:
				throw new IllegalArgumentException("Unkown Parking Type");
			}
		} else {
			ticket.setPrice(0);
		}

	}
}