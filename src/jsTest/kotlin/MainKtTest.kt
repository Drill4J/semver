import kotlin.test.*

class MainKtTest {
    @Test
    fun parse_ToString() {
        assertEquals("0.1.0", parse("0.1.0").toString())
    }

    @Test
    fun gitDescribeToSemVer_NextPrerelease() {
        val semVer = gitDescribeToSemVer("v1.2.3-1-1-g15312a5")
        assertEquals("1.2.3-2", semVer.toString())
    }

    @Test
    fun gitDescribeToSemVer_NextPrerelease_MinorIncrement() {
        val semVer = gitDescribeToSemVer("v1.2.3-1-g15312a5")
        assertEquals("1.3.0-0", semVer.toString())
    }
}
