plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    `maven-publish`
}

android {
    namespace = "com.elytelabs.toolbox"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}

// Maven publishing configuration
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.elytelabs.toolbox"
            artifactId = "toolbox"
            version = "2.0.0"
            afterEvaluate {
                from(components["release"])
            }


            pom {
                name.set("Toolbox")
                description.set("Modern Kotlin utility library for Android with extension functions for UI, intents, dialogs, and colors.")
                url.set("https://github.com/elytelabs/toolbox")

                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }
        }
    }
}