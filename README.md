# Toolbox Library

[![Release](https://jitpack.io/v/elytelabs/toolbox.svg)](https://jitpack.io/#elytelabs/toolbox)

Toolbox is a Kotlin utility library that provides a set of useful functions and extensions for Android development.

## Features

- **ColorGenerator**: Generate random or predefined color lists.
- **UI Thread Utilities**: Simplify UI thread operations.
- **Log Extensions**: Convenience functions for logging.
- **Permission Helpers**: Easy-to-use methods for handling Android permissions.
- **String Extensions**: Additional functionalities for manipulating strings.
- **UI Helper Functions**: Functions to simplify UI-related tasks.

 
## Installation

### Step 1. Add the JitPack repository to your root build.gradle

    
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
    }

### Step 2. Add the dependency


`dependencies {
    implementation 'com.github.elytelabs:toolbox:Tag'
}` 

Replace `Tag` with the latest version from the Releases page.


## Usage

### ColorGenerator


    val colorList = ColorGenerator.getColorList()
    val randomColor = ColorGenerator.getRandomColor()

### UI Thread Utilities


    runOnUiThread {
        // Your code to be executed on the UI thread
    }
    
    repeatTask(1000) {
        // Your code to be repeated every 1000 milliseconds
    }
    
    if (isMainThread()) {
        // Your code if it's running on the main thread
    }
    
    delay(2000) {
        // Your code to be delayed by 2000 milliseconds
    }

### Permission Helpers


    val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
    if (hasPermission(permission)) {
        // Your code if the permission is granted
    } else {
        requestPermission(permission, 123)
    }

### String Extensions


    val truncatedString = "This is a long string".truncate(5)
    val reversedString = "Hello, World!".reverse()
    val occurrences = "abcabcabc".countOccurrences("abc")
    val isEmailValid = "test@example.com".isEmailValid()
    val isNumeric = "12345".isNumeric()
    val titleCasedString = "hello world".toTitleCase()
    val snakeCaseString = "camelCaseString".camelCaseToSnakeCase()
    val maskedEmail = "user@example.com".maskEmail() 

### UI Helper Functions

    val textToShare = "Check out this amazing app!"
    shareText(context, textToShare)
    copyText(context, textToShare)
    
    val urlToOpen = "https://www.example.com"
    openWebPage(context, urlToOpen)

## License

Toolbox is released under the Apache 2.0 license. See [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.txt) for details.
