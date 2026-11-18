# Architecture Decision Record — Al Mohtaref 1.0

## 1. Product constraints
- One owner, one primary Android device in MVP.
- Offline-first; Room/SQLite is the source of truth.
- Android API 26 minimum, target/compile API 35 baseline.
- Arabic-first RTL; Egyptian EGP context.
- AI is optional and must never block core workflows.

## 2. Proposed layers
- Presentation: Compose screens, navigation, ViewModels, immutable UI state.
- Domain: business entities, repository contracts, use cases, validation and state machines.
- Data: Room DAOs, repository implementations, DataStore, attachment storage, provider adapters.
- Core: common types, dispatchers, security, backup, documents, AI abstractions.

## 3. Dependency direction
Feature UI -> Domain contracts <- Data implementations.
Presentation must not access DAOs directly. Domain must not depend on Android framework or provider SDKs.

## 4. Persistence
Room/SQLite is the sole source of truth. UUIDs are used for stable identity and future sync. All timestamps are stored as UTC epoch milliseconds; UI formats them in the device/business timezone. Money is stored as integer minor units (piastres), never Float/Double.

## 5. Transactions
The following operations must be atomic:
- Invoice issuance + invoice lines + stock consumption (when applicable).
- Payment registration + balance recalculation.
- Stock movement + quantity projection.
- Backup restore swap.
A failed transaction must leave the prior state intact.

## 6. Financial policy
- Invoice line snapshots retain description, quantity, unit price, and cost basis at issue time.
- Inventory purchases increase inventory asset/value and cash outflow; they are not expensed again when parts are consumed.
- Consumed parts transfer cost to the repair/service cost ledger.
- Operating profit is recognized revenue less consumed parts cost and operating expenses under the selected accrual policy.
- Cash flow is reported separately from operating profit.
- Tax is configurable and excluded unless explicitly enabled.
- Issued invoices are immutable; corrections use credit/refund/adjustment records and audit trail.

## 7. Service state machine
NEW -> INSPECTED -> AWAITING_APPROVAL -> IN_REPAIR
AWAITING_APPROVAL -> AWAITING_PART -> IN_REPAIR
IN_REPAIR -> AWAITING_PART | READY
READY -> DELIVERED
Exceptional states: CANCELLED, REJECTED, UNREPAIRABLE, CUSTOMER_UNREACHABLE, CUSTOMER_NO_SHOW.
Transitions require an explicit transition policy, timestamp, reason where required, and append-only history. Reopening delivered work creates a linked follow-up/rework request rather than erasing delivery history.

## 8. AI boundary
AiProvider exposes normalized request/response/stream types. Provider-specific schemas remain in adapters. Customer context is excluded by default; explicit per-send consent and optional redaction are required. AI can draft suggestions but cannot mutate operational or financial records.

## 9. Security
- PIN hash via a memory-hard password hashing algorithm with random salt (Argon2id preferred if a maintained Android implementation is validated; otherwise scrypt/PBKDF2-HMAC-SHA256 with calibrated parameters).
- Keystore protects local credential encryption keys.
- Keystore-bound secrets are not assumed portable to another device.
- Backup encryption uses a user-held passphrase-derived key and authenticated encryption; passphrase recovery cannot be promised.
- No secrets or personal data in logs.

## 10. Attachments and backup
Attachment metadata lives in Room; bytes live in app-private storage. Backup is a versioned archive with manifest, hashes, encrypted payload, and database schema version. Validate archive, decrypt, hash-check, run SQLite integrity checks, stage restore, then atomically swap. Keep rollback copy until successful reopen.

## 11. Performance
Use Paging for large customer/request lists, indexed search, bounded Flow queries, and avoid loading all attachment blobs into memory. Dashboard aggregates must be SQL-backed and indexed.

## 12. Future sync
Stable UUIDs, updatedAt, recordVersion, tombstones and audit events prepare future sync. No cloud sync or fake sync is part of MVP.
