package wtf.choco.relics.utils;

import com.google.common.base.Preconditions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class SoundData {

    private final Sound sound;
    private final float volume, pitch;

    private SoundData(Sound sound, float volume, float pitch) {
        Preconditions.checkArgument(sound != null, "Sound data must not have null sound");

        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Location location) {
        Preconditions.checkArgument(location.getWorld() != null, "Attempted to play sound for null world");
        Preconditions.checkArgument(location != null, "Cannot play sound at null location");

        location.getWorld().playSound(location, sound, volume, pitch);
    }

    public void play(Player player, Location location) {
        Preconditions.checkArgument(player != null, "Attempted to play sound for null player");
        Preconditions.checkArgument(location != null, "Cannot play sound at null location");

        player.playSound(location, sound, volume, pitch);
    }

    public void play(Player player) {
        Preconditions.checkArgument(player != null, "Attempted to play sound for null player");
        this.play(player, player.getLocation());
    }

    public static SoundData of(Sound sound, float volume, float pitch) {
        return new SoundData(sound, volume, pitch);
    }

    public static SoundData of(Sound sound, float pitch) {
        return new SoundData(sound, 1.0F, pitch);
    }

    public static SoundData of(Sound sound) {
        return new SoundData(sound, 1.0F, 1.0F);
    }

}
