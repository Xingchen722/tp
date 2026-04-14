package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPLICATION;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.Interview;
import seedu.address.model.application.OnlineAssessment;

public class AssessmentCommandTest {

    private static final LocalDateTime VALID_DATETIME = LocalDateTime.of(2026, 12, 31, 23, 59);

    private final ModelManager model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        OnlineAssessment assessment = new OnlineAssessment(
                "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
        AssessmentCommand command = new AssessmentCommand(INDEX_FIRST_APPLICATION, assessment);

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Online Assessment updated for:"));
    }

    @Test
    public void execute_secondIndex_success() throws Exception {
        OnlineAssessment assessment = new OnlineAssessment(
                "remote", VALID_DATETIME, "Codility", "www.codility.com");
        AssessmentCommand command = new AssessmentCommand(INDEX_SECOND_APPLICATION, assessment);

        CommandResult result = command.execute(model);

        assertTrue(result.getFeedbackToUser().contains("Online Assessment updated for:"));
    }

    @Test
    public void execute_outOfBoundsIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredApplicationList().size() + 1);
        OnlineAssessment assessment = new OnlineAssessment(
                "home", VALID_DATETIME, "Platform", "www.example.com");
        AssessmentCommand command = new AssessmentCommand(outOfBoundsIndex, assessment);

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        OnlineAssessment assessment = new OnlineAssessment(
                "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
        AssessmentCommand command = new AssessmentCommand(INDEX_FIRST_APPLICATION, assessment);
        assertEquals(command, command);
    }

    @Test
    public void equals_sameFields_returnsTrue() {
        OnlineAssessment assessment = new OnlineAssessment(
                "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
        AssessmentCommand a = new AssessmentCommand(INDEX_FIRST_APPLICATION, assessment);
        AssessmentCommand b = new AssessmentCommand(INDEX_FIRST_APPLICATION, assessment);
        assertEquals(a, b);
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        OnlineAssessment assessment = new OnlineAssessment(
                "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
        AssessmentCommand a = new AssessmentCommand(INDEX_FIRST_APPLICATION, assessment);
        AssessmentCommand b = new AssessmentCommand(INDEX_SECOND_APPLICATION, assessment);
        assertFalse(a.equals(b));
    }

    @Test
    public void equals_differentEvent_returnsFalse() {
        OnlineAssessment a1 = new OnlineAssessment("home", VALID_DATETIME, "HackerRank", "www.hr.com");
        OnlineAssessment a2 = new OnlineAssessment("office", VALID_DATETIME, "LeetCode", "www.lc.com");
        AssessmentCommand cmdA = new AssessmentCommand(INDEX_FIRST_APPLICATION, a1);
        AssessmentCommand cmdB = new AssessmentCommand(INDEX_FIRST_APPLICATION, a2);
        assertFalse(cmdA.equals(cmdB));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        OnlineAssessment assessment = new OnlineAssessment(
                "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
        AssessmentCommand command = new AssessmentCommand(INDEX_FIRST_APPLICATION, assessment);
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentCommandType_returnsFalse() {
        OnlineAssessment assessment = new OnlineAssessment(
                "home", VALID_DATETIME, "HackerRank", "www.hackerrank.com");
        AssessmentCommand assessmentCommand = new AssessmentCommand(INDEX_FIRST_APPLICATION, assessment);
        InterviewCommand interviewCommand = new InterviewCommand(INDEX_FIRST_APPLICATION,
                new Interview("Office", VALID_DATETIME));
        assertFalse(assessmentCommand.equals(interviewCommand));
    }
}
