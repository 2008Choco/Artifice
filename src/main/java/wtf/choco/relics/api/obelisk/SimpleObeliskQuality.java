package wtf.choco.relics.api.obelisk;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleObeliskQuality implements ObeliskQuality {

    private Predicate<ObeliskQuality> conflictPredicate;

    private final JavaPlugin plugin;
    private final NamespacedKey key;
    private final String name;
    private final List<String> description;

    public SimpleObeliskQuality(JavaPlugin plugin, String key, String name, List<String> description, Predicate<ObeliskQuality> conflictPredicate) {
        this.plugin = plugin;
        this.key = new NamespacedKey(plugin, key.toLowerCase());
        this.name = name;
        this.description = (description != null) ? ImmutableList.copyOf(description) : Collections.EMPTY_LIST;
        this.conflictPredicate = conflictPredicate;
    }

    public SimpleObeliskQuality(JavaPlugin plugin, String key, String name, List<String> description) {
        this(plugin, key, name, description, null);
    }

    public SimpleObeliskQuality(JavaPlugin plugin, String key, String name) {
        this(plugin, key, name, null, null);
    }

    public SimpleObeliskQuality(JavaPlugin plugin, String key) {
        this(plugin, key, key, null, null);
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getDescription() {
        return description;
    }

    @Override
    public JavaPlugin getProvidingPlugin() {
        return plugin;
    }

    @Override
    public boolean conflictsWith(ObeliskQuality quality) {
        return conflictPredicate == null || conflictPredicate.test(quality);
    }

}
