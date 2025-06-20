# 🔄 Zoho CRM ↔ Trello Integration

This Spring Boot application automates project management by integrating **Zoho CRM** with **Trello**.

When a Deal in Zoho CRM reaches a specific stage, the app automatically:

- ✅ Creates a Trello board
- ✅ Adds standard lists (To Do, In Progress, Done)
- ✅ Creates a sample card for the Deal
- ✅ Updates the Zoho Deal with the Trello Board ID

> 🔁 The app also handles **automatic refresh** of the Zoho access token using the refresh token.

---

## 🛠 Technologies Used

- Java 17 + Spring Boot
- Zoho CRM REST API v2
- Trello REST API
- OAuth 2.0 (Access + Refresh token)
- RestTemplate / WebClient
- Postman (for testing)

---

## ⚙️ Configuration

Update your `application.properties`:

```properties
spring.application.name=zohoCrmTrello

# --- Zoho Configuration ---
zoho.client-id=YOUR_CLIENT_ID
zoho.client-secret=YOUR_CLIENT_SECRET
zoho.redirect-uri=http://localhost:8080/oauth/callback
zoho.refresh-token=YOUR_REFRESH_TOKEN
zoho.token-url=https://accounts.zoho.com/oauth/v2/token
zoho.auth-url=https://accounts.zoho.com/oauth/v2/auth
zoho.api-base-url=https://www.zohoapis.com/crm/v2
zoho.scopes=ZohoCRM.modules.ALL,ZohoCRM.settings.fields.ALL

# --- Trello Configuration ---
trello.api-key=YOUR_TRELLO_API_KEY
trello.token=YOUR_TRELLO_TOKEN
```

---

## ⚙️ Setup Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/zoho-crm-trello-integration.git
   cd zoho-crm-trello-integration
   ```
2. Create a Zoho OAuth app from [Zoho API Console](https://api-console.zoho.com/).
3. Set the redirect URI to:  
   `http://localhost:8080/oauth/callback`
4. Exchange the Authorization Code manually by visiting:
   ```
   https://accounts.zoho.com/oauth/v2/auth?
   scope=ZohoCRM.modules.ALL,ZohoCRM.settings.fields.ALL&
   client_id=YOUR_CLIENT_ID&
   response_type=code&
   access_type=offline&
   redirect_uri=http://localhost:8080/oauth/callback
   ```
5. Paste the `code` parameter into `/oauth/callback?code=...` to get the tokens.
6. Update the `application.properties` file with all credentials and tokens.

---

## ▶️ How to Run

- Using IntelliJ or any IDE:  
  Simply run the `ZohoCrmTrelloApplication.java` file.

- Or using terminal:
  ```bash
  ./mvnw spring-boot:run
  ```

- To test the main functionality:
  ```http
  GET http://localhost:8080/api/test/sync
  ```

---

## 📬 Zoho API Example (Optional Testing)

**External Endpoint (Zoho Deals):**
```
GET https://www.zohoapis.com/crm/v2.1/Deals
Headers:
Authorization: Zoho-oauthtoken YOUR_ACCESS_TOKEN
```

**Local Endpoint (sync):**
```
GET http://localhost:8080/api/test/sync
```

This performs the following:
- ✅ Fetches all Deals from Zoho
- 🔍 Filters by stage
- 📋 Creates a Trello board
- 🧾 Adds default lists & card
- 🔗 Updates Zoho with the board ID

---
