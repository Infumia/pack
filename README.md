# pack
[![Maven Central Version](https://img.shields.io/maven-central/v/net.infumia/pack)](https://central.sonatype.com/artifact/net.infumia/pack)
## How to Use (Developers)
### Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    // Base module
    implementation "net.infumia:pack:VERSION"
    // Required, https://mvnrepository.com/artifact/net.kyori/adventure-api/
    implementation "net.kyori:adventure-api:4.17.0"
    // Required, https://mvnrepository.com/artifact/team.unnamed/creative-api/
    implementation "team.unnamed:creative-api:1.7.2"

    // Blank Slot (Optional)
    implementation "net.infumia:pack-blank:VERSION"

    // Language (Optional)
    implementation "net.infumia:pack-language:VERSION"

    // Generator (Optional)
    implementation "net.infumia:pack-generator:VERSION"
    // Required, https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/
    implementation "com.fasterxml.jackson.core:jackson-databind:"
    // Required, https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-yaml/
    implementation "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:"
}
```
### Code
```kotlin
fun main() {
}
```
