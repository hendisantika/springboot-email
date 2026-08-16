# Postman Collection Guide

This guide explains how to use the Postman collection for the Spring Boot Email API.

## Import Collection

1. Open Postman
2. Click **Import** button (top left)
3. Select the file: `SpringBoot-Email-API.postman_collection.json`
4. Click **Import**

## Collection Variables

The collection uses environment variables that you can customize:

| Variable | Default Value | Description |
|----------|---------------|-------------|
| `base_url` | `http://localhost:8080` | The base URL of your API server |
| `recipient_email` | `hendisantika@gmail.com` | Default recipient email address |

### How to Change Variables:

1. Click on the collection name "Spring Boot Email API"
2. Go to the **Variables** tab
3. Update the **Current Value** column
4. Click **Save**

## Available Endpoints

### 1. Send Simple Email
- **Method:** GET
- **Endpoint:** `/email/simple-email/{email}`
- **Description:** Sends a simple welcome email
- **Example:** `GET http://localhost:8080/email/simple-email/user@example.com`

### 2. Send Order Confirmation Email with Attachment
- **Method:** GET
- **Endpoint:** `/email/simple-order-email/{email}`
- **Description:** Sends an order confirmation with a PDF from classpath
- **Example:** `GET http://localhost:8080/email/simple-order-email/user@example.com`

### 3. Send Email with File Attachments
- **Method:** POST
- **Endpoint:** `/email/send-with-attachments`
- **Content-Type:** `multipart/form-data`
- **Parameters:**
  - `to` (text, required): Recipient email address
  - `subject` (text, required): Email subject
  - `message` (text, required): Email message body
  - `attachments` (file, optional): File attachment(s)

### 4. Send Email with Multiple Attachments
- **Method:** POST
- **Endpoint:** `/email/send-with-attachments`
- **Description:** Example showing multiple file attachments
- **Parameters:** Same as above, with multiple `attachments` fields

### 5. Send Email without Attachments
- **Method:** POST
- **Endpoint:** `/email/send-with-attachments`
- **Description:** Send email without any files attached
- **Parameters:** `to`, `subject`, `message` only

## How to Add File Attachments in Postman

1. Select the request "Send Email with File Attachments"
2. Go to the **Body** tab
3. Ensure **form-data** is selected
4. For the `attachments` field(s):
   - Change the dropdown from "Text" to "File"
   - Click **Select Files**
   - Choose your file(s)
5. Click **Send**

### Adding Multiple Files:

To send multiple attachments, you have two options:

**Option 1: Use the existing request "Send Email with Multiple Attachments"**
- This request has 3 `attachments` fields pre-configured
- Select files for as many as you need

**Option 2: Add more attachment fields manually**
1. In the Body tab, hover over any existing row
2. Click the **+** icon or duplicate the `attachments` row
3. Keep the key name as `attachments` (same name for all)
4. Select your file

## Testing the API

### Prerequisites:
1. Start your Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

2. Ensure your Brevo API configuration is set in `application.properties`:
   ```properties
   brevo.api.key=your-api-key
   brevo.api.url=https://api.brevo.com/v3/smtp/email
   brevo.sender.email=your-verified-sender@example.com
   brevo.sender.name=Your Name
   ```

### Running Requests:

1. Click on any request in the collection
2. Update the parameters if needed
3. Click **Send**
4. Check the response in the **Response** section below

### Expected Responses:

**Success (200 OK):**
```
Please check your inbox
```

**Success with attachments (200 OK):**
```
Email with 2 attachment(s) sent successfully! Please check your inbox.
```

**Error (400 Bad Request):**
```
Email address is required
```

**Error (500 Internal Server Error):**
```
Unable to send email: [error details]
```

## Tips

- Update the `recipient_email` variable to your email address for testing
- Check the application logs for detailed information about email sending
- Ensure files are not too large (check Brevo's attachment size limits)
- For debugging, check the Postman Console (View → Show Postman Console)

## Troubleshooting

### File Upload Issues:
- Ensure files exist and are accessible
- Check file size limits
- Verify the file type is allowed by your email provider

### API Connection Issues:
- Verify the application is running (`http://localhost:8080`)
- Check if the base_url variable is correct
- Ensure no firewall is blocking the connection

### Email Not Received:
- Check spam/junk folder
- Verify the sender email is verified in Brevo
- Check application logs for errors
- Verify Brevo API key is valid

## cURL Examples

If you prefer using cURL:

```bash
# Simple email
curl -X GET "http://localhost:8080/email/simple-email/user@example.com"

# Email with single attachment
curl -X POST http://localhost:8080/email/send-with-attachments \
  -F "to=user@example.com" \
  -F "subject=Test Email" \
  -F "message=This is a test" \
  -F "attachments=@/path/to/file.pdf"

# Email with multiple attachments
curl -X POST http://localhost:8080/email/send-with-attachments \
  -F "to=user@example.com" \
  -F "subject=Multiple Files" \
  -F "message=See attached files" \
  -F "attachments=@/path/to/file1.pdf" \
  -F "attachments=@/path/to/file2.jpg"
```

## Support

For issues or questions, please check:
- Application logs in the console
- Brevo dashboard: https://app.brevo.com
- API documentation: https://developers.brevo.com
