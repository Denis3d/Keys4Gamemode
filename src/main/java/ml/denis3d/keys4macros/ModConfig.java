package ml.denis3d.keys4macros;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.PreserveNotNull;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModConfig {
    @PreserveNotNull
    @Path("Macros.Macro")
    public List<MacroEntry> macros = new ArrayList<>();
    public transient HashMap<Integer, MacroEntry> macros_map = new HashMap<>();

    public static ModConfig load(File file) {
        Config.setInsertionOrderPreserved(true);

        ObjectConverter converter = new ObjectConverter();
        CommentedFileConfig config = CommentedFileConfig.of(file);
        config.load();

        ModConfig modConfig = converter.toObject(config, ModConfig::new);

        config.clear();
        if (modConfig.macros.isEmpty()) {
            modConfig.macros.add(new MacroEntry());
        }

        for (MacroEntry macro : modConfig.macros) {
            modConfig.macros_map.put(macro.key, macro);
        }


        converter.toConfig(modConfig, config);

        config.setComment("Macros", " Keys4Macros configuration\n" +
                "  - Curseforge : https://www.curseforge.com/minecraft/mc-mods/keys4macros\n" +
                "  - Modrinth : https://modrinth.com/mod/keys4macros\n" +
                "  - Discord : https://discord.gg/rzzd76c\n" +
                "  - Github : https://github.com/DenisD3D/Keys4Macros\n" +
                "\n" +
                " Duplicate the 'macro' block for each macro that you want to create\n" +
                "  - key is a number associated with the key. The list can be found here https://www.glfw.org/docs/3.3/group__keys.html\n" +
                "  - command is the command to execute (you need to put the / too)");

        config.save();

        return modConfig;
    }

    public static class MacroEntry {
        @PreserveNotNull
        public Integer key = 0;
        @PreserveNotNull
        public String command = "";
    }
}
