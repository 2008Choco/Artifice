package wtf.choco.relics.utils;

import com.google.common.base.Preconditions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * A utility class to act as a container for sound data which may be played to a
 * player or in the world.
 *
 * @author Parker Hawke - Choco
 */
public final class SoundData {

    private final Sound sound;
    private final float volume, pitch;

    private SoundData(Sound sound, float volume, float pitch) {
        Preconditions.checkArgument(sound != null, "Sound data must not have null sound");

        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    /**
     * Play this sound data to all players within range of the specified location.
     *
     * @param location the location at which to play the sound
     *
     * @see World#playSound(Location, Sound, float, float)
     */
    public void play(Location location) {
        Preconditions.checkArgument(location.getWorld() != null, "Attempted to play sound for null world");
        Preconditions.checkArgument(location != null, "Cannot play sound at null location");

        location.getWorld().playSound(location, sound, volume, pitch);
    }

    /**
     * Play this sound data to the specified player sourcing from the specified location.
     *
     * @param player the player for which to play the sound
     * @param location the location at which to play the sound
     *
     * @see Player#playSound(Location, Sound, float, float)
     */
    public void play(Player player, Location location) {
        Preconditions.checkArgument(player != null, "Attempted to play sound for null player");
        Preconditions.checkArgument(location != null, "Cannot play sound at null location");

        player.playSound(location, sound, volume, pitch);
    }

    /**
     * Play this sound data to the specified player.
     *
     * @param player the player for which to play the sound
     *
     * @see Player#playSound(Location, Sound, float, float)
     */
    public void play(Player player) {
        Preconditions.checkArgument(player != null, "Attempted to play sound for null player");
        this.play(player, player.getLocation());
    }

    /**
     * Create a SoundData instance.
     *
     * @param sound the sound to play
     * @param volume the sound volume
     * @param pitch the sound pitch
     *
     * @return the created sound data
     */
    public static SoundData of(Sound sound, float volume, float pitch) {
        return new SoundData(sound, volume, pitch);
    }

    /**
     * Create a SoundData instance at default volume (1.0).
     *
     * @param sound the sound to play
     * @param pitch the sound pitch
     *
     * @return the created sound data
     */
    public static SoundData of(Sound sound, float pitch) {
        return new SoundData(sound, 1.0F, pitch);
    }

    /**
     * Create a SoundData instance at default volume and pitch (1.0).
     *
     * @param sound the sound to play
     *
     * @return the created sound data
     */
    public static SoundData of(Sound sound) {
        return new SoundData(sound, 1.0F, 1.0F);
    }

}
