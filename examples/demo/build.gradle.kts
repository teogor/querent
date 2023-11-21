plugins {
  id("kotlin")
  id("application")
  id("dev.teogor.querent")
}

querent {
  buildFeatures {
    buildProfile = true
  }
}

application {
  mainClass.set("MainKt")
}

