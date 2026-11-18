# Layla AI — Setup Policy

Provider adapters planned:
- Google Gemini
- OpenAI API
- Anthropic Claude

Before implementation, verify current official API documentation for endpoint, authentication header, model IDs, request schema, streaming format, rate limits and data retention terms. Model IDs must be configurable rather than assumed permanent.

API keys:
- Entered by the owner inside the app.
- Never embedded in source, BuildConfig, resources, logs, screenshots or crash reports.
- Stored encrypted with a Keystore-backed local key.
- Not included in portable backups by default.
- Client-side API keys cannot be guaranteed secret against a determined device attacker.

Privacy:
- Context sharing is opt-in per request.
- Offer redaction of names, phone numbers and addresses.
- AI output is advisory only; no direct database mutation.
