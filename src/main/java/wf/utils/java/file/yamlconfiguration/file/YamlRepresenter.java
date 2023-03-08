/* 
 * This file is part of YamlConfiguration.
 * 
 * Implementation of SnakeYAML to be easy to use with files.
 * 
 * Copyright (C) 2010-2014 The Bukkit Project (https://bukkit.org/)
 * Copyright (C) 2014-2022 SpigotMC Pty. Ltd. (https://www.spigotmc.org/)
 * Copyright (C) 2020-2022 BSPF Systems, LLC (https://bspfsystems.org/)
 * 
 * Many of the files in this project are sourced from the Bukkit API as
 * part of The Bukkit Project (https://bukkit.org/), now maintained by
 * SpigotMC Pty. Ltd. (https://www.spigotmc.org/). These files can be found
 * at https://github.com/Bukkit/Bukkit/ and https://hub.spigotmc.org/stash/,
 * respectively.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package wf.utils.java.file.yamlconfiguration.file;

import java.util.LinkedHashMap;
import java.util.Map;
import wf.utils.java.file.yamlconfiguration.configuration.ConfigurationSection;
import wf.utils.java.file.yamlconfiguration.serialization.ConfigurationSerializable;
import wf.utils.java.file.yamlconfiguration.serialization.ConfigurationSerialization;
import wf.utils.jetbrains.annotations.NotNull;
import wf.utils.java.file.yamlconfiguration.snakeyaml.nodes.Node;
import wf.utils.java.file.yamlconfiguration.snakeyaml.representer.Representer;

/**
 * A {@link Representer} that can work with
 * {@link ConfigurationSection ConfigurationSections} and
 * {@link ConfigurationSerializable ConfigurationSerializables}.
 * <p>
 * Synchronized with the commit on 22-April-2022.
 */
public final class YamlRepresenter extends Representer {
    
    /**
     * A {@link RepresentMap} that works with
     * {@link ConfigurationSection ConfigurationSections}.
     */
    private class RepresentConfigurationSection extends RepresentMap {
    
        /**
         * Creates a new {@link RepresentConfigurationSection}.
         * 

         */
        private RepresentConfigurationSection() {
            super();
        }
    
        /**
         * Translates the given {@link Object} (known to be a
         * {@link ConfigurationSection} in this instance) into a {@link Node}.
         * 
         * @param object The {@link Object} to represent.
         * @return The {@link Node} representing the {@link Object}.
         * @see RepresentMap#representData(Object)
         */
        @NotNull
        @Override
        public Node representData(@NotNull final Object object) {
            return super.representData(((ConfigurationSection) object).getValues(false));
        }
    }
    
    /**
     * A {@link RepresentMap} that works with
     * {@link ConfigurationSerializable ConfigurationSerializables}.
     */
    private class RepresentConfigurationSerializable extends RepresentMap {
    
        /**
         * Creates a new {@link RepresentConfigurationSerializable}.
         * 

         */
        private RepresentConfigurationSerializable() {
            super();
        }
    
        /**
         * Translates the given {@link Object} (known to be a
         * {@link ConfigurationSerializable} in this instance) into a
         * {@link Node}.
         *
         * @param object The {@link Object} to represent.
         * @return The {@link Node} representing the {@link Object}.
         * @see RepresentMap#representData(Object)
         */
        @NotNull
        @Override
        public Node representData(@NotNull final Object object) {
            
            final ConfigurationSerializable serializable = (ConfigurationSerializable) object;
            final Map<String, Object> values = new LinkedHashMap<String, Object>();
            
            values.put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
            values.putAll(serializable.serialize());
            
            return super.representData(values);
        }
    }
    
    /**
     * Creates a new {@link YamlRepresenter} that can represent
     * {@link ConfigurationSection ConfigurationSections} and
     * {@link ConfigurationSerializable ConfigurationSerializables}, while
     * disallowing {@link Enum Enums}.
     */
    YamlRepresenter() {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
        this.multiRepresenters.remove(Enum.class);
    }
}
