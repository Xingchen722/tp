package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.model.application.ApplicationEvent;
import seedu.address.model.application.OnlineAssessment;

/**
 * Linux-CI-stable JavaFX unit tests:
 * - Start JavaFX once
 * - Keep it alive during suite (implicitExit=false)
 * - Close any created Stages
 * - Shut down JavaFX (Platform.exit) once at the end
 */
public class EventDetailsWindowTest {

    private static final AtomicBoolean JFX_STARTED = new AtomicBoolean(false);

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm", Locale.ENGLISH);

    private Stage stageUnderTest;
    private EventDetailsWindow windowUnderTest;

    @BeforeAll
    public static void initJavaFxRuntimeOnce() throws Exception {
        FxTestSupport.ensureJavaFxStarted();
    }

    @AfterAll
    public static void shutdownJavaFxRuntimeOnce() throws Exception {
        // Hide any showing windows (best-effort).
        FxTestSupport.runOnFxThreadAndWait(() -> {
            for (Window w : Window.getWindows()) {
                w.hide();
            }
        });

        // Terminate the JavaFX application thread so the Gradle test JVM can exit.
        Platform.exit();
    }

    @AfterEach
    public void closeStageAfterEach() throws Exception {
        if (stageUnderTest == null) {
            return;
        }
        Stage stage = stageUnderTest;
        stageUnderTest = null;
        windowUnderTest = null;

        FxTestSupport.runOnFxThreadAndWait(() -> {
            stage.hide();
            stage.close();
        });
    }

    @Test
    public void setEventDetails_withOnlineAssessmentNoNotes_populatesLabels() throws Exception {
        createWindowOnFxThread();

        LocalDateTime dt = LocalDateTime.of(2026, 8, 10, 10, 0);
        OnlineAssessment oa = new OnlineAssessment("Office", dt, "Codility", "https://c.com");

        FxTestSupport.runOnFxThreadAndWait(() -> windowUnderTest.setEventDetails(oa));

        assertEquals("Online Assessment Details", getLabelTextOnFx(windowUnderTest, "titleLabel"));
        assertEquals("Office", getLabelTextOnFx(windowUnderTest, "locationLabel"));
        assertEquals(dt.format(DISPLAY_FORMATTER), getLabelTextOnFx(windowUnderTest, "dateTimeLabel"));
        assertEquals("Codility", getLabelTextOnFx(windowUnderTest, "platformLabel"));
        assertEquals("https://c.com", getLabelTextOnFx(windowUnderTest, "linkLabel"));
        assertEquals(OnlineAssessment.EMPTY_NOTES_VALUE, getLabelTextOnFx(windowUnderTest, "notesLabel"));
    }

    @Test
    public void setEventDetails_withGenericApplicationEvent_populatesFallbackLabels() throws Exception {
        createWindowOnFxThread();

        LocalDateTime dt = LocalDateTime.of(2026, 9, 5, 9, 0);
        ApplicationEvent generic = new ApplicationEvent("Conference room", dt) { };

        FxTestSupport.runOnFxThreadAndWait(() -> windowUnderTest.setEventDetails(generic));

        assertEquals("Event Details", getLabelTextOnFx(windowUnderTest, "titleLabel"));
        assertEquals("Conference room", getLabelTextOnFx(windowUnderTest, "locationLabel"));
        assertEquals(dt.format(DISPLAY_FORMATTER), getLabelTextOnFx(windowUnderTest, "dateTimeLabel"));
        assertEquals("N/A", getLabelTextOnFx(windowUnderTest, "platformLabel"));
        assertEquals("N/A", getLabelTextOnFx(windowUnderTest, "linkLabel"));
        assertEquals("N/A", getLabelTextOnFx(windowUnderTest, "notesLabel"));
    }

    @Test
    public void handleClose_invokesHide_withoutShowingAStage() throws Exception {
        // This avoids Stage.show() entirely (more reliable on Linux CI).
        StageAndWindow created = FxTestSupport.callOnFxThreadAndWait(() -> {
            Stage stage = new Stage();
            SpyEventDetailsWindow window = new SpyEventDetailsWindow(stage);
            return new StageAndWindow(stage, window);
        });

        stageUnderTest = created.stage;
        windowUnderTest = created.window;

        FxTestSupport.runOnFxThreadAndWait(() -> {
            Method method = EventDetailsWindow.class.getDeclaredMethod("handleClose");
            method.setAccessible(true);
            method.invoke(windowUnderTest);
        });

        SpyEventDetailsWindow spy = (SpyEventDetailsWindow) windowUnderTest;
        assertTrue(spy.hideCalled, "Expected handleClose() to call hide()");
    }

    @Test
    public void constructor_withStage_usesProvidedStage() throws Exception {
        AtomicReference<Stage> rootRef = new AtomicReference<>();

        FxTestSupport.runOnFxThreadAndWait(() -> {
            Stage stage = new Stage();
            EventDetailsWindow window = new EventDetailsWindow(stage);
            rootRef.set(window.getRoot());

            // Ensure we close this stage within the same FX task to avoid leaks.
            try {
                stage.hide();
            } finally {
                stage.close();
            }
        });

        assertNotNull(rootRef.get());
    }

    private void createWindowOnFxThread() throws Exception {
        StageAndWindow created = FxTestSupport.callOnFxThreadAndWait(() -> {
            Stage stage = new Stage();
            EventDetailsWindow window = new EventDetailsWindow(stage);
            return new StageAndWindow(stage, window);
        });
        this.stageUnderTest = created.stage;
        this.windowUnderTest = created.window;
    }

    private static String getLabelTextOnFx(EventDetailsWindow window, String fieldName) throws Exception {
        return FxTestSupport.callOnFxThreadAndWait(() -> {
            Field field = EventDetailsWindow.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return ((Label) field.get(window)).getText();
        });
    }

    private record StageAndWindow(Stage stage, EventDetailsWindow window) { }

    private static final class SpyEventDetailsWindow extends EventDetailsWindow {
        private volatile boolean hideCalled = false;

        private SpyEventDetailsWindow(Stage root) {
            super(root);
        }

        @Override
        public void hide() {
            hideCalled = true;
            super.hide();
        }
    }

    /**
     * Minimal JavaFX test harness that requires no Gradle/YAML changes.
     */
    private static final class FxTestSupport {

        private static void ensureJavaFxStarted() throws Exception {
            if (JFX_STARTED.get()) {
                return;
            }

            // Optional best-effort: if Linux looks headless, try setting properties early.
            // This only helps if a headless Glass platform is available on the classpath.
            configureBestEffortHeadlessProperties();

            CountDownLatch startupLatch = new CountDownLatch(1);
            try {
                Platform.startup(startupLatch::countDown);
            } catch (IllegalStateException alreadyStarted) {
                startupLatch.countDown();
            }

            // If this times out consistently on ubuntu-latest, you likely need Xvfb/Monocle in CI.
            if (!startupLatch.await(15, TimeUnit.SECONDS)) {
                throw new AssertionError(
                        "Timed out starting JavaFX Platform. "
                               + "On headless Linux, JavaFX may require a virtual display (Xvfb) or Monocle.");
            }

            // Keep the runtime alive even if no windows are showing during the suite.
            runOnFxThreadAndWait(() -> Platform.setImplicitExit(false));

            JFX_STARTED.set(true);
        }

        private static void configureBestEffortHeadlessProperties() {
            String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
            boolean isLinux = os.contains("linux");
            boolean hasDisplay = System.getenv("DISPLAY") != null || System.getenv("WAYLAND_DISPLAY") != null;

            if (isLinux && !hasDisplay) {
                // These are common knobs used for headless JavaFX/Monocle setups.
                // They will be ignored or fail if the needed platform is not present.
                System.setProperty("glass.platform", "Monocle");
                System.setProperty("monocle.platform", "Headless");
                System.setProperty("prism.order", "sw");
                System.setProperty("java.awt.headless", "true");
            }
        }

        @FunctionalInterface
        private interface FxRunnable {
            void run() throws Exception;
        }

        private static void runOnFxThreadAndWait(FxRunnable task) throws Exception {
            if (Platform.isFxApplicationThread()) {
                task.run();
                return;
            }

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Throwable> error = new AtomicReference<>();

            Platform.runLater(() -> {
                try {
                    task.run();
                } catch (Throwable t) {
                    error.set(t);
                } finally {
                    latch.countDown();
                }
            });

            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new AssertionError("Timed out waiting for FX task to complete");
            }
            if (error.get() != null) {
                Throwable t = error.get();
                if (t instanceof Exception e) {
                    throw e;
                }
                throw new RuntimeException(t);
            }
        }

        private static <T> T callOnFxThreadAndWait(Callable<T> task) throws Exception {
            AtomicReference<T> ref = new AtomicReference<>();
            runOnFxThreadAndWait(() -> ref.set(task.call()));
            return ref.get();
        }
    }
}
