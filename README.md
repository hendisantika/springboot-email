# springboot-email

Send transactional email via the [Brevo](https://www.brevo.com/) HTTP API, built with Spring Boot.

## Requirements

- Java 25
- Maven (or use the bundled `./mvnw` wrapper)
- A [Brevo](https://app.brevo.com/settings/keys/api) account, API key, and verified sender email

## Setup

1. Clone this repository: `git clone https://github.com/hendisantika/springboot-email.git`.
2. Go to your folder: `cd springboot-email`.
3. Configure your Brevo credentials in `src/main/resources/application.properties`:
   ```properties
   brevo.api.key=your-brevo-api-key
   brevo.api.url=https://api.brevo.com/v3/smtp/email
   brevo.sender.email=your-verified-sender@example.com
   brevo.sender.name=Your Name
   ```
4. Run the application: `./mvnw spring-boot:run`.

The app starts on `http://localhost:8080` by default (configurable via `server.port`).

## API Endpoints

### Send a simple email
```
GET /email/simple-email/{user-email}
```
```bash
curl http://localhost:8080/email/simple-email/hendisantika@yahoo.co.id
```

### Send an order confirmation email with a PDF attachment
```
GET /email/simple-order-email/{user-email}
```
```bash
curl http://localhost:8080/email/simple-order-email/hendisantika@yahoo.co.id
```

### Send an email with file attachments
```
POST /email/send-with-attachments
Content-Type: multipart/form-data
```
Form fields: `to` (required), `subject` (required), `message` (required), `attachments` (optional, repeatable file field).

```bash
curl -X POST http://localhost:8080/email/send-with-attachments \
  -F "to=hendisantika@yahoo.co.id" \
  -F "subject=Test Email" \
  -F "message=This is a test" \
  -F "attachments=@/path/to/file.pdf"
```

## Postman Collection

A ready-to-import Postman collection (`SpringBoot-Email-API.postman_collection.json`) is included, covering every endpoint above with example requests. See [POSTMAN_GUIDE.md](POSTMAN_GUIDE.md) for import instructions, variable setup, and troubleshooting tips.

## Running Tests

```bash
./mvnw test
```
