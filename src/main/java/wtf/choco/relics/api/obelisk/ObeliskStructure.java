package wtf.choco.relics.api.obelisk;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/**
 * Represents a structure pattern for an obelisk. Each structure pattern is composed of 3
 * different axis of materials which correspond to a relative coordinate in the world.
 * This class is similar to the construction of a {@link ShapedRecipe}
 *
 * @author Parker Hawke - 2008Choco
 */
public class ObeliskStructure {

    private Material[][][] materials; /* [x][y][z] */
    private Material formationMaterial;
    private int xFormationOffset = 0, yFormationOffset = 0, zFormationOffset = 0;
    private boolean strict = true;

    private final int sizeX, sizeY, sizeZ;

    private ObeliskStructure(int sizeX, int sizeY, int sizeZ, Material[][][] materials) {
        this.materials = materials;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        for (Material[][] iHateDoing : materials) {
            for (Material[] thingsLikeThis : iHateDoing) {
                Arrays.fill(thingsLikeThis, Material.AIR); // This is stupid >:/
            }
        }
    }

    /**
     * Create a new obelisk structure to be detected
     *
     * @param sizeX the size of the structure along the x axis
     * @param sizeY the size of the structure along the y axis
     * @param sizeZ the size of the structure along the z axis
     * @param clazz the resulting obelisk type for this structure pattern
     */
    private ObeliskStructure(int sizeX, int sizeY, int sizeZ) {
        this(sizeX, sizeY, sizeZ, new Material[sizeX][sizeY][sizeZ]);
    }

    /**
     * Set the relative coordinates that must be clicked by a player in order to complete
     * construction of the obelisk
     *
     * @param x the relative x coordinate
     * @param y the relative y coordinate
     * @param z the relative z coordinate
     *
     * @return this instance. Allows for chained calls
     */
    public ObeliskStructure formationPoint(int x, int y, int z) {
        Preconditions.checkArgument(x >= 0 && x < getSizeX(), "x bound out of range. Got %d, expected 0 - %d", x, getSizeX());
        Preconditions.checkArgument(y >= 0 && y < getSizeY(), "y bound out of range. Got %d, expected 0 - %d", x, getSizeY());
        Preconditions.checkArgument(z >= 0 && z < getSizeZ(), "z bound out of range. Got %d, expected 0 - %d", x, getSizeZ());

        this.formationMaterial = materials[x][y][z];
        this.xFormationOffset = x;
        this.yFormationOffset = y;
        this.zFormationOffset = z;

        return this;
    }

    /**
     * Get the material representing the formation
     *
     * @return the formation material
     */
    public Material getFormationMaterial() {
        return formationMaterial;
    }

    /**
     * Get the relative x coordinate in which a formation will be made
     *
     * @return the x formation index
     */
    public int getFormationX() {
        return xFormationOffset;
    }

    /**
     * Get the relative y coordinate in which a formation will be made
     *
     * @return the y formation index
     */
    public int getFormationY() {
        return yFormationOffset;
    }

    /**
     * Get the relative z coordinate in which a formation will be made
     *
     * @return the z formation index
     */
    public int getFormationZ() {
        return zFormationOffset;
    }

    public Vector getFormationVector() {
        return new Vector(xFormationOffset, yFormationOffset, zFormationOffset);
    }

    public Block getFormationBlock(World world, int x, int y, int z) {
        return world.getBlockAt(x + xFormationOffset, y + yFormationOffset, z + zFormationOffset);
    }

    public Block getFormationBlock(World world, Vector position) {
        return getFormationBlock(world, position.getBlockX(), position.getBlockY(), position.getBlockZ());
    }

    public Block getFormationBlock(ObeliskState obelisk) {
        return getFormationBlock(obelisk.getWorld(), obelisk.getBounds().getMin());
    }

    public Block getFormationBlock(Location location) {
        return getFormationBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Set a relative position's material
     *
     * @param xPos the relative x position
     * @param yPos the relative y position
     * @param zPos the relative z position
     * @param material the material to set
     *
     * @return this instance. Allows for chained calls
     */
    public ObeliskStructure set(int xPos, int yPos, int zPos, Material material) {
        materials[xPos][yPos][zPos] = material;
        return this;
    }

    public Material get(int xPos, int yPos, int zPos) {
        return materials[xPos][yPos][zPos];
    }

    /**
     * Get the underlying block formation
     *
     * @return the block formation
     */
    public Material[][][] getBlockFormation() {
        return Arrays.copyOf(materials, materials.length);
    }

    public ObeliskStructure setNotStrict() {
        this.strict = false;
        return this;
    }

    public boolean isStrict() {
        return strict;
    }

    /**
     * Get the length of this obelisk structure (x axis)
     *
     * @return the structure length
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Get the height of this obelisk structure (y axis)
     *
     * @return the structure height
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Get the width of this obelisk structure (z axis)
     *
     * @return the structure width
     */
    public int getSizeZ() {
        return sizeZ;
    }

    public BoundingBox asBoundsFrom(Location min) {
        int x = min.getBlockX(), y = min.getBlockY(), z = min.getBlockZ();
        return new BoundingBox(x, y, z, x + sizeX, y + sizeY, z + sizeZ);
    }

    public boolean matches(Location from) {
        Preconditions.checkArgument(from != null, "Attempted to match against null \"from\" location");

        int x = from.getBlockX();
        int y = from.getBlockY();
        int z = from.getBlockZ();

        return matches(from.getWorld(), x, y, z);
    }

    public boolean matches(World world, int x, int y, int z) {
        Preconditions.checkArgument(world != null, "Cannot match against null world");

        for (int localX = 0; localX < sizeX; localX++) {
            for (int localY = 0; localY < sizeY; localY++) {
                for (int localZ = 0; localZ < sizeZ; localZ++) {
                    Material type = world.getBlockAt(x + localX, y + localY, z + localZ).getType();
                    Material obeliskType = materials[localX][localY][localZ];

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

    public static ObeliskStructure withSize(int sizeX, int sizeY, int sizeZ) {
        return new ObeliskStructure(sizeX, sizeY, sizeZ);
    }

}
