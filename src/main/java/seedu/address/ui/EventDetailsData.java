package seedu.address.ui;

/**
 * Immutable data holder for event details to be displayed in the UI.
 */
public class EventDetailsData {

    public final String title;
    public final String location;
    public final String dateTime;
    public final String platform;
    public final String link;
    public final String notes;

    /**
     * Constructs EventDetailsData for EventDetailsWindowTest
     * @param title
     * @param location
     * @param dateTime
     * @param platform
     * @param link
     * @param notes
     */
    public EventDetailsData(String title, String location, String dateTime,
                            String platform, String link, String notes) {
        this.title = title;
        this.location = location;
        this.dateTime = dateTime;
        this.platform = platform;
        this.link = link;
        this.notes = notes;
    }
}
