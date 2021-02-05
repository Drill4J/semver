/**
 * Copyright 2020 EPAM Systems
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
