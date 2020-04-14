package wtf.choco.artifice.obelisk.qualities;

import java.util.Arrays;

import wtf.choco.artifice.Artifice;
import wtf.choco.artifice.api.obelisk.ObeliskQuality;
import wtf.choco.artifice.api.obelisk.SimpleObeliskQuality;

public final class ObeliskQualities {

    public static final ObeliskQuality DEBUG_QUALITY = new SimpleObeliskQuality(Artifice.getPlugin(), "debug_quality", "Debug Quality", Arrays.asList(
            "This is a debug quality to see if the qualities work",
            "It will later be removed and replaced by proper qualities",
            "",
            "This quality will list all qualities present on an obelisk"
    ));

    private ObeliskQualities() { }

}
