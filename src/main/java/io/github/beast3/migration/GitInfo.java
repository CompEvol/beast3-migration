package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Reads the git HEAD of a working tree without dragging in JGit. Falls back
 * to the {@code .git/HEAD} ref file if {@code git} isn't on PATH so the tool
 * still works in stripped-down CI images.
 */
public record GitInfo(String shortSha, String fullSha, String branch, boolean dirty) {

    public static final GitInfo MISSING = new GitInfo("", "", "", false);

    public static GitInfo read(Path repo) {
        if (!Files.isDirectory(repo.resolve(".git")) && !Files.isRegularFile(repo.resolve(".git"))) {
            return MISSING;
        }
        try {
            String fullSha = run(repo, "git", "rev-parse", "HEAD");
            String shortSha = run(repo, "git", "rev-parse", "--short", "HEAD");
            String branch = run(repo, "git", "rev-parse", "--abbrev-ref", "HEAD");
            String status = run(repo, "git", "status", "--porcelain");
            // "Dirty" should mean *tracked* changes only — untracked cruft
            // (?? lines: target/, generated zips, .DS_Store) shouldn't flag
            // a working tree as dirty.
            boolean dirty = status.lines()
                    .anyMatch(line -> !line.isBlank() && !line.startsWith("??"));
            return new GitInfo(shortSha, fullSha, branch, dirty);
        } catch (Exception e) {
            return MISSING;
        }
    }

    /**
     * Returns the set of git-tracked files in {@code repo} as absolute paths,
     * or {@code null} if the directory isn't a git working tree, {@code git}
     * isn't on PATH, or the command fails. Caller treats {@code null} as
     * "no filter" — keep every file the walker found.
     */
    public static Set<Path> trackedFiles(Path repo) {
        if (!Files.isDirectory(repo.resolve(".git")) && !Files.isRegularFile(repo.resolve(".git"))) {
            return null;
        }
        try {
            ProcessBuilder pb = new ProcessBuilder("git", "ls-files", "-z")
                    .directory(repo.toFile()).redirectErrorStream(false);
            Process p = pb.start();
            byte[] out = p.getInputStream().readAllBytes();
            if (!p.waitFor(15, TimeUnit.SECONDS)) {
                p.destroyForcibly();
                return null;
            }
            if (p.exitValue() != 0) return null;
            Set<Path> tracked = new HashSet<>();
            int start = 0;
            for (int i = 0; i < out.length; i++) {
                if (out[i] == 0) {
                    if (i > start) {
                        String rel = new String(out, start, i - start);
                        tracked.add(repo.resolve(rel).normalize().toAbsolutePath());
                    }
                    start = i + 1;
                }
            }
            return tracked;
        } catch (Exception e) {
            return null;
        }
    }

    private static String run(Path cwd, String... cmd) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(cmd).directory(cwd.toFile()).redirectErrorStream(true);
        Process p = pb.start();
        String out = new String(p.getInputStream().readAllBytes()).trim();
        if (!p.waitFor(5, TimeUnit.SECONDS)) {
            p.destroyForcibly();
            return "";
        }
        if (p.exitValue() != 0) return "";
        return out;
    }
}
