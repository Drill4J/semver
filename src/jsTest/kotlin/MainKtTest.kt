import kotlin.test.*

class MainKtTest {
    @Test
    fun parse_ToString() {
        assertEquals("0.1.0", parse("0.1.0").toString())
        assertEquals("0.1.0-suffix", parse("0.1.0-suffix").toString())
    }

    @Test
    fun prereleaseFromGit_Increment() {
        val semVer = prereleaseFromGit("v1.2.0-1-1-g15312a5")
        assertEquals("1.2.0-2", semVer.toString())
    }

    @Test
    fun patchFromGit_Increment() {
        val semVer = patchFromGit("v1.2.3-1-g15312a5")
        assertEquals("1.2.4", semVer.toString())
    }
}
