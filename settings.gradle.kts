plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "haych-bots"
include("test-bot")
include("common-module")
include("haych-tower-of-life")
