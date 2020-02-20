import com.epam.drill.semver.*

@JsName("parse")
fun parse(str: String): SemVer = str.toSemVer()


@JsName("gitDescribeToSemVer")
fun gitDescribeToSemVer(describeResult: String): SemVer = describeResult.gitDescribeToSemVer()
