package dev.melodies.gradle

import org.gradle.api.Project
import java.io.ByteArrayOutputStream

/**
 * Run a git command and return the output as a string.
 */
internal fun Project.git(vararg command: String) = ByteArrayOutputStream().also {
    exec {
        commandLine = listOf("git", *command)
        standardOutput = it
    }
}.toString().trim()

internal val Project.branch: String get() = git("rev-parse", "--abbrev-ref", "HEAD")
internal val Project.commitHash: String get() = git("rev-parse", "--short", "HEAD")
internal val Project.dirty: Boolean get() = git("diff", "--stat").isNotBlank()

internal val Project.dirtyComponent: String get() = if (dirty) "-dirty" else ""
internal val Project.branchComponent: String get() = if (branch == "master") "" else "$branch-"

val Project.gitVersion: String get() = "$branchComponent$commitHash$dirtyComponent"