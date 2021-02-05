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
package com.epam.drill.semver

import kotlin.test.*

class SemVerTest {
    @Test
    fun toSemVer_Base_SemVer() {
        val semVer = "1.2.3".toSemVer()
        assertEquals(1, semVer.major)
        assertEquals(2, semVer.minor)
        assertEquals(3, semVer.patch)
    }

    @Test
    fun toSemVer_Suffixed_SemVer() {
        val semVer = "1.2.3-suffix".toSemVer()
        assertEquals(1, semVer.major)
        assertEquals(2, semVer.minor)
        assertEquals(3, semVer.patch)
        assertEquals("suffix", semVer.suffix)
    }

    @Test
    fun toSemVer_ParseToString() {
        assertEquals("1.2.3", "1.2.3".toSemVer().toString())
        assertEquals("1.2.3-suffix", "1.2.3-suffix".toSemVer().toString())
    }

    @Test
    fun nextMajor() {
        val semVer = "1.2.3-suffix".toSemVer()
        assertEquals("2.0.0", semVer.nexMajor().toString())
    }

    @Test
    fun nextMinor() {
        val semVer = "1.2.3-suffix".toSemVer()
        assertEquals("1.3.0", semVer.nextMinor().toString())
    }

    @Test
    fun nextPatch() {
        val semVer = "1.2.3-suffix".toSemVer()
        assertEquals("1.2.4", semVer.nextPatch().toString())
    }

    @Test
    fun nextPrerelease_NoSuffix() {
        val semVer = "1.2.3".toSemVer()
        assertEquals("1.3.0-0", semVer.nextPrerelease().toString())
    }

    @Test
    fun nextPrerelease_WithIntSuffix() {
        val semVer = "1.2.3-1".toSemVer()
        assertEquals("1.2.3-2", semVer.nextPrerelease().toString())
    }

    @Test
    fun comparing_Base() {
        assertEquals(0, compareValues("1.2.3".toSemVer(), "1.2.3".toSemVer()))
        assertTrue {  "1.2.3".toSemVer() > "1.2.2".toSemVer() }
        assertTrue {  "1.2.11".toSemVer() > "1.2.2".toSemVer() }
        assertTrue {  "2.0.0".toSemVer() > "1.99.99".toSemVer() }
    }

    @Test
    fun comparing_WithSuffixes() {
        assertEquals("1.2.3", "1.2.3".toSemVer().toString())
        assertEquals("1.2.3-suffix", "1.2.3-suffix".toSemVer().toString())
    }
}
