package wtf.choco.relics.api.obelisk;

import java.util.EnumMap;
import java.util.Map;

import com.google.common.base.Preconditions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/**
 * Represents a structure pattern for an obelisk. Each structure pattern is composed of 3
 * different axis of materials which correspond to a relative coordinate in the world.
 *
 * @author Parker Hawke - Choco
 */
public class ObeliskStructure {

    private Material[][][] materials; /* [x][y][z] */
    private Material formationMaterial;
    private int xFormationOffset = 0, yFormationOffset = 0, zFormationOffset = 0;

    private final int sizeX, sizeY, sizeZ;

    private ObeliskStructure(int sizeX, int sizeY, int sizeZ, Material[][][] materials) {
        Preconditions.checkArgument(sizeX > 0, "x size must be > 0 (got %s)", sizeX);
        Preconditions.checkArgument(sizeY > 0, "y size must be > 0 (got %s)", sizeY);
        Preconditions.checkArgument(sizeZ > 0, "z size must be > 0 (got %s)", sizeZ);

        this.materials = materials;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        for (Material[][] iHateDoing : materials) {
            for (Material[] thingsLikeThis : iHateDoing) {
                for (int i = 0; i < thingsLikeThis.length; i++) {
                    if (thingsLikeThis[i] == null) {
                        thingsLikeThis[i] = Material.AIR;
                    }
                }
            }
        }
    }

    private ObeliskStructure(int sizeX, int sizeY, int sizeZ) {
        this(sizeX, sizeY, sizeZ, new Material[sizeX][sizeY][sizeZ]);
    }

    /**
     * Set the relative coordinates that must be clicked by a player in order to complete
     * construction of the obelisk.
     *
     * @param x the relative x coordinate
     * @param y the relative y coordinate
     * @param z the relative z coordinate
     *
     * @return this instance. Allows for chained calls
     */
    public ObeliskStructure formationPoint(int x, int y, int z) {
        Preconditions.checkArgument(x >= 0 && x < getSizeX(), "x bound out of range. Got %s, expected 0 - %s (exclusive)", x, getSizeX());
        Preconditions.checkArgument(y >= 0 && y < getSizeY(), "y bound out of range. Got %s, expected 0 - %s (exclusive)", x, getSizeY());
        Preconditions.checkArgument(z >= 0 && z < getSizeZ(), "z bound out of range. Got %s, expected 0 - %s (exclusive)", x, getSizeZ());

        this.formationMaterial = get(x, y, z);
        this.xFormationOffset = x;
        this.yFormationOffset = y;
        this.zFormationOffset = z;

        return this;
    }

    /**
     * Get the material at the formation point for this structure.
     *
     * @return the formation material
     */
    public Material getFormationMaterial() {
        return formationMaterial;
    }

    /**
     * Get the relative x coordinate for the formation block for this structure.
     *
     * @return the x formation index
     */
    public int getFormationX() {
        return xFormationOffset;
    }

    /**
     * Get the relative y coordinate for the formation block for this structure.
     *
     * @return the y formation index
     */
    public int getFormationY() {
        return yFormationOffset;
    }

    /**
     * Get the relative z coordinate for the formation block for this structure.
     *
     * @return the z formation index
     */
    public int getFormationZ() {
        return zFormationOffset;
    }

    /**
     * Get the relative formation point for this structure. This is a 3D vector representation of
     * all formation offsets.
     *
     * @return the formation vector
     */
    public Vector getFormationVector() {
        return new Vector(xFormationOffset, yFormationOffset, zFormationOffset);
    }

    /**
     * Get the formation block for this obelisk structure relative to the given world and coordinates.
     *
     * @param world the world
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     *
     * @return the formation block
     */
    public Block getFormationBlock(World world, int x, int y, int z) {
        return world.getBlockAt(x + xFormationOffset, y + yFormationOffset, z + zFormationOffset);
    }

    /**
     * Get the formation block for this obelisk structure relative to the given world and coordinates.
     *
     * @param world the world
     * @param position the position in the world
     *
     * @return the formation block
     */
    public Block getFormationBlock(World world, Vector position) {
        return getFormationBlock(world, position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    /**
     * Get the formation block for this obelisk structure for the given obelisk state.
     *
     * @param obelisk the obelisk state for which to get the formation block
     *
     * @return the formation block
     */
    public Block getFormationBlock(ObeliskState obelisk) {
        return getFormationBlock(obelisk.getWorld(), obelisk.getBounds().getMin());
    }

    /**
     * Get the formation block for this obelisk structure relative to the given location.
     *
     * @param location the location
     *
     * @return the formation block
     */
    public Block getFormationBlock(Location location) {
        return getFormationBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Set a relative position's material.
     *
     * @param x the relative x coordinate
     * @param y the relative y coordinate
     * @param z the relative z coordinate
     * @param material the material to set
     *
     * @return this instance. Allows for chained calls
     */
    public ObeliskStructure set(int x, int y, int z, Material material) {
        this.materials[x][y][z] = material;
        return this;
    }

    /**
     * Get the relative position's material.
     *
     * @param x the relative x coordinate
     * @param y the relative y coordinate
     * @param z the relative z coordinate
     *
     * @return the material at the given indices
     */
    public Material get(int x, int y, int z) {
        return materials[x][y][z];
    }

    /**
     * Create a Map of {@link StructureRotation} to {@link ObeliskStructure} instances. Rotated structures
     * are automatically calculated. This is a convenience method to more easily implement
     * {@link Obelisk#getStructure(StructureRotation)}.
     *
     * @return the rotation map
     */
    public Map<StructureRotation, ObeliskStructure> compileRotationMap() {
        Map<StructureRotation, ObeliskStructure> structures = new EnumMap<>(StructureRotation.class);
        for (StructureRotation rotation : StructureRotation.values()) {
            structures.put(rotation, rotated(rotation));
        }

        return structures;
    }

    /**
     * Get the length of this obelisk structure (x axis).
     *
     * @return the structure length
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Get the height of this obelisk structure (y axis).
     *
     * @return the structure height
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Get the width of this obelisk structure (z axis).
     *
     * @return the structure width
     */
    public int getSizeZ() {
        return sizeZ;
    }

    /**
     * Create a {@link BoundingBox} relative to the given location.
     *
     * @param min the minimum location at which to create a bounding box.
     *
     * @return the created bounds
     */
    public BoundingBox asBoundsFrom(Location min) {
        int x = min.getBlockX(), y = min.getBlockY(), z = min.getBlockZ();
        return new BoundingBox(x, y, z, x + sizeX, y + sizeY, z + sizeZ);
    }

    /**
     * Check whether the location (representing the minimum location) matches this obelisk structure.
     *
     * @param from the location from which to start structure matching
     * @param strict whether to strictly match the structure blocks (including air). If true, air will
     * be considered a mandatory block.
     *
     * @return true if matches, false otherwise
     */
    public boolean matches(Location from, boolean strict) {
        Preconditions.checkArgument(from != null, "Attempted to match against null \"from\" location");

        int x = from.getBlockX();
        int y = from.getBlockY();
        int z = from.getBlockZ();

        return matches(from.getWorld(), x, y, z, strict);
    }

    /**
     * Check whether the world and coordinates (representing the minimum position) matches this obelisk structure.
     *
     * @param world the world
     * @param x the minimum x coordinate
     * @param y the minimum y coordinate
     * @param z the minimum z coordinate
     * @param strict whether to strictly match the structure blocks (including air). If true, air will
     * be considered a mandatory block.
     *
     * @return true if matches, false otherwise
     */
    public boolean matches(World world, int x, int y, int z, boolean strict) {
        Preconditions.checkArgument(world != null, "Cannot match against null world");

        if (getFormationBlock(world, x, y, z).getType() != formationMaterial) {
            return false;
        }

        for (int localX = 0; localX < sizeX; localX++) {
            for (int localY = 0; localY < sizeY; localY++) {
                for (int localZ = 0; localZ < sizeZ; localZ++) {
                    Material type = world.getBlockAt(x + localX, y + localY, z + localZ).getType();
                    Material obeliskType = get(localX, localY, localZ);

                    if (obeliskType.isAir() && !strict) {
                        continue;
                    }

                    if (type != obeliskType) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private ObeliskStructure rotated(StructureRotation rotation) {
        if (rotation == StructureRotation.NONE || rotation == null) {
            return this;
        }

        Material[][][] materials = null;
        int newSizeX = -1, newSizeZ = -1;
        int newFormationOffsetX = -1, newFormationOffsetZ = -1;

        if (rotation == StructureRotation.CLOCKWISE_90) { // 90 degrees
            newSizeX = sizeZ;
            newSizeZ = sizeX;
            newFormationOffsetX = zFormationOffset;
            newFormationOffsetZ = sizeX - xFormationOffset - 1;
            materials = new Material[newSizeX][sizeY][newSizeZ];

            for (int x = 0; x < newSizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    for (int z = 0; z < newSizeZ; z++) {
                        materials[x][y][newSizeZ - z - 1] = get(z, y, x);
                    }
                }
            }
        }

        else if (rotation == StructureRotation.CLOCKWISE_180) { // 180 degrees
            newSizeX = sizeX;
            newSizeZ = sizeZ;
            newFormationOffsetX = sizeX - xFormationOffset - 1;
            newFormationOffsetZ = sizeZ - zFormationOffset - 1;
            materials = new Material[newSizeX][sizeY][newSizeZ];

            for (int x = 0; x < newSizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    for (int z = 0; z < newSizeZ; z++) {
                        materials[newSizeX - x - 1][y][newSizeZ - z - 1] = get(x, y, z);
                    }
                }
            }
        }

        else if (rotation == StructureRotation.COUNTERCLOCKWISE_90) { // 270 degrees
            newSizeX = sizeZ;
            newSizeZ = sizeX;
            newFormationOffsetX = sizeZ - zFormationOffset - 1;
            newFormationOffsetZ = xFormationOffset;
            materials = new Material[newSizeX][sizeY][newSizeZ];

            for (int x = 0; x < newSizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    for (int z = 0; z < newSizeZ; z++) {
                        materials[newSizeX - x - 1][y][z] = get(z, y, x);
                    }
                }
            }
        }

        if (materials == null || newSizeX <= 0 || newSizeZ <= 0 || newFormationOffsetX < 0 || newFormationOffsetZ < 0) {
            throw new UnsupportedOperationException("Unsupported obelisk structure rotation");
        }

        return new ObeliskStructure(newSizeX, sizeY, newSizeZ, materials).formationPoint(newFormationOffsetX, yFormationOffset, newFormationOffsetZ);
    }

    /**
     * Create an {@link ObeliskStructure} with the given size.
     *
     * @param sizeX the size of the structure along the x axis
     * @param sizeY the size of the structure along the y axis
     * @param sizeZ the size of the structure along the z axis
     *
     * @return the created {@link ObeliskStructure}
     */
    public static ObeliskStructure withSize(int sizeX, int sizeY, int sizeZ) {
        return new ObeliskStructure(sizeX, sizeY, sizeZ);
    }

}
