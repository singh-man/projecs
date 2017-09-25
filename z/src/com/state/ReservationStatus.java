package com.state;

public enum ReservationStatus implements ReservationStatusOperations {

	// Of course the inner class used need to be SOLID impl of interface.
	NEW(new ReservationStatusOperations() {

		@Override
		public ReservationStatus accept(Reservation reservation) {
			return ACCEPTED;
		}

		@Override
		public ReservationStatus charge(Reservation reservation) {
			return null;
		}

		@Override
		public ReservationStatus cancel(Reservation reservation) {
			return null;
		}

	}),
	ACCEPTED(new ReservationStatusOperations() {

		@Override
		public ReservationStatus accept(Reservation reservation) {
			return null;
		}

		@Override
		public ReservationStatus charge(Reservation reservation) {
			return null;
		}

		@Override
		public ReservationStatus cancel(Reservation reservation) {
			return null;
		}

	});

	private final ReservationStatusOperations operations;

	ReservationStatus(ReservationStatusOperations operations) {
		this.operations = operations;
	}

	@Override
	public ReservationStatus accept(Reservation reservation) {
		return operations.accept(reservation);
	}

	@Override
	public ReservationStatus charge(Reservation reservation) {
		return operations.charge(reservation);
	}

	@Override
	public ReservationStatus cancel(Reservation reservation) {
		return operations.cancel(reservation);
	}

}


