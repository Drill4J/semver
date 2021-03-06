import java.net.*

plugins {
    kotlin("multiplatform") version "1.3.61"
    id("com.github.hierynomus.license")
    `maven-publish`
}

apply(from = "gradle/git-version.gradle.kts")
apply(from = "gradle/maven-repo.gradle.kts")

repositories {
    jcenter()
}

kotlin {
    sourceSets.commonMain {
        dependencies {
            implementation(kotlin("stdlib-common"))
        }
    }
    sourceSets.commonTest {
        dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
        }
    }

    js {
        useCommonJs()
        nodejs()

        val main by compilations
        with(main) {
            packageJson {
                dependencies -= listOf("kotlin-test", "kotlin-test-js-runner")
            }
            defaultSourceSet {
                dependencies {
                    implementation(kotlin("stdlib-js"))
                }
            }
        }
        val test by compilations
        test.defaultSourceSet {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        tasks {
            val jsDist by registering(Copy::class) {
                dependsOn(main.compileAllTaskName)
                mustRunAfter(check)
                from("$buildDir/js/packages/${project.name}") {
                    exclude("*.hash")
                }
                from("README.md")
                into("$buildDir/distributions/npm")
                doLast {destinationDir.resolve("package.json").transformPackageJson() }
            }
            build {
                dependsOn(jsDist)
            }
        }
    }

    jvm {
        val main by compilations
        main.defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("test-junit"))
                implementation("org.junit.jupiter:junit-jupiter:5.5.2")
            }
        }
    }
}

fun File.transformPackageJson() {
    val gson = com.google.gson.GsonBuilder()
        .setPrettyPrinting()
        .create()
    val jsonObjectClass = com.google.gson.JsonObject::class.java
    val packageJson = gson.fromJson(file("package.json").reader(), jsonObjectClass)
    val transformedJson: String = gson.fromJson(reader(), jsonObjectClass).apply {
        val name = get("name").asString
        addProperty("name", "@drill4j/$name")
        packageJson.entrySet().forEach { (k, v) ->
            add(k, v)
        }
    }.let(gson::toJson)
    writeText(transformedJson)
}

val licenseFormatSettings by tasks.registering(com.hierynomus.gradle.license.tasks.LicenseFormat::class) {
    source = fileTree(project.projectDir).also {
        include("**/*.kt", "**/*.java", "**/*.groovy")
        exclude("**/.idea")
    }.asFileTree
    headerURI = URI("https://raw.githubusercontent.com/Drill4J/drill4j/develop/COPYRIGHT")
}

tasks["licenseFormat"].dependsOn(licenseFormatSettings)
