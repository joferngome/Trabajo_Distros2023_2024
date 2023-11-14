
    // Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import io.quicktype.Converter;
//
// Then you can deserialize a JSON string with
//
//     Welcome data = Converter.fromJsonString(jsonString);


    import com.fasterxml.jackson.core.JsonParser;
    import com.fasterxml.jackson.core.JsonProcessingException;
    import com.fasterxml.jackson.databind.*;
    import com.fasterxml.jackson.databind.module.SimpleModule;

    import java.io.IOException;
    import java.time.OffsetDateTime;
    import java.time.OffsetTime;
    import java.time.ZoneOffset;
    import java.time.ZonedDateTime;
    import java.time.format.DateTimeFormatter;
    import java.time.format.DateTimeFormatterBuilder;
    import java.time.temporal.ChronoField;
    import java.util.Map;

    public class Converter {
        // Date-time helpers

        private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
                .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .appendOptional(DateTimeFormatter.ISO_INSTANT)
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toFormatter()
                .withZone(ZoneOffset.UTC);

        public static OffsetDateTime parseDateTimeString(String str) {
            return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
        }

        private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ISO_TIME)
                .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
                .parseDefaulting(ChronoField.YEAR, 2020)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter()
                .withZone(ZoneOffset.UTC);

        public static OffsetTime parseTimeString(String str) {
            return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
        }
        // Serialize/deserialize helpers

        public static Welcome fromJsonString(String json) throws IOException {
            return getObjectReader().readValue(json);
        }

        public static String toJsonString(Welcome obj) throws JsonProcessingException {
            return getObjectWriter().writeValueAsString(obj);
        }

        private static ObjectReader reader;
        private static ObjectWriter writer;

        private static void instantiateMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
                @Override
                public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                    String value = jsonParser.getText();
                    return Converter.parseDateTimeString(value);
                }
            });
            mapper.registerModule(module);
            reader = mapper.readerFor(Welcome.class);
            writer = mapper.writerFor(Welcome.class);
        }

        private static ObjectReader getObjectReader() {
            if (reader == null) instantiateMapper();
            return reader;
        }

        private static ObjectWriter getObjectWriter() {
            if (writer == null) instantiateMapper();
            return writer;
        }
    }

// Welcome.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Welcome {
        private Ability[] abilities;
        private long baseExperience;
        private Species[] forms;
        private GameIndex[] gameIndices;
        private long height;
        private HeldItem[] heldItems;
        private long id;
        private boolean isDefault;
        private String locationAreaEncounters;
        private Move[] moves;
        private String name;
        private long order;
        private Object[] pastAbilities;
        private Object[] pastTypes;
        private Species species;
        private Sprites sprites;
        private Stat[] stats;
        private Type[] types;
        private long weight;

        @JsonProperty("abilities")
        public Ability[] getAbilities() { return abilities; }
        @JsonProperty("abilities")
        public void setAbilities(Ability[] value) { this.abilities = value; }

        @JsonProperty("base_experience")
        public long getBaseExperience() { return baseExperience; }
        @JsonProperty("base_experience")
        public void setBaseExperience(long value) { this.baseExperience = value; }

        @JsonProperty("forms")
        public Species[] getForms() { return forms; }
        @JsonProperty("forms")
        public void setForms(Species[] value) { this.forms = value; }

        @JsonProperty("game_indices")
        public GameIndex[] getGameIndices() { return gameIndices; }
        @JsonProperty("game_indices")
        public void setGameIndices(GameIndex[] value) { this.gameIndices = value; }

        @JsonProperty("height")
        public long getHeight() { return height; }
        @JsonProperty("height")
        public void setHeight(long value) { this.height = value; }

        @JsonProperty("held_items")
        public HeldItem[] getHeldItems() { return heldItems; }
        @JsonProperty("held_items")
        public void setHeldItems(HeldItem[] value) { this.heldItems = value; }

        @JsonProperty("id")
        public long getID() { return id; }
        @JsonProperty("id")
        public void setID(long value) { this.id = value; }

        @JsonProperty("is_default")
        public boolean getIsDefault() { return isDefault; }
        @JsonProperty("is_default")
        public void setIsDefault(boolean value) { this.isDefault = value; }

        @JsonProperty("location_area_encounters")
        public String getLocationAreaEncounters() { return locationAreaEncounters; }
        @JsonProperty("location_area_encounters")
        public void setLocationAreaEncounters(String value) { this.locationAreaEncounters = value; }

        @JsonProperty("moves")
        public Move[] getMoves() { return moves; }
        @JsonProperty("moves")
        public void setMoves(Move[] value) { this.moves = value; }

        @JsonProperty("name")
        public String getName() { return name; }
        @JsonProperty("name")
        public void setName(String value) { this.name = value; }

        @JsonProperty("order")
        public long getOrder() { return order; }
        @JsonProperty("order")
        public void setOrder(long value) { this.order = value; }

        @JsonProperty("past_abilities")
        public Object[] getPastAbilities() { return pastAbilities; }
        @JsonProperty("past_abilities")
        public void setPastAbilities(Object[] value) { this.pastAbilities = value; }

        @JsonProperty("past_types")
        public Object[] getPastTypes() { return pastTypes; }
        @JsonProperty("past_types")
        public void setPastTypes(Object[] value) { this.pastTypes = value; }

        @JsonProperty("species")
        public Species getSpecies() { return species; }
        @JsonProperty("species")
        public void setSpecies(Species value) { this.species = value; }

        @JsonProperty("sprites")
        public Sprites getSprites() { return sprites; }
        @JsonProperty("sprites")
        public void setSprites(Sprites value) { this.sprites = value; }

        @JsonProperty("stats")
        public Stat[] getStats() { return stats; }
        @JsonProperty("stats")
        public void setStats(Stat[] value) { this.stats = value; }

        @JsonProperty("types")
        public Type[] getTypes() { return types; }
        @JsonProperty("types")
        public void setTypes(Type[] value) { this.types = value; }

        @JsonProperty("weight")
        public long getWeight() { return weight; }
        @JsonProperty("weight")
        public void setWeight(long value) { this.weight = value; }
    }

// Ability.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Ability {
        private Species ability;
        private boolean isHidden;
        private long slot;

        @JsonProperty("ability")
        public Species getAbility() { return ability; }
        @JsonProperty("ability")
        public void setAbility(Species value) { this.ability = value; }

        @JsonProperty("is_hidden")
        public boolean getIsHidden() { return isHidden; }
        @JsonProperty("is_hidden")
        public void setIsHidden(boolean value) { this.isHidden = value; }

        @JsonProperty("slot")
        public long getSlot() { return slot; }
        @JsonProperty("slot")
        public void setSlot(long value) { this.slot = value; }
    }

// Species.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Species {
        private String name;
        private String url;

        @JsonProperty("name")
        public String getName() { return name; }
        @JsonProperty("name")
        public void setName(String value) { this.name = value; }

        @JsonProperty("url")
        public String getURL() { return url; }
        @JsonProperty("url")
        public void setURL(String value) { this.url = value; }
    }

// GameIndex.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GameIndex {
        private long gameIndex;
        private Species version;

        @JsonProperty("game_index")
        public long getGameIndex() { return gameIndex; }
        @JsonProperty("game_index")
        public void setGameIndex(long value) { this.gameIndex = value; }

        @JsonProperty("version")
        public Species getVersion() { return version; }
        @JsonProperty("version")
        public void setVersion(Species value) { this.version = value; }
    }

// HeldItem.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class HeldItem {
        private Species item;
        private VersionDetail[] versionDetails;

        @JsonProperty("item")
        public Species getItem() { return item; }
        @JsonProperty("item")
        public void setItem(Species value) { this.item = value; }

        @JsonProperty("version_details")
        public VersionDetail[] getVersionDetails() { return versionDetails; }
        @JsonProperty("version_details")
        public void setVersionDetails(VersionDetail[] value) { this.versionDetails = value; }
    }

// VersionDetail.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class VersionDetail {
        private long rarity;
        private Species version;

        @JsonProperty("rarity")
        public long getRarity() { return rarity; }
        @JsonProperty("rarity")
        public void setRarity(long value) { this.rarity = value; }

        @JsonProperty("version")
        public Species getVersion() { return version; }
        @JsonProperty("version")
        public void setVersion(Species value) { this.version = value; }
    }

// Move.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Move {
        private Species move;
        private VersionGroupDetail[] versionGroupDetails;

        @JsonProperty("move")
        public Species getMove() { return move; }
        @JsonProperty("move")
        public void setMove(Species value) { this.move = value; }

        @JsonProperty("version_group_details")
        public VersionGroupDetail[] getVersionGroupDetails() { return versionGroupDetails; }
        @JsonProperty("version_group_details")
        public void setVersionGroupDetails(VersionGroupDetail[] value) { this.versionGroupDetails = value; }
    }

// VersionGroupDetail.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class VersionGroupDetail {
        private long levelLearnedAt;
        private Species moveLearnMethod;
        private Species versionGroup;

        @JsonProperty("level_learned_at")
        public long getLevelLearnedAt() { return levelLearnedAt; }
        @JsonProperty("level_learned_at")
        public void setLevelLearnedAt(long value) { this.levelLearnedAt = value; }

        @JsonProperty("move_learn_method")
        public Species getMoveLearnMethod() { return moveLearnMethod; }
        @JsonProperty("move_learn_method")
        public void setMoveLearnMethod(Species value) { this.moveLearnMethod = value; }

        @JsonProperty("version_group")
        public Species getVersionGroup() { return versionGroup; }
        @JsonProperty("version_group")
        public void setVersionGroup(Species value) { this.versionGroup = value; }
    }

// GenerationV.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GenerationV {
        private Sprites blackWhite;

        @JsonProperty("black-white")
        public Sprites getBlackWhite() { return blackWhite; }
        @JsonProperty("black-white")
        public void setBlackWhite(Sprites value) { this.blackWhite = value; }
    }

// GenerationIv.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GenerationIv {
        private Sprites diamondPearl;
        private Sprites heartgoldSoulsilver;
        private Sprites platinum;

        @JsonProperty("diamond-pearl")
        public Sprites getDiamondPearl() { return diamondPearl; }
        @JsonProperty("diamond-pearl")
        public void setDiamondPearl(Sprites value) { this.diamondPearl = value; }

        @JsonProperty("heartgold-soulsilver")
        public Sprites getHeartgoldSoulsilver() { return heartgoldSoulsilver; }
        @JsonProperty("heartgold-soulsilver")
        public void setHeartgoldSoulsilver(Sprites value) { this.heartgoldSoulsilver = value; }

        @JsonProperty("platinum")
        public Sprites getPlatinum() { return platinum; }
        @JsonProperty("platinum")
        public void setPlatinum(Sprites value) { this.platinum = value; }
    }

// Versions.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

    public class Versions {
        private GenerationI generationI;
        private GenerationIi generationIi;
        private GenerationIii generationIii;
        private GenerationIv generationIv;
        private GenerationV generationV;
        private Map<String, Home> generationVi;
        private GenerationVii generationVii;
        private GenerationViii generationViii;

        @JsonProperty("generation-i")
        public GenerationI getGenerationI() { return generationI; }
        @JsonProperty("generation-i")
        public void setGenerationI(GenerationI value) { this.generationI = value; }

        @JsonProperty("generation-ii")
        public GenerationIi getGenerationIi() { return generationIi; }
        @JsonProperty("generation-ii")
        public void setGenerationIi(GenerationIi value) { this.generationIi = value; }

        @JsonProperty("generation-iii")
        public GenerationIii getGenerationIii() { return generationIii; }
        @JsonProperty("generation-iii")
        public void setGenerationIii(GenerationIii value) { this.generationIii = value; }

        @JsonProperty("generation-iv")
        public GenerationIv getGenerationIv() { return generationIv; }
        @JsonProperty("generation-iv")
        public void setGenerationIv(GenerationIv value) { this.generationIv = value; }

        @JsonProperty("generation-v")
        public GenerationV getGenerationV() { return generationV; }
        @JsonProperty("generation-v")
        public void setGenerationV(GenerationV value) { this.generationV = value; }

        @JsonProperty("generation-vi")
        public Map<String, Home> getGenerationVi() { return generationVi; }
        @JsonProperty("generation-vi")
        public void setGenerationVi(Map<String, Home> value) { this.generationVi = value; }

        @JsonProperty("generation-vii")
        public GenerationVii getGenerationVii() { return generationVii; }
        @JsonProperty("generation-vii")
        public void setGenerationVii(GenerationVii value) { this.generationVii = value; }

        @JsonProperty("generation-viii")
        public GenerationViii getGenerationViii() { return generationViii; }
        @JsonProperty("generation-viii")
        public void setGenerationViii(GenerationViii value) { this.generationViii = value; }
    }

// Sprites.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Sprites {
        private String backDefault;
        private Object backFemale;
        private String backShiny;
        private Object backShinyFemale;
        private String frontDefault;
        private Object frontFemale;
        private String frontShiny;
        private Object frontShinyFemale;
        private Other other;
        private Versions versions;
        private Sprites animated;

        @JsonProperty("back_default")
        public String getBackDefault() { return backDefault; }
        @JsonProperty("back_default")
        public void setBackDefault(String value) { this.backDefault = value; }

        @JsonProperty("back_female")
        public Object getBackFemale() { return backFemale; }
        @JsonProperty("back_female")
        public void setBackFemale(Object value) { this.backFemale = value; }

        @JsonProperty("back_shiny")
        public String getBackShiny() { return backShiny; }
        @JsonProperty("back_shiny")
        public void setBackShiny(String value) { this.backShiny = value; }

        @JsonProperty("back_shiny_female")
        public Object getBackShinyFemale() { return backShinyFemale; }
        @JsonProperty("back_shiny_female")
        public void setBackShinyFemale(Object value) { this.backShinyFemale = value; }

        @JsonProperty("front_default")
        public String getFrontDefault() { return frontDefault; }
        @JsonProperty("front_default")
        public void setFrontDefault(String value) { this.frontDefault = value; }

        @JsonProperty("front_female")
        public Object getFrontFemale() { return frontFemale; }
        @JsonProperty("front_female")
        public void setFrontFemale(Object value) { this.frontFemale = value; }

        @JsonProperty("front_shiny")
        public String getFrontShiny() { return frontShiny; }
        @JsonProperty("front_shiny")
        public void setFrontShiny(String value) { this.frontShiny = value; }

        @JsonProperty("front_shiny_female")
        public Object getFrontShinyFemale() { return frontShinyFemale; }
        @JsonProperty("front_shiny_female")
        public void setFrontShinyFemale(Object value) { this.frontShinyFemale = value; }

        @JsonProperty("other")
        public Other getOther() { return other; }
        @JsonProperty("other")
        public void setOther(Other value) { this.other = value; }

        @JsonProperty("versions")
        public Versions getVersions() { return versions; }
        @JsonProperty("versions")
        public void setVersions(Versions value) { this.versions = value; }

        @JsonProperty("animated")
        public Sprites getAnimated() { return animated; }
        @JsonProperty("animated")
        public void setAnimated(Sprites value) { this.animated = value; }
    }

// GenerationI.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GenerationI {
        private RedBlue redBlue;
        private RedBlue yellow;

        @JsonProperty("red-blue")
        public RedBlue getRedBlue() { return redBlue; }
        @JsonProperty("red-blue")
        public void setRedBlue(RedBlue value) { this.redBlue = value; }

        @JsonProperty("yellow")
        public RedBlue getYellow() { return yellow; }
        @JsonProperty("yellow")
        public void setYellow(RedBlue value) { this.yellow = value; }
    }

// RedBlue.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class RedBlue {
        private String backDefault;
        private String backGray;
        private String backTransparent;
        private String frontDefault;
        private String frontGray;
        private String frontTransparent;

        @JsonProperty("back_default")
        public String getBackDefault() { return backDefault; }
        @JsonProperty("back_default")
        public void setBackDefault(String value) { this.backDefault = value; }

        @JsonProperty("back_gray")
        public String getBackGray() { return backGray; }
        @JsonProperty("back_gray")
        public void setBackGray(String value) { this.backGray = value; }

        @JsonProperty("back_transparent")
        public String getBackTransparent() { return backTransparent; }
        @JsonProperty("back_transparent")
        public void setBackTransparent(String value) { this.backTransparent = value; }

        @JsonProperty("front_default")
        public String getFrontDefault() { return frontDefault; }
        @JsonProperty("front_default")
        public void setFrontDefault(String value) { this.frontDefault = value; }

        @JsonProperty("front_gray")
        public String getFrontGray() { return frontGray; }
        @JsonProperty("front_gray")
        public void setFrontGray(String value) { this.frontGray = value; }

        @JsonProperty("front_transparent")
        public String getFrontTransparent() { return frontTransparent; }
        @JsonProperty("front_transparent")
        public void setFrontTransparent(String value) { this.frontTransparent = value; }
    }

// GenerationIi.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GenerationIi {
        private Crystal crystal;
        private Gold gold;
        private Gold silver;

        @JsonProperty("crystal")
        public Crystal getCrystal() { return crystal; }
        @JsonProperty("crystal")
        public void setCrystal(Crystal value) { this.crystal = value; }

        @JsonProperty("gold")
        public Gold getGold() { return gold; }
        @JsonProperty("gold")
        public void setGold(Gold value) { this.gold = value; }

        @JsonProperty("silver")
        public Gold getSilver() { return silver; }
        @JsonProperty("silver")
        public void setSilver(Gold value) { this.silver = value; }
    }

// Crystal.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Crystal {
        private String backDefault;
        private String backShiny;
        private String backShinyTransparent;
        private String backTransparent;
        private String frontDefault;
        private String frontShiny;
        private String frontShinyTransparent;
        private String frontTransparent;

        @JsonProperty("back_default")
        public String getBackDefault() { return backDefault; }
        @JsonProperty("back_default")
        public void setBackDefault(String value) { this.backDefault = value; }

        @JsonProperty("back_shiny")
        public String getBackShiny() { return backShiny; }
        @JsonProperty("back_shiny")
        public void setBackShiny(String value) { this.backShiny = value; }

        @JsonProperty("back_shiny_transparent")
        public String getBackShinyTransparent() { return backShinyTransparent; }
        @JsonProperty("back_shiny_transparent")
        public void setBackShinyTransparent(String value) { this.backShinyTransparent = value; }

        @JsonProperty("back_transparent")
        public String getBackTransparent() { return backTransparent; }
        @JsonProperty("back_transparent")
        public void setBackTransparent(String value) { this.backTransparent = value; }

        @JsonProperty("front_default")
        public String getFrontDefault() { return frontDefault; }
        @JsonProperty("front_default")
        public void setFrontDefault(String value) { this.frontDefault = value; }

        @JsonProperty("front_shiny")
        public String getFrontShiny() { return frontShiny; }
        @JsonProperty("front_shiny")
        public void setFrontShiny(String value) { this.frontShiny = value; }

        @JsonProperty("front_shiny_transparent")
        public String getFrontShinyTransparent() { return frontShinyTransparent; }
        @JsonProperty("front_shiny_transparent")
        public void setFrontShinyTransparent(String value) { this.frontShinyTransparent = value; }

        @JsonProperty("front_transparent")
        public String getFrontTransparent() { return frontTransparent; }
        @JsonProperty("front_transparent")
        public void setFrontTransparent(String value) { this.frontTransparent = value; }
    }

// Gold.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Gold {
        private String backDefault;
        private String backShiny;
        private String frontDefault;
        private String frontShiny;
        private String frontTransparent;

        @JsonProperty("back_default")
        public String getBackDefault() { return backDefault; }
        @JsonProperty("back_default")
        public void setBackDefault(String value) { this.backDefault = value; }

        @JsonProperty("back_shiny")
        public String getBackShiny() { return backShiny; }
        @JsonProperty("back_shiny")
        public void setBackShiny(String value) { this.backShiny = value; }

        @JsonProperty("front_default")
        public String getFrontDefault() { return frontDefault; }
        @JsonProperty("front_default")
        public void setFrontDefault(String value) { this.frontDefault = value; }

        @JsonProperty("front_shiny")
        public String getFrontShiny() { return frontShiny; }
        @JsonProperty("front_shiny")
        public void setFrontShiny(String value) { this.frontShiny = value; }

        @JsonProperty("front_transparent")
        public String getFrontTransparent() { return frontTransparent; }
        @JsonProperty("front_transparent")
        public void setFrontTransparent(String value) { this.frontTransparent = value; }
    }

// GenerationIii.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GenerationIii {
        private OfficialArtwork emerald;
        private Gold fireredLeafgreen;
        private Gold rubySapphire;

        @JsonProperty("emerald")
        public OfficialArtwork getEmerald() { return emerald; }
        @JsonProperty("emerald")
        public void setEmerald(OfficialArtwork value) { this.emerald = value; }

        @JsonProperty("firered-leafgreen")
        public Gold getFireredLeafgreen() { return fireredLeafgreen; }
        @JsonProperty("firered-leafgreen")
        public void setFireredLeafgreen(Gold value) { this.fireredLeafgreen = value; }

        @JsonProperty("ruby-sapphire")
        public Gold getRubySapphire() { return rubySapphire; }
        @JsonProperty("ruby-sapphire")
        public void setRubySapphire(Gold value) { this.rubySapphire = value; }
    }

// OfficialArtwork.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class OfficialArtwork {
        private String frontDefault;
        private String frontShiny;

        @JsonProperty("front_default")
        public String getFrontDefault() { return frontDefault; }
        @JsonProperty("front_default")
        public void setFrontDefault(String value) { this.frontDefault = value; }

        @JsonProperty("front_shiny")
        public String getFrontShiny() { return frontShiny; }
        @JsonProperty("front_shiny")
        public void setFrontShiny(String value) { this.frontShiny = value; }
    }

// Home.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Home {
        private String frontDefault;
        private Object frontFemale;
        private String frontShiny;
        private Object frontShinyFemale;

        @JsonProperty("front_default")
        public String getFrontDefault() { return frontDefault; }
        @JsonProperty("front_default")
        public void setFrontDefault(String value) { this.frontDefault = value; }

        @JsonProperty("front_female")
        public Object getFrontFemale() { return frontFemale; }
        @JsonProperty("front_female")
        public void setFrontFemale(Object value) { this.frontFemale = value; }

        @JsonProperty("front_shiny")
        public String getFrontShiny() { return frontShiny; }
        @JsonProperty("front_shiny")
        public void setFrontShiny(String value) { this.frontShiny = value; }

        @JsonProperty("front_shiny_female")
        public Object getFrontShinyFemale() { return frontShinyFemale; }
        @JsonProperty("front_shiny_female")
        public void setFrontShinyFemale(Object value) { this.frontShinyFemale = value; }
    }

// GenerationVii.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GenerationVii {
        private DreamWorld icons;
        private Home ultraSunUltraMoon;

        @JsonProperty("icons")
        public DreamWorld getIcons() { return icons; }
        @JsonProperty("icons")
        public void setIcons(DreamWorld value) { this.icons = value; }

        @JsonProperty("ultra-sun-ultra-moon")
        public Home getUltraSunUltraMoon() { return ultraSunUltraMoon; }
        @JsonProperty("ultra-sun-ultra-moon")
        public void setUltraSunUltraMoon(Home value) { this.ultraSunUltraMoon = value; }
    }

// DreamWorld.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class DreamWorld {
        private String frontDefault;
        private Object frontFemale;

        @JsonProperty("front_default")
        public String getFrontDefault() { return frontDefault; }
        @JsonProperty("front_default")
        public void setFrontDefault(String value) { this.frontDefault = value; }

        @JsonProperty("front_female")
        public Object getFrontFemale() { return frontFemale; }
        @JsonProperty("front_female")
        public void setFrontFemale(Object value) { this.frontFemale = value; }
    }

// GenerationViii.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class GenerationViii {
        private DreamWorld icons;

        @JsonProperty("icons")
        public DreamWorld getIcons() { return icons; }
        @JsonProperty("icons")
        public void setIcons(DreamWorld value) { this.icons = value; }
    }

// Other.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Other {
        private DreamWorld dreamWorld;
        private Home home;
        private OfficialArtwork officialArtwork;

        @JsonProperty("dream_world")
        public DreamWorld getDreamWorld() { return dreamWorld; }
        @JsonProperty("dream_world")
        public void setDreamWorld(DreamWorld value) { this.dreamWorld = value; }

        @JsonProperty("home")
        public Home getHome() { return home; }
        @JsonProperty("home")
        public void setHome(Home value) { this.home = value; }

        @JsonProperty("official-artwork")
        public OfficialArtwork getOfficialArtwork() { return officialArtwork; }
        @JsonProperty("official-artwork")
        public void setOfficialArtwork(OfficialArtwork value) { this.officialArtwork = value; }
    }

// Stat.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Stat {
        private long baseStat;
        private long effort;
        private Species stat;

        @JsonProperty("base_stat")
        public long getBaseStat() { return baseStat; }
        @JsonProperty("base_stat")
        public void setBaseStat(long value) { this.baseStat = value; }

        @JsonProperty("effort")
        public long getEffort() { return effort; }
        @JsonProperty("effort")
        public void setEffort(long value) { this.effort = value; }

        @JsonProperty("stat")
        public Species getStat() { return stat; }
        @JsonProperty("stat")
        public void setStat(Species value) { this.stat = value; }
    }

// Type.java

package io.quicktype;

import com.fasterxml.jackson.annotation.*;

    public class Type {
        private long slot;
        private Species type;

        @JsonProperty("slot")
        public long getSlot() { return slot; }
        @JsonProperty("slot")
        public void setSlot(long value) { this.slot = value; }

        @JsonProperty("type")
        public Species getType() { return type; }
        @JsonProperty("type")
        public void setType(Species value) { this.type = value; }
    }


