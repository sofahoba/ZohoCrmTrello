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

## 🔐 OAuth 2.0 Setup (Zoho)

Before using the app:

1. Create a Zoho OAuth client [here](https://api-console.zoho.com/).
2. Set the redirect URI (e.g. `http://localhost:8080/oauth/callback`)
3. Get the **Authorization Code** manually by visiting:


4. After visiting the URL, Zoho will redirect to your app with `?code=...`
5. Paste that full URL in the browser or call `/oauth/callback?code=...` to exchange it for tokens.

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

# Zoho Endpoints
zoho.token-url=https://accounts.zoho.com/oauth/v2/token
zoho.auth-url=https://accounts.zoho.com/oauth/v2/auth
zoho.api-base-url=https://www.zohoapis.com/crm/v2

# Zoho Scopes
zoho.scopes=ZohoCRM.modules.ALL,ZohoCRM.settings.fields.ALL

# --- Trello Configuration ---
trello.api-key=YOUR_TRELLO_API_KEY
trello.token=YOUR_TRELLO_TOKEN

```
###  Get All Zoho Deals

**Local Endpoint:**
GET https://www.zohoapis.com/crm/v2.1/Deals
Headers:
Authorization: Zoho-oauthtoken YOUR_ACCESS_TOKEN
this endpoint responsable for appearing the data of zoho deals 

GET http://localhost:8080/api/test/sync
This endpoint performs the core automation job of the project. When called, it:

    ✅ Fetches all Deals from Zoho CRM (via https://www.zohoapis.com/crm/v2/Deals)

    🔍 Filters deals that are at a specific stage (e.g., "Proposal") and do not have a Trello board yet.

    📋 Creates a new Trello board for each of those deals.

    📌 Adds default Trello lists:

        To Do

        In Progress

        Done

    🧾 Optionally creates a sample card on the board with the Deal's name.
<details> <summary><b>📁 Project Structure</b></summary>
src/
└── main/
    ├── java/
    │   └── com.flakTechTask.zohoCrmTrello/
    │       ├── AOP/
    │       │   └── LoggingAspect.java
    │       ├── component/
    │       │   ├── SyncScheduler.java
    │       │   ├── TrelloProperties.java
    │       │   └── ZohoProperties.java
    │       ├── config/
    │       │   └── AppConfig.java
    │       ├── controller/
    │       │   └── TestController.java
    │       ├── exception/
    │       │   ├── ApiException.java
    │       │   └── GlobalExceptionHandler.java
    │       ├── services/
    │       │   ├── TrelloService.java
    │       │   ├── ZohoAuthService.java
    │       │   └── ZohoCRMService.java
    │       └── ZohoCrmTrelloApplication.java
    └── resources/
        ├── static/
        ├── templates/
        └── application.properties
</details>
