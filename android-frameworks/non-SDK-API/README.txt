
[Cause Description]:
    Since Android 10, there is a black list for non-SDK API.
    Without this commit, some apks crash directly.

[Total Solution]:
    Add Your SDK API to whitelist.

[Testing Proposal]:
    Run SDK test apk, to check if it works fine.

