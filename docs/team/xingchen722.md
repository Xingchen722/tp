---
layout: page
title: Guo Xingchen's Project Portfolio Page
---

### Project: Hired!

A **desktop app for managing internship applications**, optimised for a **CLI** workflow while keeping a **JavaFX GUI**. Users manage roles, companies, deadlines, status, notes, resumes, and events in one place. The product evolved from the AddressBook Level 3 teaching codebase.

Given below are my contributions to the project.

* **New Feature: Company name & company location on applications**
  * What it does: Each application stores a **company name** and an optional **company location** (`l/` prefix), available on **`add`** and **`edit`** (and reflected in the UI cards).
  * Justification: Internship tracking needs employer identity and office location, not just a generic “name” field from AB3.
  * Highlights: Propagated fields through **model**, **parser**, **commands**, **storage/JSON**, and **UI** so the feature stayed consistent end-to-end.

* **New Feature: `undo` and `redo`**
  * What it does: Users can step backward and forward through recent **data-modifying** commands (versioned state list with a cap on history depth).
  * Justification: Reduces cost of mistakes and supports exploratory editing of applications.
  * Highlights: Affects **every command that commits** to history; required careful coordination so new features (e.g. `reminder`, `sort`) integrate with the same undo/redo contract.

* **New Feature: `reminder`**
  * What it does: Re-sorts the list by **deadline** (nearest first) and turns on **deadline-based highlighting** on each card (e.g. urgent vs overdue). Works together with **`undo`/`redo`** for both list order and highlight preference where applicable.
  * Justification: Students need one command to both reorder by urgency and see visual cues without reading every date.
  * Highlights: Logic in **`ReminderCommand`**, rules in **`Deadline`**, presentation in **`ApplicationCard`**, plus tests and UG/DG alignment.

* **Team-based task: Project-wide “environment” refactor** *(with **Gao Huiying**)*
  * What we did: Reworked the codebase from the **AddressBook / `Person`** teaching shape toward the **Hired! / `Application`** product—naming, packages, build/CI expectations, and cross-layer consistency so the whole team could develop on a shared, stable baseline.
  * Justification: Without this migration, feature work would keep fighting old AB3 assumptions (fields, commands, storage).
  * Highlights: High coordination cost; many files and tests had to move or be updated together to keep the app buildable at each step.

* **Enhancement: Command box horizontal scrolling**
  * What it does: Long commands or file paths can be scrolled horizontally in the command input instead of being clipped.
  * Highlights: `CommandBox` (Java + FXML + CSS) and `CommandBoxTest`.

* **Documentation — User Guide** 
  * **Product framing & navigation**: Opening description of Hired!, **table of contents** for Features / FAQ / Known issues / Command summary / Future Improvement, **Quick start** (including UI screenshot `Ui_current.png`), and guidance on how to read the guide.
  * **Concepts & defaults**: **Default behavior at a glance** (sample data, `add` defaults, reminder off until first `reminder`, displayed-index rules).
  * **Prefixes & duplicate identity**: Note block clarifying **`r/` / `p/` / `c/` / `l/`**, phone format flexibility, and **duplicate detection** (role + company name + location, empty-`l/` rules, case/whitespace normalization) with examples.
  * **Command sections** (usage, formats, bullets, examples as applicable): **`list`**, **`edit`** (incl. `c/`, `l/`, clearing deadline with `d/-`), **`find`** / **`findnote`** (keyword requirements), **`status`**, **`deadline`** (formats, validation, distinction from assessment/interview `et/`), **`delete`**, **`reminder`** (sort order, colours, persistence, interaction with `deadline`/`edit`, datetime vs date-only, minute-level overdue behaviour and UI refresh), **`sort`**, **`undo`**, **`redo`**.
  * **Reminder + history (deeper UG)**: **Reminder-specific `undo` / `redo` behaviour** (title scoped so it does not clash with global command sections); **when `reminder` commits** to undo history vs UI-only colour updates; documentation that **`redo` fails** after another **data-modifying** command following `undo` (expected behaviour, with concrete `edit` example).
  * **Supporting material**: **FAQ** (e.g. `find`/`findnote` format, `delete` vs field edit, reminder colours on startup), **Known issues** (multi-monitor, Help window, index context, non-timer reminder refresh, date-only vs datetime deadlines), **Command summary** table rows for the commands above, **`Archiving` (v6.0)** placeholder.
  * **Future Improvement**: Items **1–4** (filters, export/import, auto reminder refresh, clearer errors) plus **item 5** (table of **richer `reminder` success messages** by scenario).

* **Documentation — Developer Guide** 

  * **Model**: **`ModelClassDiagram`** — high-level model overview (`docs/images/ModelClassDiagram.png`; source `docs/diagrams/ModelClassDiagram.puml`).
  * **Undo/redo**: End-to-end explanation of **`VersionedAddressBook`** (`addressBookStateList`, `currentStatePointer`, `commit` / `undo` / `redo`), interaction with **`Model#commitAddressBook()`** and failed commands that do not commit, **state diagrams** for the step-by-step walkthrough (`UndoRedoState0`–`UndoRedoState3` images; sources `docs/diagrams/UndoRedoState0.puml` … `UndoRedoState3.puml`), **clear** purging “future” states and why **redo** is invalidated after a new branch, plus notes on **`canUndo` / `canRedo`** checks.
  * **`reminder`**: Sequence diagram through **Logic** (`ReminderSequenceDiagram-Logic.png`; `docs/diagrams/ReminderSequenceDiagram-Logic.puml`) and numbered explanation (`LogicManager` → `AddressBookParser` → `ReminderCommand`, `ReminderHighlightState`, `UserPrefs`, sort by deadline, `commit`).
  * **`sort`**: Sequence diagram for **Logic** (`SortSequenceDiagram-Logic.png`; `docs/diagrams/SortSequenceDiagram-Logic.puml`) and step-by-step flow (`SortCommandParser`, comparator, `Model#updateSortedApplicationList`, `commit`).
  * **`add`**: Sequence diagram for **Logic** (`AddSequenceDiagram-Logic.png`; `docs/diagrams/AddSequenceDiagram-Logic.puml`) and flow through `AddCommandParser`, duplicate check (`Model#hasApplication`), `Model#addApplication`, `commit`.
  * **`delete`**: Cross-reference to core logic / **`DeleteSequenceDiagram`** (`docs/diagrams/DeleteSequenceDiagram.puml`, exported image under `docs/images/`) as in the Design walkthrough.
  * **`edit` / `deadline`**: Contributed or co-authored material where the DG focuses on extra design considerations (exact subsection titles may differ in the latest `DeveloperGuide.md`); glossary-style definitions (role, status, company, deadline, MSS, etc.) where present in the team’s DG.

* **Project management & integration**
  * Merged teammates’ PRs into `master` and helped fix **CI** / tests when integrations broke (e.g. after error-handling or UI changes).

* **Testing**
  * For work delivered through **pull requests**, added or updated **corresponding test classes** so **`./gradlew check`** passes and **Codecov** on CI stays within the team’s coverage expectations.
  * Examples of areas covered: **reminder**, **deadline** parsing, **ApplicationCard**, **CommandBox**, and regression fixes after merges.

* **Community**
  * **PR reviews**: Regularly **reviewed** teammates’ PRs and **approved** them when ready, to unblock merges.
  * **Team support**: Helped teammates who were **stuck** (e.g. failing tests, CI, or understanding model/logic/UI code).

* **Tools**
  * **PlantUML** (`.puml` under `docs/diagrams/`, exported **PNG** under `docs/images/`) for the diagrams listed above; team `style.puml` / shared conventions where applicable.

