# Implementation Roadmap

## Gate 0 — Build foundation
- Verify Gradle/AGP/Kotlin/Compose compatibility with Android Studio.
- Generate and commit Gradle wrapper.
- Compile and install blank app.
- Add CI debug build.

## Gate 1 — Data foundation
- Room schema, UUID converters, migrations, indexes.
- Customer/device/request repositories.
- Migration and DAO tests.

## Gate 2 — Core service workflow
- Customer CRUD and duplicate phone warning.
- Device registration and history.
- Work order state machine and append-only status history.
- Intake and home visit records.

## Gate 3 — Finance and inventory
- Quotations, immutable issued invoices, payments/refunds.
- Stock movement ledger and transactional consumption.
- Expense ledger and accounting policy tests.

## Gate 4 — Documents and resilience
- Arabic PDF, attachments, backup archive, validation and atomic restore.

## Gate 5 — AI and reminders
- Consent, redaction, provider adapters, streaming, local chat.
- Notifications and WorkManager.

## Gate 6 — Acceptance
- End-to-end offline tests.
- Performance tests with target data volumes.
- Device install, backup/restore round-trip, release signing.
