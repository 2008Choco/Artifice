package wtf.choco.relics.artifacts;

/**
 * Represents the different types of discoverable artifacts
 *
 * @author Parker Hawke - Choco
 */
public enum ArtifactType {

    REGULAR("Regular"),
    FOSSILIZED("Fossilized"),
    CORRUPTED("Corrupted"),
    NECROTIC("Necrotic");

    // TODO ANCIENT - Re-add with the capability to discover them in dungeon chests


    private String name;

    private ArtifactType(String name) {
        this.name = name;
    }

    /**
     * Get the friendly name for this artifact type
     *
     * @return the friendly name
     */
    public String getName() {
        return name;
    }

    /**
     * Get an artifact type according to its name.
     *
     * @param name the artifact type name (case insensitive)
     *
     * @return the artifact type. null if non-existent
     */
    public static ArtifactType getByName(String name) {
        for (ArtifactType type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }

        return null;
    }
}
