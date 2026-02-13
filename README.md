# DentalFirst - Product Details Screen Implementation

A modern Android application showcasing a **Product Details Screen** implementation using **Jetpack Compose**, **Kotlin Coroutines**, and **Clean Architecture** principles. This project demonstrates production-ready code patterns for displaying product information from a mock API/local data source.

---

## 📋 Overview

**DentalFirst** is a test assignment project for a medical dental equipment sales application. It features:

- ✅ **Product Catalog Screen** - Browse a list of dental equipment products
- ✅ **Product Details Screen** - View comprehensive product information with specifications
- ✅ **Modern Architecture** - MVVM pattern with clear separation of concerns
- ✅ **State Management** - Proper handling of loading, success, and error states
- ✅ **Asynchronous Data Loading** - Kotlin Coroutines and Flow for reactive programming
- ✅ **Beautiful UI** - Material 3 design with Jetpack Compose

---

## 🏗️ Architecture

The project follows **MVVM (Model-View-ViewModel)** architecture with clear separation of layers:

```
┌─────────────────────────────────────┐
│     UI Layer (Jetpack Compose)      │
│  - CatalogScreen                    │
│  - ProductDetailsScreen             │
│  - ProductCard                      │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│   ViewModel Layer                   │
│  - CatalogViewModel                 │
│  - ProductDetailsViewModel          │
│  - ProductDetailsViewModelFactory   │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│   Repository Layer                  │
│  - ProductRepository                │
└────────────────┬────────────────────┘
                 │
┌────────────────▼────────────────────┐
│   Data Layer                        │
│  - MockProductService               │
│  - Local Product Models             │
└─────────────────────────────────────┘
```

### Directory Structure

```
app/src/main/java/com/tomtom/dentalfirst/
├── MainActivity.kt                    # Entry point, Navigation setup
├── data/
│   ├── model/
│   │   └── Product.kt                # Product data models
│   └── repository/
│       └── ProductRepository.kt       # Data access layer
├── ui/
│   ├── catalog/
│   │   ├── CatalogScreen.kt          # Catalog list screen
│   │   ├── CatalogViewModel.kt       # Catalog logic (ViewModel)
│   │   └── ProductCard.kt            # Reusable product card component
│   ├── details/
│   │   └── ProductDetailsScreen.kt   # Product details main screen
│   └── theme/
│       ├── Theme.kt                  # Material 3 theme configuration
│       └── Color.kt                  # Color scheme
└── viewmodel/
    ├── ProductDetailsViewModel.kt    # Details screen logic
    └── ProductDetailsViewModelFactory.kt # Factory for ViewModel creation
```

---

## 🛠️ Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Language** | Kotlin | 2.0.21 |
| **UI Framework** | Jetpack Compose | 2024.09.00 |
| **Architecture** | MVVM | - |
| **State Management** | StateFlow/Flow | - |
| **Navigation** | Navigation Compose | 2.7.7 |
| **Coroutines** | Kotlin Coroutines | 2.10.0 |
| **Image Loading** | Coil | 2.4.0 |
| **Material Design** | Material 3 | 1.4.0 |
| **JSON Parsing** | Gson | 2.10.1 |
| **Minimum SDK** | 34 | - |
| **Target SDK** | 36 | - |

---

## 📱 Features

### 1. Catalog Screen
- Displays a scrollable list of products
- Each product card shows:
  - Product image
  - Product name
  - Product price
- Click navigation to Product Details Screen
- Clean, reusable product card component

### 2. Product Details Screen
- **Comprehensive Product Information**:
  - Product image gallery
  - Product name and price
  - Detailed description
  - Specifications/characteristics
  - Additional product details

- **State Management**:
  - Loading state indicator
  - Error state handling with fallback UI
  - Success state with full content

- **Interactive Elements**:
  - Back button with navigation
  - Share button (stubbed for extensibility)
  - Favorite/Like button (stubbed)
  - Action button (e.g., Add to Cart - stubbed)

- **Responsive Design**:
  - Works on various screen sizes
  - Proper padding and spacing
  - Scrollable content for long descriptions

### 3. Navigation
- Clean navigation flow from Catalog → Details
- Type-safe navigation with Compose Navigation
- Proper back navigation handling

---

## 🔄 Data Flow

### Reactive Data Flow with Flow

```
ProductDetailsViewModel
├── Product Flow (StateFlow)
│   └── Collected by ProductDetailsScreen
├── Loading State
├── Success State
└── Error State
```

### Key Components

1. **ViewModel** - Manages UI state and data fetching
2. **Repository** - Abstracts data source (mock or real)
3. **Mock Service** - Simulates API calls with coroutines
4. **Compose UI** - Collects and displays state

---

## 📦 Dependencies

### Core Dependencies
```kotlin
// Compose UI
androidx.compose.ui:ui:2024.09.00
androidx.compose.material3:material3:1.4.0
androidx.compose.material:material-icons-extended

// Navigation
androidx.navigation:navigation-compose:2.7.7

// ViewModel & Lifecycle
androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0
androidx.lifecycle:lifecycle-runtime-ktx:2.10.0

// Image Loading
io.coil-kt:coil-compose:2.4.0

// JSON Parsing
com.google.code.gson:gson:2.10.1
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (2024.1 or newer)
- JDK 11+
- Gradle 8.x
- Android SDK 34+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-repo/DentalFirst.git
   cd DentalFirst
   ```

2. **Open in Android Studio**
   - File → Open → Select DentalFirst folder
   - Android Studio will sync Gradle files automatically

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device (API 34+) or start an emulator
   - Click "Run" in Android Studio or:
   ```bash
   ./gradlew installDebug
   ```

---

## 💻 Code Examples

### ViewModel Pattern (ProductDetailsViewModel)

```kotlin
class ProductDetailsViewModel(productId: String) : ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product.asStateFlow()

    init {
        viewModelScope.launch {
            _product.value = repository.getProductById(productId)
        }
    }
}
```

### Composable with State Collection

```kotlin
@Composable
fun ProductDetailsScreen(productId: String, onBackClick: () -> Unit) {
    val viewModel: ProductDetailsViewModel = viewModel(
        factory = ProductDetailsViewModelFactory(productId)
    )
    val product = viewModel.product.collectAsState()

    if (product.value != null) {
        ProductDetailsContent(product.value!!, onBackClick)
    } else {
        LoadingScreen()
    }
}
```

### Reusable Component (ProductCard)

```kotlin
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(product.id) }
    ) {
        // Card content
    }
}
```

---

## 🎨 UI/UX Highlights

- **Material 3 Design System** - Follows Google's latest design guidelines
- **Responsive Layout** - Adapts to different screen sizes
- **State Indicators** - Clear loading and error states
- **Smooth Navigation** - Compose Navigation with back stack handling
- **Image Optimization** - Coil for efficient image loading
- **Accessibility** - Proper content descriptions and contrast

---

## 🔍 Assumptions & Design Decisions

### 1. Mock Data Source
- No real backend API is implemented
- Mock products are loaded from local data with simulated network delay
- Easy to swap for real API by modifying the Repository layer

### 2. Navigation Pattern
- Simple 2-screen flow: Catalog → Details
- No complex navigation flows (Cart, Checkout, etc.)
- Back navigation handled by NavController

### 3. Image Handling
- Uses Coil for image loading with caching
- Images are loaded from local drawable resources
- Can be extended to support remote URLs

### 4. State Management
- StateFlow for reactive state updates
- ViewModels follow Google's recommended patterns
- All state updates happen on the UI thread (safe with Compose)

### 5. Error Handling
- Fallback UI for missing products
- Proper error state in ViewModel
- Extensible for HTTP error codes if backend is added

---

## 🧪 Testing

The project is structured to support unit and UI testing:

```kotlin
// Example: Testing ViewModel
@Test
fun testProductDetailsLoading() {
    val viewModel = ProductDetailsViewModel("product-id")
    // Assert loading state
}

// Example: Testing Composables
@Test
fun testProductCardRenders() {
    composeRule.setContent {
        ProductCard(mockProduct)
    }
    // Assert UI elements
}
```

### Running Tests
```bash
./gradlew test                  # Unit tests
./gradlew connectedAndroidTest  # Instrumentation tests
```

---

## 🔐 Code Quality

- **Clean Architecture** - Clear separation of concerns
- **SOLID Principles** - Extensible and maintainable code
- **Kotlin Best Practices** - Idiomatic Kotlin usage
- **Compose Guidelines** - Stateless composables where possible
- **Resource Management** - Proper ViewModel scoping
- **Error Handling** - Graceful error states

---

## 📈 Extensibility

The project is designed for easy extension:

### To Add Real API Integration:
1. Create a Retrofit service interface
2. Update `ProductRepository` to use Retrofit
3. Add proper error mapping and retry logic

### To Add Database Caching:
1. Integrate Room database
2. Implement local-first caching strategy
3. Sync with repository

### To Add More Features:
- Cart management (new ViewModel + screen)
- Product search/filtering (Catalog enhancements)
- User authentication (new screen + interceptor)
- Product reviews (new screen + data models)

---

## 🐛 Known Limitations

1. **No Real Backend** - Uses mock data with simulated delays
2. **Simplified Navigation** - Only Catalog and Details screens
3. **No Persistence** - Data is not saved between app sessions
4. **No Authentication** - No user login system
5. **Limited Customization** - Cart and favorites are stubbed

These limitations are intentional for the test assignment scope and can be addressed in future iterations.

---

## 📝 Development Guidelines

### Code Style
- Follow Kotlin conventions
- Use meaningful variable names
- Add kdoc comments for public APIs
- Keep functions small and focused

### Commit Messages
```
[Feature] Add product details screen
[Bugfix] Fix navigation back button
[Refactor] Extract ProductCard component
[Chore] Update dependencies
```

### Branch Naming
```
feature/product-details
bugfix/navigation-issue
chore/dependency-update
```

---

## 👥 Contributing

This is a test assignment project. For contributions:

1. Create a feature branch from `main`
2. Follow the code style guidelines
3. Add meaningful commit messages
4. Ensure code compiles without warnings
5. Test on multiple Android versions (34+)

---

## 📄 License

This project is provided as-is for the test assignment. All rights reserved.

---

## 🔗 Resources

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [MVVM Architecture Guide](https://developer.android.com/codelabs/android-mvvm-architecture)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Material 3 Design](https://m3.material.io/)
- [Android Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

---

## 📧 Contact & Support

For questions about this implementation:
- Review the code comments and kdoc
- Check the architecture diagrams above
- Refer to the Tech Stack section for library documentation

---

**Last Updated**: February 2026  
**Version**: 1.0.0  
**Status**: Production Ready

