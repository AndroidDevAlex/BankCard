# BankCard

An app for creating and managing bank cards. Register, fill in your personal details, and create your virtual card by providing the number, cardholder name, expiration date, payment system, and also a password for locking/unlocking the card.

## Technology Stack

- **Programming Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture Pattern**: MVI (Model-View-Intent) 
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Asynchronous Programming**: [Kotlin Flow](https://kotlinlang.org/docs/flow.html), [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Database**: [SQLite](https://www.sqlite.org/index.html) (with [Room](https://developer.android.com/training/data-storage/room))
- **Navigation**: [Navigation component](https://developer.android.com/develop/ui/compose/navigation)
- **View Layer**: [Jetpack Compose](https://developer.android.com/compose)
- **Image loading**: [Coil](https://coil-kt.github.io/coil/compose/)



## Screenshots

### Authentication

<p float="left">
  <img alt="Splash" src="screenshots/1.png" width="250">
  <img alt="Loading" src="screenshots/2.png" width="250">
  <img alt="Loading" src="screenshots/3.png" width="250">
</p>

### Profile Settings

<p float="left">
  <img alt="Splash" src="screenshots/4.png" width="250">
  <img alt="Loading" src="screenshots/5.png" width="250">
  <img alt="Loading" src="screenshots/6.png" width="250">
</p>

### Card Settings

<p float="left">
  <img alt="Splash" src="screenshots/7.png" width="250">
  <img alt="Loading" src="screenshots/8.png" width="250">
  <img alt="Loading" src="screenshots/10.png" width="250">
</p>

### Main Screen

<p float="left">
  <img alt="Splash" src="screenshots/9.png" width="250">
</p>

### Dark theme

<p float="left">
  <img alt="Splash" src="screenshots/13.png" width="250">
  <img alt="Splash" src="screenshots/12.png" width="250">
  <img alt="Splash" src="screenshots/11.png" width="250">
</p>



## Features
- **Secure user registration using SecureRandom for salt generation. Multi-step hashing algorithm for enhanced password security**
