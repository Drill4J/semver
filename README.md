# Multiplatform SemVer Lib

Supported targets: Jvm, Js.

## Usage

### JavaScript



```javascript
let semver = require("@drill4j/semver");

let ver = semver.parse("0.1.0-suffix");

console.log(ver.major); // -> 0
console.log(ver.minor); // -> 1
console.log(ver.patch); // -> 0
console.log(ver.suffix); // -> "suffix"

console.log(ver.toString()); // -> "0.1.0-suffix"
console.log(ver.nextMajor().toString()); // -> "1.0.0"
console.log(ver.nextMinor().toString()); // -> "0.2.0"
console.log(ver.nextPatch().toString()); // -> "0.1.1"
console.log(ver.nextPrerelease().toString()); // -> "0.1.1"

let releaseVer = semver.parse("0.1.0");
console.log(releaseVer.suffix); // ->  "" (empty string )

// semver from git
let prerelNoAbbrev = semver.prereleaseFromGit("v1.1.0"); // -> "1.1.0"
let prerelZeroDistance = semver.prereleaseFromGit("v1.1.0-0-g15312a5"); // -> "1.1.0"
let prerel0 = semver.prereleaseFromGit("v1.1.0-1-g15312a5"); // -> "1.2.0-0"
let prerel1 = semver.prereleaseFromGit("1.2.0-0-1-g15312a5"); // -> "1.2.0-1"

let patchNoAbbrev = semver.prereleaseFromGit("v1.1.1"); // -> "1.1.1"
let patchZeroDistance = semver.prereleaseFromGit("v1.1.1-0-g15312a5"); // -> "1.1.1"
let patchIncrement = semver.prereleaseFromGit("v1.1.1-1-g15312a5"); // -> "1.1.2"
```
