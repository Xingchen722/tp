package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

/**
 * Formats an {@link ApplicationEvent} into display-ready data.
 *
 * This class contains no JavaFX code and is safe for unit testing.
 */
public class EventDetailsFormatter {

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

    /**
     * Converts an {@link ApplicationEvent} into {@link EventDetailsData}.
     *
     * @param event The event to format
     * @return formatted data ready for UI display
     */
    public static EventDetailsData format(ApplicationEvent event) {
        String location = event.getLocation();
        String dateTime = event.getLocalDate().format(DISPLAY_FORMATTER);

        if (event instanceof OnlineAssessment oa) {
            return new EventDetailsData(
                    "Online Assessment Details",
                    location,
                    dateTime,
                    oa.getPlatform(),
                    oa.getLink(),
                    oa.getNotes()
            );
        } else {
            // fallback for future event types
            return new EventDetailsData(
                    "Event Details",
                    location,
                    dateTime,
                    "N/A",
                    "N/A",
                    "N/A"
            );
        }
    }
}
