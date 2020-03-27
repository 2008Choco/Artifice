package wtf.choco.relics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import wtf.choco.relics.api.ArtifactManager;
import wtf.choco.relics.api.ObeliskManager;
import wtf.choco.relics.api.QualityRegistry;
import wtf.choco.relics.artifacts.corrupted.PoisonedArtifact;
import wtf.choco.relics.artifacts.fossilized.TestArtifact;
import wtf.choco.relics.artifacts.necrotic.DevilsStaff;
import wtf.choco.relics.command.ArtifactCmd;
import wtf.choco.relics.command.RelicsCmd;
import wtf.choco.relics.listeners.artifact.ArtifactDroppedListener;
import wtf.choco.relics.listeners.artifact.ArtifactProtectionListener;
import wtf.choco.relics.listeners.discovery.ArtifactCorruptedLootListener;
import wtf.choco.relics.listeners.discovery.ArtifactFossilizedLootListener;
import wtf.choco.relics.listeners.discovery.ArtifactNecroticLootListener;
import wtf.choco.relics.listeners.obelisk.ObeliskEngraveListener;
import wtf.choco.relics.listeners.obelisk.ObeliskProtectionListener;
import wtf.choco.relics.listeners.obelisk.ObeliskDebugQualityListener;
import wtf.choco.relics.listeners.obelisk.StructureDetectionListener;
import wtf.choco.relics.obelisk.TestObelisk;
import wtf.choco.relics.obelisk.TestTwoObelisk;
import wtf.choco.relics.obelisk.qualities.ObeliskQualities;
import wtf.choco.relics.runnable.ArtifactEffectRunnable;
import wtf.choco.relics.runnable.AsyncObeliskFormationHint;
import wtf.choco.relics.runnable.ObeliskEffectRunnable;

public class Relics extends JavaPlugin {

    public static final String[] GLOBAL_FORMATION_MESSAGES = {
            "A strange aura seems to empower and surround the structure",
            "The structure seems to start shaking as it illuminates dimly",
            "As you step away from the structures, you hear silent whispers"
    };

    private static Relics instance;
    private ObeliskManager obeliskManager;
    private ArtifactManager artifactManager;
    private QualityRegistry qualityRegistry;

    private ObeliskEffectRunnable obeliskEffectLoop;
    private ArtifactEffectRunnable artifactEffectLoop;
    private AsyncObeliskFormationHint formationHint;

    @Override
    public void onEnable() {
        instance = this;
        this.artifactManager = new ArtifactManager();
        this.obeliskManager = new ObeliskManager();
        this.qualityRegistry = new QualityRegistry();

        // Register events
        this.getLogger().info("Registering event listeners");
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new StructureDetectionListener(this), this);
        pluginManager.registerEvents(new ObeliskProtectionListener(this), this);
        pluginManager.registerEvents(new ArtifactProtectionListener(this), this);

        pluginManager.registerEvents(new ArtifactFossilizedLootListener(this), this);
        pluginManager.registerEvents(new ArtifactNecroticLootListener(this), this);
        pluginManager.registerEvents(new ArtifactCorruptedLootListener(this), this);
        pluginManager.registerEvents(new ArtifactDroppedListener(this), this);

        pluginManager.registerEvents(new ObeliskEngraveListener(this), this);
        pluginManager.registerEvents(new ObeliskDebugQualityListener(this), this);

        // Register artifacts
        this.getLogger().info("Registering core artifacts");
        this.artifactManager.registerArtifact(new TestArtifact(this));
        this.artifactManager.registerArtifact(new PoisonedArtifact(this));
        this.artifactManager.registerArtifact(new DevilsStaff(this));

        // Register qualities
        this.getLogger().info("Registering core obelisk qualities");
        this.qualityRegistry.registerQuality(ObeliskQualities.DEBUG_QUALITY);

        // Register obelisks
        this.getLogger().info("Registering core obelisks");
        this.obeliskManager.registerObelisk(new TestObelisk(this));
        this.obeliskManager.registerObelisk(new TestTwoObelisk(this));

        // Register commands
        this.getLogger().info("Registering commands");
        this.getCommand("relics").setExecutor(new RelicsCmd(this));
        this.getCommand("artifact").setExecutor(new ArtifactCmd(this));

        // Commence effect loops
        this.obeliskEffectLoop = ObeliskEffectRunnable.runTask(this);
        this.artifactEffectLoop = ArtifactEffectRunnable.runTask(this);
        this.formationHint = AsyncObeliskFormationHint.runTask(this);

        // TODO: Load obelisks from file
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Saving obelisk information to file");
        // TODO: Write obelisks to file

        this.getLogger().info("Clearing local relic registration information");
        this.obeliskManager.clear();
        this.qualityRegistry.clear();
        this.artifactManager.clearArtifacts();

        this.getLogger().info("Cancelling obelisk and artifact effect loops");
        this.obeliskEffectLoop.cancel();
        this.artifactEffectLoop.cancel();
        this.artifactEffectLoop.clearArtifactEntities();
        this.formationHint.cancel();
    }

    /**
     * Get the singleton instance of this plugin.
     *
     * @return this instance
     */
    public static Relics getPlugin() {
        return instance;
    }

    /**
     * Get the plugin's {@link ObeliskManager}.
     *
     * @return the obelisk manager
     */
    public ObeliskManager getObeliskManager() {
        return obeliskManager;
    }

    /**
     * Get the plugin's {@link ArtifactManager}.
     *
     * @return the artifact manager
     */
    public ArtifactManager getArtifactManager() {
        return artifactManager;
    }

    /**
     * Get the plugin's {@link QualityRegistry}.
     *
     * @return the quality registry
     */
    public QualityRegistry getQualityRegistry() {
        return qualityRegistry;
    }

    /**
     * Get this plugin's {@link ArtifactEffectRunnable}.
     *
     * @return the artifact effect loop
     */
    public ArtifactEffectRunnable getArtifactEffectLoop() {
        return artifactEffectLoop;
    }

    /**
     * Send a message to a user with the Relics prefix.
     *
     * @param sender the user to which to send a message
     * @param message the message to send
     */
    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Relics" + ChatColor.GRAY + "] " + message);
    }

}
