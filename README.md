# 🛠️ TGD Maintenance Soft

TGD Maintenance Soft is a maintenance management system for companies that operate with industrial equipment on-site. It
allows planning, executing, and monitoring preventive and corrective maintenance tasks, as well as managing operators,
equipment, maintenance routes, and asset status.

---

## 📖 Table of Contents

- [🧭 Introduction](#-introduction)
- [⚙️ Technologies Used](#-technologies-used)
- [🚀 Installation and Setup](#-installation-and-setup)
- [🔐 Auth0 Configuration](#-auth0-configuration)

---

## 🧭 Introduction

**TGD Maintenance Soft** is a platform aimed at companies managing industrial maintenance within production plants. The
system allows detailed tracking of:

- **Equipment** and their statuses (operational, stopped, under maintenance).
- **Operators** responsible for executing assigned tasks.
- **Maintenance routes**, defined with frequencies (daily, weekly, monthly, or custom).
- Logging of **executed maintenance tasks** with observations, actual duration, and results.
- Notifications, custom forms, and complete traceability of maintenance history.

This system is developed as the final project for the **Technical Programming Degree** at the **Universidad Tecnológica
Nacional**, with the goal of providing a robust, intuitive, and adaptable digital solution for **TGD Company**.

---

## ⚙️ Technologies Used

- **Frontend**: Next.js + Tailwind CSS + ShadCN UI + Tanstack Query + Zod (among others)
- **Backend**: Java + Spring Boot + JPA + MySQL
- **Authentication**: Auth0 (OAuth 2.0 / JWT)
- **Database**: MySQL
- **Version Control**: Git + GitHub

---

## 🚀 Installation and Setup

### 1. Clone the repository

```bash
git clone https://github.com/JoaquinBenegas2/tgd-maintenance-soft-server.git
cd tgd-maintenance-soft-server
```

### 2. Configure environment variables and application profiles

This project
uses [Spring Boot profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
to manage different configurations for development, production, and testing.

You’ll find the following files in the `src/main/resources` directory:

```
application.properties             # Common base configuration
application-dev.properties         # Development environment
application-prod.properties        # Production environment
```

To activate a specific profile, set the environment variable `spring.profiles.active` when running the application.

> For example, to start the app using the **development** profile:

```bash
java -Dspring.profiles.active=dev -jar target/tgd-maintenance-soft.jar
```

In each profile file, you can customize the database connection, logging level, Auth0 settings, etc.

You can also use system environment variables inside properties files like this:

```properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

Make sure to set those variables in your shell or deployment environment.

### 3. Build and Run the service

To start the backend locally:

#### If using Maven Wrapper:

```bash
./mvnw spring-boot:run
```

#### Or if Maven is installed globally:

```bash
mvn spring-boot:run
```

This will start the Spring Boot application at `http://localhost:8080`.

After building, you can also run it as a JAR:

```bash
mvn clean package
java -Dspring.profiles.active=dev -jar target/tgd-maintenance-soft.jar
```

---

## 🔐 Auth0 Configuration

### 1. Register on Auth0

#### Create an Auth0 account:

1. Go to the official [Auth0](https://auth0.com/) website and click on **Sign Up**.
2. Fill out the registration form with your email, password, and other required information.
    - Alternatively, you can sign up using an external provider such as Google or GitHub.
3. Once registered, log in and access the **Dashboard**.

---

### 2. Create a New Application in Auth0

#### Create an application:

1. In the **Dashboard**, go to **Applications > Applications** from the side menu.
2. Click the **+ Create Application** button.

#### Configure the application:

1. In the pop-up modal:
    - **Name**: Enter a descriptive name for your application, such as "Spring Boot & Next.js App".
    - **Type**: Select **Regular Web Application**.
2. Click **Create** to create the application.

#### Set up the application URLs:

1. Go to your application's settings.
2. Fill out the following fields:
    - **Allowed Callback URLs**: Specify the URLs Auth0 will redirect to after authentication. Example:
        - `http://localhost:3000/api/auth/callback/auth0`
    - **Allowed Logout URLs**: Specify the URLs Auth0 will redirect to after logging out. Example:
        - `http://localhost:3000`
    - **Allowed Web Origins**: Specify the allowed URLs for your app usage. Example:
        - `http://localhost:3000`
3. Click **Save Changes** to apply the configuration.

---

### 3. Set Up an API in Auth0

#### Create an API:

1. In the **Dashboard**, go to **Applications > APIs** in the side menu.
2. Click the **+ Create API** button.

#### Configure the API:

1. Fill out the following fields:
    - **Name**: Enter a descriptive name for your API, such as "Spring Boot API".
    - **Identifier**: Enter a unique URL for your API, such as:
        - `https://spring-boot-api`
    - **Signing Algorithm**: Select **RS256**.
2. Click **Create** to register the API.

#### Configure RBAC settings:

1. In the API settings, find the **RBAC Settings** section.
2. Enable the following options:
    - **Enable RBAC**.
    - **Add Permissions in the Access Token**.

---

### 4. Add Roles to the Token Using a Trigger

#### Create an Action to add roles:

1. In the **Dashboard**, go to **Actions > Library**.
2. Click **+ Create Action** and select the **Post Login** template.
3. Name it something like: **Add User Roles to Token (Generic)**.
4. In the code editor, you can choose between the following versions:

##### Simple version (single app):

```javascript
exports.onExecutePostLogin = async (event, api) => {
    const namespace = "https://spring-boot-api"; // Replace with your own namespace if needed
    const assignedRoles = (event.authorization || {}).roles;

    if (assignedRoles) {
        api.idToken.setCustomClaim(`${namespace}/roles`, assignedRoles);
        api.accessToken.setCustomClaim(`${namespace}/roles`, assignedRoles);
    }
};
```

##### Generic version for multiple apps with secrets:

```javascript
exports.onExecutePostLogin = async (event, api) => {
    const namespaces = [
        {
            namespace: "https://spring-boot-api",
            clientId: event.secrets.SPRING_BOOT_APP_CLIENT_ID,
        },
    ];

    const assignedRoles = event.authorization?.roles;
    const clientId = event.client.client_id;

    if (assignedRoles) {
        const app = namespaces.find((app) => app.clientId === clientId);
        if (app) {
            api.idToken.setCustomClaim(`${app.namespace}/roles`, assignedRoles);
            api.accessToken.setCustomClaim(`${app.namespace}/roles`, assignedRoles);
        }
    }
};
```

> Make sure to define the necessary secrets in the **Secrets** tab inside the Actions editor.

5. Click **Deploy** to deploy the Action.
6. Go to the **Flows** tab, select **Login**, and drag the Action into the **Post Login** flow.

---

### 5. Configure Authentication Methods (Connections)

#### Select available login methods:

1. In the Dashboard, go to **Authentication > Database**.
2. Select or create a connection, for example: **Username-Password-Authentication**.
    - You can enable or disable the **"Allow Sign Ups"** option to prevent users from signing up on their own.
3. Save the changes.

#### Add social login (e.g., Google):

1. Go to **Authentication > Social**.
2. Select **Google OAuth 2.0**.
3. Configure the provider (Google Client ID and Secret).
4. Activate the connection and save it.

#### Link the Connections to your Application:

1. Go to **Applications > Applications**.
2. Select your application.
3. In the **Connections** tab, enable the connections you want to allow:
    - `Username-Password-Authentication`
    - `google-oauth2` (or others you have enabled)
4. Save the changes.

> This way, you can control exactly which applications allow which authentication methods, and prevent unauthorized user
> registrations.

---
