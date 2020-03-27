package wtf.choco.relics.api.obelisk;

import java.util.List;

import org.bukkit.Keyed;
import org.bukkit.plugin.java.JavaPlugin;

public interface ObeliskQuality extends Keyed {

    public String getName();

    public List<String> getDescription();

    public JavaPlugin getProvidingPlugin();

    public default boolean conflictsWith(ObeliskQuality quality) {
        return false;
    }

}
