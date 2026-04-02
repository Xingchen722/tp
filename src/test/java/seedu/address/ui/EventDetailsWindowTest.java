package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

/**
 * Tests the logic used by EventDetailsWindow without invoking JavaFX.
 * <p>
 * IMPORTANT:
 * - This test avoids JavaFX entirely (CI-safe)
 * - It validates the behavior of EventDetailsFormatter, which backs the UI
 */
public class EventDetailsWindowTest {

    /**
     * Stub implementation of ApplicationEvent for non-OnlineAssessment cases.
     */
    private static class StubEvent extends ApplicationEvent {

        public StubEvent(String location, LocalDateTime dateTime) {
            super(location, dateTime);
        }
    }

    @Test
    public void setEventDetails_onlineAssessment_allFieldsCorrect() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 4, 1, 14, 30);

        OnlineAssessment oa = new OnlineAssessment(
                "Remote", // location
                dateTime,
                "Zoom", // platform
                "https://example.com", // link
                "Bring laptop" // notes
        );

        EventDetailsData data = EventDetailsFormatter.format(oa);

        assertEquals("Online Assessment Details", data.title);
        assertEquals("Remote", data.location);
        assertEquals("01 Apr 2025, 14:30", data.dateTime);
        assertEquals("Zoom", data.platform);
        assertEquals("https://example.com", data.link);
        assertEquals("Bring laptop", data.notes);
    }

    @Test
    public void setEventDetails_onlineAssessment_defaultNotesUsed() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 15, 10, 0);

        OnlineAssessment oa = new OnlineAssessment(
                "Online",
                dateTime,
                "HackerRank",
                "https://hackerrank.com/test" // uses constructor with default notes
        );

        EventDetailsData data = EventDetailsFormatter.format(oa);

        assertEquals("Online Assessment Details", data.title);
        assertEquals("Online", data.location);
        assertEquals("15 Jun 2025, 10:00", data.dateTime);
        assertEquals("HackerRank", data.platform);
        assertEquals("https://hackerrank.com/test", data.link);
        assertEquals(OnlineAssessment.EMPTY_NOTES_VALUE, data.notes);
    }

    @Test
    public void setEventDetails_nonOnlineAssessment_defaultsToNA() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 5, 10, 9, 0);

        ApplicationEvent event = new StubEvent("NUS", dateTime);

        EventDetailsData data = EventDetailsFormatter.format(event);

        assertEquals("Event Details", data.title);
        assertEquals("NUS", data.location);
        assertEquals("10 May 2025, 09:00", data.dateTime);
        assertEquals("N/A", data.platform);
        assertEquals("N/A", data.link);
        assertEquals("N/A", data.notes);
    }

    @Test
    public void setEventDetails_dateFormatting_correct() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 12, 25, 18, 45);

        ApplicationEvent event = new StubEvent("Office", dateTime);

        EventDetailsData data = EventDetailsFormatter.format(event);

        assertEquals("25 Dec 2025, 18:45", data.dateTime);
    }
}
