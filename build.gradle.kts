plugins {
    kotlin("multiplatform") version "1.4.0-rc"
    `maven-publish`
}

apply(from = "gradle/git-version.gradle.kts")
apply(from = "gradle/maven-repo.gradle.kts")

repositories {
    jcenter()
}

kotlin {
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
