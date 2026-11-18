# Database Design — Logical Model

All tables use UUID text primary keys unless a compact local surrogate key is justified. Timestamps are epoch milliseconds. Money is integer piastres (EGP × 100).

## Core tables
- Customer(id, code UNIQUE, fullName, primaryPhone, whatsappPhone, secondaryPhone, governorate, city, district, address, latitude, longitude, notes, createdAt, updatedAt, archivedAt)
- Device(id, customerId FK, category, brand, model, serialNumber, purchaseDate, warrantyUntil, installedAt, notes, createdAt, updatedAt)
- ServiceRequest(id, ticketNumber UNIQUE, customerId FK, deviceId FK nullable, channel, status, priority, faultDescription, diagnosticSummary, technicianNotes, estimatedCostMinor, approvedQuoteMinor, finalCostMinor, laborCostMinor, receivedAt, expectedAt, deliveredAt, createdAt, updatedAt)
- RequestStatusHistory(id, requestId FK, previousStatus, newStatus, reason, actorId nullable, occurredAt)
- Diagnostics(id, requestId FK, reportedSymptoms, inspectionFindings, suspectedFaults, confirmedFaults, testsPerformed, repairActions, finalResult, createdAt)
- Appointment(id, requestId FK nullable, customerId FK, scheduledAt, visitAddress, visitFeeMinor, result, reminderState)
- Quotation(id, requestId FK, quoteNumber UNIQUE, status, subtotalMinor, discountMinor, taxMinor, totalMinor, customerDecisionAt, createdAt)
- QuotationLine(id, quotationId FK, itemType, description, quantityScaled, unitPriceMinor, unitCostMinor)
- Invoice(id, requestId FK, customerId FK, invoiceNumber UNIQUE, status, issuedAt, subtotalMinor, discountMinor, taxMinor, totalMinor, paidMinor, dueMinor)
- InvoiceLine(id, invoiceId FK, itemType, descriptionSnapshot, quantityScaled, unitPriceMinor, unitCostMinor)
- Payment(id, invoiceId FK, amountMinor, method, direction, reference, occurredAt, notes)
- Expense(id, category, description, amountMinor, paymentMethod, occurredAt, attachmentId nullable, notes)
- Part(id, code UNIQUE, name, category, compatibleCategories, purchasePriceMinor, sellingPriceMinor, quantity, minimumQuantity, supplier, storageLocation, archivedAt)
- StockMovement(id, partId FK, movementType, quantityDelta, unitCostMinor, referenceType, referenceId, occurredAt, reason)
- RequestPart(id, requestId FK, partId FK, quantity, unitCostSnapshotMinor, unitSellingPriceSnapshotMinor)
- Warranty(id, requestId FK, customerId FK, startsAt, endsAt, terms, status)
- FileAttachment(id, ownerType, ownerId, relativePath, mimeType, sizeBytes, sha256, createdAt)
- Note(id, ownerType, ownerId, body, createdAt, updatedAt)
- AuditLog(id, entityType, entityId, action, beforeSummary, afterSummary, occurredAt)
- AppSettings(key, value, updatedAt)
- AiThread(id, title, provider, modelId, createdAt, updatedAt)
- AiChatMessage(id, threadId FK, role, content, deliveryState, createdAt)

## Constraints and indexes
- Foreign keys enabled on every database connection.
- Customer phone is indexed but not unique; duplicate warning is application logic.
- Device serial number is indexed, not globally unique by default.
- Ticket/invoice/quote numbers are unique.
- Request status and createdAt are indexed.
- Stock quantity is a projection reconciled against movements; every adjustment must write a movement in the same transaction.
- No cascade delete for customers, devices, invoices, payments, or service history. Use archive/soft delete.
- Add composite indexes for (status, createdAt), (customerId, createdAt), and (partId, occurredAt).

## Money and quantities
Money uses Long in piastres. Quantities use a documented scaled integer (e.g. 1000 = 1.000 unit) if fractional stock units are required. Never use floating point for financial arithmetic.

## Migration policy
- Export Room schemas and commit them.
- Every schema change increments database version and adds an explicit migration.
- No destructive migration in production.
- Migration tests cover representative old database files and validate row counts, foreign keys, and integrity_check.
