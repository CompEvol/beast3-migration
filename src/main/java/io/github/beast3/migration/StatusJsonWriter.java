package io.github.beast3.migration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Tiny hand-rolled JSON serializer for the status report. Avoids dragging in
 * Jackson for what amounts to a small object tree.
 */
public final class StatusJsonWriter {

    private StatusJsonWriter() {}

    public static void write(Path file, List<Report> reports) throws IOException {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("generatedAt", java.time.OffsetDateTime.now().toString());
        java.util.List<Object> arr = new java.util.ArrayList<>();
        for (Report r : reports) arr.add(r.toJsonLike());
        root.put("packages", arr);

        StringBuilder sb = new StringBuilder();
        encode(root, sb, 0);
        Files.writeString(file, sb.toString());
    }

    private static void encode(Object value, StringBuilder sb, int indent) {
        if (value == null) { sb.append("null"); return; }
        if (value instanceof Map<?, ?> m) {
            sb.append("{");
            if (m.isEmpty()) { sb.append("}"); return; }
            sb.append('\n');
            int i = 0;
            int size = m.size();
            for (Map.Entry<?, ?> e : m.entrySet()) {
                pad(sb, indent + 1);
                sb.append('"').append(escape(e.getKey().toString())).append("\": ");
                encode(e.getValue(), sb, indent + 1);
                if (++i < size) sb.append(',');
                sb.append('\n');
            }
            pad(sb, indent);
            sb.append('}');
            return;
        }
        if (value instanceof List<?> l) {
            sb.append('[');
            if (l.isEmpty()) { sb.append(']'); return; }
            sb.append('\n');
            for (int i = 0; i < l.size(); i++) {
                pad(sb, indent + 1);
                encode(l.get(i), sb, indent + 1);
                if (i + 1 < l.size()) sb.append(',');
                sb.append('\n');
            }
            pad(sb, indent);
            sb.append(']');
            return;
        }
        if (value instanceof Boolean || value instanceof Number) {
            sb.append(value.toString());
            return;
        }
        sb.append('"').append(escape(value.toString())).append('"');
    }

    private static void pad(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) sb.append("  ");
    }

    private static String escape(String s) {
        StringBuilder out = new StringBuilder(s.length() + 4);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\' -> out.append("\\\\");
                case '"' -> out.append("\\\"");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default -> {
                    if (c < 0x20) out.append(String.format("\\u%04x", (int) c));
                    else out.append(c);
                }
            }
        }
        return out.toString();
    }
}
