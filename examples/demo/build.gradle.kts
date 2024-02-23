plugins {
  id("kotlin")
  id("application")
  id("dev.teogor.querent")
}

querent {
  buildFeatures {
    buildProfile = true
    xmlResources = true
    languagesSchema = true
  }
}

application {
  mainClass.set("MainKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
