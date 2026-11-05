# NextCart

NextCart is a demo Android shopping app built with **Kotlin, Jetpack Compose, MVVM and a multi-module Clean Architecture**. It uses **Ktor** for API calls, **Koin** for dependency injection, **Kotlin Serialization** for JSON parsing and **DataStore** for local preferences/authentication data. The app uses [DummyJSON](https://dummyjson.com/) for sample products, categories and authentication.

## Architecture

The project is organized into separate Gradle modules for the app, features and shared core functionality. Each feature follows MVVM: **Compose UI → ViewModel → UseCase → Repository → Ktor API**. The main features are authentication, home, product catalogue (listing, category filtering and details), and profile.

## DummyJSON API

Base URL: `https://dummyjson.com`

- Login: `POST /auth/login`
- Current profile: `GET /auth/me`
- Categories: `GET /products/categories`
- Products: `GET /products?limit=20&skip=0`
- Search: `GET /products/search?q=phone`
- Category products: `GET /products/category/beauty?limit=20&skip=0`
- Product details: `GET /products/1`
- Flash deals: `GET /products?sortBy=discountPercentage&order=desc&limit=10`
- Best sellers: `GET /products?sortBy=rating&order=desc&limit=10`
- New arrivals: `GET /products?sortBy=id&order=desc&limit=10`

## Demo login accounts

Use these DummyJSON demo credentials:

| User ID | Username | Password |
|---:|---|---|
| `1` | `emilys` | `emilyspass` |
| `2` | `michaelw` | `michaelwpass` |
| `3` | `sophiab` | `sophiabpass` |
| `4` | `avat` | `avatpass` |

Demo credentials and mock API data are for development/testing only. DummyJSON simulates many write operations; it is not a production backend and does not permanently save cart, order or payment data.
