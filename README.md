# BaroqueBeacon  

A state-of-the-art Android application to explore the beauty of Baroque artwork. The app is designed using the latest software architecture patterns and technologies to ensure a seamless and efficient user experience.

## Features 🌟

- **MVVM Architecture:** The app employs the Model-View-ViewModel (MVVM) pattern, allowing for a clear separation of concerns and an easier path to unit testing.
- **Clean Architecture:** Building on top of MVVM, the project is structured with Clean Architecture principles in mind. This promotes the SOLID principles, making the app more scalable and maintainable.
- **Use Cases:** With use cases, we ensure the separation and encapsulation of business logic, making features more modular and easier to manage.
- **ViewModels:** utilize Android's ViewModel for efficient data handling and UI state management.
- **Day-Night Design Support:** The application adapts to your preferred theme! (Dark or Light)
- **Pagination:** Efficiently loads artworks in manageable chunks to enhance the browsing experience. Instead of relying on third-party pagination libraries, the app uses a custom implementation tailored to its specific needs, keeping things light and simple.
  
## Libraries & Technologies 🛠️

- **Hilt:** Dependency injection is managed by Hilt, ensuring that the app's dependencies are provided efficiently and in a decoupled manner.
- **Kotlin Coroutines:** For handling asynchronous operations and making the app more responsive.
- **StateFlow & MutableStateFlow:** To efficiently handle and observe data streams within the application.
- **JUnit & Mockk:** For unit testing to ensure the app's reliability and robustness.

## Upcoming Features & TODOs 📝

- **More Tests:** Enhancing the application's reliability with comprehensive tests.
- **Caching:** Implement caching mechanisms to reduce redundant network calls and speed up data retrieval.
- **Search Functionality:** Allow users to search through the vast collection of artwork.
