package seedu.address.ui;

import static seedu.address.logic.commands.ReminderCommand.REMINDER_TAG_NAME;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import seedu.address.model.application.Application;

public class ApplicationCard extends UiPart<Region> {

    private static final String FXML       = "ApplicationListCard.fxml";
    private static final Color  ICON_COLOR = Color.WHITE;
    private static final int    ICON_SIZE  = 14;

    public final Application application;

    @FXML private HBox     cardPane;
    @FXML private Label    role;
    @FXML private Label    id;
    @FXML private FlowPane tags;
    @FXML private Label    deadline;
    @FXML private VBox     detailsBox;

    public ApplicationCard(Application application, int displayedIndex) {
        super(FXML);
        this.application = application;

        // Index + Role
        id.setText(displayedIndex + ". ");
        role.setText(application.getRole().roleName);

        // Deadline
        if (application.getDeadline().isEmpty()) {
            deadline.setVisible(false);
            deadline.setManaged(false);
        } else {
            deadline.setText(application.getDeadline().value);
            deadline.setGraphic(calendarIcon());
        }

        // Status chip
        String statusText = application.getStatus().toString();
        String statusKey  = statusText.toLowerCase().replace("_", "-");
        Label statusChip  = new Label(toTitleCase(statusText));
        statusChip.getStyleClass().addAll("chip", "chip-status-" + statusKey);
        tags.getChildren().add(statusChip);

        // Custom tags (reminder etc.)
        application.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label chip = new Label(tag.tagName);
                    chip.getStyleClass().add("chip");
                    if (tag.tagName.equalsIgnoreCase(REMINDER_TAG_NAME)) {
                        chip.getStyleClass().add("chip-urgent");
                    }
                    tags.getChildren().add(chip);
                });

        // Detail rows
        detailsBox.getChildren().add(
                iconRow(FontAwesomeSolid.BUILDING, application.getCompany().companyName));

        if (!application.getCompany().companyLocation.isEmpty()) {
            detailsBox.getChildren().add(
                    iconRow(FontAwesomeSolid.MAP_MARKER_ALT,
                            application.getCompany().companyLocation));
        }

        detailsBox.getChildren().add(
                iconRow(FontAwesomeSolid.PHONE, application.getPhone().value));

        detailsBox.getChildren().add(
                iconRow(FontAwesomeSolid.ENVELOPE, application.getHrEmail().value));

        if (!application.getNote().value.isEmpty()) {
            detailsBox.getChildren().add(
                    iconRow(FontAwesomeSolid.STICKY_NOTE, application.getNote().value));
        }

        if (!application.getResume().isEmpty()) {
            detailsBox.getChildren().add(
                    iconRow(FontAwesomeSolid.FILE_ALT, application.getResume().value));
        }
    }

    private HBox iconRow(FontAwesomeSolid iconCode, String text) {
        FontIcon fi = new FontIcon(iconCode);
        fi.setIconSize(ICON_SIZE);
        fi.setIconColor(ICON_COLOR);

        Label lbl = new Label(text);
        lbl.getStyleClass().add("detail-label");

        HBox row = new HBox(8, fi, lbl);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private FontIcon calendarIcon() {
        FontIcon fi = new FontIcon(FontAwesomeSolid.CALENDAR_ALT);
        fi.setIconSize(ICON_SIZE);
        fi.setIconColor(Color.web("#e53935"));
        return fi;
    }

    private static String toTitleCase(String s) {
        if (s == null || s.isEmpty()) return s;
        String lower = s.toLowerCase().replace("_", " ");
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    static String formatPhone(String value) {
        return value;
    }

    static String formatHrEmail(String value) {
        return value;
    }

    static String formatCompanyName(String value) {
        return value;
    }

    static String formatCompanyLocation(String value) {
        return value;
    }

    static String formatDeadline(String value) {
        return value;
    }

    static String formatNote(String value) {
        return value;
    }

    static String formatResume(String value) {
        return value;
    }
}
