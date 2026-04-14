package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_APPLICATIONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalApplications.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.application.NoteContainsKeywordsPredicate;
import seedu.address.testutil.ApplicationBuilder;

public class FindNoteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        NoteContainsKeywordsPredicate firstPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));
        NoteContainsKeywordsPredicate secondPredicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("recruiter"));

        FindNoteCommand findFirstCommand = new FindNoteCommand(firstPredicate);
        FindNoteCommand findSecondCommand = new FindNoteCommand(secondPredicate);

        assertEquals(findFirstCommand, findFirstCommand);
        assertEquals(findFirstCommand, new FindNoteCommand(firstPredicate));
        assertNotEquals(findFirstCommand, null);
        assertNotEquals(findFirstCommand, findSecondCommand);
        assertNotEquals(findFirstCommand, new ClearCommand());
    }

    @Test
    public void toStringMethod() {
        NoteContainsKeywordsPredicate predicate =
                new NoteContainsKeywordsPredicate(Collections.singletonList("follow"));
        FindNoteCommand command = new FindNoteCommand(predicate);
        String str = command.toString();
        assertTrue(str.startsWith(FindNoteCommand.class.getCanonicalName() + "{"));
        assertTrue(str.contains("predicate="));
        assertTrue(str.endsWith("}"));
    }

    @Test
    public void execute_keywordMatchesNote_listsMatchingApplications() {
        NoteContainsKeywordsPredicate predicate = preparePredicate("mentorship");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 1);

        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_zeroKeywords_noApplicationFound() {
        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW, 0);
        NoteContainsKeywordsPredicate predicate = preparePredicate("nomatch");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_multipleKeywords_multipleApplicationsFound() {
        model.addApplication(new ApplicationBuilder().withRole("Intern A").withNote("Follow up next Monday").build());
        model.addApplication(new ApplicationBuilder().withRole("Intern B").withNote("Met recruiter at fair").build());

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Modified to test PARTIAL match: "foll" matches "Follow", "recruit" matches "recruiter"
        NoteContainsKeywordsPredicate predicate = preparePredicate("foll recruit");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredApplicationList().size());

        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    @Test
    public void execute_one_extraKeyword() {
        model.addApplication(new ApplicationBuilder().withRole("Intern C").withNote("Follow up with HR").build());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        NoteContainsKeywordsPredicate predicate = preparePredicate("follow zzznotfound");
        FindNoteCommand command = new FindNoteCommand(predicate);
        expectedModel.updateFilteredApplicationList(predicate);

        String expectedMessage = String.format(MESSAGE_APPLICATIONS_LISTED_OVERVIEW,
                expectedModel.getFilteredApplicationList().size());
        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
        assertEquals(expectedModel.getFilteredApplicationList(), model.getFilteredApplicationList());
    }

    /**
     * Parses {@code userInput} into a {@code NoteContainsKeywordsPredicate}.
     */
    private NoteContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NoteContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
