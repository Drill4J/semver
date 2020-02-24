repositories {
    ossReleaseRepo()
}

extensions.findByType<PublishingExtension>()?.repositories {
    ossReleaseRepo().credentials {
        username = project.propOrEnv("bintrayUser", "BINTRAY_USER")
        password = project.propOrEnv("bintrayApiKey", "BINTRAY_API_KEY")
    }
}

fun RepositoryHandler.ossReleaseRepo() = maven(url = "https://oss.jfrog.org/oss-release-local")

fun Project.propOrEnv(prop: String, env: String): String? = run {
    findProperty(prop)?.toString() ?: System.getenv(env)
}
