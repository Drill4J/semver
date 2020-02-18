import kotlin.test.*

class MainKtTest {
    @Test
    fun parse_ToString() {
        assertEquals("0.1.0", parse("0.1.0").toString())
    }
}
