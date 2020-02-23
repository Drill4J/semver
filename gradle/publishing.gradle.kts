plugins.apply(MavenPublishPlugin::class)

configure<PublishingExtension> {
    fun Project.propOrEnv(prop: String, env: String): String? = run {
        findProperty(prop)?.toString() ?: System.getenv(env)
    }

    repositories {
        maven {
            url = uri("http://oss.jfrog.org/oss-release-local")
            credentials {
                username = project.propOrEnv("bintrayUser", "BINTRAY_USER")
                password = project.propOrEnv("bintrayApiKey", "BINTRAY_API_KEY")
            }
        }
    }
}
