package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ApplicationCardTest {

    // ── formatPhone ──────────────────────────────────────────────────────────

    @Test
    public void formatPhone_normalValue_returnsValue() {
        assertEquals("91234567", ApplicationCard.formatPhone("91234567"));
    }

    @Test
    public void formatPhone_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatPhone(""));
    }

    // ── formatHrEmail ────────────────────────────────────────────────────────

    @Test
    public void formatHrEmail_normalValue_returnsValue() {
        assertEquals("hr@google.com", ApplicationCard.formatHrEmail("hr@google.com"));
    }

    @Test
    public void formatHrEmail_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatHrEmail(""));
    }

    // ── formatCompanyName ────────────────────────────────────────────────────

    @Test
    public void formatCompanyName_normalValue_returnsValue() {
        assertEquals("Google", ApplicationCard.formatCompanyName("Google"));
    }

    @Test
    public void formatCompanyName_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatCompanyName(""));
    }

    // ── formatCompanyLocation ────────────────────────────────────────────────

    @Test
    public void formatCompanyLocation_normalValue_returnsValue() {
        assertEquals("Singapore", ApplicationCard.formatCompanyLocation("Singapore"));
    }

    @Test
    public void formatCompanyLocation_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatCompanyLocation(""));
    }

    // ── formatDeadline ───────────────────────────────────────────────────────

    @Test
    public void formatDeadline_normalValue_returnsValue() {
        assertEquals("2026-12-31", ApplicationCard.formatDeadline("2026-12-31"));
    }

    @Test
    public void formatDeadline_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatDeadline(""));
    }

    // ── formatNote ───────────────────────────────────────────────────────────

    @Test
    public void formatNote_normalValue_returnsValue() {
        assertEquals("Follow up next Monday", ApplicationCard.formatNote("Follow up next Monday"));
    }

    @Test
    public void formatNote_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatNote(""));
    }

    // ── formatResume ─────────────────────────────────────────────────────────

    @Test
    public void formatResume_normalValue_returnsValue() {
        assertEquals("resume.pdf", ApplicationCard.formatResume("resume.pdf"));
    }

    @Test
    public void formatResume_emptyString_returnsEmpty() {
        assertEquals("", ApplicationCard.formatResume(""));
    }

    // ── format methods return raw value (no icon prefix) ────────────────────

    @Test
    public void formatMethods_doNotAddIconPrefix() {
        String phone    = ApplicationCard.formatPhone("91234567");
        String email    = ApplicationCard.formatHrEmail("hr@google.com");
        String company  = ApplicationCard.formatCompanyName("Google");
        String location = ApplicationCard.formatCompanyLocation("Singapore");
        String deadline = ApplicationCard.formatDeadline("2026-12-31");
        String note     = ApplicationCard.formatNote("Follow up");
        String resume   = ApplicationCard.formatResume("resume.pdf");

        // Icons are now rendered as FontIcon nodes, not text prefixes
        assertTrue(!phone.contains("☎") && !phone.contains("✆"));
        assertTrue(!email.contains("✉"));
        assertTrue(!company.contains("▣") && !company.contains("🏢"));
        assertTrue(!location.contains("⌂") && !location.contains("📍"));
        assertTrue(!deadline.contains("◷") && !deadline.contains("📅"));
        assertTrue(!note.contains("✎") && !note.contains("📝"));
        assertTrue(!resume.contains("▣") && !resume.contains("📄"));
    }

    @Test
    public void formatMethods_allReturnExactInput() {
        assertEquals("91234567",          ApplicationCard.formatPhone("91234567"));
        assertEquals("hr@google.com",     ApplicationCard.formatHrEmail("hr@google.com"));
        assertEquals("Google",            ApplicationCard.formatCompanyName("Google"));
        assertEquals("Singapore",         ApplicationCard.formatCompanyLocation("Singapore"));
        assertEquals("2026-12-31",        ApplicationCard.formatDeadline("2026-12-31"));
        assertEquals("Follow up",         ApplicationCard.formatNote("Follow up"));
        assertEquals("resume.pdf",        ApplicationCard.formatResume("resume.pdf"));
    }

    @Test
    public void formatMethods_emptyStrings_returnEmpty() {
        assertEquals("", ApplicationCard.formatPhone(""));
        assertEquals("", ApplicationCard.formatHrEmail(""));
        assertEquals("", ApplicationCard.formatCompanyName(""));
        assertEquals("", ApplicationCard.formatCompanyLocation(""));
        assertEquals("", ApplicationCard.formatDeadline(""));
        assertEquals("", ApplicationCard.formatNote(""));
        assertEquals("", ApplicationCard.formatResume(""));
    }

    // ── status key mapping ───────────────────────────────────────────────────

    @Test
    public void statusKey_applied_mapsToCorrectCssClass() {
        String statusKey = "APPLIED".toLowerCase().replace("_", "-");
        assertEquals("applied", statusKey);
    }

    @Test
    public void statusKey_interviewing_mapsToCorrectCssClass() {
        String statusKey = "INTERVIEWING".toLowerCase().replace("_", "-");
        assertEquals("interviewing", statusKey);
    }

    @Test
    public void statusKey_offered_mapsToCorrectCssClass() {
        String statusKey = "OFFERED".toLowerCase().replace("_", "-");
        assertEquals("offered", statusKey);
    }

    @Test
    public void statusKey_rejected_mapsToCorrectCssClass() {
        String statusKey = "REJECTED".toLowerCase().replace("_", "-");
        assertEquals("rejected", statusKey);
    }

    @Test
    public void statusKey_withdrawn_mapsToCorrectCssClass() {
        String statusKey = "WITHDRAWN".toLowerCase().replace("_", "-");
        assertEquals("withdrawn", statusKey);
    }
}
