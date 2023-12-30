Releasing
========

1. Update the version in module `build.gradle` file.
2. Update the `CHANGELOG.md` for the impending release.
3. Update the `README.md` with the new version if needed.
4. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
5. `./gradlew publishAllPublicationsToMavenCentral`.
6. `./gradlew closeAndReleaseRepository`.
7. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
8. Update the `gradle.properties` to the next SNAPSHOT version.
9. `git commit -am "Prepare next development version."`
10. `git push && git push --tags`
