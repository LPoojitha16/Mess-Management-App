# 🍽️ Mess Management System

The **Mess Management System** is a mobile application built with Android Studio (Kotlin) for students and administrators to efficiently manage mess operations. It includes daily/weekly menu displays, food order handling, permission requests for absence, and an admin dashboard to monitor and approve student requests.

---

## 📱 Features

### 👥 User Authentication
- Secure **Login/Register** for Students and Admins
- Persistent login using **SharedPreferences**

### 🏠 Home Page
- View **today's menu**
- Weekly schedule at a glance

### 📋 Menu Page
- Complete weekly mess menu
- Highlight of **today's meal**

### 🛒 Order Page
- Select and add food items to the **cart**
- Proceed to **payment**
- **Payment status** page for order confirmation

### 📝 Form Page
- Submit permission for **mess absence**
- Request permission to **order food from outside**
- Enter multiple **Registration Numbers** and **Names**
- Admin approval/denial interface

### 🧾 FormActivity
- Form with **Reg No**, **Reason**, and **Submit** button
- Students can check **permission status**
- Admins redirected to **Permission Adapter Page** to review all requests

---

## 🛠️ Tech Stack

| Layer         | Technology               |
| ------------- | ------------------------ |
| Frontend      | Android Studio (Kotlin)  |
| Backend       | Spring Boot (Java)       |
| Database      | MySQL                    |
| API Handling  | Retrofit (Android)       |
| Data Storage  | SharedPreferences (Login State) |

---

## 🏗️ Project Structure

```plaintext
mess-management/
│
├── android-app/             # Android Studio (Kotlin) frontend
│   ├── activities/
│   ├── adapters/
│   ├── models/
│   ├── api/                 # Retrofit interface for backend calls
│
├── springboot-back
