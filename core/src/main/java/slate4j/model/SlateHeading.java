package slate4j.model;

import java.util.List;

/**
 * The {@code SlateHeading} class.
 */
public class SlateHeading {

    /**
     * The {@code level} field.
     */
    public final int level;
    /**
     * The {@code id} field.
     */
    public final String id;
    /**
     * The {@code name} field.
     */
    public final String name;
    /**
     * The {@code subHeadings} field.
     */
    public final List<SlateHeading> subHeadings;

    /**
     * Creates a new instance.
     *
     * @param level the level
     * @param id the id
     * @param name the name
     * @param subHeadings the sub headings
     */
    public SlateHeading(final int level, final String id, final String name, final List<SlateHeading> subHeadings) {
        this.level = level;
        this.id = id;
        this.name = name;
        this.subHeadings = subHeadings;
    }
}
