package slate4j.model;

import java.util.List;

public class SlateHeading {

    public final int level;
    public final String id;
    public final String name;
    public final List<SlateHeading> subHeadings;

    public SlateHeading(final int level, final String id, final String name, final List<SlateHeading> subHeadings) {
        this.level = level;
        this.id = id;
        this.name = name;
        this.subHeadings = subHeadings;
    }
}
