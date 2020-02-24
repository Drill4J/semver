import com.epam.drill.semver.*

@JsName("parse")
fun parse(str: String): SemVer = str.toSemVer()

@JsName("prereleaseFromGit")
fun prereleaseFromGit(describeResult: String): SemVer = describeResult.prereleaseFromGit()

@JsName("patchFromGit")
fun patchFromGit(describeResult: String): SemVer = describeResult.patchFromGit()
