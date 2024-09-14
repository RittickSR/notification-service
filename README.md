
# Notification Service Application

This is a Spring Boot-based notification service application designed to send notifications via different channels such as Email, SMS, and IVR. The project also includes functionality to fetch notification statistics.

## Design Patterns Used

- **Factory Pattern**: Used to manage different types of notifications (Email, SMS, IVR). The service only uses an interface to interact with the notifier, internally a factory sets up the correct type of notifier. This design pattern ensures extensibility in case of new integrations, since all notifiers have to just implement the basic interface.
- **Repository Pattern**: Used for database operations with the `UserRepository` and `NotificationRepository` classes act as intermediary between the actual business logic code and the DB, thereby giving abstraction and making the code more readable and easy to understand.
- **DTO (Data Transfer Object)**: DTOs are used to ensure correctness and validity of requests and responses, greatly reducing points of failure at runtime.

## Database Structure

The project uses an PostgresSQL for storing notifications and user data. The primary entities are:

- **User**: Stores user information for sending notifications, eg: email and mobile number.
- **Notification**: Stores information about notifications sent to users, including the type and status.

### Tables

- **User**:
  - `id` (Primary Key)
  - `name`
  - `email`
  - `phone_number`

- **Notification**:
  - `id` (Primary Key)
  - `user_id` (Foreign Key)
  - `type` (Email, SMS, IVR)
  - `status`
  - `created_at`

## API Endpoints

### 1. Send Notification

**URL**: `/notifications/send`  
**Method**: `POST`

**Request Body** (Example for Email):
```json
{
  "type": "sms",
  "message": "This is a test sms notification",
  "userid": 1
}
```

**Response**:

- Success
```
SMS "This is a test sms notification" sent to 12345
```
- Failure (Incorrect notification method)
```
No such notifier present
```
-Failure (Incorrect user id)
```
No such user present
```
### 2. Get Notification Stats

**URL**: `/notifications/stats`  
**Method**: `GET`

**Request Body**:
```json
{
  "startTime":"2024-09-14T00:00:00",
  "endTime":"2024-09-14T23:59:59"
}
```

**Response**:
- In case of Date range within a single day
```json
{
  "notifications": [
    {
      "id": 3,
      "type": "sms",
      "status": "SENT",
      "sentTime": "2024-09-13T15:43:56.435",
      "message": "This is a test sms notification"
    }
  ],
  "count": 1
}
```
- In case of Date range spanning more than one day
```json
[
  {
    "date": "2024-09-13",
    "notifications": [
      {
        "id": 3,
        "type": "sms",
        "status": "SENT",
        "sentTime": "2024-09-13T15:43:56.435",
        "message": "This is a test sms notification"
      }
    ],
    "count": 1
  },
  {
    "date": "2024-09-14",
    "notifications": [
      {
        "id": 1,
        "type": "IVR",
        "status": "SENT",
        "sentTime": "2024-09-14T15:42:43.940131",
        "message": "This is a test ivr notification"
      },
      {
        "id": 2,
        "type": "email",
        "status": "SENT",
        "sentTime": "2024-09-14T15:43:11.240451",
        "message": "This is a test email notification"
      },
      {
        "id": 6,
        "type": "sms",
        "status": "SENT",
        "sentTime": "2024-09-14T16:11:09.176001",
        "message": "This is a test sms notification"
      }
    ],
    "count": 3
  },
  {
    "date": "2024-09-15",
    "notifications": [
      {
        "id": 5,
        "type": "sms",
        "status": "SENT",
        "sentTime": "2024-09-15T16:10:38.4",
        "message": "This is a test sms notification"
      },
      {
        "id": 7,
        "type": "sms",
        "status": "SENT",
        "sentTime": "2024-09-15T16:12:02.031",
        "message": "This is a test sms notification"
      }
    ],
    "count": 2
  }
]
```
- In case no messages are present
```json
[]
```

### 3. Create User

**URL**: `/user/add`  
**Method**: `POST`

**Request Body**:
```json
{
  "name":"Rittick2",
  "email":"rt2@gmail.com",
  "phone":"421421"
}
```

**Response**:
```
User created successfully
```

## cURL Examples

### 1. Send Notification
```bash
curl --location 'http://localhost:8080/notifications/send' \
--header 'Content-Type: application/json' \
--data '{
    "type": "sms",
    "message": "This is a test sms notification",
    "userid": 1
}'
```

### 2. Get Notification Stats
```bash
curl --location --request GET 'http://localhost:8080/notifications/stats' \
--header 'Content-Type: application/json' \
--data '{
    "startTime":"2024-09-13T00:00:00",
    "endTime":"2024-09-15T23:59:59"
}'
```

### 3. Create User
```bash
curl --location 'http://localhost:8080/user/add' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name":"Rittick2",
    "email":"rt2@gmail.com",
    "phone":"421421"
}'
```

