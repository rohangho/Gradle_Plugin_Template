plugins{
    `java-gradle-plugin`
    `kotlin-dsl`
}
gradlePlugin {
    plugins {
        create("my-plugin") {
            id = "com.example.rohan"
            implementationClass = "com.example.rohan.MyGradlePlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies{
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.23")
}