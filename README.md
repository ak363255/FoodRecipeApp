# 🍽️ Food Recipe App (Under developement)

The **Food Recipe App** is a modern Android application that allows users to explore a wide variety of recipes, view detailed information about them, and search recipes based on ingredients. Users can also bookmark their favorite recipes for quick access later.

This project follows Clean Architecture principles, is written in **Kotlin**, uses **Jetpack Compose** for the UI, and leverages modern Android development practices such as **Kotlin Coroutines**, **Flow**, **Navigation Component**, **Paging 3**, and **Dependency Injection** with **Koin**.

---## Screenshots

<img src="https://github.com/user-attachments/assets/8d203cc7-905d-4011-b5bc-dc3943c60e7c" alt="project-screenshot" width="250" height="500/">   
<img src="https://github.com/user-attachments/assets/e1e0506e-faff-4100-8313-55eaa277f202" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/7d529afa-5c45-4f0b-a72d-84558ac50bfc" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/985f3bcc-e48e-420f-ac06-e8c8a34008e5" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/644002e8-4927-421e-be7d-a2adbbb4f89e" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/0bacc41b-b292-4a6c-a2c5-6769cefe0d79" alt="project-screenshot" width="250" height="500/">



## ✨ Features
- 👋 Onboarding screen to introduce app features
- 🔥 View popular recipes
- 📄 See detailed recipe information including:
  - Instructions
  - Calories
  - Fats
- 🔍 Search recipes by ingredients
- 📚 Bookmark recipes for future reference
- 📄 Paginated list of recipes using the Paging 3 library
- 🧭 Bottom Navigation Bar for seamless navigation


---

## 🧱 Architecture

The app is built using **Clean MVVM Architecture** and is divided into three modules:

### 1. **Data Module**
- Handles network and local data sources
- Contains the repository implementation
- Uses Retrofit for API calls

### 2. **Domain Module**
- Contains use cases and repository interfaces
- Isolated from Android and UI-specific logic

### 3. **Presentation Module**
- Built with **Jetpack Compose**
- Includes ViewModels scoped to navigation graph composables
- Handles UI logic and interaction

---

## 🔧 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Kotlin Coroutines & Flow**
- **Navigation Component**
- **Paging 3**
- **Koin** for Dependency Injection
- **MVVM with Clean Architecture**
- **Modularization (data, domain, presentation)**

---

## 🧠 Key Use Cases

- `GetPopularRecipesUseCase`
- `GetCuisinesUseCase`
- `GetRecipesByIngredientsUseCase`

Each use case is responsible for a single business logic operation and is injected into ViewModels via Koin.

---

## 🗺️ Navigation

- Built using **Jetpack Navigation Component**
- ViewModels are scoped to **NavHost Composables**
- Integrated with **Bottom Navigation Bar** for multiple sections

---

## 📦 Project Structure
food-recipe-app/
│
├── data/ # Network, local sources, repository implementation
├── domain/ # Use cases, models, repository interfaces
├── presentation/ # UI (Jetpack Compose), ViewModels, Navigation
│
└── build.gradle.kts # Kotlin DSL for Gradle

## 🧪 Future Enhancements

- Offline support using Room
- Dark mode theming
- User authentication for personalized bookmarking
- Filter recipes by dietary preferences

