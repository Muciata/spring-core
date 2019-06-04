package ua.epam.spring.hometask.domain.statistics;

public class EventStatistics {
    private int callsByName;
    private int callsByPriceCheck;
    private int callsByTicketsBooked;
    private String name;

    public EventStatistics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCallsByName() {
        return callsByName;
    }

    public void setCallsByName(int callsByName) {
        this.callsByName = callsByName;
    }

    public int getCallsByPriceCheck() {
        return callsByPriceCheck;
    }

    public void setCallsByPriceCheck(int callsByPriceCheck) {
        this.callsByPriceCheck = callsByPriceCheck;
    }

    public int getCallsByTicketsBooked() {
        return callsByTicketsBooked;
    }

    public void setCallsByTicketsBooked(int callsByTicketsBooked) {
        this.callsByTicketsBooked = callsByTicketsBooked;
    }
}
