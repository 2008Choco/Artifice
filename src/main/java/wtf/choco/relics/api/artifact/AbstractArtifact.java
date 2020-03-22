package wtf.choco.relics.api.artifact;

import org.bukkit.NamespacedKey;

import wtf.choco.relics.artifacts.ArtifactType;
import wtf.choco.relics.artifacts.Rarity;

public abstract class AbstractArtifact implements Artifact {

    private final NamespacedKey key;
    private final Rarity rarity;
    private final ArtifactType type;

    protected AbstractArtifact(NamespacedKey key, Rarity rarity, ArtifactType type) {
        this.key = key;
        this.rarity = rarity;
        this.type = type;
    }

    @Override
    public final NamespacedKey getKey() {
        return key;
    }

    @Override
    public final Rarity getRarity() {
        return rarity;
    }

    @Override
    public final ArtifactType getType() {
        return type;
    }

    @Override
    public String getName() {
        return key.getKey();
    }

}
