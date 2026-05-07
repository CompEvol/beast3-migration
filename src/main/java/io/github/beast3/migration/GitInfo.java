package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
