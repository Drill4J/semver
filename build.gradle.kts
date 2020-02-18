plugins {
    kotlin("multiplatform") version "1.3.61"
    `maven-publish`
}

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
        main.compileAllTaskName
        with(main) {
            packageJson {
                dependencies.remove("kotlin-test")
                dependencies.remove("kotlin-test-js-runner")

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
                from("$buildDir/js/packages/${project.name}")
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
    val jsonObject = gson.fromJson(reader(), com.google.gson.JsonObject::class.java)
    val transformedJson: String = jsonObject.apply {
        val name = get("name").asString
        addProperty("name", "@drill4j/$name")
        addProperty("author", "Drill4j")
        addProperty("license", "Apache-2.0")
        addProperty("description", "A multiplatform SemVer library.")
        add("repository", com.google.gson.JsonObject().apply {
            addProperty("type", "git")
            addProperty("url", "https://github.com/Drill4J/semver")
        })
        gson.toJson(this)
    }.let(gson::toJson)
    writeText(transformedJson)
}
