package com.nanonaitor.forgedarmor.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nanonaitor.forgedarmor.ArmorForgeMenu;
import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentSlot;
import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmor;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Game tests for the Armor Forge, attachment data and asset completeness.
 * Run with {@code ./gradlew runGameTestServer}; they are only registered when game tests are enabled.
 */
@GameTestHolder(ForgedArmor.ID)
@PrefixGameTestTemplate(false)
public final class ForgedArmorGameTests {
    private static final String TEMPLATE = "empty";

    private ForgedArmorGameTests() {}

    private static ArmorForgeMenu forge(GameTestHelper helper, Player player) {
        return new ArmorForgeMenu(0, player.getInventory(), helper.absolutePos(BlockPos.ZERO));
    }

    private static ItemStack part(AttachmentType type) {
        return AttachmentData.attachmentStack(type);
    }

    private static void takeResult(ArmorForgeMenu menu, Player player) {
        menu.clicked(2, 0, ClickType.QUICK_MOVE, player);
    }

    private static int count(Player player, Item item) {
        return player.getInventory().countItem(item);
    }

    private static ItemStack find(Player player, Item item) {
        for (ItemStack stack : player.getInventory().items) if (stack.is(item)) return stack;
        return ItemStack.EMPTY;
    }

    private static void check(GameTestHelper helper, boolean condition, String message) {
        if (!condition) helper.fail(message);
    }

    @GameTest(template = TEMPLATE)
    public static void forgeAttachesAndSwaps(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        ArmorForgeMenu menu = forge(helper, player);
        menu.getSlot(0).set(new ItemStack(Items.DIAMOND_HELMET));
        menu.getSlot(1).set(part(AttachmentType.RAVAGER_HORN));
        check(helper, AttachmentData.has(menu.getSlot(2).getItem(), AttachmentType.RAVAGER_HORN),
                "helmet + ravager horn should preview a horned helmet");
        takeResult(menu, player);
        ItemStack forged = find(player, Items.DIAMOND_HELMET);
        check(helper, AttachmentData.has(forged, AttachmentType.RAVAGER_HORN), "taking the result should give the forged helmet");
        check(helper, menu.getSlot(0).getItem().isEmpty() && menu.getSlot(1).getItem().isEmpty(), "inputs should be used up");

        // Same position: the hoglin horn replaces the ravager horn, which goes back to the player.
        menu.getSlot(0).set(forged.split(1));
        menu.getSlot(1).set(part(AttachmentType.HOGLIN_HORN));
        takeResult(menu, player);
        check(helper, AttachmentData.has(find(player, Items.DIAMOND_HELMET), AttachmentType.HOGLIN_HORN),
                "the hoglin horn should take the Horns position");
        check(helper, count(player, part(AttachmentType.RAVAGER_HORN).getItem()) == 1, "the replaced ravager horn should be returned");
        helper.succeed();
    }

    @GameTest(template = TEMPLATE)
    public static void forgeRejectsWrongArmorPiece(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        ArmorForgeMenu menu = forge(helper, player);
        menu.getSlot(0).set(new ItemStack(Items.IRON_BOOTS));
        menu.getSlot(1).set(part(AttachmentType.RAVAGER_HORN));
        check(helper, menu.getSlot(2).getItem().isEmpty(), "a helmet attachment must not fit boots");
        helper.succeed();
    }

    @GameTest(template = TEMPLATE)
    public static void forgeRemovesAllAttachments(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        ArmorForgeMenu menu = forge(helper, player);
        ItemStack boots = AttachmentData.attach(AttachmentData.attach(new ItemStack(Items.IRON_BOOTS),
                AttachmentType.FROG_LEGS), AttachmentType.STRIDER_FEET);
        boots.enchant(net.minecraft.world.item.enchantment.Enchantments.FALL_PROTECTION, 2);
        menu.getSlot(0).set(boots);
        check(helper, menu.isRemoving(), "armor on its own should switch the forge to removal");
        takeResult(menu, player);
        ItemStack bare = find(player, Items.IRON_BOOTS);
        check(helper, !bare.isEmpty() && AttachmentData.attached(bare).isEmpty(), "the boots should come out bare");
        check(helper, bare.isEnchanted(), "removing attachments must keep enchantments");
        check(helper, count(player, part(AttachmentType.FROG_LEGS).getItem()) == 1
                && count(player, part(AttachmentType.STRIDER_FEET).getItem()) == 1, "both attachments should be returned");
        helper.succeed();
    }

    @GameTest(template = TEMPLATE)
    public static void legacyTurtleShellMovesToBack(GameTestHelper helper) {
        ItemStack chest = new ItemStack(Items.IRON_CHESTPLATE);
        CompoundTag attachments = chest.getOrCreateTagElement("NanonaitorForgedArmor");
        attachments.putString(AttachmentSlot.CHEST.key(), AttachmentType.TURTLE_SHELL.id());
        check(helper, AttachmentType.TURTLE_SHELL.id().equals(AttachmentData.getId(chest, AttachmentSlot.BACK)),
                "an old Chest-slot Turtle Shell should read as Back");
        check(helper, AttachmentData.getId(chest, AttachmentSlot.CHEST).isEmpty(), "and must not also fill Chest");
        ItemStack forged = AttachmentData.attach(chest, AttachmentType.WITCH_HEART);
        check(helper, AttachmentData.has(forged, AttachmentType.TURTLE_SHELL) && AttachmentData.has(forged, AttachmentType.WITCH_HEART),
                "forging a heart onto it should keep the shell on the Back");
        helper.succeed();
    }

    /** Every item this mod registers needs a model, the textures that model names, and an English name. */
    @GameTest(template = TEMPLATE)
    public static void everyItemHasModelTextureAndName(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();
        JsonObject lang = json(asset("lang/en_us.json"), problems);
        for (Map.Entry<net.minecraft.resources.ResourceKey<Item>, Item> entry : ForgeRegistries.ITEMS.getEntries()) {
            ResourceLocation id = entry.getKey().location();
            if (!id.getNamespace().equals(ForgedArmor.ID)) continue;
            String name = id.getPath();
            if (lang != null && !lang.has("item." + ForgedArmor.ID + "." + name) && !lang.has("block." + ForgedArmor.ID + "." + name))
                problems.add("no English name for " + id);
            checkModel("models/item/" + name + ".json", problems);
        }
        if (!problems.isEmpty()) helper.fail(String.join("; ", problems));
        helper.succeed();
    }

    private static void checkModel(String path, List<String> problems) {
        JsonObject model = json(asset(path), problems);
        if (model == null) return;
        if (model.has("parent")) {
            ResourceLocation parent = new ResourceLocation(model.get("parent").getAsString());
            if (parent.getNamespace().equals(ForgedArmor.ID)) checkModel("models/" + parent.getPath() + ".json", problems);
        }
        if (!model.has("textures")) return;
        for (Map.Entry<String, JsonElement> texture : model.getAsJsonObject("textures").entrySet()) {
            String value = texture.getValue().getAsString();
            if (value.startsWith("#")) continue;
            ResourceLocation location = new ResourceLocation(value);
            if (location.getNamespace().equals(ForgedArmor.ID) && !Files.exists(asset("textures/" + location.getPath() + ".png")))
                problems.add(path + " uses missing texture " + location);
        }
    }

    private static Path asset(String path) {
        return ModList.get().getModFileById(ForgedArmor.ID).getFile().findResource("assets", ForgedArmor.ID, path);
    }

    private static JsonObject json(Path path, List<String> problems) {
        if (!Files.exists(path)) {
            problems.add("missing " + path.getFileName());
            return null;
        }
        try {
            return JsonParser.parseString(Files.readString(path)).getAsJsonObject();
        } catch (IOException | RuntimeException e) {
            problems.add("unreadable " + path.getFileName() + ": " + e.getMessage());
            return null;
        }
    }
}
