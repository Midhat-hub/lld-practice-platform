package com.example.lld_practice_platform.config;

import com.example.lld_practice_platform.model.Problem;
import com.example.lld_practice_platform.repository.ProblemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProblemRepository problemRepository;

    public DataSeeder(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    @Override
    public void run(String... args) {
        if (problemRepository.count() == 0) {

            Problem parkingLot = new Problem();
            parkingLot.setTitle("Parking Lot System");
            parkingLot.setDifficulty("Medium");
            parkingLot.setDescription("Design a multi-level parking lot system that can manage multiple vehicle types and parking spots across multiple floors.");
            parkingLot.setRequirements("""
                CORE REQUIREMENTS:
                1. Support 3 vehicle types: Motorcycle (Small spot), Car (Compact spot), Truck/Bus (Large spot).
                2. Spot Allocation: assign the nearest available spot matching vehicle size, or a larger one if none matches exactly.
                3. Support multiple floors, each with configurable spot counts per type.
                4. Ticket Generation: issue an entry ticket with a unique ID, timestamp, floor, and spot number.
                5. Payment & Pricing: support flat rate and hourly rate, with a vehicle-type multiplier. Should allow plugging in new pricing rules later.
                6. Free up the spot and calculate the final fee when the vehicle exits.
                7. Real-time capacity tracking: available spots per floor and per spot type.

                EDGE CASES TO CONSIDER:
                - What happens when the lot is completely full?
                - How is a lost ticket handled?
                - Should spot allocation behave differently during peak hours?

                EXPECTED DELIVERABLES:
                - Class/interface hierarchy (entities, interfaces, enums)
                - Responsibility assignment per class (SRP reasoning)
                - Design patterns used and why
                - How your design would extend to a new vehicle type or pricing rule
                """);
            parkingLot.setReferenceSolution("""
                Classes: ParkingLot (manages Floors), Floor (manages Spots), ParkingSpot (size, availability),
                Vehicle (abstract; Motorcycle/Car/Truck subclasses), Ticket (entry time, spot, vehicle),
                PaymentStrategy (interface) with CashPayment/CardPayment, PricingStrategy (interface) for
                per-vehicle-type rates. SpotAllocator service picks nearest matching spot.
                """);
            problemRepository.save(parkingLot);

            Problem vendingMachine = new Problem();
            vendingMachine.setTitle("Vending Machine System");
            vendingMachine.setDifficulty("Easy");
            vendingMachine.setDescription("Design a standalone vending machine that dispenses snacks and drinks upon payment.");
            vendingMachine.setRequirements("""
                CORE REQUIREMENTS:
                1. Support multiple products, each with a price and stock count.
                2. Accept multiple payment methods: cash and card.
                3. Dispense the selected product only after sufficient payment is received.
                4. Return change if the customer overpays.
                5. Handle out-of-stock and insufficient-payment scenarios gracefully.
                6. Track and update inventory after every successful dispense.

                EDGE CASES TO CONSIDER:
                - What if a product runs out of stock mid-transaction?
                - What if exact change isn't available in the machine?
                - What if a payment is initiated but never completed (timeout/cancel)?

                EXPECTED DELIVERABLES:
                - Class/interface hierarchy
                - How you modeled the machine's internal states (e.g. idle, has-money, dispensing)
                - Design patterns used and why
                - How this would extend to support a new payment method
                """);
            vendingMachine.setReferenceSolution("""
                Classes: VendingMachine (holds Inventory, current State), Product (name, price),
                Inventory (tracks stock per product), PaymentStrategy (interface) with CashPayment/CardPayment,
                VendingMachineState (interface) with IdleState, HasMoneyState, DispensingState, OutOfStockState
                (State pattern drives machine behavior transition).
                """);
            problemRepository.save(vendingMachine);

            Problem elevator = new Problem();
            elevator.setTitle("Elevator Control System");
            elevator.setDifficulty("Hard");
            elevator.setDescription("Design a multi-elevator control system for a high-rise building that efficiently dispatches elevators to serve requests.");
            elevator.setRequirements("""
                CORE REQUIREMENTS:
                1. Support multiple elevators serving the same set of floors.
                2. Handle external requests (a user pressing up/down on a floor) and internal requests (a user selecting a floor inside the elevator).
                3. Dispatch the most suitable elevator for a new request (e.g. nearest, already moving in the right direction).
                4. Each elevator should track its current floor, direction, and pending stops.
                5. Support opening/closing doors and basic capacity limits.
                6. Handle simultaneous requests from multiple floors gracefully.

                EDGE CASES TO CONSIDER:
                - What happens if all elevators are busy when a new request comes in?
                - How do you avoid an elevator changing direction too frequently (starvation of requests)?
                - What if an elevator breaks down mid-route?

                EXPECTED DELIVERABLES:
                - Class/interface hierarchy
                - Your dispatch/scheduling algorithm at a conceptual level (doesn't need to be optimal, needs to be reasoned)
                - Design patterns used and why
                - How this would extend to prioritize VIP or emergency requests
                """);
            elevator.setReferenceSolution("""
                Classes: ElevatorController (dispatches requests to elevators), Elevator (current floor, direction,
                state, list of stops), Request (source floor, destination floor, direction), 
                ElevatorState (interface) with MovingUp/MovingDown/Idle/DoorOpen states (State pattern),
                DispatchStrategy (interface) e.g. NearestElevatorStrategy (Strategy pattern) to allow swapping
                scheduling algorithms later.
                """);
            problemRepository.save(elevator);

            Problem library = new Problem();
            library.setTitle("Library Management System");
            library.setDifficulty("Medium");
            library.setDescription("Design a library system that manages books, members, and the borrowing/returning process.");
            library.setRequirements("""
                CORE REQUIREMENTS:
                1. Support multiple copies of the same book title, tracked individually.
                2. Members can search for books, borrow available copies, and return them.
                3. Enforce a borrowing limit per member and a due date per loan.
                4. Calculate late fees if a book is returned after its due date.
                5. Support reserving a book that is currently unavailable, and notify when it's returned.
                6. Track borrowing history per member.

                EDGE CASES TO CONSIDER:
                - What happens if a member tries to borrow beyond their limit?
                - What if a reserved book becomes available but the member doesn't collect it in time?
                - How do you handle a lost or damaged book?

                EXPECTED DELIVERABLES:
                - Class/interface hierarchy (Book vs BookCopy distinction should be considered)
                - Responsibility assignment per class
                - Design patterns used and why
                - How this would extend to support e-books or multiple library branches
                """);
            library.setReferenceSolution("""
                Classes: Book (title, author, metadata), BookCopy (individual physical copy, status),
                Member (borrowing limit, history), Loan (book copy, member, due date), 
                ReservationQueue (per book, FIFO), FeeCalculator (interface) for late fee strategies
                (Strategy pattern), LibraryCatalog (search/index of books).
                """);
            problemRepository.save(library);

            Problem rideSharing = new Problem();
            rideSharing.setTitle("Ride Sharing System");
            rideSharing.setDifficulty("Hard");
            rideSharing.setDescription("Design a simplified ride-sharing system (like Uber/Ola) that matches riders with nearby drivers and manages a trip's lifecycle.");
            rideSharing.setRequirements("""
                CORE REQUIREMENTS:
                1. Riders can request a ride specifying pickup and drop locations.
                2. Match the request to a nearby available driver.
                3. Support different ride types (e.g. economy, premium) with different pricing.
                4. Track a trip's lifecycle: requested, accepted, ongoing, completed, cancelled.
                5. Calculate fare based on distance/time and ride type.
                6. Allow both rider and driver to rate each other after trip completion.

                EDGE CASES TO CONSIDER:
                - What happens if no drivers are available nearby?
                - How do you handle a rider or driver cancelling mid-match or mid-trip?
                - What if the driver's location updates are delayed or missing?

                EXPECTED DELIVERABLES:
                - Class/interface hierarchy
                - How you modeled the trip lifecycle (state machine reasoning encouraged)
                - Design patterns used and why
                - How this would extend to support ride-pooling (multiple riders sharing a trip)
                """);
            rideSharing.setReferenceSolution("""
                Classes: Rider, Driver, Trip (state machine: Requested/Accepted/Ongoing/Completed/Cancelled),
                MatchingService (finds nearest available driver — Strategy pattern for matching algorithm),
                FareCalculator (interface) with per-ride-type pricing strategies, TripState (interface) 
                driving lifecycle transitions (State pattern), Rating (trip, rater, ratee, score).
                """);
            problemRepository.save(rideSharing);

            System.out.println("Seeded 5 problems.");
        }
    }
}